package com.epam.esm.validation;

import com.epam.esm.dto.request.GiftCertificateDto;
import com.epam.esm.exception.IncorrectParamException;
import com.epam.esm.implementation.GiftCertificateServiceImplementation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateValidationTest {

    @InjectMocks
    private GiftCertificateServiceImplementation giftCertificateService;


    @Test
    public void validateGiftCertificateForNullNameTest() {
        GiftCertificateDto dto = new GiftCertificateDto();
        dto.setName(null);
        Assertions.assertThrows(IncorrectParamException.class, () -> giftCertificateService.create(dto));
    }

    @Test
    public void validateGiftCertificateForMinLengthNameTest() {
        GiftCertificateDto dto = new GiftCertificateDto();
        dto.setName("xy");
        Assertions.assertThrows(IncorrectParamException.class, () -> giftCertificateService.create(dto));
    }

    @Test
    public void validateGiftCertificateForMaxLengthNameTest() {
        GiftCertificateDto dto = new GiftCertificateDto();
        dto.setName("qwertyuiopasdfghjklzxcvbnmqwertyuiop");
        Assertions.assertThrows(IncorrectParamException.class, () -> giftCertificateService.create(dto));
    }

    @Test
    public void validateGiftCertificateForNullDescriptionTest() {
        GiftCertificateDto dto = new GiftCertificateDto();
        dto.setName("xyzt");
        dto.setDescription(null);
        Assertions.assertThrows(IncorrectParamException.class, () -> giftCertificateService.create(dto));
    }

    @Test
    public void validateGiftCertificateForMaxLengthDescriptionTest() {
        GiftCertificateDto dto = new GiftCertificateDto();
        dto.setName("xyzt");
        char[] str = new char[301];
        dto.setDescription(str.toString());
        Assertions.assertThrows(IncorrectParamException.class, () -> giftCertificateService.create(dto));
    }

    @Test
    public void validateGiftCertificateForMinPriceTest() {
        GiftCertificateDto dto = new GiftCertificateDto();
        dto.setName("xyzt");
        dto.setDescription("abcd");
        dto.setPrice(new BigDecimal("0.0001"));
        Assertions.assertThrows(IncorrectParamException.class, () -> giftCertificateService.create(dto));
    }

    @Test
    public void validateGiftCertificateForMaxPriceTest() {
        GiftCertificateDto dto = new GiftCertificateDto();
        dto.setName("xyzt");
        dto.setDescription("abcd");
        dto.setPrice(new BigDecimal("9999999.99"));
        Assertions.assertThrows(IncorrectParamException.class, () -> giftCertificateService.create(dto));
    }

    @Test
    public void validateGiftCertificateForMinDurationTest() {
        GiftCertificateDto dto = new GiftCertificateDto();
        dto.setName("xyzt");
        dto.setDescription("abcd");
        dto.setPrice(new BigDecimal("9999.99"));
        dto.setDuration(0);
        Assertions.assertThrows(IncorrectParamException.class, () -> giftCertificateService.create(dto));
    }

    @Test
    public void validateGiftCertificateForMaxDurationTest() {
        GiftCertificateDto dto = new GiftCertificateDto();
        dto.setName("xyzt");
        dto.setDescription("abcd");
        dto.setPrice(new BigDecimal("9999.99"));
        dto.setDuration(367);
        Assertions.assertThrows(IncorrectParamException.class, () -> giftCertificateService.create(dto));
    }
}
