package co.edu.unbosque.clientesback.repository;

import co.edu.unbosque.clientesback.model.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ClientRepository extends JpaRepository<ClientEntity, Integer> {
    Optional<ClientEntity> findByname(String name);

    boolean existsByEmail(String email);

    Optional<ClientEntity> findByEmail(String email);

    boolean existsByName(String name);
}
