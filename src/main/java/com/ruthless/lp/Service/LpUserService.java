package com.ruthless.lp.Service;

import com.ruthless.lp.Model.LpUser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface LpUserService {

    void createUser(LpUser user);

    void login(String username, String password);

    void deleteUser(Long id);

    ResponseEntity<LpUser> updateUser(LpUser user);
    Optional<ResponseEntity<LpUser>> getUserById(Long userId);

    Optional<LpUser> getUserByUserName(String username);

    ResponseEntity<List<LpUser>> getAllUsers();


}
