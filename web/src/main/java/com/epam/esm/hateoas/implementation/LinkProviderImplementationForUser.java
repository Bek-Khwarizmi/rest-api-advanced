package com.epam.esm.hateoas.implementation;

import com.epam.esm.controller.UserController;
import com.epam.esm.dto.response.UserItem;
import com.epam.esm.hateoas.LinkProvider;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component("link_for_user")
public class LinkProviderImplementationForUser implements LinkProvider<UserItem> {

    @SneakyThrows
    @Override
    public void link(UserItem userItem, String rel) {
        userItem.add(linkTo(methodOn(UserController.class).getUserById(userItem.getId())).withRel(rel));
    }
}
