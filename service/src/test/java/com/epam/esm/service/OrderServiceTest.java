package com.epam.esm.service;

import com.epam.esm.dto.request.OrderDto;
import com.epam.esm.dto.response.OrderItemForInfo;
import com.epam.esm.exception.GeneralPersistenceException;
import com.epam.esm.exception.IncorrectParamException;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.epam.esm.entity.EntitiesForServicesTest.ORDER_1;
import static com.epam.esm.entity.EntitiesForServicesTest.ORDER_2;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private final OrderService service;

    public OrderServiceTest(OrderService service) {
        this.service = service;
    }

    @Test
    public void listTest() throws GeneralPersistenceException {

        List<OrderItemForInfo> actual = service.list(Pageable.ofSize(2)).getContent();
        List<OrderItemForInfo> expected = Stream.of(ORDER_1, ORDER_2).map(OrderItemForInfo::fromOrder).collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    public void getByIdTest() throws GeneralPersistenceException, IncorrectParamException {

        OrderItemForInfo actual = service.getById(ORDER_2.getId());
        OrderItemForInfo expected = OrderItemForInfo.fromOrder(ORDER_2);

        assertEquals(expected, actual);
    }

    @Test
    public void createTest() throws GeneralPersistenceException, IncorrectParamException {
        OrderDto dto = new OrderDto();
        dto.setUserId(ORDER_1.getUser().getId());
        dto.setGiftCertificateId(ORDER_1.getGiftCertificate().getId());

        OrderItemForInfo actual = service.create(dto);
        OrderItemForInfo expected = OrderItemForInfo.fromOrder(ORDER_1);

        assertEquals(expected, actual);
    }
}
