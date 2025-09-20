package Entidades;

import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder

public class Domicilio {
    private Long id;
    private String calle;
    private Integer numero;
    private Integer cp;
    private Localidad localidad;
}