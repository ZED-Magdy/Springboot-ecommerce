package com.zed.ecommerce.services;

import com.zed.ecommerce.Dtos.LoginUserDto;
import com.zed.ecommerce.Dtos.UserDto;
import com.zed.ecommerce.exceptions.UserAlreadyExistsException;
import com.zed.ecommerce.exceptions.UserNotFoundException;
import com.zed.ecommerce.repositories.UserRepository;
import com.zed.ecommerce.models.User;
import com.zed.ecommerce.roles.UserRole;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository _userRepository;
    private final PasswordEncoder _passwordEncoder;
    private final AuthenticationManager _authenticationManager;



    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this._userRepository = userRepository;
        this._passwordEncoder = passwordEncoder;
        this._authenticationManager = authenticationManager;
    }

    public List<UserDto> findAll() {
        List<User> user = _userRepository.findAll();
        return user
                .stream()
                .map(u -> new UserDto(
                        u.getId(),
                        u.getEmail(),
                        "", // Password is not returned for security reasons
                        u.getFirstName(),
                        u.getLastName(),
                        u.getPhoneNumber(),
                        u.getAddress(),
                        Date.from(OffsetDateTime.parse(u.getBirthDate()).toInstant())
                )).collect(Collectors.toList());
    }

    public UserDto findByEmail(String email) {
        return _userRepository.findByEmail(email)
                .map(u -> new UserDto(
                        u.getId(),
                        u.getEmail(),
                        "", // Password is not returned for security reasons
                        u.getFirstName(),
                        u.getLastName(),
                        u.getPhoneNumber(),
                        u.getAddress(),
                        Date.from(OffsetDateTime.parse(u.getBirthDate()).toInstant())
                ))
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
    }

    public UserDto findByPhoneNumber(String phone) {
        return _userRepository.findByPhoneNumber(phone)
                .map(u -> new UserDto(
                        u.getId(),
                        u.getEmail(),
                        "", // Password is not returned for security reasons
                        u.getFirstName(),
                        u.getLastName(),
                        u.getPhoneNumber(),
                        u.getAddress(),
                        Date.from(OffsetDateTime.parse(u.getBirthDate()).toInstant())
                ))
                .orElseThrow(() -> new UserNotFoundException("User with phone number " + phone + " not found"));
    }

    public UserDto create(UserDto userDto) throws UserAlreadyExistsException {
        if (_userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + userDto.getEmail() + " already exists");
        }
        if (_userRepository.findByPhoneNumber(userDto.getPhoneNumber()).isPresent()) {
            throw new UserAlreadyExistsException("User with phone number " + userDto.getPhoneNumber() + " already exists");
        }

        var hashedPassword = _passwordEncoder.encode(userDto.getPassword());

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(hashedPassword);
        user.setAddress(userDto.getAddress());
        user.setBirthDate(userDto.getBirthDate().toInstant().toString());
        user.setRole(new UserRole().getName());

        var savedUser = _userRepository.save(user);
        return new UserDto(
                savedUser.getId(),
                savedUser.getEmail(),
                "", // Password is not returned for security reasons
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getPhoneNumber(),
                savedUser.getAddress(),
                Date.from(OffsetDateTime.parse(savedUser.getBirthDate()).toInstant())
        );
    }

    public User login(LoginUserDto userDto) {
        _authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getPhoneNumber(),
                        userDto.getPassword()
                )
        );

        return _userRepository.findByPhoneNumber(userDto.getPhoneNumber()).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
