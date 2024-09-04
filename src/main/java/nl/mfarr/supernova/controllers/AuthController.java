package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.models.UserEntity;
import nl.mfarr.supernova.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam String username, @RequestParam String password) {
        UserEntity newUser = userService.registerNewUser(username, password);
        return ResponseEntity.ok("Gebruiker geregistreerd: " + newUser.getUsername());
    }

    @GetMapping("/login")
    public ResponseEntity<?> login() {
        return ResponseEntity.ok("Ingelogd");
    }
}
