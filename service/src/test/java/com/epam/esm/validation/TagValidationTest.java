package com.epam.esm.validation;

import com.epam.esm.dto.request.TagDto;
import com.epam.esm.exception.IncorrectParamException;
import com.epam.esm.service.TagService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TagValidationTest {

    @Mock
    private final TagService tagService;

    public TagValidationTest(TagService tagService) {
        this.tagService = tagService;
    }

    @Test
    public void validateNameForMinLengthTest() {
        TagDto dto = new TagDto();
        dto.setName("xy");
        Assertions.assertThrows(IncorrectParamException.class, () -> tagService.create(dto));
    }

    @Test
    public void validateNameForMaxLengthTest() {
        TagDto dto = new TagDto();
        dto.setName("qwertyuiopasdfghjklzxcvbnm");
        Assertions.assertThrows(IncorrectParamException.class, () -> tagService.create(dto));
    }
}
