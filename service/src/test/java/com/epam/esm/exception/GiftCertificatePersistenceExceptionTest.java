package com.epam.esm.exception;

import com.epam.esm.dto.request.GiftCertificateDto;
import com.epam.esm.implementation.GiftCertificateServiceImplementation;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GiftCertificatePersistenceExceptionTest {

    @Mock
    private GiftCertificateRepository giftCertificateRepository;
    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private GiftCertificateServiceImplementation giftCertificateService;


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