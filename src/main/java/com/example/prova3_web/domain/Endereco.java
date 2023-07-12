package com.example.prova3_web.domain;

import com.example.prova3_web.controller.EnderecoController;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endereco extends AbstractEntity{

    private String rua;
    private int numero;
    private String bairro;
    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Override
    public void partialUpdate(AbstractEntity e) {
        if (e instanceof Endereco endereco){
            this.rua = endereco.rua;
            this.numero = endereco.numero;
            this.bairro = endereco.bairro;
        }
    }

    @Data
    public static class DtoRequest {
        @NotBlank(message = "Rua com nome em branco")
        private String rua;
        @NotNull(message = "Numero com nome em branco")
        private int numero;
        @NotBlank(message = "Bairro com nome em branco")
        private String bairro;

        public static Endereco convertToEntity(DtoRequest dto, ModelMapper mapper) {
            return mapper.map(dto, Endereco.class);
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class DtoResponse extends RepresentationModel<DtoResponse> {
        String rua;
        int numero;
        String bairro;

        public static DtoResponse convertToDto(Endereco e, ModelMapper mapper) {
            return mapper.map(e, DtoResponse.class);
        }

        public void generateLinks(Long id){
            add(linkTo(EnderecoController.class).slash(id).withSelfRel());
            add(linkTo(EnderecoController.class).slash(id).withRel("delete"));
            add(linkTo(EnderecoController.class).withRel("usuarios"));
        }
    }
}
