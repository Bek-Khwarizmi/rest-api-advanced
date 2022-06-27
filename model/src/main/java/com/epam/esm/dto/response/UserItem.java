package com.epam.esm.dto.response;

import com.epam.esm.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class UserItem  extends RepresentationModel<UserItem> {

    private Long id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    public static UserItem fromUser(User user){
        UserItem item = new UserItem();
        item.setId(user.getId());
        item.setFirstName(user.getFirstName());
        item.setLastName(user.getLastName());
        item.setPhoneNumber(user.getPhoneNumber());
        item.setEmail(user.getEmail());
        return item;
    }
}
