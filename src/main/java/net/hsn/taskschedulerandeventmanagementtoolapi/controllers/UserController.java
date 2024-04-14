package net.hsn.taskschedulerandeventmanagementtoolapi.controllers;

import net.hsn.taskschedulerandeventmanagementtoolapi.entities.Role;
import net.hsn.taskschedulerandeventmanagementtoolapi.entities.User;
import net.hsn.taskschedulerandeventmanagementtoolapi.enums.URole;
import net.hsn.taskschedulerandeventmanagementtoolapi.repositories.RoleRepository;
import net.hsn.taskschedulerandeventmanagementtoolapi.repositories.UserRepository;
import net.hsn.taskschedulerandeventmanagementtoolapi.security.jwt.JwtUtils;
import net.hsn.taskschedulerandeventmanagementtoolapi.security.payload.request.LoginRequest;
import net.hsn.taskschedulerandeventmanagementtoolapi.security.payload.request.SignupRequest;
import net.hsn.taskschedulerandeventmanagementtoolapi.security.payload.response.JwtResponse;
import net.hsn.taskschedulerandeventmanagementtoolapi.security.payload.response.MessageResponse;
import net.hsn.taskschedulerandeventmanagementtoolapi.security.services.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping( "/api/auth")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    private static final String NOTFOUND = "Error: Role is not found.";

    public UserController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .toList();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        Boolean existsByUsername = userRepository.existsByUsername(signUpRequest.getUsername());
        if (Boolean.TRUE.equals(existsByUsername)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        Boolean existsByEmail = userRepository.existsByEmail(signUpRequest.getEmail());
        if (Boolean.TRUE.equals(existsByEmail)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(URole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException(NOTFOUND));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if(role.equals("admin")) {
                    Role adminRole = roleRepository.findByName(URole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException(NOTFOUND));
                    roles.add(adminRole);
                } else {
                    Role userRole = roleRepository.findByName(URole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException(NOTFOUND));
                    roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
