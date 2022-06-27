package com.epam.esm.validation;

import com.epam.esm.exception.IncorrectParamException;
import com.epam.esm.exception.IncorrectParamExceptionCode;
import lombok.experimental.UtilityClass;

import java.util.Set;

@UtilityClass
public class SortValidation  extends IncorrectParamExceptionCode {

    private static Set<String> options = Set.of(
            "name-asc", "name-desc", "date-asc", "date-desc",
            "name-asc-date-asc", "name-asc-date-desc",
            "name-desc-date-asc", "name-desc-date-desc",
            "date-asc-date-asc", "date-asc-name-desc",
            "date-desc-name-asc", "date-desc-name-desc"
    );

    public static void validateSort(String sort) throws IncorrectParamException {
        System.out.println(sort);
        if (sort == null) {
            return;
        }
        System.out.println(sort);

        if(!options.contains(sort)){
            throw new IncorrectParamException(BAD_NAME);
        }
    }
}
