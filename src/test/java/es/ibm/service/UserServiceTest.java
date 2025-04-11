package es.ibm.service;

import es.ibm.usermanagement.model.User;
import es.ibm.usermanagement.repository.UserRepository;
import es.ibm.usermanagement.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    // Simulamos el repositorio para que no toque la base de datos.
    @Mock
    private UserRepository userRepository;

    // Se mete el mock dentro del servicio, así podemos probar el servicio sin que dependa del repositorio real.
    @InjectMocks
    private UserService userService;

    // Un usuario de prueba que vamos a usar en los tests.
    private User user;

    @BeforeEach
    void setup() {
        // Iniciamos los mocks para que funcionen bien.
        MockitoAnnotations.openMocks(this);

        // Creamos un usuario dummy para probar.
        user = new User();
        user.setNombre("Carlos");
        user.setApellidos("Gomez");
        user.setEdad(30);
        user.setSuscripcion(true);
        user.setCodigo_postal("28001");
    }

    // Test para comprobar que el usuario se guarda.
    @Test
    void crearUsuario_debeGuardarUsuario() {
        // Cuando se llame al save, devolvemos el user de prueba.
        when(userRepository.save(any(User.class))).thenReturn(user);
        User result = userService.crearUsuario(user);
        // Comprobamos que el nombre sea el que esperamos.
        assertEquals("Carlos", result.getNombre());
    }

    // Test para probar a obtener un usuario existente por UUID.
    @Test
    void obtenerUsuario_debeDevolverUsuarioExistente() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        // Cuando se pida por ID, devolvemos un Optional con el user
        Optional<User> result = userService.obtenerUsuario("uuid");
        assertTrue(result.isPresent());
        // Comprobamos que sí lo encontró y que es correcto.
        assertEquals("Carlos", result.get().getNombre());
    }

    // Test para probar si el filtrado por nombre funciona.
    @Test
    void filtrarPorNombre_debeDevolverUsuarios() {
        // Creamos una "página" de resultados con solo nuestro usuario.
        Page<User> page = new PageImpl<>(Collections.singletonList(user));

        // Configuramos el mock para que devuelva esa página cuando se busque por nombre.
        when(userRepository.findByNombreContainingIgnoreCase(eq("Carlos"), any(Pageable.class))).thenReturn(page);

        // Ejecutamos el método con los parámetros de prueba.
        Page<User> result = userService.filtrarPorNombre("Carlos", PageRequest.of(0, 10));

        // Confirmamos que devolvió exactamente 1 resultado.
        assertEquals(1, result.getTotalElements());
    }
}