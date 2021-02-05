package br.com.zup.freemarket.authentication;


import br.com.zup.freemarket.token.TokenResponse;
import br.com.zup.freemarket.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Profile({"prod", "test"})
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/auth")
    public ResponseEntity<?> toAuthenticate(@RequestBody @Valid LoginRequest request){
        UsernamePasswordAuthenticationToken credentials = request.converterToUsernamePasswordAuthenticationToken();

        try {
            Authentication authentication = manager.authenticate(credentials);
            String token = tokenService.generateToken(authentication);

            return ResponseEntity.ok(new TokenResponse(token, "Bearer "));
        }catch (AuthenticationException e){
            return ResponseEntity.badRequest().build();
        }
    }
}
