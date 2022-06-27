package com.epam.esm.entity;

import com.epam.esm.entity.entityListeners.GiftCertificateEntityListener;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/*
I put validation in Service layer so in entity
I did not write any constraints to fields
 */

@Entity
@Getter
@Setter
@EntityListeners(GiftCertificateEntityListener.class)
public class GiftCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer duration;

    private String createDate;

    @Column(name = "date")
    private String lastUpdateDate;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Tag> tags;

    @OneToMany(mappedBy = "giftCertificate")
    List<Order> orders;

    public GiftCertificate() {
    }

    public GiftCertificate(
            Long id, String name, String description, BigDecimal price,
            Integer duration, String createDate, String lastUpdateDate, Set<Tag> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tags = tags;
    }
}
