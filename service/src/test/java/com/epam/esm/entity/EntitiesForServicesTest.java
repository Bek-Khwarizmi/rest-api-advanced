package com.epam.esm.entity;

import java.math.BigDecimal;
import java.util.Set;

public class EntitiesForServicesTest {

    //Tags
    public static final Tag TAG_1 = new Tag(1L, "Tag 1");
    public static final Tag TAG_2 = new Tag(2L, "Tag 3");
    public static final Tag TAG_3 = new Tag(3L, "Tag 5");
    public static final Tag TAG_4 = new Tag(4L, "Tag 4");
    public static final Tag TAG_5 = new Tag(5L, "Tag 2");


    //Gift Certificates
    public static final GiftCertificate GIFT_CERTIFICATE_1 = new GiftCertificate(1L, "giftCertificate 1",
            "description 1", new BigDecimal("10.10"), 1, "2020-08-29T06:12:15",
            "2020-08-29T06:12:15", Set.of(TAG_2, TAG_5));
    public static final GiftCertificate GIFT_CERTIFICATE_2 = new GiftCertificate(2L, "giftCertificate 3",
            "description 3", new BigDecimal("30.30"), 3, "2019-08-29T06:12:15",
            "2019-08-29T06:12:15", Set.of(TAG_2));
    public static final GiftCertificate GIFT_CERTIFICATE_3 = new GiftCertificate(3L, "giftCertificate 2",
            "description 2", new BigDecimal("20.20"), 2, "2018-08-29T06:12:15",
            "2018-08-29T06:12:15", Set.of(TAG_3));

    //Users
    public static final User USER_1 = new User(1L, "Name 1", "Surname 1", "+998901234567", "name1_surname1@gmail.com");
    public static final User USER_2 = new User(2L, "Name 2", "Surname 2", "+998901234568", "name2_surname2@gmail.com");;

    //Orders
    public static final Order ORDER_1 = new Order(1L, new BigDecimal("10.10"),
            "2018-08-29T06:12:15", USER_1, GIFT_CERTIFICATE_1);
    public static final Order ORDER_2 = new Order(2L, new BigDecimal("30.30"),
            "2018-08-29T06:12:15", USER_1, GIFT_CERTIFICATE_2);
}
