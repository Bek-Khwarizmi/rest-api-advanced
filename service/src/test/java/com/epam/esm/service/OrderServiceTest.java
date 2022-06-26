package com.epam.esm.service;

import com.epam.esm.dto.request.OrderDto;
import com.epam.esm.dto.response.OrderItemForInfo;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.GeneralPersistenceException;
import com.epam.esm.exception.IncorrectParamException;
import com.epam.esm.implementation.OrderServiceImplementation;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static com.epam.esm.entity.EntitiesForServicesTest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    @InjectMocks
    private OrderServiceImplementation orderService;


    @Test
    public void listTest() throws GeneralPersistenceException {
        Page<Order> orders = getOrders();
        Page<OrderItemForInfo> expected = getOrderItems();
        when(orderRepository.findAll(Pageable.ofSize(2))).thenReturn(orders);

        Page<OrderItemForInfo> actual = orderService.list(Pageable.ofSize(2));

        assertEquals(expected, actual);
    }

    @Test
    public void getByIdTest() throws GeneralPersistenceException, IncorrectParamException {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(ORDER_1));
        OrderItemForInfo expected = OrderItemForInfo.fromOrder(ORDER_1);

        OrderItemForInfo actual = orderService.getById(ORDER_1.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void createTest() throws GeneralPersistenceException, IncorrectParamException {
        when(userRepository.findById(1L)).thenReturn(Optional.of(USER_1));
        when(giftCertificateRepository.findById(1L)).thenReturn(Optional.of(GIFT_CERTIFICATE_1));
        when(orderRepository.save(ORDER_1)).thenReturn(ORDER_1);
        OrderItemForInfo actual = OrderItemForInfo.fromOrder(ORDER_1);
        OrderDto dto = new OrderDto();
        dto.setUserId(1L);
        dto.setGiftCertificateId(1L);
        when(orderService.create(dto)).thenReturn(actual);
        OrderItemForInfo expected = OrderItemForInfo.fromOrder(ORDER_1);

        assertEquals(expected, actual);
    }
}
