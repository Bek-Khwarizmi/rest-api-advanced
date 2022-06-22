package com.epam.esm.exception;

import com.epam.esm.dto.request.TagDto;
import com.epam.esm.service.TagService;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class TagPersistenceExceptionTest {

    @Mock
    private final TagService tagService;

    public TagPersistenceExceptionTest(TagService tagService) {
        this.tagService = tagService;
    }

    @Test
    public void listOfTagsWhenThereIsNoAnyTagTest() throws GeneralPersistenceException, IncorrectParamException {
        tagService.delete(1L);
        tagService.delete(2L);
        tagService.delete(3L);
        tagService.delete(4L);
        tagService.delete(5L);
        Assertions.assertThrows(GeneralPersistenceException.class, () -> tagService.list(Pageable.ofSize(5)));
    }

    @Test
    public void getByIdWithNotTagTest() {
        Assertions.assertThrows(GeneralPersistenceException.class, () -> tagService.getById(6L));
    }

    @Test
    public void createAlreadyExistedTagTest() {
        TagDto dto = new TagDto();
        dto.setName("Tag 1");
        Assertions.assertThrows(GeneralPersistenceException.class, () -> tagService.create(dto));
    }

    @Test
    public void deleteTagThatIsNotExistTest() {
        Assertions.assertThrows(GeneralPersistenceException.class, () -> tagService.delete(6L));
    }

    @Test
    public void deleteTagThatHasConnectionTest() {
        Assertions.assertThrows(GeneralPersistenceException.class, () -> tagService.delete(1L));
    }

}
