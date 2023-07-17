package com.example.prova3_web.controller;

import com.example.prova3_web.domain.Pedido;
import com.example.prova3_web.service.PedidoService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    PedidoService service;
    ModelMapper mapper;

    public PedidoController(PedidoService service, ModelMapper mapper){
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido.DtoResponse create(@RequestBody Pedido.DtoRequest p){
        Pedido pedido = this.service.create(Pedido.DtoRequest.convertToEntity(p, mapper), p.getAlmocos_id(), p.getUsuario_id());

        Pedido.DtoResponse response = Pedido.DtoResponse.convertToDto(pedido, mapper);
        response.generateLinks(pedido.getId());
        return response;
    }

    @GetMapping
    public ResponseEntity<Page<Pedido.DtoResponse>> find(Pageable page) {

        Page<Pedido.DtoResponse> dtoResponses = service
                .find(page)
                .map(record -> {
                    Pedido.DtoResponse response = Pedido.DtoResponse.convertToDto(record, mapper);
                    response.generateLinks(record.getId());
                    return response;
                });

        return new ResponseEntity<>(dtoResponses, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public Pedido.DtoResponse getById(@PathVariable Long id){

        Pedido pedido = this.service.getById(id);
        Pedido.DtoResponse response = Pedido.DtoResponse.convertToDto(pedido, mapper);
        response.generateLinks(pedido.getId());

        return response;
    }


    @PutMapping("/{id}")
    public Pedido.DtoResponse update(@RequestBody Pedido.DtoRequest dtoRequest, @PathVariable Long id){
        Pedido p = Pedido.DtoRequest.convertToEntity(dtoRequest, mapper);
        Pedido.DtoResponse response = Pedido.DtoResponse.convertToDto(this.service.update(p, id), mapper);
        response.generateLinks(id);
        return response;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        this.service.delete(id);
    }
}
