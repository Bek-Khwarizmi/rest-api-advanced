package com.epam.esm.service;

import com.epam.esm.dto.request.GiftCertificateDto;
import com.epam.esm.dto.request.TagDto;
import com.epam.esm.dto.response.GiftCertificateItem;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.GeneralPersistenceException;
import com.epam.esm.exception.IncorrectParamException;
import com.epam.esm.implementation.GiftCertificateServiceImplementation;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.esm.entity.EntitiesForServicesTest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateServiceTest {

    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private GiftCertificateServiceImplementation giftCertificateService;

    @After("")
    public void reset_mocks() {
        Mockito.reset(giftCertificateService);
    }
    
    @Test
    public void listTest() throws GeneralPersistenceException, IncorrectParamException {
        Page<GiftCertificate> giftCertificates = getGiftCertificates();
        when(giftCertificateRepository.findAll(null, Pageable.ofSize(3))).thenReturn(giftCertificates);
        Page<GiftCertificateItem> expectedPage = giftCertificates.map(GiftCertificateItem::fromGiftCertificate);

        Page<GiftCertificateItem> actualPage = giftCertificateService.list(null, null, null, Pageable.ofSize(3));
        List<GiftCertificateItem> expected = expectedPage.getContent();
        List<GiftCertificateItem> actual = actualPage.getContent();

        assertEquals(expected, actual);
    }

    @Test
    public void getByIdTest() throws GeneralPersistenceException, IncorrectParamException {
        when(giftCertificateRepository.findById(2L)).thenReturn(Optional.of(GIFT_CERTIFICATE_2));
        GiftCertificateItem expected = GiftCertificateItem.fromGiftCertificate(GIFT_CERTIFICATE_2);

        GiftCertificateItem actual = giftCertificateService.getById(GIFT_CERTIFICATE_2.getId());

        assertEquals(expected, actual);
    }

    @Test
    public void createTest() throws IncorrectParamException {
        when(giftCertificateRepository.save(any())).thenReturn(GIFT_CERTIFICATE_3);
        GiftCertificateItem expected = GiftCertificateItem.fromGiftCertificate(GIFT_CERTIFICATE_3);

        GiftCertificateDto dto = new GiftCertificateDto();
        dto.setName(GIFT_CERTIFICATE_3.getName());
        dto.setDescription(GIFT_CERTIFICATE_3.getDescription());
        dto.setPrice(GIFT_CERTIFICATE_3.getPrice());
        dto.setDuration(GIFT_CERTIFICATE_3.getDuration());
        Set<TagDto> tags = GIFT_CERTIFICATE_3.getTags().stream().map(tag -> {
            TagDto tagDto = new TagDto();
            tagDto.setName(tag.getName());
            return tagDto;
        }).collect(Collectors.toSet());
        dto.setTags(tags);
        GiftCertificateItem actual = giftCertificateService.create(dto);
        assertEquals(expected, actual);
    }

    @Test
    public void updateTest() throws GeneralPersistenceException, IncorrectParamException{
        when(giftCertificateRepository.save(any())).thenReturn(GIFT_CERTIFICATE_2);
        when(giftCertificateRepository.findById(1L)).thenReturn(Optional.of(GIFT_CERTIFICATE_1));
        GiftCertificateItem expected = GiftCertificateItem.fromGiftCertificate(GIFT_CERTIFICATE_2);

        GiftCertificateDto dto = new GiftCertificateDto();
        dto.setName(GIFT_CERTIFICATE_2.getName());
        dto.setDescription(GIFT_CERTIFICATE_2.getDescription());
        dto.setPrice(GIFT_CERTIFICATE_2.getPrice());
        dto.setDuration(GIFT_CERTIFICATE_2.getDuration());
        Set<TagDto> tags = GIFT_CERTIFICATE_2.getTags().stream().map(tag -> {
            TagDto tagDto = new TagDto();
            tagDto.setName(tag.getName());
            return tagDto;
        }).collect(Collectors.toSet());
        dto.setTags(tags);
        GiftCertificateItem actual = giftCertificateService.update(1L, dto);
        assertEquals(expected, actual);
    }

    @Test
    public void deleteTest() throws GeneralPersistenceException, IncorrectParamException {

        Long id = GIFT_CERTIFICATE_2.getId();
        when(giftCertificateRepository.findById(id)).thenReturn(Optional.of(GIFT_CERTIFICATE_2));
        doNothing().when(giftCertificateRepository).delete(GIFT_CERTIFICATE_2);

        giftCertificateService.delete(id);
        verify(giftCertificateRepository).findById(id);
        verify(giftCertificateRepository).delete(GIFT_CERTIFICATE_2);
    }
}
