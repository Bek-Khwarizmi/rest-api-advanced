package com.epam.esm.exception;

import com.epam.esm.dto.request.OrderDto;
import com.epam.esm.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.mockito.Mock;

public class OrderPersistenceExceptionTest {

    @Mock
    private final OrderService orderService;

    public OrderPersistenceExceptionTest(OrderService orderService) {
        this.orderService = orderService;
    }

    @Test
    public void createOrderWithNotUserIdTest(){
        OrderDto dto = new OrderDto();
        dto.setUserId(10L);
        dto.setGiftCertificateId(2L);
        Assertions.assertThrows(GeneralPersistenceException.class, () -> orderService.create(dto));
    }

    @Test
    public void createOrderWithNotGiftCertificateIdTest(){
        OrderDto dto = new OrderDto();
        dto.setUserId(1L);
        dto.setGiftCertificateId(20L);
        Assertions.assertThrows(GeneralPersistenceException.class, () -> orderService.create(dto));
    }

    @Test
    public void getByIdWithNotExistedOrderTest(){
        Assertions.assertThrows(GeneralPersistenceException.class, () -> orderService.getById(6L));
    }
}
