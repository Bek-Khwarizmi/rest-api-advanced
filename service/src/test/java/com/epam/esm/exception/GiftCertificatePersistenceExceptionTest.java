package com.epam.esm.exception;

import com.epam.esm.dto.request.GiftCertificateDto;
import com.epam.esm.service.GiftCertificateService;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.mockito.Mock;

public class GiftCertificatePersistenceExceptionTest {

    @Mock
    private final GiftCertificateService giftCertificateService;

    public GiftCertificatePersistenceExceptionTest(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @Test
    public void getByIdWithNotGiftCertificateTest() {
        Assertions.assertThrows(GeneralPersistenceException.class, () -> giftCertificateService.getById(6L));
    }

    @Test
    public void updateWithNotGiftCertificateTest() {
        GiftCertificateDto dto = new GiftCertificateDto();
        dto.setName("abc");
        Assertions.assertThrows(GeneralPersistenceException.class, () -> giftCertificateService.update(6L, dto));
    }

    @Test
    public void deleteByIdWithNotGiftCertificateTest() {
        Assertions.assertThrows(GeneralPersistenceException.class, () -> giftCertificateService.delete(6L));
    }

    @Test
    public void deleteByIdWithGiftCertificateThatHasConnectionsTest() {
        Assertions.assertThrows(GeneralPersistenceException.class, () -> giftCertificateService.delete(1L));
    }
}