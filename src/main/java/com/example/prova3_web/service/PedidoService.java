package com.example.prova3_web.service;

import com.example.prova3_web.domain.Almoco;
import com.example.prova3_web.domain.Pedido;
import com.example.prova3_web.domain.Usuario;
import com.example.prova3_web.repository.AlmocoRepository;
import com.example.prova3_web.repository.PedidoRepository;
import com.example.prova3_web.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService extends GenericService<Pedido, PedidoRepository> {
    UsuarioRepository usuarioRepository;
    AlmocoRepository almocoRepository;

    public PedidoService(PedidoRepository repository, UsuarioRepository usuarioRepository, AlmocoRepository almocoRepository) {
        super(repository);
        this.usuarioRepository = usuarioRepository;
        this.almocoRepository = almocoRepository;
    }


    public Pedido create(Pedido pedido, List<Long> almocos_id, Long usuario_id) {
        Optional<Usuario> usuario = usuarioRepository.findById(usuario_id);
        List<Almoco> almocos = new ArrayList<>();

        if (usuario.isPresent()){
            for (Long almoco_id : almocos_id){
                Almoco c = almocoRepository.findById(almoco_id).get();
                almocos.add(c);
                System.out.println(c);
            }
        }else throw new UsernameNotFoundException("User not found");

        pedido.setAlmocos(almocos);
        pedido.setUsuario(usuario.get());

        return this.repository.save(pedido);
    }
}
