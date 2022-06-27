package com.epam.esm.validation;

import com.epam.esm.exception.IncorrectParamException;
import com.epam.esm.exception.IncorrectParamExceptionCode;
import lombok.experimental.UtilityClass;

import java.util.Set;

@UtilityClass
public class SortValidation  extends IncorrectParamExceptionCode {

    private static Set<String> options =
            Set.of("id", "name", "description", "price", "duration", "createDate", "date"
    );

    private static Set<String> orders = Set.of("asc", "desc");

    public static void validateSort(String sort) throws IncorrectParamException {
        if (sort == null) {
            return;
        }

        String[] args = sort.split("-");
        if(args.length % 2 !=0){
            throw new IncorrectParamException(BAD_NAME);
        }
        for (int i=0; i< args.length; i+=2){
            if(!options.contains(args[i])){
                throw new IncorrectParamException(BAD_NAME);
            }
            if(!orders.contains(args[i+1])){
                throw new IncorrectParamException(BAD_NAME);
            }
        }
    }
}
