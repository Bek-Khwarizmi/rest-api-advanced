package com.epam.esm.hateoas.implementation;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.dto.request.GiftCertificateDto;
import com.epam.esm.dto.request.TagDto;
import com.epam.esm.dto.response.GiftCertificateItem;
import com.epam.esm.dto.response.TagItem;
import com.epam.esm.exception.GeneralPersistenceException;
import com.epam.esm.exception.IncorrectParamException;
import com.epam.esm.hateoas.LinkProvider;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component("link_for_giftCertificate")
public class LinkProviderImplementationForGiftCertificate implements LinkProvider<GiftCertificateItem> {

    private final LinkProvider<TagItem> tagLink;

    public LinkProviderImplementationForGiftCertificate(LinkProvider<TagItem> tagLink) {
        this.tagLink = tagLink;
    }

    @SneakyThrows
    @Override
    public void link(GiftCertificateItem item, String rel) {
        switch(rel){
            case "GET":

            case "POST":

            case "PUT":
                get(item);
                update(item);
                delete(item);
                break;
            case "DELETE":
                list(item);
                break;
        }
        /*
        This method will not throw any exception because it is called after service methods has completed its work successfully
        so if service has no issue with this gift certificate item then there will not be any issue as here we access only
        available gift certificate dto(s)!
        */
        addLinkToTags(item.getTags());
    }

    private void get(GiftCertificateItem item) throws GeneralPersistenceException, IncorrectParamException {
        item.add(linkTo(methodOn(GiftCertificateController.class).getById(item.getId())).withRel("GET"));
    }

    private void update(GiftCertificateItem item) throws GeneralPersistenceException, IncorrectParamException {
        GiftCertificateDto dto = fromGiftCertificateItem(item);

        item.add(linkTo(methodOn(GiftCertificateController.class).update(item.getId(), dto)).withRel("PUT"));
    }

    private void delete(GiftCertificateItem item) throws GeneralPersistenceException, IncorrectParamException {
        item.add(linkTo(methodOn(GiftCertificateController.class).delete(item.getId())).withRel("DELETE"));
    }

    private void list(GiftCertificateItem item) throws IncorrectParamException {
        GiftCertificateDto dto = fromGiftCertificateItem(item);

        item.add(linkTo(methodOn(GiftCertificateController.class).create(dto)).withRel("LIST"));
    }

    private void addLinkToTags(Set<TagItem> tags){
        if (tags!=null){
            tags.forEach(tag -> tagLink.link(tag, "GET"));
        }
    }

    /*
    This method is used for conversion for Gift Certificate from Item to Dto
     */
    private GiftCertificateDto fromGiftCertificateItem(GiftCertificateItem item){
        GiftCertificateDto dto = new GiftCertificateDto();
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setDuration(item.getDuration());
        dto.setPrice(item.getPrice());
        dto.setTags(
                item.getTags().stream().map(tagItem -> {
                    TagDto tagDto = new TagDto();
                    tagDto.setName(tagItem.getName());
                    return tagDto;
                }).collect(Collectors.toSet())
        );
        return dto;
    }
}
