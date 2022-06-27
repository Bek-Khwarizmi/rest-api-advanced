package com.epam.esm.exception;

import com.epam.esm.service.UserService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class UserPersistenceExceptionTest {

    @Mock
    private final UserService userService;

    public UserPersistenceExceptionTest(UserService userService) {
        this.userService = userService;
    }

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
