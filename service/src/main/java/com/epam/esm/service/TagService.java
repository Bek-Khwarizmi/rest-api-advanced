package com.epam.esm.service;

import com.epam.esm.dto.request.TagDto;
import com.epam.esm.dto.response.TagItem;
import com.epam.esm.exception.GeneralPersistenceException;
import com.epam.esm.exception.IncorrectParamException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagService {

    Page<TagItem> list(Pageable pageable) throws GeneralPersistenceException;

    TagItem getById(Long id) throws GeneralPersistenceException, IncorrectParamException;

    TagItem create(TagDto tagDto) throws GeneralPersistenceException, IncorrectParamException;

    TagItem delete(Long id) throws GeneralPersistenceException, IncorrectParamException;
}
