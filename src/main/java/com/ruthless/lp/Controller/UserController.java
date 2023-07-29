package com.ruthless.lp.Controller;

import com.ruthless.lp.Model.LpUser;
import com.ruthless.lp.Service.LpUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/")
public class UserController {

    private AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private LpUserService lpUserService;


    @GetMapping("/login")
    @CrossOrigin
   public ResponseEntity<String> Login (String username, String password) throws Exception {
        lpUserService.login(username,password);
        authenticator(username,password);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    public void authenticator(String username, String password) throws Exception{
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
@PostMapping("/register")
@CrossOrigin
    public ResponseEntity<?> register(String username, String email, String password) throws Exception{
        LpUser user = new LpUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        lpUserService.createUser(user);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping
    public String getHello(){
        return "hello";
    }
}
