package com.epam.esm.hateoas.implementation;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.request.TagDto;
import com.epam.esm.dto.response.TagItem;
import com.epam.esm.exception.GeneralPersistenceException;
import com.epam.esm.exception.IncorrectParamException;
import com.epam.esm.hateoas.LinkProvider;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component("link_for_tag")
public class LinkProviderImplementationForTag implements LinkProvider<TagItem> {

    @SneakyThrows
    @Override
    public void link(TagItem tagItem, String rel){
        switch(rel){
            case "GET":

            case "POST":
                get(tagItem);
                delete(tagItem);
                break;
            case "DELETE":
                list(tagItem);
                break;
        }
        /*
        This method will not throw any exception because it is called after service methods has completed its work successfully
        so if service has no issue with this tagDto then there will not be any issue as here we access only available tagDto(s)!
        */
    }

    private void get(TagItem tagItem) throws GeneralPersistenceException, IncorrectParamException {
        tagItem.add(linkTo(methodOn(TagController.class).getById(tagItem.getId())).withRel("GET"));
    }

    private void delete(TagItem tagItem) throws GeneralPersistenceException, IncorrectParamException {
        tagItem.add(linkTo(methodOn(TagController.class).delete(tagItem.getId())).withRel("DELETE"));
    }

    private void list(TagItem tagItem) throws GeneralPersistenceException, IncorrectParamException {
        TagDto tagDto = new TagDto();
        tagDto.setName(tagItem.getName());
        tagItem.add(linkTo(methodOn(TagController.class).create(tagDto)).withRel("LIST"));
    }

}
