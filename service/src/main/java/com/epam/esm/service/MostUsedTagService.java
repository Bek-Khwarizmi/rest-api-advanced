package com.epam.esm.service;

import com.epam.esm.dto.response.TagItem;
import com.epam.esm.exception.GeneralPersistenceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MostUsedTagService {

    Page<TagItem> find(Pageable pageable) throws GeneralPersistenceException;

}
