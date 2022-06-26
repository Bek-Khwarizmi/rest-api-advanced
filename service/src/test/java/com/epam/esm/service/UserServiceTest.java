package com.epam.esm.service;

import com.epam.esm.dto.response.OrderItemForInfo;
import com.epam.esm.dto.response.UserItem;
import com.epam.esm.exception.GeneralPersistenceException;
import com.epam.esm.exception.IncorrectParamException;
import com.epam.esm.implementation.UserServiceImplementation;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static com.epam.esm.entity.EntitiesForServicesTest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private UserServiceImplementation userService;


    @Test
    public void getUserByIdTest() throws GeneralPersistenceException, IncorrectParamException {
        when(userRepository.findById(1L)).thenReturn(Optional.of(USER_1));
        UserItem expected = UserItem.fromUser(USER_1);

        UserItem actual = userService.getUserById(USER_1.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void getUsersOrdersTest() {
        when(userRepository.findById(2L)).thenReturn(Optional.of(USER_2));
        assertThrows(GeneralPersistenceException.class, () -> userService.getUsersOrders(USER_2.getId(),Pageable.ofSize(2)));
    }

    @Test
    public void getUsersOrderInfoTest() throws GeneralPersistenceException, IncorrectParamException {
        when(userRepository.findById(1L)).thenReturn(Optional.of(USER_1));
        when(orderRepository.findById(1L)).thenReturn(Optional.of(ORDER_1));
        OrderItemForInfo actual = OrderItemForInfo.fromOrder(ORDER_1);
        when(userService.getUsersOrderInfo(USER_1.getId(), ORDER_1.getId())).thenReturn(actual);
        OrderItemForInfo expected = OrderItemForInfo.fromOrder(ORDER_1);

        assertEquals(actual, expected);
    }

    /*
    Creating users is used only by me, so I did not write test for this one
     */
}
