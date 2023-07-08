package com.example.prova3_web.service;

import com.example.prova3_web.domain.Pedido;
import com.example.prova3_web.repository.PedidoRepository;
import org.springframework.stereotype.Service;

@Service
public class PedidoService extends GenericService<Pedido, PedidoRepository> {
    public PedidoService(PedidoRepository repository){
        super(repository);
    }
}
