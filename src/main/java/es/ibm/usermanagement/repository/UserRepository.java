package es.ibm.usermanagement.repository;

import es.ibm.usermanagement.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Se utiliza para interactuar con la base de datos.
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // Método para buscar usuarios por nombre, ignorando mayúsculas y minúsculas.
    // Devuelve los resultados de forma paginada (Pageable).
    Page<User> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);

    // Método para buscar usuarios por su edad.
    // Devuelve los resultados de forma paginada (Pageable).
    Page<User> findByEdad(int edad, Pageable pageable);
}
