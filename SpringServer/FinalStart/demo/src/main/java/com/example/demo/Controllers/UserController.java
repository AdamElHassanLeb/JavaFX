package com.example.demo.Controllers;

import com.example.demo.Exceptions.*;
import com.example.demo.Models.*;
import com.example.demo.Services.*;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/userById/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/userByUsername/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User savedUser = userService.insertUser(user);
            return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
        } catch (ServiceConstraintViolationException e) {
            String errorMessage = e.getMessage(); // Get the error message from the exception
            return new ResponseEntity<String>(errorMessage, HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/user")
    public ResponseEntity<?> updateUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid user data");
        }
        try {
            User updatedUser = userService.updateUser(user);
            return ResponseEntity.ok(updatedUser);
        }catch (ServiceConstraintViolationException e) {
            String errorMessage = e.getMessage(); // Get the error message from the exception
            return new ResponseEntity<String>(errorMessage, HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/authUser")
    public ResponseEntity<User> authUser(@RequestBody AuthJson auth) {
        return ResponseEntity.ok(userService.authUser(auth.username, auth.password));
    }

    static class AuthJson{
        public AuthJson(){}
        @NotBlank(message = "Username cannot be blank")
        public String username;
        @NotBlank(message = "Password cannot be blank")
        public String password;
    }

}
