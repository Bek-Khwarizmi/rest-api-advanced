package com.epam.esm.service;

import com.epam.esm.dto.request.TagDto;
import com.epam.esm.dto.response.TagItem;
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

import static com.epam.esm.entity.EntitiesForServicesTest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

    @Mock
    private final TagService service;

    public TagServiceTest(TagService service) {
        this.service = service;
    }

    @Test
    public void listTest() throws GeneralPersistenceException {
        List<TagItem> actual = service.list(Pageable.ofSize(10)).getContent();
        List<TagItem> expected = Stream.of(TAG_1, TAG_2, TAG_3, TAG_4, TAG_5).map(TagItem::fromTag)
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    public void getByIdTest() throws GeneralPersistenceException, IncorrectParamException {
        TagItem actual = service.getById(TAG_3.getId());
        TagItem expected = TagItem.fromTag(TAG_3);

        assertEquals(expected, actual);
    }

    @Test
    public void createTest() throws GeneralPersistenceException, IncorrectParamException {
        TagDto tagDto1 = new TagDto();
        tagDto1.setName(TAG_2.getName());

        TagDto tagDto2 = new TagDto();
        tagDto1.setName(TAG_3.getName());

        assertEquals(service.create(tagDto1), TagItem.fromTag(TAG_2));
        assertEquals(service.create(tagDto2), TagItem.fromTag(TAG_3));
    }


    @Test
    public void deleteTest() throws GeneralPersistenceException, IncorrectParamException {
        assertEquals(service.delete(1L), TagItem.fromTag(TAG_1));
        assertEquals(service.delete(2L), TagItem.fromTag(TAG_2));
        assertEquals(service.delete(3L), TagItem.fromTag(TAG_3));
    }
}
