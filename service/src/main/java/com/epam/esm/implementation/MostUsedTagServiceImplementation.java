package com.epam.esm.implementation;

import com.epam.esm.dto.response.TagItem;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.GeneralPersistenceException;
import com.epam.esm.service.MostUsedTagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.exception.GeneralPersistenceExceptionMessageCode.NO_TAG_ENTITY_FOUND;

@Service
public class MostUsedTagServiceImplementation implements MostUsedTagService {

    @PersistenceContext
    EntityManager entityManager;


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

    @Override
    public Page<TagItem> find(Pageable pageable) throws GeneralPersistenceException {
        int size = pageable.getPageSize();
        int page = pageable.getPageNumber();

        List<Tag> tags = entityManager
                .createNativeQuery(query, Tag.class)
                .setMaxResults(size)
                .setFirstResult(page * size)
                .getResultList();

        if (tags.isEmpty()){
            throw new GeneralPersistenceException(NO_TAG_ENTITY_FOUND);
        }

        List<TagItem> tagItems = tags.stream().map(TagItem::fromTag).collect(Collectors.toList());

        return new PageImpl<>(tagItems, pageable, tagItems.size());
    }

}
