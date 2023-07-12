package com.example.prova3_web.repository;

import com.example.prova3_web.domain.Usuario;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepository extends IGenericRepository<Usuario> {
    UserDetails findByLogin(String login);

}
