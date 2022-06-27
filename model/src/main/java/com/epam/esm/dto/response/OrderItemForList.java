package com.epam.esm.dto.response;

import com.epam.esm.entity.Order;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

/*
This response model is used when we get list of order(s) of a user.
To see full information about an order we have another model for order info.
 */

@Getter
@Setter
public class OrderItemForList  extends RepresentationModel<OrderItemForList> {

    private Long id;

    public static OrderItemForList fromOrder(Order order){
        OrderItemForList item = new OrderItemForList();
        item.setId(order.getId());
        return item;
    }
}
