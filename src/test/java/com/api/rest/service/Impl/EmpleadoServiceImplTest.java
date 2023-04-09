package com.api.rest.service.Impl;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import com.api.rest.exception.ResourceNotFoundException;
import com.api.rest.model.Empleado;
import com.api.rest.repository.EmpleadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class EmpleadoServiceImplTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @InjectMocks
    private EmpleadoServiceImpl empleadoService;

    private Empleado empleado;

    @BeforeEach
    void setUp() {
        empleado = Empleado.builder()
                .id(1L)
                .nombre("Jose")
                .apellido("Lopez")
                .email("jose@gmail.com")
                .build();

    }

    @DisplayName("Test para guardar un empleado")
    @Test
    void saveEmpleado() {
        //given
        given(empleadoRepository.findByEmail(empleado.getEmail()))
                .willReturn(Optional.empty());
        given(empleadoRepository.save(empleado)).willReturn(empleado);
        Empleado empleadoGuardado = empleadoService.saveEmpleado(empleado);
        assertThat(empleadoGuardado).isNotNull();
    }

    @DisplayName("Test para guardar un empleado con throw exception")
    @Test
    void saveEmpleadoThrowException() {
        //given
        given(empleadoRepository.findByEmail(empleado.getEmail()))
                .willReturn(Optional.of(empleado));

        assertThrows(
                ResourceNotFoundException.class,()->{
                    empleadoService.saveEmpleado(empleado);
                });

        verify(empleadoRepository, never()).save(any(Empleado.class));
    }

    @DisplayName("Test para listar a los empleados")
    @Test
    void getAllEmpleados() {
        //given
        Empleado empleado1 = Empleado.builder()
                .id(2L)
                .nombre("Nombre1")
                .apellido("Apellido1")
                .email("email1")
                .build();

        given(empleadoRepository.findAll()).willReturn(
                List.of(empleado,empleado1)
        );
        //when
        List<Empleado> empleados = empleadoService.getAllEmpleados();
        //then
        assertThat(empleados).isNotNull();
        assertThat(empleados.size()).isEqualTo(2);
    }


    @DisplayName("Test para listar a los empleados")
    @Test
    void getAllEmpleadosListaVacia() {
        //given
        Empleado empleado1 = Empleado.builder()
                .id(2L)
                .nombre("Nombre1")
                .apellido("Apellido1")
                .email("email1")
                .build();

        given(empleadoRepository.findAll()).willReturn(
                Collections.emptyList()
        );
        //when
        List<Empleado> empleados = empleadoService.getAllEmpleados();
        //then
        assertThat(empleados.size()).isEqualTo(0);
    }

    @DisplayName("Test para buscar empleado por Id")
    @Test
    void getEmpleadoById() {
        //given
        given(empleadoRepository.findById(1L))
                .willReturn(Optional.of(empleado));

        //when
        Empleado empleadoGuardado = empleadoService.getEmpleadoById(empleado.getId()).get();

        //then
        assertThat(empleadoGuardado).isNotNull();
    }

    @DisplayName("Test para actualizar empleado por Id")
    @Test
    void updateEmpleado() {
        given(empleadoRepository.save(empleado))
                .willReturn(empleado);
        empleado.setNombre("prueba2");
        empleado.setApellido("prueba2");
        empleado.setEmail("prueba2@mail.com");
        Empleado empleado1 = empleadoService.saveEmpleado(empleado);
        assertThat(empleado1.getNombre()).isEqualTo("prueba2");
        assertThat(empleado1.getApellido()).isEqualTo("prueba2");
        assertThat(empleado1.getEmail()).isEqualTo("prueba2@mail.com");
    }

    @DisplayName("Test para eliminar un empleado")
    @Test
    void deleteEmpleado() {
        //given
        long empleadoId = 1L;
        willDoNothing().given(empleadoRepository).deleteById(empleadoId);
        //when
        empleadoService.deleteEmpleado(empleadoId);
        //then
        verify(empleadoRepository,times(1)).deleteById(empleadoId);
    }
}