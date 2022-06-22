package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateRepository{


    private final String FIND_ALL =
            "SELECT gc FROM GiftCertificate gc";
    private final String DELETE =
            "DELETE FROM GiftCertificate WHERE id = :id";
    private final String FIND_BY_PART =
            "SELECT gc FROM GiftCertificate gc " +
            "WHERE gc.name LIKE '%' ||:part|| '%' OR gc.description LIKE '%'||:part||'%'";

    @PersistenceContext
    EntityManager entityManager;


    public Optional<GiftCertificate> findById(Long id){
        GiftCertificate certificateEntity = entityManager.find(GiftCertificate.class, id);
        if(certificateEntity != null)
            return Optional.of(certificateEntity);
        return Optional.empty();
    }

    public Page<GiftCertificate> findAll(String sort, Pageable pageable){
        List<GiftCertificate> giftCertificates =  entityManager
                .createQuery(FIND_ALL+sort(sort), GiftCertificate.class)
                .getResultList();
        return new PageImpl<>(giftCertificates, pageable, giftCertificates.size());
    }

    public Page<GiftCertificate> findByPartOfNameOrDescription(String part, String sort, Pageable pageable){
        List<GiftCertificate> giftCertificates = entityManager
                .createQuery(FIND_BY_PART+sort(sort), GiftCertificate.class)
                .setParameter("part", part)
                .getResultList();
        return new PageImpl<>(giftCertificates, pageable, giftCertificates.size());
    }

    public GiftCertificate save(GiftCertificate giftCertificate){
        entityManager.persist(giftCertificate);
        if(giftCertificate.getId() != null)
            return giftCertificate;
        return null;
    }

    public void delete(GiftCertificate giftCertificate){
        entityManager
                .createQuery(DELETE)
                .setParameter("id", giftCertificate.getId())
                .executeUpdate();
    }

    private String sort(String sort){
        if(sort == null){
            return "";
        }
        switch(sort){
            case "name-asc":
                return " ORDER BY gc.name";
            case "name-desc":
                return " ORDER BY gc.name DESC";
            case "date-asc":
                return " ORDER BY gc.lastUpdateDate";
            case "date-desc":
                return " ORDER BY gc.lastUpdateDate DESC";
            case "name-asc-date-asc":
                return " ORDER BY gc.name ASC, gc.lastUpdateDate ASC";
            case "name-asc-date-desc":
                return " ORDER BY gc.name ASC, gc.lastUpdateDate DESC";
            case "name-desc-date-asc":
                return " ORDER BY gc.name DESC, gc.lastUpdateDate ASC";
            case "name-desc-date-desc":
                return " ORDER BY gc.name DESC, gc.lastUpdateDate DESC";
            case "date-asc-date-asc":
                return " ORDER BY gc.lastUpdateDate ASC, gc.name ASC";
            case "date-asc-name-desc":
                return " ORDER BY gc.lastUpdateDate ASC, gc.name DESC";
            case "date-desc-name-asc":
                return " ORDER BY gc.lastUpdateDate DESC, gc.name ASC";
            case "date-desc-name-desc":
                return " ORDER BY gc.lastUpdateDate DESC, gc.name DESC";
            default:
                return "";
        }
    }

}
