package raf.rs.demo.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import raf.rs.demo.model.User;

import java.beans.Transient;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsernameAndPassword(String username, String password);

    Optional<User> findUserByUsername(String username);

    @Modifying
    @Transactional
    @Query(value="UPDATE User u SET u.active = true where u.id =?1", nativeQuery = true)
    void activateUser(Long id);

    @Modifying
    @Transactional
    @Query(value="UPDATE User u SET u.active = false where u.id =?1", nativeQuery = true)
    void deactiveteUser(Long id);

    @Modifying
    @Transactional
    @Query(value="UPDATE User u SET u.password =?1 where u.id =?2")
    void changePassword(String password, Long id);
}
