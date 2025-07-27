package com.zed.ecommerce.controllers;

import com.zed.ecommerce.Dtos.LoginResponse;
import com.zed.ecommerce.Dtos.LoginUserDto;
import com.zed.ecommerce.Dtos.UserDto;
import com.zed.ecommerce.services.JwtService;
import com.zed.ecommerce.exceptions.UserAlreadyExistsException;
import com.zed.ecommerce.exceptions.UserNotFoundException;
import com.zed.ecommerce.services.UserService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HelloController {
    private static final Logger logger = LogManager.getLogger(HelloController.class);
    private final UserService _userService;
    private final JwtService _jwtService;

    public HelloController(UserService userService, JwtService jwtService) {
        this._userService = userService;
        _jwtService = jwtService;
    }
    @GetMapping("/hello")
    public String sayHello() {
//        logger.info("Info level log example");
//        logger.debug("Debug level log example");
//        logger.error("Error level log example", new Exception("Example exception"));
        return "Hello, World!";
    }

    @PostMapping("/auth/register")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserDto userDto) {
        logger.info("Creating user: {}", userDto);
        try {
            UserDto createdUser = _userService.create(userDto);

            logger.info("User created successfully: {}", createdUser);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (UserAlreadyExistsException e) {
            logger.error("User creation failed: {}", e.getMessage());

            var message = e.getMessage();
            var details = new HashMap<>(Map.of("message", message));

            return ResponseEntity.badRequest().body(details);
        } catch (Exception e) {
            logger.error("Unexpected error during user creation: {}", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "An unexpected error occurred while creating the user"));
        }
    }

    @PostMapping("auth/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginUserDto loginDto) {
        try {
            var user = _userService.login(loginDto);
            String jwtToken = _jwtService.generateToken(user);

            UserDto userDto = new UserDto(
                    user.getId(),
                    user.getEmail(),
                    "", // Password is not returned for security reasons
                    user.getFirstName(),
                    user.getLastName(),
                    user.getPhoneNumber(),
                    user.getAddress(),
                    Date.from(OffsetDateTime.parse(user.getBirthDate()).toInstant())
            );

            LoginResponse loginResponse = new LoginResponse(jwtToken, _jwtService.getExpirationTime(), userDto);

            return ResponseEntity.ok(loginResponse);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(Map.of("message", "User not found"));
        }

    }

    @GetMapping("/user/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        logger.info("Fetching all users");
        try {
            List<UserDto> users = _userService.findAll();
            logger.info("Users fetched successfully");
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            logger.error("Error fetching users: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
