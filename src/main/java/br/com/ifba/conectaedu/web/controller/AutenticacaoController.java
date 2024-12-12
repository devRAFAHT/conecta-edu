package br.com.ifba.conectaedu.web.controller;

import br.com.ifba.conectaedu.jwt.JwtToken;
import br.com.ifba.conectaedu.jwt.JwtUserDetailsService;
import br.com.ifba.conectaedu.web.dto.UsuarioLoginDTO;
import br.com.ifba.conectaedu.web.exception.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("conecta-edu/v1")
public class AutenticacaoController {
    private final JwtUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public ResponseEntity<?> autenticar(@RequestBody @Valid UsuarioLoginDTO dto, HttpServletRequest request){
        log.info("Processo de autenticação pelo login {}", dto.getUsername());
        try{
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
            authenticationManager.authenticate(authenticationToken);
            JwtToken token = userDetailsService.getTokenAuthenticated(dto.getUsername());
            return ResponseEntity.ok(token);
        }catch(AuthenticationException e){
            log.warn("Bad credentials from username {}", dto.getUsername());
        }
        return ResponseEntity.badRequest().body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Credenciais inválidas"));
    }
}