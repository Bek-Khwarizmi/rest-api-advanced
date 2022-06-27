package com.epam.esm.hateoas.implementation;

import com.epam.esm.controller.OrderController;
import com.epam.esm.dto.response.GiftCertificateItem;
import com.epam.esm.dto.response.OrderItemForInfo;
import com.epam.esm.dto.response.UserItem;
import com.epam.esm.hateoas.LinkProvider;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component("link_for_order_info")
public class LinkProviderImplementationForOrderItemForInfo implements LinkProvider<OrderItemForInfo> {

    private final LinkProvider<UserItem> userLink;
    private final LinkProvider<GiftCertificateItem> giftCertificateLink;

    public LinkProviderImplementationForOrderItemForInfo(
            @Qualifier("link_for_user") LinkProvider<UserItem> userLink,
            @Qualifier("link_for_giftCertificate") LinkProvider<GiftCertificateItem> giftCertificateLink) {
        this.userLink = userLink;
        this.giftCertificateLink = giftCertificateLink;
    }

    @SneakyThrows
    @Override
    public void link(OrderItemForInfo orderItemForInfo, String rel) {
        orderItemForInfo.add(linkTo(methodOn(OrderController.class).getById(orderItemForInfo.getId())).withRel("GET"));
        UserItem userItem = orderItemForInfo.getUserItem();
        GiftCertificateItem giftCertificateItem = orderItemForInfo.getGiftCertificateItem();

        userLink.link(userItem, rel);
        giftCertificateLink.link(giftCertificateItem, rel);
    }
}
