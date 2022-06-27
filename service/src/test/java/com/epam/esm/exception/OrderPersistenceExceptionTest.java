package com.epam.esm.exception;

import com.epam.esm.dto.request.OrderDto;
import com.epam.esm.implementation.OrderServiceImplementation;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OrderPersistenceExceptionTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    @InjectMocks
    private OrderServiceImplementation orderService;


    @Test
    public void createOrderWithNotUserIdTest(){
        OrderDto dto = new OrderDto();
        dto.setUserId(10L);
        dto.setGiftCertificateId(2L);
        assertThrows(GeneralPersistenceException.class, () -> orderService.create(dto));
    }

    @Test
    public void createOrderWithNotGiftCertificateIdTest(){
        OrderDto dto = new OrderDto();
        dto.setUserId(1L);
        dto.setGiftCertificateId(20L);
        assertThrows(GeneralPersistenceException.class, () -> orderService.create(dto));
    }

    @Test
    public void getByIdWithNotExistedOrderTest(){
        assertThrows(GeneralPersistenceException.class, () -> orderService.getById(6L));
    }
}
