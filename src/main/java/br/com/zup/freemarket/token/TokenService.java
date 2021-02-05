package br.com.zup.freemarket.token;

import br.com.zup.freemarket.authentication.UsuarioRepository;
import br.com.zup.freemarket.registernewuser.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${free_market.jwt.expiration}")
    private String expiration;

    @Value("${free_market.jwt.secret}")
    private String secret;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public String generateToken(Authentication authentication) {

        Usuario logged = (Usuario) authentication.getPrincipal();
        Date today = new Date();
        Date expirationDate = new Date(today.getTime() + Long.parseLong(expiration));
        return Jwts.builder()
                .setIssuer("Free Market API")
                .setSubject(logged.getUsername())
                .setIssuedAt(today)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean isValidToken(String token) {

        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public String getEmailUser(String token) {
        Claims body = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();

        return body.getSubject();
    }

    public Usuario getLoggedUser(String token){
        String login = this.getEmailUser(token);

        return usuarioRepository.findByLogin(login).get();
    }
}
