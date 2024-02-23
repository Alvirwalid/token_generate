package com.example.tokengenerete.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {


    @GetMapping("demo")
    public ResponseEntity<String>getDemo(){
        return  ResponseEntity.ok("Hello Demo" );
    }


    @GetMapping("admin_only")
    public ResponseEntity<String>adminOnly(){
        return  ResponseEntity.ok("Hello Admin Only" );
    }

    @GetMapping("user_only")
    public ResponseEntity<String>userOnly(){
        return  ResponseEntity.ok("Hello User Only" );
    }
}
