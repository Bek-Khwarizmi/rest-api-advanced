package com.epam.esm.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
public class GiftCertificateDto {

    private String name;

    private String description;

    private BigDecimal price;

    private Integer duration;

    private Set<TagDto> tags;

}
