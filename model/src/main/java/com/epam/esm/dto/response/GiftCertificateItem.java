package com.epam.esm.dto.response;

import com.epam.esm.entity.GiftCertificate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class GiftCertificateItem  extends RepresentationModel<GiftCertificateItem> {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer duration;

    private String createDate;

    private String lastUpdateDate;

    private Set<TagItem> tags;

    public static GiftCertificateItem fromGiftCertificate(GiftCertificate giftCertificate){
        GiftCertificateItem item = new GiftCertificateItem();
        item.setId(giftCertificate.getId());
        item.setName(giftCertificate.getName());
        item.setDescription(giftCertificate.getDescription());
        item.setPrice(giftCertificate.getPrice());
        item.setDuration(giftCertificate.getDuration());
        item.setCreateDate(giftCertificate.getCreateDate());
        item.setLastUpdateDate(giftCertificate.getLastUpdateDate());
        item.setTags(
                giftCertificate.getTags()
                        .stream()
                        .map(TagItem::fromTag)
                        .collect(Collectors.toSet())
        );
        return item;
    }
}
