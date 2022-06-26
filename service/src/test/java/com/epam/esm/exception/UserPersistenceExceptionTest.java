package com.epam.esm.exception;

import com.epam.esm.implementation.UserServiceImplementation;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class UserPersistenceExceptionTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private UserServiceImplementation userService;


    @Test
    public void getUserByIdWithNotExistedUserTest(){
        Assertions.assertThrows(GeneralPersistenceException.class, () -> userService.getUserById(6L));
    }

    @Test
    public void getUsersOrdersWithNotExistedUserTest(){
        Assertions.assertThrows(GeneralPersistenceException.class, () -> userService.getUsersOrders(6L, Pageable.ofSize(10)));
    }

    @Test
    public void getUsersOrderInfoWithNotExistedUserTest(){
        Assertions.assertThrows(GeneralPersistenceException.class, () -> userService.getUsersOrderInfo(6L, 1L));
    }

    @Test
    public void getUsersOrderInfoWithNotExistedOrderTest(){
        Assertions.assertThrows(GeneralPersistenceException.class, () -> userService.getUsersOrderInfo(1L, 10L));
    }
}
