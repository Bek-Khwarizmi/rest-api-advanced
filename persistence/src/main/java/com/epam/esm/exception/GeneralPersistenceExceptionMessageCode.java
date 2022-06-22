package com.epam.esm.exception;

public class GeneralPersistenceExceptionMessageCode {

    /*
    Exceptions for Tag
     */
    public static final String NO_TAG_ENTITY_FOUND = "4041000"; // if there is not any tag in db
    public static final String NO_TAG_ID = "4041001"; // if there is not any tag that has specified id
    public static final String TAG_ALREADY_EXISTS = "4041002"; // if we try to create already existed tag
    public static final String TAG_HAS_CONNECTION = "4041003"; // if we try to delete a tag that is connected with gift certificate(s)


     /*
    Exceptions for GiftCertificate
     */
    public static final String NO_GIFT_CERTIFICATE_ENTITY_FOUND = "4042000"; // if there is not any gift certificate in db
    public static final String NO_GIFT_CERTIFICATE_ID = "4042001"; // if there is not any gift certificate that has specified id
    public static final String GIFT_CERTIFICATE_HAS_CONNECTION = "4042002"; // if we try to delete a gift certificate that is connected with user(s)


     /*
    Exceptions for User
     */
    public static final String NO_USER_ENTITY_FOUND = "4043000"; // if there is not any user in db
    public static final String NO_USER_ID = "4043001"; // if there is not any user that has specified id
    public static final String USER_HAS_NO_ORDER = "4043002"; // if we try to get list of users' orders of a user that does not have any order
    public static final String NOT_USER_ORDER = "4043003"; // if user does not have specified order


     /*
    Exceptions for Order
     */
    public static final String NO_ORDER_ENTITY_FOUND = "4044000"; // if there is not any order in db
    public static final String NO_ORDER_ID = "4044001"; // if there is not any order that has specified id

}
