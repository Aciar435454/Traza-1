import Entidades.*;
import Repositorios.InMemoryRepository;

import java.time.LocalTime;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println(">>> INICIANDO PRUEBA DEL SISTEMA <<<");

        // Inicializar repositorio
        InMemoryRepository<Empresa> repoEmpresas = new InMemoryRepository<>();

        // Crear país y sucursales
        Pais argentina = Pais.builder().nombre("Argentina").build();

        Sucursal s1 = crearSucursal(1, "Sucursal 1", true, "Calle 1", 100, 1000, "CABA", "Buenos Aires", argentina);
        Sucursal s2 = crearSucursal(2, "Sucursal 2", false, "Calle 2", 200, 2000, "La Plata", "Buenos Aires", argentina);
        Sucursal s3 = crearSucursal(3, "Sucursal 3", true, "Calle 3", 300, 3000, "Córdoba Capital", "Córdoba", argentina);
        Sucursal s4 = crearSucursal(4, "Sucursal 4", false, "Calle 4", 400, 4000, "Villa Carlos Paz", "Córdoba", argentina);

        // Empresas
        Empresa e1 = Empresa.builder()
                .nombre("Empresa 1")
                .razonSocial("Razon Social 1")
                .cuil(12345678901L)
                .sucursales(new HashSet<>(Set.of(s1, s2)))
                .build();

        Empresa e2 = Empresa.builder()
                .nombre("Empresa 2")
                .razonSocial("Razon Social 2")
                .cuil(22225678901L)
                .sucursales(new HashSet<>(Set.of(s3, s4)))
                .build();

        // Vinculación bidireccional
        s1.setEmpresa(e1);
        s2.setEmpresa(e1);
        s3.setEmpresa(e2);
        s4.setEmpresa(e2);

        // Guardar en repositorio
        repoEmpresas.save(e1);
        repoEmpresas.save(e2);

        // Mostrar todas las empresas
        System.out.println(">>> EMPRESAS REGISTRADAS:");
        repoEmpresas.findAll().forEach(System.out::println);

        // Mostrar sucursales de empresa 2
        repoEmpresas.findById(2L).ifPresent(emp -> {
            System.out.println(">>> SUCURSALES DE " + emp.getNombre() + ":");
            for (Sucursal suc : emp.getSucursales()) {
                System.out.println(suc);
            }
        });

        // Buscar empresa por nombre
        System.out.println(">>> BUSCANDO EMPRESA 1:");
        List<Empresa> filtradas = repoEmpresas.genericFindByField("nombre", "Empresa 1");
        for (Empresa emp : filtradas) {
            System.out.println(emp);
        }

        // Actualizar empresa 1
        Empresa e1Modificada = Empresa.builder()
                .id(1L)
                .nombre("Empresa 1 Actualizada")
                .razonSocial("Razon Social 1 Actualizada")
                .cuil(12345678901L)
                .sucursales(e1.getSucursales())
                .build();

        repoEmpresas.genericUpdate(1L, e1Modificada);
        repoEmpresas.findById(1L).ifPresent(emp ->
                System.out.println(">>> EMPRESA ACTUALIZADA: " + emp)
        );

        // Eliminar empresa 1
        repoEmpresas.genericDelete(1L);
        if (repoEmpresas.findById(1L).isEmpty()) {
            System.out.println(">>> Empresa con ID 1 eliminada.");
        }

        // Mostrar empresas restantes
        System.out.println(">>> EMPRESAS RESTANTES:");
        for (Empresa emp : repoEmpresas.findAll()) {
            System.out.println(emp);
        }
    }

    // ---------------- MÉTODO AUXILIAR ----------------
    private static Sucursal crearSucursal(long id, String nombre, boolean matriz,
                                          String calle, int numero, int cp,
                                          String locNombre, String provNombre, Pais pais) {

        Provincia provincia = Provincia.builder()
                .id(id)
                .nombre(provNombre)
                .pais(pais)
                .build();

        Localidad localidad = Localidad.builder()
                .id(id)
                .nombre(locNombre)
                .provincia(provincia)
                .build();

        Domicilio dom = Domicilio.builder()
                .id(id)
                .calle(calle)
                .numero(numero)
                .cp(cp)
                .localidad(localidad)
                .build();

        return Sucursal.builder()
                .id(id)
                .nombre(nombre)
                .esCasaMatriz(matriz)
                .horarioApertura(LocalTime.of(9, 0))
                .horarioCierre(LocalTime.of(18, 0))
                .domicilio(dom)
                .build();
    }
}
