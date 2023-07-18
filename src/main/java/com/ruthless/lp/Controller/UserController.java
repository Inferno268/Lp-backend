package com.ruthless.lp.Controller;

import com.ruthless.lp.Exception.MyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/user")
public class UserController {


    @GetMapping("/error")
    public void profile(){
        throw new MyException("andad",HttpStatus.IM_USED );
    }
}
