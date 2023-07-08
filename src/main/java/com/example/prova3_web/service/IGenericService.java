package com.example.prova3_web.service;

import com.example.prova3_web.domain.AbstractEntity;
import jakarta.validation.constraints.Min;

import java.util.List;

public interface IGenericService <E extends AbstractEntity>{
    public E create(E e);
    public E update(E e, Long id);
    public void delete(Long id);
    public List<E> list();
    public E getById(Long id);
}
