package com.epam.esm.exception;

import com.epam.esm.implementation.TagServiceImplementation;
import com.epam.esm.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TagPersistenceExceptionTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagServiceImplementation tagService;


    @Test
    public void listOfTagsWhenThereIsNoAnyTagTest(){
        assertThrows(NullPointerException.class, () -> tagService.list(Pageable.ofSize(5)));
    }

    @Test
    public void getByIdWithNotTagTest() {
        assertThrows(GeneralPersistenceException.class, () -> tagService.getById(6L));
    }

    @Test
    public void deleteTagThatIsNotExistTest() {
        assertThrows(GeneralPersistenceException.class, () -> tagService.delete(6L));
    }

    @Test
    public void deleteTagThatHasConnectionTest() {
        assertThrows(GeneralPersistenceException.class, () -> tagService.delete(1L));
    }

}
