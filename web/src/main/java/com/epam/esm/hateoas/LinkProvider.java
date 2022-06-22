package com.epam.esm.hateoas;

import com.epam.esm.dto.response.TagItem;

public interface LinkProvider<T> {

    void link(T t, String rel);

}
