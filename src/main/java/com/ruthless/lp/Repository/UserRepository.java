package com.ruthless.lp.Repository;

import com.ruthless.lp.Model.LpUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<LpUser, Long> {

    Optional<LpUser> findByUsername(String username);

    Optional<LpUser> findByEmail(String email);
}
