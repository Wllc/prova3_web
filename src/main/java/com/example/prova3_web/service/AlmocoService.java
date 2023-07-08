package com.example.prova3_web.service;

import com.example.prova3_web.domain.Almoco;
import com.example.prova3_web.repository.AlmocoRepository;
import org.springframework.stereotype.Service;

@Service
public class AlmocoService extends GenericService<Almoco, AlmocoRepository> {
    public AlmocoService(AlmocoRepository repository){
        super(repository);
    }
}
