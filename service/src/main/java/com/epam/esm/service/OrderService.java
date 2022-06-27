package com.epam.esm.service;

import com.epam.esm.dto.request.OrderDto;
import com.epam.esm.dto.response.OrderItemForInfo;
import com.epam.esm.exception.GeneralPersistenceException;
import com.epam.esm.exception.IncorrectParamException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    OrderItemForInfo create(OrderDto orderDto) throws GeneralPersistenceException, IncorrectParamException;

    Page<OrderItemForInfo> list(Pageable pageable) throws GeneralPersistenceException;

    OrderItemForInfo getById(Long id) throws GeneralPersistenceException, IncorrectParamException;

}
