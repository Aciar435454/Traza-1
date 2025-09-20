package Entidades;

import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder

public class Localidad {
    private Long id;
    private String nombre;
    private Provincia provincia;
}