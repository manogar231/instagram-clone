package com.instagram.clone.Repository;

import com.instagram.clone.Entity.User;
import com.instagram.clone.Enum.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByuserName(String userName);

    @Query(value = "UPDATE user\n" +
            "SET status = CASE\n" +
            "    WHEN status = :status THEN 'INACTIVE'\n" +
            "    WHEN status = :status THEN 'ACTIVE'\n" +
            "    ELSE status\n" +
            "  END\n" +
            "WHERE id = :id;", nativeQuery = true)
    void updateStatusForUser(Long id, Status status);

    @Query(value = "update user set status = 'DELETED' where id=:id", nativeQuery = true)
    void deleteUser(Long id);

}
