package com.example.prova3_web.service;

import com.example.prova3_web.domain.AbstractEntity;
import com.example.prova3_web.repository.IGenericRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public abstract class GenericService<E extends AbstractEntity, R extends IGenericRepository<E>> implements IGenericService<E> {
    R repository;

    public GenericService(R repository) {
        this.repository = repository;
    }


    @Override
    public E create(E e) {
        return (E) this.repository.save(e);
    }

    @Override
    public E update(E updatedEntity, Long id) {

        Optional<E> entity = repository.findById(id);
        if (entity.isPresent()){
            E e = entity.get();
            e.partialUpdate(updatedEntity);
            return (E) this.repository.save(e);
        }else{
            throw  new EntityNotFoundException();
        }
    }

    @Override
    public E getById(Long id) {
        Optional<E> pessoaBanco = repository.findById(id);
        if (pessoaBanco.isPresent()){
            return (E) pessoaBanco.get();
        }else{
            throw  new EntityNotFoundException();
        }
    }

    @Override
    public void delete(Long id) {
        this.repository.deleteById(id);
    }

    @Override
    public List<E> list() {
        return (List<E>) this.repository.findAll();
    }
}