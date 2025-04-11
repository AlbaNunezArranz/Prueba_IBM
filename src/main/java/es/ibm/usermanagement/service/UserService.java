package es.ibm.usermanagement.service;

import es.ibm.usermanagement.model.User;
import es.ibm.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    // Inyección de la dependencia 'UserRepository' para interactuar con la base de datos.
    @Autowired
    private UserRepository repository;

    // Método para crear un nuevo usuario.
    // Recibe un 'User' y lo guarda en la bbdd.
    public User crearUsuario(User user) {
        return repository.save(user);
    }

    // Método para obtener el usuario por UUID.
    // Devuelve un 'Optional<User>' para manejar posibles valores nulos.
    public Optional<User> obtenerUsuario(String uuid) {
        // Se llama al 'findById' para encontrar el usuario.
        return repository.findById(uuid);
    }

    // Método para filtrar por nombre.
    // Ignora mayúsculas y minúsculas, y soporta paginación.
    public Page<User> filtrarPorNombre(String nombre, Pageable pageable) {
        return repository.findByNombreContainingIgnoreCase(nombre, pageable);
    }

    // Método para filtrar por edad.
    // Devuelve los resultados de la búsqueda de forma paginada.
    public Page<User> filtrarPorEdad(int edad, Pageable pageable) {
        return repository.findByEdad(edad, pageable);
    }
}
