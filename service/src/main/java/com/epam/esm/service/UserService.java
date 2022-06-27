package com.epam.esm.service;

import com.epam.esm.dto.request.UserDto;
import com.epam.esm.dto.response.OrderItemForInfo;
import com.epam.esm.dto.response.OrderItemForList;
import com.epam.esm.dto.response.UserItem;
import com.epam.esm.exception.GeneralPersistenceException;
import com.epam.esm.exception.IncorrectParamException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserItem getUserById(Long id) throws GeneralPersistenceException, IncorrectParamException;

    Page<OrderItemForList> getUsersOrders(Long id, Pageable pageable) throws GeneralPersistenceException, IncorrectParamException;

    OrderItemForInfo getUsersOrderInfo(Long userId, Long orderId) throws GeneralPersistenceException, IncorrectParamException;

    UserItem create(UserDto userDto);

}
