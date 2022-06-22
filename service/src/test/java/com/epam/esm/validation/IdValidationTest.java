package com.epam.esm.validation;

import com.epam.esm.dto.request.GiftCertificateDto;
import com.epam.esm.exception.IncorrectParamException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.junit.Test;

@ExtendWith(MockitoExtension.class)
public class IdValidationTest {

    @Mock
    private final TagService tagService;
    private final GiftCertificateService giftCertificateService;
    private final UserService userService;
    private final OrderService orderService;

    public IdValidationTest(
            TagService tagService, GiftCertificateService giftCertificateService,
            UserService userService, OrderService orderService) {
        this.tagService = tagService;
        this.giftCertificateService = giftCertificateService;
        this.userService = userService;
        this.orderService = orderService;
    }

    //Id validations for tag: getById() methods

    @Test
    public void validateIdForTagGetByIdTest(){
        Assertions.assertThrows(IncorrectParamException.class, () -> tagService.delete(-1L));
    }

    //Id validations for gift certificate: getById() and update() methods

    @Test
    public void validateIdForGiftCertificateGetByIdTest(){
        Assertions.assertThrows(IncorrectParamException.class, () -> giftCertificateService.delete(-1L));
    }

    @Test
    public void validateIdForGiftCertificateUpdateTest(){
        Assertions.assertThrows(IncorrectParamException.class, () -> {
            GiftCertificateDto dto = new GiftCertificateDto();
            giftCertificateService.update(-1L, dto);
        });
    }

    //Id validations for user: getUserById(), getUsersOrders() and getUsersOrderInfo() methods

    @Test
    public void validateIdForUserGetUserByIdTest(){
        Assertions.assertThrows(IncorrectParamException.class, () -> userService.getUserById(-1L));
    }

    @Test
    public void validateIdForUserGetUsersOrdersTest(){
        Assertions.assertThrows(IncorrectParamException.class, () -> userService.getUsersOrders(-1L, Pageable.ofSize(10)));
    }

    @Test
    public void validateIdForUserGetUsersOrderInfoTest(){
        Assertions.assertThrows(IncorrectParamException.class, () -> {
            userService.getUsersOrderInfo(-1L, 1L);
            userService.getUsersOrderInfo(1L, -1L);
        });
    }

    //Id validations for order: getById() method

    @Test
    public void validateIdForOrderGetByIdTest(){
        Assertions.assertThrows(IncorrectParamException.class, () -> orderService.getById(-1L));
    }

}
