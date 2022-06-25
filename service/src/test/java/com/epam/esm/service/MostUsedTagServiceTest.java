//package com.epam.esm.service;
//
//import com.epam.esm.dto.response.TagItem;
//import com.epam.esm.exception.GeneralPersistenceException;
//import org.junit.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.Pageable;
//
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//import static com.epam.esm.entity.EntitiesForServicesTest.TAG_2;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@ExtendWith(MockitoExtension.class)
//public class MostUsedTagServiceTest {
//
//    @Mock
//    private final MostUsedTagService service;
//
//    public MostUsedTagServiceTest(MostUsedTagService service) {
//        this.service = service;
//    }
//
//    @Test
//    public void listTest() throws GeneralPersistenceException {
//        List<TagItem> actual = service.find(Pageable.ofSize(10)).getContent();
//        List<TagItem> expected = Stream.of(TAG_2).map(TagItem::fromTag).collect(Collectors.toList());
//
//        assertEquals(expected, actual);
//    }
//
//}
