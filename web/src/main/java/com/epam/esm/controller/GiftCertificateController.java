package com.epam.esm.controller;

import com.epam.esm.dto.request.GiftCertificateDto;
import com.epam.esm.dto.response.GiftCertificateItem;
import com.epam.esm.exception.GeneralPersistenceException;
import com.epam.esm.exception.IncorrectParamException;
import com.epam.esm.hateoas.LinkProvider;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class {@code GiftCertificateController}
 * which allows performing CRUD and addition operations on gift certificates.
 * Annotated by {@link RestController} with no parameters to provide an answer in application/json.
 * Annotated by {@link RequestMapping} with parameter value = "/gift-certificate".
 * The Class can be accessed by sending request to "/gift-certificate".
 *
 * @author Bekhzod Kurbonboev
 * @since 1.0
 */
@RestController
@RequestMapping("gift-certificate")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;
    private final LinkProvider<GiftCertificateItem> giftCertificateLink;

    public GiftCertificateController(
            GiftCertificateService giftCertificateService,
            @Qualifier("link_for_giftCertificate") LinkProvider<GiftCertificateItem> giftCertificateLink) {
        this.giftCertificateService = giftCertificateService;
        this.giftCertificateLink = giftCertificateLink;
    }

    /**
     * Method for retrieving page of gift certificates
     * @param tags that is the list of tag name(s)
     * @param part is used to search gift certificates by their :part of names, or description
     * @param sort is used to sort result page by :name, :date or both of gift certificates
     * @param pageable
     * @return page of gift certificates
     * @throws GeneralPersistenceException if there is no gift certificate in the database
     * @throws IncorrectParamException is :sort param violates constraints
     */
    @GetMapping
    public ResponseEntity list(
            @RequestParam(required = false) List<String> tags,
            @RequestParam(required = false) String part,
            @RequestParam(required = false) String sort,
            Pageable pageable) throws GeneralPersistenceException, IncorrectParamException {
        Page<GiftCertificateItem> list = giftCertificateService.list(tags, part, sort, pageable);
        list.forEach(item -> giftCertificateLink.link(item, "GET"));

        return ResponseEntity.ok(list);
    }

    /**
     * Method for retrieving gift certificate that has specified :id
     * @param id
     * @return gift certificate that has specified :id
     * @throws GeneralPersistenceException if there is no gift certificate that has specified :id
     * @throws IncorrectParamException if :id parameter is non-positive
     */
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id) throws GeneralPersistenceException, IncorrectParamException {
        GiftCertificateItem giftCertificateItem = giftCertificateService.getById(id);
        giftCertificateLink.link(giftCertificateItem, "GET");

        return ResponseEntity.ok(giftCertificateItem);
    }

    /**
     * Method for saving gift certificate
     * @param giftCertificateDto that has seven parameters (:name, :description, :price, :duration, :createdDate, :lastUpdatedDate, :tags)
     * @return gift certificate that has been saved
     * @throws IncorrectParamException if one or more of parameters of giftCertificateDto violates gift certificate constraints
     */
    @PostMapping
    public ResponseEntity create(@RequestBody GiftCertificateDto giftCertificateDto) throws IncorrectParamException {
        GiftCertificateItem giftCertificateItem = giftCertificateService.create(giftCertificateDto);
        giftCertificateLink.link(giftCertificateItem, "POST");

        return ResponseEntity.ok(giftCertificateItem);
    }

    /**
     * Method for updating gift certificate that has specified :id
     * @param id
     * @param giftCertificateDto that has seven parameters (:name, :description, :price, :duration, :createdDate, :lastUpdatedDate, :tags)
     * @return gift certificate that has been updated
     * @throws GeneralPersistenceException if there is no gift certificate that has specified :id
     * @throws IncorrectParamException if one or more of parameters of giftCertificateDto violates gift certificate constraints,
     * or :id parameter is non-positive
     */
    @PatchMapping("/{id}")
    public ResponseEntity update(
            @PathVariable Long id,
            @RequestBody GiftCertificateDto giftCertificateDto) throws GeneralPersistenceException, IncorrectParamException {
        GiftCertificateItem giftCertificateItem = giftCertificateService.update(id, giftCertificateDto);
        giftCertificateLink.link(giftCertificateItem, "PUT");

        return ResponseEntity.ok(giftCertificateItem);
    }

    /**
     * Method for deleting gift certificate that has specified :id
     * @param id
     * @return gift certificate that has been deleted
     * @throws GeneralPersistenceException if there is no gift certificate in the database that has specified :id
     * @throws IncorrectParamException if :id parameter is non-positive
     */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) throws GeneralPersistenceException, IncorrectParamException {
        GiftCertificateItem giftCertificateItem = giftCertificateService.delete(id);
        giftCertificateLink.link(giftCertificateItem, "DELETE");

        return ResponseEntity.ok(giftCertificateItem);
    }
}
