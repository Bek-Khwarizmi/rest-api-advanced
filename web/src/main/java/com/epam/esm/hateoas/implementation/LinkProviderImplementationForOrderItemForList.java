package com.epam.esm.hateoas.implementation;

import com.epam.esm.controller.OrderController;
import com.epam.esm.dto.response.OrderItemForList;
import com.epam.esm.hateoas.LinkProvider;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component("link_for_order_list")
public class LinkProviderImplementationForOrderItemForList implements LinkProvider<OrderItemForList> {

    @SneakyThrows
    @Override
    public void link(OrderItemForList orderItemForList, String rel) {
        orderItemForList.add(linkTo(methodOn(OrderController.class).getById(orderItemForList.getId())).withRel("GET"));
    }
}