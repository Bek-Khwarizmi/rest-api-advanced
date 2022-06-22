package com.epam.esm.dto.response;

import com.epam.esm.entity.Order;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

/*
This response model is used to give full information about order
when we get all orders or an order of a user
 */

@Getter
@Setter
public class OrderItemForInfo  extends RepresentationModel<OrderItemForInfo> {

    private Long id;

    private BigDecimal cost;

    private String date;

    private UserItem userItem;

    private GiftCertificateItem giftCertificateItem;

    public static OrderItemForInfo fromOrder(Order order){
        OrderItemForInfo item = new OrderItemForInfo();
        item.setId(order.getId());
        item.setCost(order.getCost());
        item.setDate(order.getDate());
        item.setUserItem(UserItem.fromUser(order.getUser()));
        item.setGiftCertificateItem(GiftCertificateItem.fromGiftCertificate(order.getGiftCertificate()));
        return item;
    }

}
