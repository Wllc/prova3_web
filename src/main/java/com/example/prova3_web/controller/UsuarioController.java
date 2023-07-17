package com.example.prova3_web.controller;

import com.example.prova3_web.domain.Usuario;
import com.example.prova3_web.service.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    UsuarioService service;
    ModelMapper mapper;

    public UsuarioController(UsuarioService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario.DtoResponse create(@RequestBody Usuario.DtoRequest u) {
        Usuario usuario = Usuario.DtoRequest.convertToEntity(u, mapper);
        this.service.createUsuario(usuario);

        Usuario.DtoResponse response = Usuario.DtoResponse.convertToDto(usuario, mapper);
        response.generateLinks(usuario.getId());
        return response;

    }

//    @GetMapping
//    public List<Usuario.DtoResponse> list(){
//        return this.service.list().stream().map(
//                elementoAtual -> {
//                    Usuario.DtoResponse response = Usuario.DtoResponse.convertToDto(elementoAtual, mapper);
//                    response.generateLinks(elementoAtual.getId());
//                    return response;
//                }).toList();
//    }
    @GetMapping
    public ResponseEntity<Page<Usuario.DtoResponse>> find(Pageable page) {

        Page<Usuario.DtoResponse> dtoResponses = service
                .find(page)
                .map(record -> {
                    Usuario.DtoResponse response = Usuario.DtoResponse.convertToDto(record, mapper);
                    response.generateLinks(record.getId());
                    return response;
                });

        return new ResponseEntity<>(dtoResponses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Usuario.DtoResponse getById(@PathVariable Long id){
        Usuario usuario = this.service.getById(id);
        Usuario.DtoResponse response = Usuario.DtoResponse.convertToDto(usuario, mapper);
        response.generateLinks(usuario.getId());

        return response;
    }

    @PutMapping("/{id}")
    public Usuario.DtoResponse update(@RequestBody Usuario.DtoRequest dtoRequest, @PathVariable Long id) {
        Usuario u = Usuario.DtoRequest.convertToEntity(dtoRequest, mapper);
        Usuario.DtoResponse response = Usuario.DtoResponse.convertToDto(this.service.update(u, id), mapper);
        response.generateLinks(id);
        return response;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.service.delete(id);
    }
}
