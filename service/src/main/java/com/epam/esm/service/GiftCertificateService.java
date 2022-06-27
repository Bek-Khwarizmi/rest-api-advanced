package com.epam.esm.service;

import com.epam.esm.dto.request.GiftCertificateDto;
import com.epam.esm.dto.response.GiftCertificateItem;
import com.epam.esm.exception.GeneralPersistenceException;
import com.epam.esm.exception.IncorrectParamException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GiftCertificateService {

    Page<GiftCertificateItem> list(List<String> tags, String part, String sort, Pageable pageable) throws GeneralPersistenceException, IncorrectParamException;

    GiftCertificateItem getById(Long id) throws GeneralPersistenceException, IncorrectParamException;

    GiftCertificateItem create(GiftCertificateDto giftCertificateDto) throws IncorrectParamException;

    GiftCertificateItem update(Long id, GiftCertificateDto giftCertificateDto) throws GeneralPersistenceException, IncorrectParamException;

    GiftCertificateItem delete(Long id) throws GeneralPersistenceException, IncorrectParamException;

}
