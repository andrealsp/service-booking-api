package service_booking_api.infrastructure.persistence.identity;

import service_booking_api.core.domain.auth.entity.IdentityEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface IdentityRepository extends JpaRepository<IdentityEntity, UUID> {

    // Fix: Select from 'Identity' (the entity name) instead of 'User'
    @Query("SELECT i FROM IdentityEntity i WHERE i.username = :username")
    Optional<IdentityEntity> findByUsername(@Param("username") String username);

    @Query("SELECT i FROM IdentityEntity i WHERE i.email = :email")
    Optional<IdentityEntity> findByEmail(@Param("email") String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
