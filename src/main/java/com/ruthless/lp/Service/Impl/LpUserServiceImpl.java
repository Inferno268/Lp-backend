package com.ruthless.lp.Service.Impl;

import com.ruthless.lp.DTO.UserDTO;
import com.ruthless.lp.Model.LpUser;
import com.ruthless.lp.Repository.LpUserRepository;
import com.ruthless.lp.Service.LpUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@ResponseStatus
@Service
public class LpUserServiceImpl implements LpUserService {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private LpUserRepository userRepository;


    @Override
    public void createUser(UserDTO userDTO) {
        verifyCredentials(userDTO);
        LpUser dbUser = new LpUser();
        dbUser.setUsername(userDTO.getUsername());
        dbUser.setEmail(userDTO.getEmail());
        dbUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(dbUser);
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
        userRepository.deleteById(id);
    }

    @Override
    public LpUser updateUser(Long id, UserDTO userDTO) {
        LpUser existingUser = userRepository.getById(id);
        verifyCredentialsforUpdate(userDTO);
        existingUser.setUsername(userDTO.getUsername());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userRepository.save(existingUser);
    }

    public void verifyCredentials(UserDTO user){
        userRepository.findByUsername(user.getUsername());
        if (user.getUsername() == null || user.getPassword() == null || user.getEmail() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please fill in all fields");
        }

        if (!emailValidation(user.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is not in the correct format");
        }


        if (!passwordValidationOnregister(user.getPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is not in the correct format");
        }

        if (user.getUsername().length() < 5 || user.getUsername().length() > 25){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username must be between 5 to 25 characters");
        }

        if (userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already taken");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already taken");
        }
    }
        public void verifyCredentialsforUpdate(UserDTO user){
            if (user.getUsername() == null || user.getPassword() == null || user.getEmail() == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please fill in all fields");
            }

            if (!emailValidation(user.getEmail())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is not in the correct format");
            }


            if (!passwordValidationOnregister(user.getPassword())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is not in the correct format");
            }

            if (user.getUsername().length() < 5 || user.getUsername().length() > 25){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username must be between 5 to 25 characters");
            }

            if (userRepository.findByUsername(user.getUsername()).isPresent() && compareId(user)) {
                throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "Username already taken");
            }
            if (userRepository.findByEmail(user.getEmail()).isPresent()&& compareId(user)){
                throw new ResponseStatusException(HttpStatus.SEE_OTHER, "Email already taken");
            }
        }

    public boolean compareId(UserDTO dto){
        Optional<LpUser> user = userRepository.findByUsername(dto.getUsername());
        if (user.isPresent()) {
            LpUser lpUser = user.get(); // Retrieve the wrapped LpUser object
            if (lpUser.getId() != null && lpUser.getUsername() != null){
                System.out.println("Ne");
                return false;

            }

        }

        System.out.println("Ano");
            return true;
    }
    @Override
    public Optional<LpUser> getUserById(Long id) {
        Optional<LpUser> user = userRepository.findById(id);
        return user;
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
        return passwordEncoder.matches(dbPassword, inputPassword);
    }

    public boolean passwordValidationOnregister(String inputPassword){
        Pattern pattern = Pattern.compile("^(?=.*\\d.*\\d.*\\d)(?=.*[A-Z].*[A-Z].*[A-Z])[A-Za-z0-9]{5,25}$");
        return pattern.matcher(inputPassword).matches();
    }
    public boolean emailValidation(String email){
        Pattern pattern = Pattern.compile("^((?!\\.)[\\w-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$");
        return pattern.matcher(email).matches();
    }
}

