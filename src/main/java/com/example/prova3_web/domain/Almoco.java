package com.example.prova3_web.domain;

import com.example.prova3_web.controller.AlmocoController;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
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
public class Almoco extends AbstractEntity{
    @NotBlank
    private String nomeAlmoco;
    @NotNull
    @Min(0)
    private float preco;
    @NotBlank
    private String descricao;
    @NotBlank
    private String tamanho;

    @Override
    public void partialUpdate(AbstractEntity e) {
        if(e instanceof Almoco almoco){
            this.nomeAlmoco = almoco.nomeAlmoco;
            this.preco = almoco.preco;
            this.descricao = almoco.descricao;
            this.tamanho = almoco.tamanho;
        }
    }

    @Data
    public static class DtoRequest {
        @NotBlank(message = "Almoço com nome em branco")
        String nomeAlmoco;
        @NotBlank(message = "Preço com nome em branco")
        float preco;
        @NotBlank(message = "Descrição com nome em branco")
        String descricao;
        @NotBlank(message = "Tamanho com nome em branco")
        String tamanho;

        public static Almoco convertToEntity(DtoRequest dto, ModelMapper mapper) {
            return mapper.map(dto, Almoco.class);
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class DtoResponse extends RepresentationModel<DtoResponse> {
        Long id;
        String nomeAlmoco;
        String preco;
        String descricao;
        String tamanho;

        public static DtoResponse convertToDto(Almoco almoco, ModelMapper mapper) {
            return mapper.map(almoco, DtoResponse.class);
        }

        public void generateLinks(Long id){
            add(linkTo(AlmocoController.class).slash(id).withSelfRel());
            add(linkTo(AlmocoController.class).withRel("usuarios"));
            add(linkTo(AlmocoController.class).slash(id).withRel("delete"));
        }
    }
}






