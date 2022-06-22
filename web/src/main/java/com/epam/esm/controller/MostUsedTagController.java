package com.epam.esm.controller;

import com.epam.esm.dto.response.TagItem;
import com.epam.esm.exception.GeneralPersistenceException;
import com.epam.esm.hateoas.LinkProvider;
import com.epam.esm.service.MostUsedTagService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class {@code MostUsedTagController} which allows performing R operations on tags.
 * Annotated by {@link RestController} with no parameters to provide an answer in application/json.
 * Annotated by {@link RequestMapping} with parameter value = "/most-used-tag".
 * The Class can be accessed by sending request to "/most-used-tag".
 *
 * @author Bekhzod Kurbonboev
 * @since 1.0
 */
@RestController
@RequestMapping("most-used-tag")
public class MostUsedTagController {

    private final MostUsedTagService mostUsedTagService;
    private final LinkProvider<TagItem> tagLink;

    public MostUsedTagController(
            MostUsedTagService mostUsedTagService,
            @Qualifier("link_for_tag") LinkProvider<TagItem> tagLink) {
        this.mostUsedTagService = mostUsedTagService;
        this.tagLink = tagLink;
    }

    /**
     * Method for retrieving page of the most widely used tag(s) of a user with the highest cost of all orders
     * @param pageable
     * @return page of tags if there is available tag in the database
     * @throws GeneralPersistenceException if there is no tag in the database
     */
    @GetMapping
    public ResponseEntity find(Pageable pageable) throws GeneralPersistenceException {
        Page<TagItem> tags = mostUsedTagService.find(pageable);
        tags.forEach(tag -> tagLink.link(tag, "GET"));

        return ResponseEntity.ok(tags);
    }
}
