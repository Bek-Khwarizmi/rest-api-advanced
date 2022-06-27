package com.epam.esm.entity.entityListeners;

import com.epam.esm.DateFormatISO8601.CurrentDate;
import com.epam.esm.entity.GiftCertificate;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/*
Custom entity listener for setting time automatically when gift certificate created and updated.
 */

public class GiftCertificateEntityListener {

    @PrePersist
    void onPrePersist(GiftCertificate giftCertificate){
        giftCertificate.setCreateDate(CurrentDate.now());
        giftCertificate.setLastUpdateDate(CurrentDate.now());
    }

    @PreUpdate
    void onPreUpdate(GiftCertificate giftCertificate){
        giftCertificate.setLastUpdateDate(CurrentDate.now());
    }
}
