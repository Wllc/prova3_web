package com.example.prova3_web.domain;

import com.example.prova3_web.controller.PedidoController;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido extends AbstractEntity{

    @NotNull
    @Min(1)
    private int quantidade;
    @NotBlank
    private String observacao;

    //1-N
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    //N-N
    @ManyToMany
    @JoinTable(
            name = "pedido_almoco",
            joinColumns = { @JoinColumn(name = "pedido_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "almoco_id")})
    private List<Almoco> almocos = new ArrayList<Almoco>();

    @Override
    public void partialUpdate(AbstractEntity e) {
        if(e instanceof Pedido pedido){
            this.quantidade = pedido.quantidade;
            this.observacao = pedido.observacao;
        }
    }

    @Data
    public static class DtoRequest{
        @NotBlank(message = "Quantidade com nome em branco")
        String quantidade;
        @NotBlank(message = "Observação com nome em branco")
        String observacao;
        @NotNull
        @Min(1)
        @Max(1)
        Long usuario_id;
        @NotNull
        List<Long> almocos_id;

        public static Pedido convertToEntity(DtoRequest dto, ModelMapper mapper){
            return mapper.map(dto, Pedido.class);
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class DtoResponse extends RepresentationModel<DtoResponse> {
        String quantidade;
        String observacao;
        @JsonIgnoreProperties({"login", "password", "isAdmin", "endereco", "pedidos", "enabled",
                "authorities", "credentialsNonExpired", "accountNonExpired", "accountNonLocked",
                "deletedAt", "createdAt", "updatedAt"})
        Usuario usuario;
        @JsonIgnoreProperties({"descricao", "tamanho", "deletedAt", "createdAt", "updatedAt"})
        List<Almoco> almocos;

        public static DtoResponse convertToDto(Pedido p, ModelMapper mapper){
            return mapper.map(p, DtoResponse.class);
        }

        public void generateLinks(Long id){
            add(linkTo(PedidoController.class).slash(id).withSelfRel());
            add(linkTo(PedidoController.class).withRel("pedidos"));
            add(linkTo(PedidoController.class).slash(id).withRel("delete"));
        }
    }


}
