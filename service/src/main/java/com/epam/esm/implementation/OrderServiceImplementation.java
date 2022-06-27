package com.epam.esm.implementation;

import com.epam.esm.dto.request.OrderDto;
import com.epam.esm.dto.response.OrderItemForInfo;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.GeneralPersistenceException;
import com.epam.esm.exception.IncorrectParamException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.OrderService;
import com.epam.esm.validation.IdValidation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.epam.esm.exception.GeneralPersistenceExceptionMessageCode.*;

@Service
public class OrderServiceImplementation implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final GiftCertificateRepository giftCertificateRepository;

    public OrderServiceImplementation(OrderRepository orderRepository, UserRepository userRepository, GiftCertificateRepository giftCertificateRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.giftCertificateRepository = giftCertificateRepository;
    }

    @Override
    @Transactional
    public OrderItemForInfo create(OrderDto orderDto) throws GeneralPersistenceException, IncorrectParamException {
        Long userId = orderDto.getUserId();
        Long giftCertificateId = orderDto.getGiftCertificateId();

        IdValidation.validateId(userId);
        IdValidation.validateId(giftCertificateId);

        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateRepository.findById(giftCertificateId);
        if (optionalUser.isEmpty()){
            throw new GeneralPersistenceException(NO_USER_ID);
        }
        if (optionalGiftCertificate.isEmpty()){
            throw new GeneralPersistenceException(NO_GIFT_CERTIFICATE_ID);
        }
        Order order = new Order();
        User user = optionalUser.get();
        GiftCertificate giftCertificate = optionalGiftCertificate.get();
        order.setCost(giftCertificate.getPrice());
        order.setUser(user);
        order.setGiftCertificate(giftCertificate);

        return OrderItemForInfo.fromOrder(orderRepository.save(order));
    }

    @Override
    public Page<OrderItemForInfo> list(Pageable pageable) throws GeneralPersistenceException {
        Page<Order> list = orderRepository.findAll(pageable);
        if(list.isEmpty()){
            throw new GeneralPersistenceException(NO_ORDER_ENTITY_FOUND);
        }
        return list.map(OrderItemForInfo::fromOrder);
    }

    @Override
    public OrderItemForInfo getById(Long id) throws GeneralPersistenceException, IncorrectParamException {
        IdValidation.validateId(id);

        Optional<Order> optionalOrder = orderRepository.findById(id);
        if(optionalOrder.isEmpty()){
            throw new GeneralPersistenceException(NO_ORDER_ID);
        }
        return OrderItemForInfo.fromOrder(optionalOrder.get());
    }
}
