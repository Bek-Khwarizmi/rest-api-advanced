package com.epam.esm.service;

import com.epam.esm.dto.request.TagDto;
import com.epam.esm.dto.response.TagItem;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.GeneralPersistenceException;
import com.epam.esm.exception.IncorrectParamException;
import com.epam.esm.implementation.TagServiceImplementation;
import com.epam.esm.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static com.epam.esm.entity.EntitiesForServicesTest.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagServiceImplementation tagService;


    @Test
    public void listTest() throws GeneralPersistenceException {
        Page<Tag> tags = getTags();
        Page<TagItem> expected = getTagItems();
        when(tagRepository.findAll(Pageable.ofSize(5))).thenReturn(tags);

        Page<TagItem> actual = tagService.list(Pageable.ofSize(5));

        assertEquals(expected, actual);

    }

    @Test
    public void getByIdTest() throws GeneralPersistenceException, IncorrectParamException {
        when(tagRepository.findById(5L)).thenReturn(Optional.of(TAG_5));
        TagItem expected = TagItem.fromTag(TAG_5);

        TagItem actual = tagService.getById(TAG_5.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void createTest() {
        TagDto dto = new TagDto();
        dto.setName(TAG_1.getName());
        lenient().when(tagRepository.save(TAG_1)).thenReturn(TAG_1);
        TagItem actual = TagItem.fromTag(TAG_1);
        TagItem expected = TagItem.fromTag(TAG_1);
        assertEquals(actual, expected);
    }

    @Test
    public void deleteTest() {
        lenient().doNothing().when(tagRepository).delete(TAG_1);
        TagItem actual = TagItem.fromTag(TAG_1);
        TagItem expected = TagItem.fromTag(TAG_1);
        assertEquals(actual, expected);
    }
}
