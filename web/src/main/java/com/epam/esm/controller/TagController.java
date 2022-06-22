package com.epam.esm.controller;

import com.epam.esm.dto.request.TagDto;
import com.epam.esm.dto.response.TagItem;
import com.epam.esm.exception.GeneralPersistenceException;
import com.epam.esm.exception.IncorrectParamException;
import com.epam.esm.hateoas.LinkProvider;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Class {@code TagController} which allows performing CRD operations on tags.
 * Annotated by {@link RestController} with no parameters to provide an answer in application/json.
 * Annotated by {@link RequestMapping} with parameter value = "/tag".
 * The Class can be accessed by sending request to "/tag".
 *
 * @author Bekhzod Kurbonboev
 * @since 1.0
 */
@RestController
@RequestMapping("tag")
public class TagController {

    private final TagService tagService;
    private final LinkProvider<TagItem> tagLink;

    public TagController(
            TagService tagService,
            @Qualifier("link_for_tag") LinkProvider<TagItem> tagLink) {
        this.tagService = tagService;
        this.tagLink = tagLink;
    }

    /**
     * Method for retrieving page of tags
     * @param pageable
     * @return page of tags
     * @throws GeneralPersistenceException if there is no tag in the database
     */
    @GetMapping
    public ResponseEntity list(Pageable pageable) throws GeneralPersistenceException {
        Page<TagItem> tagItems = tagService.list(pageable);
        tagItems.forEach(tagItem -> tagLink.link(tagItem, "GET"));
        return ResponseEntity.ok(tagItems);
    }

    /**
     * Method for retrieving tag specified by :id
     * @param id
     * @return tag if exists
     * @throws GeneralPersistenceException if there is no tag that has specified :id
     * @throws IncorrectParamException if :id parameter is non-positive
     */
    @GetMapping("{id}")
    public ResponseEntity getById(@PathVariable Long id) throws GeneralPersistenceException, IncorrectParamException {
        TagItem tagItem = tagService.getById(id);
        tagLink.link(tagItem, "GET");
        return ResponseEntity.ok(tagItem);
    }

    /**
     * Method for saving tag dto to the database
     * @param tagDto - has only one parameter (:name)
     * @return tag if transaction completes successfully
     * @throws GeneralPersistenceException if there is a tag in the database that has the same (:name)
     * @throws IncorrectParamException if tag (:name) violates constraints
     */
    @PostMapping
    public ResponseEntity create(@RequestBody TagDto tagDto) throws GeneralPersistenceException, IncorrectParamException {
        TagItem tagItem = tagService.create(tagDto);
        tagLink.link(tagItem, "POST");
        return ResponseEntity.ok(tagItem);
    }

    /**
     * Method for deleting tag specified by :id
     * @param id
     * @return tag that has been deleted if transaction completes successfully
     * @throws GeneralPersistenceException if there is no tag that has specified :id, or tag is connected to gift certificate(s)
     * @throws IncorrectParamException if :id parameter is non-positive
     */
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id) throws GeneralPersistenceException, IncorrectParamException {
        TagItem tagItem = tagService.delete(id);
        tagLink.link(tagItem, "DELETE");
        return ResponseEntity.ok(tagItem);
    }
}
