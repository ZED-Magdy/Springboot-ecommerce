package com.zed.ecommerce;

import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HelloController {
    private static final Logger logger = LogManager.getLogger(HelloController.class);
    private final UserService _userService;

    public HelloController(UserService userService) {
        this._userService = userService;
    }
    @GetMapping("/hello")
    public String sayHello() {
        logger.info("Info level log example");
        logger.debug("Debug level log example");
        logger.error("Error level log example", new Exception("Example exception"));
        return "Hello, World!";
    }

    @PostMapping("/user")
    public ResponseEntity createUser(@Valid @RequestBody UserDto userDto) {
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
