package com.epam.esm.repository;

import com.epam.esm.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepository{

    private final String FIND_ALL =
            "SELECT o FROM Order o";


    @PersistenceContext
    private EntityManager entityManager;

    public Optional<Order> findById(Long id){
        Order order = entityManager.find(Order.class, id);
        if (order != null){
            return Optional.of(order);
        }
        return Optional.empty();
    }

    public Order save(Order order){
        entityManager.persist(order);
        if (order.getId() != null)
            return order;
        return null;
    }

    public Page<Order> findAll(Pageable pageable){
        int size = pageable.getPageSize();
        int page = pageable.getPageNumber();
        List<Order> orders = entityManager
                .createQuery(FIND_ALL, Order.class)
                .setMaxResults(size)
                .setFirstResult(page * size)
                .getResultList();

        return new PageImpl<>(orders, pageable, orders.size());
    }

}
