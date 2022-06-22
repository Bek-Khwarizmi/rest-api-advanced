package com.epam.esm.service;

import com.epam.esm.dto.response.OrderItemForInfo;
import com.epam.esm.dto.response.OrderItemForList;
import com.epam.esm.dto.response.UserItem;
import com.epam.esm.entity.EntitiesForServicesTest;
import com.epam.esm.exception.GeneralPersistenceException;
import com.epam.esm.exception.IncorrectParamException;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.entity.EntitiesForServicesTest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private final UserService service;

    public UserServiceTest(UserService service) {
        this.service = service;
    }


    @Test
    public void getUserByIdTest() throws GeneralPersistenceException, IncorrectParamException {
        UserItem actual = service.getUserById(USER_2.getId());
        UserItem expected = UserItem.fromUser(EntitiesForServicesTest.USER_2);

        assertEquals(expected, actual);
    }

    @Test
    public void getUsersOrdersTest() throws GeneralPersistenceException, IncorrectParamException {
        List<OrderItemForList> actual = service.getUsersOrders(USER_1.getId(), Pageable.ofSize(3)).getContent();
        List<OrderItemForList> expected = USER_1.getOrders()
                .stream().map(OrderItemForList::fromOrder).collect(Collectors.toList());

        assertEquals(actual, expected);
    }

    @Test
    public void getUsersOrderInfoTest() throws GeneralPersistenceException, IncorrectParamException {
        OrderItemForInfo actual = service.getUsersOrderInfo(USER_1.getId(), ORDER_1.getId());
        OrderItemForInfo expected = OrderItemForInfo.fromOrder(ORDER_1);

        assertEquals(actual, expected);
    }

    /*
    Creating users is used only by me, so I did not write test for this one
     */
}
