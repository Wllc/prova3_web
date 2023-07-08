package com.example.prova3_web.service;

import com.example.prova3_web.domain.Endereco;
import com.example.prova3_web.repository.EnderecoRepository;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService extends GenericService<Endereco, EnderecoRepository> {
    public EnderecoService(EnderecoRepository repository){
        super(repository);
    }
}
