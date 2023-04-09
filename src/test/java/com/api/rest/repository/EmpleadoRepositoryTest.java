package com.api.rest.repository;

import com.api.rest.model.Empleado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EmpleadoRepositoryTest {

    @Autowired
    private EmpleadoRepository e,empleadoRepository;

    private Empleado empleadoInit;

    @BeforeEach
    void setUp() {
        empleadoInit = Empleado.builder()
                .nombre("Cristian")
                .apellido("Ramirez")
                .email("prueba@gmail.com")
                .build();
    }

    @Test
    void findByEmail() {
    }

    @DisplayName("Test para guardar un empleado")
    @Test
    public void guardarEmpleado(){
        //given - dao o condicion previa
        Empleado empleado = Empleado.builder()
                .nombre("Jonnathan")
                .apellido("Campoberde")
                .email("jonnathanca9601@gmail.com")
                .build();

        //when -acción o el comportamiento que vamos a dar
        Empleado empleadoGuardado = empleadoRepository.save(empleado);

        //Then - verificar la salida

        assertThat(empleadoGuardado.getId()).isGreaterThan(0);

    }

    @DisplayName("Test para listar a los empleados")
    @Test
    void listarEmpleados(){
        //given
        Empleado empleado = Empleado.builder()
                .nombre("Jose")
                .apellido("Lopez")
                .email("jose@gmail.com")
                .build();

        empleadoRepository.save(empleado);
        empleadoRepository.save(empleadoInit);

        //when
        List<Empleado> empleadoList = empleadoRepository.findAll();

        //then
        assertThat(empleadoList).isNotNull();
        assertThat(empleadoList.size()).isEqualTo(2);
    }

    @DisplayName("Testa para obtener un empleado por Id")
    @Test
    void testObtenerEmpleadoById(){
        Empleado empleadoGuardado = empleadoRepository.save(empleadoInit);

        //when
        Empleado empleado = empleadoRepository.findById(empleadoGuardado.getId()).get();

        assertThat(empleado).isNotNull();
    }

    @DisplayName("Test para actualizar un empleado")
    @Test
    void testActualizarEmpleado(){
        Empleado empleadoGuardar = empleadoRepository.save(empleadoInit);

        Empleado empleadoBuscar = empleadoRepository.findById(empleadoGuardar.getId()).get();

         empleadoGuardar.setEmail("prueba1@gmail.com");
         empleadoGuardar.setNombre("prueba2");
         empleadoGuardar.setApellido("prueba2");
         Empleado empleadoActualizada = empleadoRepository.save(empleadoGuardar);
         assertThat(empleadoActualizada.getEmail()).isEqualTo("prueba1@gmail.com");

    }

    @DisplayName("Eliminar empleado")
    @Test
    void eliminarEmpleado(){
        empleadoRepository.save(empleadoInit);
        empleadoRepository.deleteById(empleadoInit.getId());
        Optional<Empleado> empleadoOptional = empleadoRepository.findById(empleadoInit.getId());

        assertThat(empleadoOptional.isPresent()).isEqualTo(Boolean.FALSE);
    }
}