package com.epam.esm.implementation;

import com.epam.esm.dto.request.TagDto;
import com.epam.esm.dto.response.TagItem;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.GeneralPersistenceException;
import com.epam.esm.exception.IncorrectParamException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.validation.IdValidation;
import com.epam.esm.validation.TagValidation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.exception.GeneralPersistenceExceptionMessageCode.*;

@Service
public class TagServiceImplementation implements TagService {

    private final TagRepository tagRepository;

    public TagServiceImplementation(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Page<TagItem> list(Pageable pageable) throws GeneralPersistenceException {
        Page<TagItem> list = tagRepository.findAll(pageable).map(TagItem::fromTag);
        if(list.isEmpty()){
            throw new GeneralPersistenceException(NO_TAG_ENTITY_FOUND);
        }
        else{
            return list;
        }
    }

    @Override
    public TagItem getById(Long id) throws GeneralPersistenceException, IncorrectParamException {
        IdValidation.validateId(id);

        Optional<Tag> optionalTag = tagRepository.findById(id);
        if (optionalTag.isEmpty()){
            throw new GeneralPersistenceException(NO_TAG_ID);
        }else{
            return TagItem.fromTag(optionalTag.get());
        }
    }

    @Override
    @Transactional
    public TagItem create(TagDto tagDto) throws GeneralPersistenceException, IncorrectParamException {
        TagValidation.validate(tagDto);

        Optional<Tag> optionalTag = tagRepository.findTagByName(tagDto.getName());
        if (optionalTag.isEmpty()){
            Tag tag = new Tag();
            tag.setName(tagDto.getName());
            return TagItem.fromTag(tagRepository.save(tag));
        }else{
            throw new GeneralPersistenceException(TAG_ALREADY_EXISTS);
        }
    }

    @Override
    @Transactional
    public TagItem delete(Long id) throws GeneralPersistenceException, IncorrectParamException {
        IdValidation.validateId(id);

        Optional<Tag> optionalTag = tagRepository.findById(id);
        if (optionalTag.isEmpty()){
            throw new GeneralPersistenceException(NO_TAG_ID);
        }
        Tag tag = optionalTag.get();
        List<GiftCertificate> giftCertificates = tag.getGiftCertificates();
        if (giftCertificates!=null && !giftCertificates.isEmpty()){
            throw new GeneralPersistenceException(TAG_HAS_CONNECTION);
        }
        tagRepository.delete(tag);
        return TagItem.fromTag(tag);
    }

    /*
    Method for widely used tag(s) of a user
     */

    @Override
    public Page<TagItem> findMostUsedTag(Pageable pageable) throws GeneralPersistenceException {
        Page<Tag> tags = tagRepository.findMostUsedTag(pageable);
        if(tags.isEmpty()){
            throw new GeneralPersistenceException(NO_TAG_ENTITY_FOUND);
        }

        return tags.map(TagItem::fromTag);
    }
}
