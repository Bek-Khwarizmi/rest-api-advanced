package com.epam.esm.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/*
I put validation in Service layer so in entity
I did not write any constraints to fields
 */
/*
I use Set in gift certificate to hold tags that is why
I implemented hashcode() and equals methods() of Object.
Because Set uses them:
    - first it uses hashcode() to find a bucket to tag item, then
    - if bucket is not empty it uses equals() to verify uniqueness.
 */

@Entity
@Getter
@Setter
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<GiftCertificate> giftCertificates;

    public Tag() {
    }

    public Tag(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag)) return false;
        Tag tag = (Tag) o;
        return getId().equals(tag.getId()) && getName().equals(tag.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
