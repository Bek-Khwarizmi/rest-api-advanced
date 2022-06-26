package com.epam.esm.service;

import com.epam.esm.dto.request.GiftCertificateDto;
import com.epam.esm.dto.request.TagDto;
import com.epam.esm.dto.response.GiftCertificateItem;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.GeneralPersistenceException;
import com.epam.esm.exception.IncorrectParamException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.epam.esm.entity.EntitiesForServicesTest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateServiceTest {

    @Mock
    private final GiftCertificateService service;

    public GiftCertificateServiceTest(GiftCertificateService service) {
        this.service = service;
    }

    @Test
    public void listTest() throws GeneralPersistenceException, IncorrectParamException {
        List<GiftCertificateItem> actual = service.list(null, null, null, Pageable.ofSize(3)).getContent();

        List<GiftCertificateItem> expected = Stream.of(GIFT_CERTIFICATE_1,
                GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_3).map(GiftCertificateItem::fromGiftCertificate).collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    public void getByIdTest() throws GeneralPersistenceException, IncorrectParamException {
        GiftCertificateItem actual = service.getById(GIFT_CERTIFICATE_2.getId());
        GiftCertificateItem expected = GiftCertificateItem.fromGiftCertificate(GIFT_CERTIFICATE_2);

        assertEquals(expected, actual);
    }

    @Test
    public void createTest() throws IncorrectParamException {

        GiftCertificateDto item = fromGiftCertificate(GIFT_CERTIFICATE_2);

        GiftCertificateItem expected = service.create(item);
        GiftCertificateItem actual = GiftCertificateItem.fromGiftCertificate(GIFT_CERTIFICATE_2);

        assertEquals(expected, actual);
    }

    @Test
    public void updateTest() throws GeneralPersistenceException, IncorrectParamException{

        GiftCertificateDto item = fromGiftCertificate(GIFT_CERTIFICATE_2);

        GiftCertificateItem expected = service.update(GIFT_CERTIFICATE_1.getId(), item);
        GiftCertificateItem actual = GiftCertificateItem.fromGiftCertificate(GIFT_CERTIFICATE_2);

        assertEquals(expected, actual);

    }

    @Test
    public void deleteTest() throws GeneralPersistenceException, IncorrectParamException {

        GiftCertificateItem expected = GiftCertificateItem.fromGiftCertificate(GIFT_CERTIFICATE_1);
        GiftCertificateItem actual = service.delete(GIFT_CERTIFICATE_1.getId());

        assertEquals(expected, actual);

    }

    private GiftCertificateDto fromGiftCertificate(GiftCertificate giftCertificate){
        GiftCertificateDto item = new GiftCertificateDto();
        item.setName(giftCertificate.getName());
        item.setDescription(giftCertificate.getDescription());
        item.setPrice(giftCertificate.getPrice());
        item.setDuration(giftCertificate.getDuration());
        Set<Tag> tag1 = Set.copyOf(giftCertificate.getTags());
        Set<TagDto> tagDtos = new HashSet<>();

        for (Tag tag: tag1){
            TagDto t = new TagDto();
            t.setName(tag.getName());
            tagDtos.add(t);
        }
        item.setTags(tagDtos);
        return item;
    }
}
