package com.ruthless.lp.Controller;

import com.ruthless.lp.DTO.UserDTO;
import com.ruthless.lp.DTO.UserLoginDTO;
import com.ruthless.lp.Model.LpUser;
import com.ruthless.lp.Repository.LpUserRepository;
import com.ruthless.lp.Service.LpUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    private AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private LpUserService lpUserService;


    private LpUserRepository userRepository;


    @RequestMapping(method =RequestMethod.POST, value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    @ResponseBody
   public ResponseEntity<?> login (@RequestBody UserLoginDTO dto) throws Exception {
        log.info("Username {} Password: {}",dto.getUsername(), dto.getPassword());

        lpUserService.login(dto.getUsername(),dto.getPassword());

        return new ResponseEntity<>("Logged in",HttpStatus.OK);

    }

    public void authenticator(String username, String password) throws Exception{
        log.info("Username {} Password: {}",username, password);
    try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.SEE_OTHER);

        }
    }
    @RequestMapping(method =RequestMethod.POST, value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    @ResponseBody
    public ResponseEntity<?> register(@RequestBody UserDTO dto) throws Exception{
        LpUser user = new LpUser();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        lpUserService.createUser(user);
        return new ResponseEntity<>("User created",HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUSer(@PathVariable Long id){
        Optional<LpUser> user = lpUserService.getUserById(id);
        if (user.isEmpty()){
            return new ResponseEntity<>("User with id " + id + " doesn't exist",HttpStatus.NOT_FOUND);
        }
        lpUserService.deleteUser(id);
        return  new ResponseEntity<>("User with id " + id + " was deleted", HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @CrossOrigin
    @ResponseBody
    public LpUser updateUser(@PathVariable Long id,@RequestBody UserDTO dto) throws Exception{
        return lpUserService.updateUser(id,dto);

    }

}
