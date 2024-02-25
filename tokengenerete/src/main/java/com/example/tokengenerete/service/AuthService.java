package com.example.tokengenerete.service;


import com.example.tokengenerete.model.AuthenticationResponse;
import com.example.tokengenerete.model.User;
import com.example.tokengenerete.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
//@Data
@Service
public class AuthService {

    private  final UserRepository repo;

    private  final PasswordEncoder passwordEncoder;

    private  final  JwtService jwtService ;

    private  final AuthenticationManager authenticationManager;



    public AuthService(UserRepository repo,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,AuthenticationManager authenticationManager) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager=authenticationManager;
    }

    public AuthenticationResponse register(User request){
        User user=new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(request.getRole());

        user =repo.save(user);


        String token=jwtService.generateToken(user);


        return new AuthenticationResponse(token);

    }

    public  AuthenticationResponse login(User request){


        System.out.printf("Loginnnnnnnn "+request.getUsername(),request.getPassword());


        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user=repo.findByUsername(request.getUsername()).orElseThrow();
        String token=jwtService.generateToken(user);

        return new   AuthenticationResponse(token);
    }
}
