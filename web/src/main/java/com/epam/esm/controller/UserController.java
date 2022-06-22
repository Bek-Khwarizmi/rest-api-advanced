package com.epam.esm.controller;

import com.epam.esm.dto.request.UserDto;
import com.epam.esm.dto.response.*;
import com.epam.esm.exception.GeneralPersistenceException;
import com.epam.esm.exception.IncorrectParamException;
import com.epam.esm.hateoas.LinkProvider;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Class {@code UserController} which allows performing R operations on users.
 * Annotated by {@link RestController} with no parameters to provide an answer in application/json.
 * Annotated by {@link RequestMapping} with parameter value = "/user".
 * The Class can be accessed by sending request to "/user".
 *
 * @author Bekhzod Kurbonboev
 * @since 1.0
 */
@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;
    private final LinkProvider<UserItem> userLink;
    private final LinkProvider<OrderItemForInfo> orderInfoLink;
    private final LinkProvider<OrderItemForList> orderListLink;

    public UserController(
            UserService userService,
            @Qualifier("link_for_user") LinkProvider<UserItem> userLink,
            @Qualifier("link_for_order_info") LinkProvider<OrderItemForInfo> orderInfoLink,
            @Qualifier("link_for_order_list") LinkProvider<OrderItemForList> orderListLink) {

        this.userService = userService;
        this.userLink = userLink;
        this.orderInfoLink = orderInfoLink;
        this.orderListLink = orderListLink;
    }

    /**
     * Method for retrieving user specified by :id
     * @param id
     * @return user if there is a user that has specified :id in the database
     * @throws GeneralPersistenceException if there is no tag that has specified :id
     * @throws IncorrectParamException if :id parameter is non-positive
     */
    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable Long id) throws GeneralPersistenceException, IncorrectParamException {
        UserItem userItem = userService.getUserById(id);
        userLink.link(userItem, "GET");

        return ResponseEntity.ok(userItem);
    }

    /**
     * Method for retrieving page of order(s) of user that has specified :id
     * @param id
     * @param pageable
     * @return page of order(s) if specified user has order(s)
     * @throws GeneralPersistenceException  if there is no user that has specified :id or user do not have any order
     * @throws IncorrectParamException if :id parameter is non-positive
     */
    @GetMapping("/{id}/orders")
    public ResponseEntity getUsersOrders(@PathVariable Long id, Pageable pageable) throws GeneralPersistenceException, IncorrectParamException {
        Page<OrderItemForList> orderList = userService.getUsersOrders(id, pageable);
        orderList.forEach(order -> orderListLink.link(order, "GET"));

        return ResponseEntity.ok(orderList);
    }

    /**
     * Method for retrieving specified order by :orderId of user that has specified :userId
     * @param userId
     * @param orderId
     * @return order if specified user has specified order
     * @throws GeneralPersistenceException if there is no user that has specified :userId, or if there is no order that has specified :orderId,
     * or if specified user does not have specified order
     * @throws IncorrectParamException if one or both of :userId or :orderId params are non-positive
     */
    @GetMapping("/{userId}/order/{orderId}")
    public ResponseEntity getUsersOrderInfo(@PathVariable Long userId, @PathVariable Long orderId ) throws GeneralPersistenceException, IncorrectParamException {
        OrderItemForInfo orderItem = userService.getUsersOrderInfo(userId, orderId);
        orderInfoLink.link(orderItem, "GET");

        return ResponseEntity.ok(orderItem);
    }


    /*
    This method is used only by @author to add users in testing.
     */
    @PostMapping
    public ResponseEntity create(@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.create(userDto));
    }

}
