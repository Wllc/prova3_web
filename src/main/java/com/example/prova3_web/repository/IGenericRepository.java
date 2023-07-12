package com.example.prova3_web.repository;

import com.example.prova3_web.domain.AbstractEntity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IGenericRepository<E extends AbstractEntity> extends ListCrudRepository<E, Long>, PagingAndSortingRepository<E, Long> {
}
