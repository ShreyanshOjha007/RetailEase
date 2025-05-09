package com.shreyansh.BillingSoftware.controller;

import com.shreyansh.BillingSoftware.io.AuthRequest;
import com.shreyansh.BillingSoftware.io.AuthResponse;
import com.shreyansh.BillingSoftware.service.UserService;
import com.shreyansh.BillingSoftware.service.impl.AppUserDetailsService;
import com.shreyansh.BillingSoftware.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final AppUserDetailsService appUserDetailsService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(@RequestBody AuthRequest authrequest) throws Exception {
        authenticate(authrequest.getEmail(),authrequest.getPassword());
        final UserDetails userDetails = appUserDetailsService.loadUserByUsername(authrequest.getEmail());
        final String jwtToken = jwtUtil.generateToken(userDetails);
        String role = userService.getUserRole(authrequest.getEmail());
        return new AuthResponse(authrequest.getEmail(),jwtToken,role);
    }

    private void authenticate(String email, String password) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
        }catch (DisabledException e){
            throw new Exception("User disabled");
        }catch (BadCredentialsException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email or Password is incorrect");
        }
    }

    @PostMapping("/encode")
    public String encodePassword(@RequestBody Map<String,String> request){
        return passwordEncoder.encode(request.get("password"));
    }
}
