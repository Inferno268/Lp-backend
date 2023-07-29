package com.ruthless.lp.Service.Impl;

import com.ruthless.lp.Exception.MyException;
import com.ruthless.lp.Model.LpUser;
import com.ruthless.lp.Repository.UserRepository;
import com.ruthless.lp.Service.LpUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@ResponseStatus
@Service
public class LpUserServiceImpl implements LpUserService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;
    @Override
    public void createUser(LpUser user) {
        if (user.getUsername() == null || user.getPassword() == null || user.getEmail() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fill in all fields");
        }

        if (!emailValidation(user.getEmail())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email is not in the correct format");
        }


        if (!passwordValidationOnregister(user.getPassword())){
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "Password is not in the correct format");
        }

        if (user.getUsername().length() < 5 || user.getUsername().length() > 25){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Username must be between 5 to 25 characters");
        }

        if (userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new ResponseStatusException(HttpStatus.LOCKED, "Username already taken");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already taken");
        }
//        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
//        user.setPassword(encodedPassword);
        userRepository.save(user);
    }
    //Login method
    @Override
    public void login(String username, String password) {
        Optional<LpUser> lpUsers = userRepository.findByUsername(username);
        if (lpUsers.isEmpty()) {
            throw new UsernameNotFoundException("User wasn't found");
        }
        if (lpUsers.get().getUsername() == null || lpUsers.get().getPassword() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fill in all fields");
        }
        //Returns user, if there is user under the inserted username
        LpUser user = lpUsers.get();
        if (!passwordValidationOnLogin(password, user.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect password");
        }
    }
    @Override
    public void deleteUser(Long id) {

    }

    @Override
    public ResponseEntity<LpUser> updateUser(LpUser user) {
        return null;
    }

    @Override
    public Optional<ResponseEntity<LpUser>> getUserById(Long userId) {
        return Optional.empty();
    }

    @Override
    public Optional<LpUser> getUserByUserName(String username) {
        return Optional.empty();
    }

    @Override
    public ResponseEntity<List<LpUser>> getAllUsers() {
        return null;
    }

    public boolean passwordValidationOnLogin(String dbPassword, String inputPassword){
        return bCryptPasswordEncoder.matches(dbPassword, inputPassword);
    }

    public boolean passwordValidationOnregister(String inputPassword){
        Pattern pattern = Pattern.compile("^(?=.*\\d.*\\d.*\\d)(?=.*[A-Z].*[A-Z].*[A-Z])[A-Za-z0-9]{5,25}$");
        return pattern.matcher(inputPassword).matches();
    }
    public boolean emailValidation(String email){
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$\n");
        return pattern.matcher(email).matches();
    }
}
