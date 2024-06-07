package auth.repository;

import auth.models.UserIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryImpl extends JpaRepository<UserIdentity, Long> {
    UserIdentity findByEmail(String email);
}
