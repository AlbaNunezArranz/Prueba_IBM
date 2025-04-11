package es.ibm.usermanagement.controller;

import es.ibm.usermanagement.model.User;
import es.ibm.usermanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

// Definición del controlador REST para solicitudes HTTP.
@RestController

// Mapea todas las solicitudes que comiencen con "/api/users".
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService service;

    // Método para crear un nuevo usuario (se activa con una solicitud POST).
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(service.crearUsuario(user));
    }

    // Método para obtener un usuario por su UUID (se activa con una solicitud GET).
    @GetMapping("/{uuid}")
    @Operation(summary = "get_user", description = "Usuario por UUID.")
    public ResponseEntity<User> getUser(@PathVariable String uuid) {
        // Llama al servicio para obtener el usuario.
        Optional<User> user = service.obtenerUsuario(uuid);

        // Si se encuentra el usuario, devuelve una respuesta con el usuario.
        // Si no, devuelve un '404 Not Found'.
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Método para buscar usuarios por nombre o edad con paginación (se activa con una solicitud GET).
    @GetMapping
    @Operation(summary = "search_users", description = "Filtrar usuarios por nombre o edad con paginación.")
    public ResponseEntity<Page<User>> searchUsers(@RequestParam(required = false) String nombre,
                                                  @RequestParam(required = false) Integer edad,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        // Se crea un objeto 'PageRequest' para la paginación, usando 'page' y 'size'.
        PageRequest pageRequest = PageRequest.of(page, size);

        // Si se proporciona un 'nombre', se filtran por nombre.
        if (nombre != null)
            return ResponseEntity.ok(service.filtrarPorNombre(nombre, pageRequest));

        // Si se proporciona una 'edad', se filtran por edad
        if (edad != null)
            return ResponseEntity.ok(service.filtrarPorEdad(edad, pageRequest));

        // Si no se proporciona nombre ni edad, devuelve un '400 Bad Request'.
        return ResponseEntity.badRequest().build();
    }
}