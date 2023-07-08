package com.example.prova3_web.service;

import com.example.prova3_web.domain.Usuario;
import com.example.prova3_web.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService extends GenericService<Usuario, UsuarioRepository> {
    public UsuarioService(UsuarioRepository repository){
        super(repository);
    }
}
