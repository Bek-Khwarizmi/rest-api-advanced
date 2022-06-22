package com.epam.esm.implementation;

import com.epam.esm.dto.request.UserDto;
import com.epam.esm.dto.response.OrderItemForInfo;
import com.epam.esm.dto.response.OrderItemForList;
import com.epam.esm.dto.response.UserItem;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.GeneralPersistenceException;
import com.epam.esm.exception.IncorrectParamException;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import com.epam.esm.validation.IdValidation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.esm.exception.GeneralPersistenceExceptionMessageCode.*;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public UserServiceImplementation(
            UserRepository userRepository,
            OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public UserItem getUserById(Long id) throws GeneralPersistenceException, IncorrectParamException {
        IdValidation.validateId(id);

        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()){
            throw new GeneralPersistenceException(NO_USER_ID);
        }
        return UserItem.fromUser(optionalUser.get());
    }

    @Override
    public Page<OrderItemForList> getUsersOrders(Long id, Pageable pageable) throws GeneralPersistenceException, IncorrectParamException {
        IdValidation.validateId(id);

        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()){
            throw new GeneralPersistenceException(NO_USER_ID);
        }
        if(optionalUser.get().getOrders() == null || optionalUser.get().getOrders().isEmpty()){
            throw new GeneralPersistenceException(USER_HAS_NO_ORDER);
        }
        List<OrderItemForList> list = optionalUser.get().getOrders()
                .stream()
                .map(OrderItemForList::fromOrder)
                .collect(Collectors.toList());
        int total = list.size();

        return new PageImpl<>(list, pageable, total);

    }

    @Override
    public OrderItemForInfo getUsersOrderInfo(Long userId, Long orderId) throws GeneralPersistenceException, IncorrectParamException {
        IdValidation.validateId(userId);
        IdValidation.validateId(orderId);

        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalUser.isEmpty()){
            throw new GeneralPersistenceException(NO_USER_ID);
        }
        if (optionalOrder.isEmpty()){
            throw new GeneralPersistenceException(NO_ORDER_ID);
        }
        if (!optionalUser.get().getOrders().contains(optionalOrder.get())){
            throw new GeneralPersistenceException(NOT_USER_ORDER);
        }
        return OrderItemForInfo.fromOrder(optionalOrder.get());
    }


    /*
    This is only for adding users to test the application.
    The method will not be used by clients other than me,
    so I did not add any validation.
     */
    @Override
    @Transactional
    public UserItem create(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setEmail(userDto.getEmail());
        return UserItem.fromUser(userRepository.save(user));
    }
}
