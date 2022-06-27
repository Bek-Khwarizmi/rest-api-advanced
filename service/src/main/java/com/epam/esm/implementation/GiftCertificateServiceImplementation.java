package com.epam.esm.implementation;

import com.epam.esm.dto.request.GiftCertificateDto;
import com.epam.esm.dto.request.TagDto;
import com.epam.esm.dto.response.GiftCertificateItem;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.GeneralPersistenceException;
import com.epam.esm.exception.IncorrectParamException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validation.GiftCertificateValidation;
import com.epam.esm.validation.IdValidation;
import com.epam.esm.validation.SortValidation;
import com.epam.esm.validation.TagValidation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.epam.esm.exception.GeneralPersistenceExceptionMessageCode.*;

@Service
public class GiftCertificateServiceImplementation implements GiftCertificateService {

    private final GiftCertificateRepository giftCertificateRepository;
    private final TagRepository tagRepository;

    public GiftCertificateServiceImplementation(
            GiftCertificateRepository giftCertificateRepository,
            TagRepository tagRepository) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public Page<GiftCertificateItem> list(
            List<String> tags, String part,
            String sort, Pageable pageable) throws GeneralPersistenceException, IncorrectParamException {
        SortValidation.validateSort(sort);

        Page<GiftCertificate> list;
        List<GiftCertificateItem> result;
        if(part == null){
            list = giftCertificateRepository.findAll(sort, pageable);
        }
        else{
            list = giftCertificateRepository.findByPartOfNameOrDescription(part, sort, pageable);
        }

        if(list==null || list.isEmpty()){
            throw new GeneralPersistenceException(NO_GIFT_CERTIFICATE_ENTITY_FOUND);
        }

        if(tags != null){
            Set<Tag> requiredTags = new HashSet<>();
            //We do not need to use tags that are not present in db:
            for (String tagName : tags) {
                Optional<Tag> optionalTag = tagRepository.findTagByName(tagName);
                optionalTag.ifPresent(requiredTags::add);
            }

            //If all given tags for search are invalid we do need to do the rest of the work,
            // because anyway there will not be any valid gift certificate, we simply throw exception:
            if(requiredTags.isEmpty()){
                throw new GeneralPersistenceException(NO_GIFT_CERTIFICATE_ENTITY_FOUND);
            }

            result = list.stream().filter(giftCertificate -> {
                Set<Tag> availableTag = giftCertificate.getTags();
                if(availableTag==null) {
                    return false;
                }
                for(Tag tag: requiredTags){
                    if (availableTag.contains(tag)){
                        return true;
                    }
                }
                return false;
            }).map(GiftCertificateItem::fromGiftCertificate).collect(Collectors.toList());

            if(result.isEmpty()){
                throw new GeneralPersistenceException(NO_GIFT_CERTIFICATE_ENTITY_FOUND);
            }

            return createPage(result, pageable);
        }

        result = list.map(GiftCertificateItem::fromGiftCertificate).getContent();
        return createPage(result, pageable);
    }

    @Override
    public GiftCertificateItem getById(Long id) throws GeneralPersistenceException, IncorrectParamException {
        IdValidation.validateId(id);

        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateRepository.findById(id);
        if(optionalGiftCertificate.isEmpty()){
            throw new GeneralPersistenceException(NO_GIFT_CERTIFICATE_ID);
        }
        return GiftCertificateItem.fromGiftCertificate(optionalGiftCertificate.get());
    }

    @Override
    @Transactional
    public GiftCertificateItem create(GiftCertificateDto giftCertificateDto) throws IncorrectParamException {
        GiftCertificateValidation.validate(giftCertificateDto);

        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName(giftCertificateDto.getName());
        giftCertificate.setDescription(giftCertificateDto.getDescription());
        giftCertificate.setPrice(giftCertificateDto.getPrice());
        giftCertificate.setDuration(giftCertificateDto.getDuration());
        Set<Tag> tags = createSetOfTagsFromSetOfTagDtos(giftCertificateDto.getTags());
        giftCertificate.setTags(tags);

        return GiftCertificateItem.fromGiftCertificate(giftCertificateRepository.save(giftCertificate));
    }

    @Override
    @Transactional
    public GiftCertificateItem update(Long id, GiftCertificateDto giftCertificateDto) throws GeneralPersistenceException, IncorrectParamException {
        GiftCertificateValidation.validateForUpdate(giftCertificateDto);

        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateRepository.findById(id);
        if(optionalGiftCertificate.isEmpty()){
            throw new GeneralPersistenceException(NO_GIFT_CERTIFICATE_ID);
        }
        else{
            GiftCertificate giftCertificate = optionalGiftCertificate.get();
            if(giftCertificateDto.getName()!=null){
                giftCertificate.setName(giftCertificateDto.getName());
            }
            if(giftCertificateDto.getDescription()!=null){
                giftCertificate.setDescription(giftCertificateDto.getDescription());
            }
            if(giftCertificateDto.getPrice()!=null){
                giftCertificate.setPrice(giftCertificateDto.getPrice());
            }
            if(giftCertificateDto.getDuration()!=null){
                giftCertificate.setDuration(giftCertificateDto.getDuration());
            }
            if(giftCertificateDto.getTags()!=null){
                Set<Tag> tags = createSetOfTagsFromSetOfTagDtos(giftCertificateDto.getTags());
                giftCertificate.setTags(tags);
            }
            return GiftCertificateItem.fromGiftCertificate(giftCertificateRepository.save(giftCertificate));
        }
    }

    @Override
    @Transactional
    public GiftCertificateItem delete(Long id) throws GeneralPersistenceException, IncorrectParamException {
        IdValidation.validateId(id);

        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateRepository.findById(id);
        if(optionalGiftCertificate.isEmpty()){
            throw new GeneralPersistenceException(NO_GIFT_CERTIFICATE_ID);
        }
        GiftCertificate giftCertificate = optionalGiftCertificate.get();
        if(giftCertificate.getOrders()!=null && !giftCertificate.getOrders().isEmpty()){
            throw new GeneralPersistenceException(GIFT_CERTIFICATE_HAS_CONNECTION);
        }
        giftCertificateRepository.delete(giftCertificate);
        return GiftCertificateItem.fromGiftCertificate(giftCertificate);
    }

    /*
    Method for creating connections. It first saves Tags into database
    then sets them to gift certificate. Thus, if we save gift certificate
    Hibernate will do the rest: creates ManyToMany connection table.
     */
    private Set<Tag> createSetOfTagsFromSetOfTagDtos(Set<TagDto> tags) throws IncorrectParamException {
        if (tags==null) return new HashSet<>();
        for (TagDto tagDto: tags){
            TagValidation.validate(tagDto);
        }

        return tags
                .stream()
                .map(tagDto -> {
                    Optional<Tag> optionalTag = tagRepository.findTagByName(tagDto.getName());
                    if(optionalTag.isEmpty()){
                        Tag tag = new Tag();
                        tag.setName(tagDto.getName());
                        return tagRepository.save(tag);
                    }else{
                        return optionalTag.get();
                    }
                })
                .collect(Collectors.toSet());
    }

    /*
    Page of gift certificates are filtered in this layer in list() method.
    This method is used to do pagination after filtering.
     */
    private Page<GiftCertificateItem> createPage(List<GiftCertificateItem> certificates, Pageable pageable) throws GeneralPersistenceException {
        final int size = pageable.getPageSize();
        final int page = pageable.getPageNumber();
        if(certificates.size()<size*page){
            throw new GeneralPersistenceException(NO_GIFT_CERTIFICATE_ENTITY_FOUND);
        }
        final int limit = Math.min((page+1)*size, certificates.size());
        List<GiftCertificateItem> result = new ArrayList<>();
        for (int i = page*size; i<limit; i++){
            result.add(certificates.get(i));
        }
        return new PageImpl<>(result, pageable, result.size());
    }
}
