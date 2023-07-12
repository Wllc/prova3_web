package com.example.prova3_web.service;

import com.example.prova3_web.domain.AbstractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IGenericService <E extends AbstractEntity>{
    public E create(E e);
    public E update(E e, Long id);
    public void delete(Long id);
    public List<E> list();
    public E getById(Long id);
    public Page<E> find(Pageable page);
}
