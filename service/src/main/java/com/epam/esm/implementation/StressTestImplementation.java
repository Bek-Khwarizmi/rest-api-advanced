package com.epam.esm.implementation;

import com.epam.esm.dto.request.GiftCertificateDto;
import com.epam.esm.dto.request.OrderDto;
import com.epam.esm.dto.request.TagDto;
import com.epam.esm.dto.request.UserDto;
import com.epam.esm.service.*;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class StressTestImplementation implements StressTestService {

    private final TagService tagService;
    private final GiftCertificateService giftCertificateService;
    private final UserService userService;
    private final OrderService orderService;

    private static int tagCount = 1;
    private static List<TagDto> availableTagDto = new ArrayList<>();
    private static List<Long> availableGiftCertificateId = new ArrayList<>();
    private static List<Long> availableUserId = new ArrayList<>();
    private static String[] names =
            {
                    "Abdulaziz", "Adam", "Adham", "Akbar", "Akmal", "Akram", "Alisher", "Ansar", "Azamat", "Aziz",
                    "Babor", "Bahadur", "Bilol", "Carlos", "Dalton", "Edel", "Elman", "Farhod", "Farrukh", "Gabriel",
                    "Hawthorne", "Hidayat", "Hilol", "Husan", "Ibroxim", "Izel", "Izzat", "Jafar", "Jaloliddin", "James",
                    "Jamshid", "Jasur", "Java", "Kobe", "Lars", "Makhmud", "Makhsudbek", "Mansur", "Marcel", "Mardon",
                    "Max", "Mohsin", "Muazzam", "Muhammadjon", "Muzaffar", "Naim", "Naufal", "Qamar", "Raphael", "Rashid",
                    "Ruslan", "Rustam", "Sarvar", "Shahram", "Sohail", "Stephen", "Sufian", "Sunnat", "Timothy", "Timur",
                    "Tom", "Tyler", "Umid", "Vlad", "Walid", "Aida", "Aila", "Aisara", "Ajva", "Albina",
                    "Alchira", "Alina", "Alsu", "Amina", "Anira", "Anora", "Ashura", "Azadija", "Bibidana", "Chinara",
                    "Dana", "Darisa", "Daruna", "Dilara", "Diliya", "Durdona", "Feruza", "Florida", "Friend", "Gabriella",
                    "Guldasta", "Gulisa", "Gulnora", "Indira", "Kamara", "Larisa", "Leila", "Lena", "Bobur", "Sanjar"
            };
    private static String[] numbers =
            {
                    "+998914257810", "+998914257811", "+998914257812", "+998914257813", "+998914257814",
                    "+998914257815", "+998914257816", "+998914257817", "+998914257818", "+998914257819",
                    "+998914257820", "+998914257821", "+998914257822", "+998914257823", "+998914257824",
                    "+998914257825", "+998914257826", "+998914257827", "+998914257828", "+998914257829",
                    "+998914257830",
            };
    private static String[] descriptions =
            {
                    "It is for navruz", "It is for christmas", "It is for independence day",
                    "It is for youngsters day", "It is for school holiday", "It is for friend",
                    "It is for parents", "It is for mothers day", "It is for victory day",
            };


    public StressTestImplementation(
            TagService tagService,
            GiftCertificateService giftCertificateService,
            UserService userService,
            OrderService orderService) {
        this.tagService = tagService;
        this.giftCertificateService = giftCertificateService;
        this.userService = userService;
        this.orderService = orderService;
    }

    /*
    This method takes approximately 105138[ms] to create 10,000 tags
     */
    @SneakyThrows
    @Override
    @Transactional
    public List<String> createTag(Long number){
        int numberOfTags = (int)(tagCount+number);

        Long startTime = System.currentTimeMillis();
        for ( ; tagCount<numberOfTags; tagCount++){
            TagDto tagDto = tagDto("Tag-"+tagCount);
            availableTagDto.add(tagDto);
            tagService.create(tagDto);
        }
        Long endTime = System.currentTimeMillis();

        return List.of("Number of tags created = "+number, "Time spent = "+(endTime-startTime)+"[ms]");
    }

    /*
    This method takes approximately 342270[ms] to create 10,000 gift certificates
    using above 10,000 tags.
     */
    @SneakyThrows
    @Override
    @Transactional
    public List<String> createGiftCertificate(Long number) {
        int numberOfGiftCertificates = (int)(number+1);

        Long startTime = System.currentTimeMillis();
        for ( int i = 1; i < numberOfGiftCertificates; i++){
            String name = "giftCertificate-"+i;
            String description = descriptions[random(descriptions.length)];
            BigDecimal price = new BigDecimal(i%71+1);
            Integer duration = i%28+3;
            Set<TagDto> tags = new HashSet<>();
            int tagNumber = random(7);
            while(tagNumber-->0){
                TagDto tag = availableTagDto.get(random(availableTagDto.size()));
                tags.add(tag);
            }

            GiftCertificateDto giftCertificateDto = giftCertificateDto(name, description, price, duration, tags);
            availableGiftCertificateId.add(giftCertificateService.create(giftCertificateDto).getId());

        }
        Long endTime = System.currentTimeMillis();

        return List.of("Number of gift certificates created = "+number, "Time spent = "+(endTime-startTime)+"[ms]");
    }

    /*
    This method takes approximately 76300[ms] to create 10,000 users
     */
    @SneakyThrows
    @Override
    @Transactional
    public List<String> createUser(Long number) {
        int numberOfUsers = (int)(number+1);

        Long startTime = System.currentTimeMillis();
        for ( int i = 1; i < numberOfUsers; i++){
            String firstName = names[random(names.length)];
            String lastName = names[random(names.length)];
            String phoneNumber = numbers[random(numbers.length)];
            String email = firstName.toLowerCase()+"_"+lastName.toLowerCase()+"@gmail.com";

            UserDto user = userDto(firstName, lastName, phoneNumber, email);
            availableUserId.add(userService.create(user).getId());

        }
        Long endTime = System.currentTimeMillis();

        return List.of("Number of users created = "+number, "Time spent = "+(endTime-startTime)+"[ms]");
    }

    /*
    This method takes approximately 501086[ms] to create 10,000 orders
    using above 10,000 users and 10,000 gift certificates
     */
    @SneakyThrows
    @Override
    @Transactional
    public List<String> createOrder(Long number) {
        int numberOfOrders = (int)(number+1);

        Long startTime = System.currentTimeMillis();
        for ( int i = 1; i < numberOfOrders; i++){
            Long userId = availableUserId.get(random(availableUserId.size()));
            Long giftCertificateId = availableGiftCertificateId.get(random(availableGiftCertificateId.size()));

            OrderDto order = orderDto(userId, giftCertificateId);
            orderService.create(order);
        }
        Long endTime = System.currentTimeMillis();

        return List.of("Number of orders created = "+number, "Time spent = "+(endTime-startTime)+"[ms]");
    }

    private int random(int scale){
        return (int)(Math.random()*scale);
    }

    /*
    Below methods are used to create dto(s) of objects:
    I could do this by creating all args constructor in Dto classes,
    but I decided not to change existing code
     */
    private TagDto tagDto(String name){
        TagDto tagDto = new TagDto();
        tagDto.setName(name);
        return tagDto;
    }

    private GiftCertificateDto giftCertificateDto(String name, String description, BigDecimal price, Integer duration, Set<TagDto> tags){
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setName(name);
        giftCertificateDto.setDescription(description);
        giftCertificateDto.setPrice(price);
        giftCertificateDto.setDuration(duration);
        giftCertificateDto.setTags(tags);
        return giftCertificateDto;
    }

    private UserDto userDto(String firstName, String lastName, String phoneNumber, String email){
        UserDto userDto = new UserDto();
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);
        userDto.setPhoneNumber(phoneNumber);
        userDto.setEmail(email);
        return userDto;
    }

    private OrderDto orderDto(Long userId, Long giftCertificateId){
        OrderDto orderDto = new OrderDto();
        orderDto.setUserId(userId);
        orderDto.setGiftCertificateId(giftCertificateId);
        return orderDto;
    }
}
