package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.dtos.UserRequestDto;
import nl.mfarr.supernova.dtos.UserResponseDto;
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
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRequestDto userRequestDto) {
        UserEntity newUser = userService.registerNewUser(userRequestDto.getUsername(), userRequestDto.getPassword());
        UserResponseDto userResponseDto = new UserResponseDto(newUser.getUsername());
        return ResponseEntity.ok(userResponseDto);
    }

    @GetMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("Ingelogd");
    }
}
