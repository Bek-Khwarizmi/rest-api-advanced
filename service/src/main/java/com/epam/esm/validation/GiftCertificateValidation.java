package com.epam.esm.validation;

import com.epam.esm.dto.request.GiftCertificateDto;
import com.epam.esm.exception.IncorrectParamException;
import com.epam.esm.exception.IncorrectParamExceptionCode;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class GiftCertificateValidation extends IncorrectParamExceptionCode {

    private final int MAX_LENGTH_DESCRIPTION = 300;
    private final int MAX_SCALE = 2;
    private final BigDecimal MIN_PRICE = new BigDecimal("0.001");
    private final BigDecimal MAX_PRICE = new BigDecimal("999999.99");
    private final int MAX_DURATION = 366;
    private final int MIN_DURATION = 1;
    private final int MIN_LENGTH_NAME=3;
    private final int MAX_LENGTH_NAME=35;

    public void validate(GiftCertificateDto giftCertificate) throws IncorrectParamException {
        if (giftCertificate!=null){
            validateName(giftCertificate.getName());
            validateDescription(giftCertificate.getDescription());
            validatePrice(giftCertificate.getPrice());
            validateDuration(giftCertificate.getDuration());
        }
        else
            validateEntity();
    }

    public void validateForUpdate(GiftCertificateDto giftCertificate) throws IncorrectParamException {
        if (giftCertificate!=null){
            if (giftCertificate.getName() != null) {
                validateName(giftCertificate.getName());
            }
            if (giftCertificate.getDescription() != null) {
                validateDescription(giftCertificate.getDescription());
            }
            if (giftCertificate.getPrice() != null) {
                validatePrice(giftCertificate.getPrice());
            }
            if (giftCertificate.getDuration() != null) {
                validateDuration(giftCertificate.getDuration());
            }
        }
        else validateEntity();
    }

    public void validateName(String name) throws IncorrectParamException {
        if (name == null || name.length() < MIN_LENGTH_NAME || name.length() > MAX_LENGTH_NAME) {
            throw new IncorrectParamException(BAD_NAME);
        }
    }

    public void validateDescription(String description) throws IncorrectParamException {
        if (description == null || description.length() > MAX_LENGTH_DESCRIPTION) {
            throw new IncorrectParamException(BAD_GIFT_CERTIFICATE_DESCRIPTION);
        }
    }

    private void validateEntity() throws IncorrectParamException {
        throw new IncorrectParamException(BAD_GIFT_CERTIFICATE);
    }

    private void validatePrice(BigDecimal price) throws IncorrectParamException {
        if (price == null || price.scale() > MAX_SCALE
                || price.compareTo(MIN_PRICE) < 0 || price.compareTo(MAX_PRICE) > 0) {
            throw new IncorrectParamException(BAD_GIFT_CERTIFICATE_PRICE);
        }
    }

    private void validateDuration(int duration) throws IncorrectParamException {
        if (duration < MIN_DURATION || duration > MAX_DURATION) {
            throw new IncorrectParamException(BAD_GIFT_CERTIFICATE_DURATION);
        }
    }

}
