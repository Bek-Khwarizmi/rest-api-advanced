package com.epam.esm.entity.entityListeners;

import com.epam.esm.DateFormatISO8601.CurrentDate;
import com.epam.esm.entity.Order;

import javax.persistence.PrePersist;

/*
Custom entity listener for setting time automatically when order created.
 */

public class OrderEntityListener {

    @PrePersist
    void onPrePersist(Order order){
        order.setDate(CurrentDate.now());
    }
}
