package com.epam.esm.validation;

import com.epam.esm.dto.request.TagDto;
import com.epam.esm.exception.IncorrectParamException;
import com.epam.esm.implementation.TagServiceImplementation;
import com.epam.esm.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TagValidationTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagServiceImplementation tagService;


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
