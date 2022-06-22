package com.epam.esm.entity;

import com.epam.esm.entity.entityListeners.OrderEntityListener;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

/*
I did not put ant constraints to fields of this entity because
cost is taken from gift certificate thus it had been verified
by validation in Service layer when gift certificate was created
and date field is completed by entity listeners.
 */

@Entity
@Getter
@Setter
@Table(name = "orders")
@EntityListeners(OrderEntityListener.class)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal cost;

    private String date;

    @ManyToOne
    private User user;

    @ManyToOne
    private GiftCertificate giftCertificate;

    public Order() {
    }

    public Order(Long id, BigDecimal cost, String date,
                 User user, GiftCertificate giftCertificate) {
        this.id = id;
        this.cost = cost;
        this.date = date;
        this.user = user;
        this.giftCertificate = giftCertificate;
    }
}
