package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;


@Repository
public class TagRepository{

    private final String FIND_ALL =
            "SELECT tag FROM Tag tag";

    private final String DELETE_BY_ID =
            "DELETE FROM Tag WHERE id = :id";

    private final String FIND_BY_NAME =
            "SELECT tag FROM Tag tag WHERE tag.name = :name";


    @PersistenceContext
    private EntityManager entityManager;


    public Page<Tag> findAll(Pageable pageable){
        int size = pageable.getPageSize();
        int page = pageable.getPageNumber();
        List<Tag> tags = entityManager
                .createQuery(FIND_ALL, Tag.class)
                .setMaxResults(size)
                .setFirstResult(page * size)
                .getResultList();

        return new PageImpl<>(tags, pageable, tags.size());
    }

    public Optional<Tag> findById(Long id){
        Tag tag = entityManager.find(Tag.class, id);
        if (tag != null){
            return Optional.of(tag);
        }
        return Optional.empty();
    }

    public Optional<Tag> findTagByName(String name){
        try {
            Tag tag = entityManager.createQuery(FIND_BY_NAME, Tag.class)
                    .setParameter("name", name).getSingleResult();
            return Optional.of(tag);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Transactional
    public Tag save(Tag tag){
        entityManager.persist(tag);
        if (tag.getId() != null)
            return tag;
        return null;
    }

    public void delete(Tag tag){
        entityManager
                .createQuery(DELETE_BY_ID)
                .setParameter("id", tag.getId())
                .executeUpdate();
    }

    /*
    Method for widely used tag(s) of a user
     */

    String query =
            "SELECT * FROM tag WHERE tag.id IN " +
                    "( " +
                    "SELECT tags_id FROM " +
                    "( " +
                    "SELECT gift_certificate_id from orders WHERE user_id = " +
                    "( " +
                    "SELECT user_id FROM orders GROUP BY user_id ORDER BY SUM(cost) DESC LIMIT 1 " +
                    ") " +
                    ") AS tableA " +
                    "INNER JOIN gift_certificate_tags ON gift_certificate_tags.gift_certificates_id = tableA.gift_certificate_id " +
                    "GROUP BY tags_id " +
                    "HAVING COUNT(tags_id) = " +    // Below is the same query as above, I used them twice because
                    "( " +                          // I need to return list of most used tags, so first I find number of uses
                    "SELECT MAX(count) FROM " +     // then get tags that has the same amount of uses
                    "( " +
                    "SELECT tags_id, COUNT(tags_id) FROM " +
                    "( " +
                    "SELECT gift_certificate_id from orders WHERE user_id = " +
                    "( " +
                    "SELECT user_id FROM orders GROUP BY user_id ORDER BY SUM(cost) DESC LIMIT 1 " +
                    ") " +
                    ") AS tableA " +
                    "INNER JOIN gift_certificate_tags ON gift_certificate_tags.gift_certificates_id = tableA.gift_certificate_id " +
                    "GROUP BY tags_id " +
                    ") AS tableB " +
                    ") " +
                    ")";


    public Page<Tag> findMostUsedTag(Pageable pageable) {
        int size = pageable.getPageSize();
        int page = pageable.getPageNumber();

        List<Tag> tags = entityManager
                .createNativeQuery(query, Tag.class)
                .setMaxResults(size)
                .setFirstResult(page * size)
                .getResultList();

        return new PageImpl<>(tags, pageable, tags.size());
    }
}