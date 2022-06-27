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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
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
        when(tagRepository.findAll(Pageable.ofSize(5))).thenReturn(tags);
        Page<TagItem> expected = tags.map(TagItem::fromTag);

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
    public void createTest() throws GeneralPersistenceException, IncorrectParamException {
        when(tagRepository.save(any())).thenReturn(TAG_1);
        TagItem expected = TagItem.fromTag(TAG_1);
        TagDto dto = new TagDto();
        dto.setName(TAG_1.getName());
        TagItem actual = tagService.create(dto);
        assertEquals(actual, expected);
    }

    @Test
    public void deleteTest() throws GeneralPersistenceException, IncorrectParamException {
        Long id = TAG_1.getId();
        when(tagRepository.findById(id)).thenReturn(Optional.of(TAG_1));
        doNothing().when(tagRepository).delete(TAG_1);

        tagService.delete(id);
        verify(tagRepository).findById(id);
        verify(tagRepository).delete(TAG_1);
    }

    @Test
    public void findMostUsedTagTest() throws GeneralPersistenceException {
        Page<Tag> tags = new PageImpl<>(List.of(TAG_2), Pageable.ofSize(1), 1);
        when(tagRepository.findMostUsedTag(Pageable.ofSize(1))).thenReturn(tags);
        Page<TagItem> expected = tags.map(TagItem::fromTag);

        Page<TagItem> actual = tagService.findMostUsedTag(Pageable.ofSize(1));
        assertEquals(actual, expected);
    }
}
