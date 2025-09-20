package Entidades;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString(exclude = "sucursales")
@SuperBuilder

public class Empresa {
    private Long id;
    private String nombre;
    private String razonSocial;
    private Long cuil;
    private String logo;

    @Builder.Default
    private Set<Sucursal> sucursales = new HashSet<>();
}
