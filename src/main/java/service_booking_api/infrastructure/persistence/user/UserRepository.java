package service_booking_api.infrastructure.persistence.user;

import service_booking_api.core.domain.auth.entity.IdentityEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<IdentityEntity, UUID> {

    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<IdentityEntity> findByUsername(@Param("username") String username);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<IdentityEntity> findByEmail(@Param("email") String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}
