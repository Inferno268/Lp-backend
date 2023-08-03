package com.ruthless.lp.Service;

import com.ruthless.lp.DTO.UserDTO;
import com.ruthless.lp.Model.LpUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface LpUserService {

    void createUser(LpUser user);

    void login(String username, String password);

    void deleteUser(Long id);

    LpUser updateUser(Long id, UserDTO user);
    Optional<LpUser> getUserById(Long id);

    Optional<LpUser> getUserByUserName(String username);

    ResponseEntity<List<LpUser>> getAllUsers();


}
