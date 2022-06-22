package com.epam.esm.controller;

import com.epam.esm.dto.request.OrderDto;
import com.epam.esm.dto.response.*;
import com.epam.esm.exception.GeneralPersistenceException;
import com.epam.esm.exception.IncorrectParamException;
import com.epam.esm.hateoas.LinkProvider;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Class {@code OrderController} which allows performing CR operations on orders.
 * Annotated by {@link RestController} with no parameters to provide an answer in application/json.
 * Annotated by {@link RequestMapping} with parameter value = "/order".
 * The Class can be accessed by sending request to "/order".
 *
 * @author Bekhzod Kurbonboev
 * @since 1.0
 */
@RestController
@RequestMapping("order")
public class OrderController {

    private final OrderService orderService;
    private final LinkProvider<OrderItemForInfo> orderInfoLink;

    public OrderController(
            OrderService orderService,
            @Qualifier("link_for_order_info") LinkProvider<OrderItemForInfo> orderInfoLink) {
        this.orderService = orderService;
        this.orderInfoLink = orderInfoLink;
    }

    /**
     * Method for saving order in the database
     * @param orderDto that has two fields (:userId) and (:giftCertificateId)
     * @return order item if transaction completes its work successfully
     * @throws GeneralPersistenceException if there is no user that has specified :userId,
     * or if there is no gift certificate that has specified :giftCertificateId
     * @throws IncorrectParamException if one or both of :userId and :giftCertificateId parameters are non-positive
     */
    @PostMapping
    public ResponseEntity create(@RequestBody OrderDto orderDto) throws GeneralPersistenceException, IncorrectParamException {
        OrderItemForInfo orderItemForInfo = orderService.create(orderDto);
        orderInfoLink.link(orderItemForInfo, "GET");

        return ResponseEntity.ok(orderItemForInfo);
    }

    /**
     * Method for retrieving page of orders
     * @param pageable
     * @return page of order(s) if there are order(s) in the database
     * @throws GeneralPersistenceException if there are no order in the database
     */
    @GetMapping
    public ResponseEntity list(Pageable pageable) throws GeneralPersistenceException {
        Page<OrderItemForInfo> orderInfoItems = orderService.list(pageable);
        orderInfoItems.forEach(order -> orderInfoLink.link(order, "GET"));

        return ResponseEntity.ok(orderInfoItems);
    }

    /**
     * Methor for retrieving order that has specified :id
     * @param id
     * @return order if there is an order that has specified :id in the database
     * @throws GeneralPersistenceException if there in no order that has spesified :id
     * @throws IncorrectParamException if :id parameter is non-positive
     */
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id) throws GeneralPersistenceException, IncorrectParamException {
        OrderItemForInfo orderItemForInfo = orderService.getById(id);
        orderInfoLink.link(orderItemForInfo, "GET");

        return ResponseEntity.ok(orderItemForInfo);
    }

}
