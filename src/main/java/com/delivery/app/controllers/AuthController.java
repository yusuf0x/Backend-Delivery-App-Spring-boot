package com.delivery.app.controllers;

import com.delivery.app.config.JwtService;
import com.delivery.app.config.UserDetailsImpl;
import com.delivery.app.models.User;
import com.delivery.app.payload.request.LoginRequest;
import com.delivery.app.payload.request.RegisterDeliveryRequest;
import com.delivery.app.payload.response.ApiResponse;
import com.delivery.app.payload.response.JwtResponse;
import com.delivery.app.services.PersonService;
import com.delivery.app.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PersonService personService;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, PersonService personService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.personService = personService;
    }

//    @PostMapping("register")
//    public ResponseEntity<?> register(@RequestBody SignupRequest request){
////        if (userService.existsByUsername(request.getUsername())) {
////            return ResponseEntity
////                    .badRequest()
////                    .body(new ApiResponse(false,"Error: Username is already taken!"));
////        }
////
////        if (userService.existsByEmail(request.getEmail())) {
////            return ResponseEntity
////                    .badRequest()
////                    .body(new ApiResponse(false,"Error: Email is already in use!"));
////        }
//        int randomNumber = (int) (10000 + Math.random() * 90000);
//        User user = User.builder()
//                .username(request.getUsername())
//                .email(request.getEmail())
//                .passwordd(passwordEncoder.encode(request.getPassword()))
//                .notificationToken(String.valueOf(randomNumber))
//                .build();
//        Person person = Person.builder()
//                .firstName(request.getUsername())
//                .created(new Date())
//                .build();
////        personService.savePerson(person);
//        user.setPerson(person);
//        userService.save(user);
//        // send Email Verify with randomNumber
//        return ResponseEntity.ok(new ApiResponse(true,"User registered successfully!"));
//    }
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        if (!userService.existsByEmail(request.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse(false,"Email not found !"));
        }
        User user = userService.findByEmail(request.getEmail());
//        if(!user.isEmailVerified()){
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body(new ApiResponse(false,"Verify Your Email Please"));
//        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String jwt = jwtService.createToken(new HashMap<>(),request.getEmail());
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtService.generateToken(userDetails);
//        System.out.println(userDetails.getUsername());
        return ResponseEntity.ok(JwtResponse.builder()
                        .email(request.getEmail())
                        .id(userDetails.getId())
                        .token(jwt)
                        .username(userDetails.getUsername())
                        .status(userDetails.isEnabled())
                        .roles(userDetails.getAuthorities().stream().map(item -> item.getAuthority() ).toList())
                .build());
    }
    @PostMapping("/client")
    public ResponseEntity<?> registerClient(
            @RequestPart("file") MultipartFile file,
            @RequestPart("client") RegisterDeliveryRequest client
    ) {
        if (userService.existsByEmail(client.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse(false,"Error: Email is already in use!"));
        }
        try {
            userService.registerClient(client,file);
            return ResponseEntity.ok(new ApiResponse(true, "Client successfully registered"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
    @PostMapping("/delivery")
    public ResponseEntity<?> registerDelivery(
            @RequestPart("file") MultipartFile file,
            @RequestPart("delivery") RegisterDeliveryRequest delivery
            ) {
        if (userService.existsByEmail(delivery.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse(false,"Error: Email is already in use!"));
        }
        try {
            userService.registerDelivery(delivery,file);
            return ResponseEntity.ok(new ApiResponse(true, "Delivery person successfully registered"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
}
