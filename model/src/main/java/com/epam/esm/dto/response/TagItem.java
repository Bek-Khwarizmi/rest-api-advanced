package com.epam.esm.dto.response;


import com.epam.esm.entity.Tag;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

@Getter
@Setter
public class TagItem extends RepresentationModel<TagItem> {

    private Long id;

    private String name;

    public static TagItem fromTag(Tag tag){
        TagItem item = new TagItem();
        item.setId(tag.getId());
        item.setName(tag.getName());
        return item;
    }

    /*
    In my assumption tags must be unique. So I use Set<Tag> in relation with (within) Gift Certificates.
    And Set uses hashcode() to find a bucket then if bucket is not empty it use equals() to verify uniqueness.
    So I overrode below methods
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TagItem)) return false;
        if (!super.equals(o)) return false;
        TagItem tagItem = (TagItem) o;
        return getId().equals(tagItem.getId()) && getName().equals(tagItem.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getName());
    }
}
