package com.example.prova3_web.controller;

import com.example.prova3_web.domain.Almoco;
import com.example.prova3_web.service.AlmocoService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/almocos")
@CrossOrigin(origins = "http://localhost:3000")
public class AlmocoController {
    AlmocoService service;
    ModelMapper mapper;
    public AlmocoController(AlmocoService service, ModelMapper mapper){
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Almoco.DtoResponse create(@RequestBody Almoco.DtoRequest a){
        Almoco almoco = this.service.create(Almoco.DtoRequest.convertToEntity(a, mapper));

        Almoco.DtoResponse response = Almoco.DtoResponse.convertToDto(almoco, mapper);
        response.generateLinks(almoco.getId());

        return response;
    }

    @GetMapping
    public List<Almoco.DtoResponse> list(){
        return this.service.list().stream().map(
            elementoAtual -> {
                Almoco.DtoResponse response = Almoco.DtoResponse.convertToDto(elementoAtual, mapper);
                response.generateLinks(elementoAtual.getId());
                return response;
            }).toList();
    }
    @GetMapping("/{id}")
    public Almoco.DtoResponse getById(@PathVariable Long id){
        Almoco a = this.service.getById(id);
        Almoco.DtoResponse response = Almoco.DtoResponse.convertToDto(a, mapper);
        response.generateLinks(a.getId());

        return response;
    }
    @PutMapping("/{id}")
    public Almoco.DtoResponse update(@RequestBody Almoco.DtoRequest dtoRequest, @PathVariable Long id){
        Almoco a = Almoco.DtoRequest.convertToEntity(dtoRequest, mapper);
        Almoco.DtoResponse response = Almoco.DtoResponse.convertToDto(this.service.update(a, id), mapper);
        response.generateLinks(id);
        return response;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        this.service.delete(id);
    }

}
