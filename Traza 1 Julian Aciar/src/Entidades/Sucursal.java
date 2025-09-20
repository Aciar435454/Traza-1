package Entidades;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder

public class Sucursal {
    private Long id;
    private String nombre;
    private LocalTime horarioApertura;
    private LocalTime horarioCierre;
    private boolean esCasaMatriz;
    private Domicilio domicilio;
    private Empresa empresa;
}