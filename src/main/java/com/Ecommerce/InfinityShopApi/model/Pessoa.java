package com.Ecommerce.InfinityShopApi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Pessoa {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_pessoa")
    private Long id;

    @NotNull
    @Size(min = 3,max = 35)
    private String nome;
    @NotNull
    private Boolean ativo;
    @Embedded
    private Endereco endereco;
    @JsonIgnore
    @Transient
    public boolean isInativo(){
        return !this.ativo;
    }
}
