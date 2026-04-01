package it.adozioni.animali.Controller;

import it.adozioni.animali.Dto.AdottanteDto;
import it.adozioni.animali.Dto.LoginRequest;
import it.adozioni.animali.Service.AdottanteService;
import it.adozioni.animali.Service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("!dev")
@RequestMapping("/api/auth")
public class AuthController {

    private final AdottanteService adottanteService;
    private final JwtService jwtService;

    @Autowired
    public AuthController(AdottanteService adottanteService, JwtService jwtService) {
        this.adottanteService = adottanteService;
        this.jwtService = jwtService;
    }

    // Endpoint per la registrazione: POST http://localhost:8080/api/auth/register
    @PostMapping("/register")
    public ResponseEntity<AdottanteDto> register(@RequestBody AdottanteDto dto) {
        // Chiamiamo il metodo 'registra' che abbiamo creato nel Service
        AdottanteDto nuovoUtente = adottanteService.registra(dto);
        return ResponseEntity.ok(nuovoUtente);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        // 1. Autentica l'utente (usa AuthenticationManager)
        // 2. Se OK, genera il token
        UserDetails user = adottanteService.loadUserByUsername(request.getEmail());
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(token);
    }
}