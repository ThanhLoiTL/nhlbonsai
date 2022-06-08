package com.nhlshop.service;

import java.util.Collection;

import org.springframework.data.domain.Pageable;

public interface IPageService<T> {
    Collection<T> findAll(Pageable pageable);

    int totalItem();
}
