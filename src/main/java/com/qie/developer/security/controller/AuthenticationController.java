package com.qie.developer.security.controller;
import com.qie.developer.security.entity.Token;
import com.qie.developer.security.entity.User;
import com.qie.developer.security.repository.TokenRepository;
import com.qie.developer.security.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.qie.developer.security.dto.AuthenticationRequest;
import com.qie.developer.security.dto.AuthenticationResponse;
import com.qie.developer.security.dto.RegisterRequest;
import com.qie.developer.security.service.Impl.AuthenticationServiceImpl;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {

 @Autowired
  private AuthenticationServiceImpl service;

  @Autowired
  private TokenRepository tokenRepository;

  @Autowired
  private UserRepository userRepository;

 @PostMapping("/validate")
 public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
  final String jwt = token.substring(7);
  Optional<Token> isTokenValid = tokenRepository.findByToken(jwt);
  if (isTokenValid.isPresent()) {
   Optional<User> userData = userRepository.findById(isTokenValid.get().getUser().getId());
   if (userData.isPresent()) {
    return ResponseEntity.ok(userData.get());
   }
   return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
  }
  return ResponseEntity.badRequest().body("Invalid token.");
 }


  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody RegisterRequest request
  ) {
    return ResponseEntity.ok(service.register(request));
  }
  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request
  ) {
    return ResponseEntity.ok(service.authenticate(request));
  }

  @PostMapping("/refresh-token")
  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws IOException {
    service.refreshToken(request, response);
  }


}
