package com.example.prova3_web.controller;

import com.example.prova3_web.domain.Endereco;
import com.example.prova3_web.service.EnderecoService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {
    EnderecoService service;
    ModelMapper mapper;
    public EnderecoController(EnderecoService service, ModelMapper mapper){
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Endereco.DtoResponse create(@RequestBody Endereco.DtoRequest e){
        Endereco endereco = this.service.create(Endereco.DtoRequest.convertToEntity(e, mapper));

        Endereco.DtoResponse response = Endereco.DtoResponse.convertToDto(endereco, mapper);
        response.generateLinks(endereco.getId());
        return response;
    }

//    @GetMapping
//    public List<Endereco.DtoResponse> list(){
//        return this.service.list().stream().map(
//                elementoAtual -> {
//                    Endereco.DtoResponse response = Endereco.DtoResponse.convertToDto(elementoAtual, mapper);
//                    response.generateLinks(elementoAtual.getId());
//                    return response;
//                }).toList();
//    }
    @GetMapping
    public ResponseEntity<Page<Endereco.DtoResponse>> find(Pageable page) {

        Page<Endereco.DtoResponse> dtoResponses = service
                .find(page)
                .map(record -> {
                    Endereco.DtoResponse response = Endereco.DtoResponse.convertToDto(record, mapper);
                    response.generateLinks(record.getId());
                    return response;
                });

        return new ResponseEntity<>(dtoResponses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Endereco.DtoResponse getById(@PathVariable Long id){

        Endereco endereco = this.service.getById(id);
        Endereco.DtoResponse response = Endereco.DtoResponse.convertToDto(endereco, mapper);
        response.generateLinks(endereco.getId());

        return response;
    }


    @PutMapping("/{id}")
    public Endereco.DtoResponse update(@RequestBody Endereco.DtoRequest dtoRequest, @PathVariable Long id){
        Endereco e = Endereco.DtoRequest.convertToEntity(dtoRequest, mapper);
        Endereco.DtoResponse response = Endereco.DtoResponse.convertToDto(this.service.update(e, id), mapper);
        response.generateLinks(id);
        return response;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        this.service.delete(id);
    }
}
