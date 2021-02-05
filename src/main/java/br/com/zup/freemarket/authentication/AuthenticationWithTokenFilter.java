package br.com.zup.freemarket.authentication;

import br.com.zup.freemarket.registernewuser.Usuario;
import br.com.zup.freemarket.token.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationWithTokenFilter extends OncePerRequestFilter {


    private TokenService tokenService;
    private UsuarioRepository repository;

    public AuthenticationWithTokenFilter(TokenService tokenService, UsuarioRepository repository) {
        this.tokenService = tokenService;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = getToken(request);

        boolean isValid = tokenService.isValidToken(token);
        if (isValid){
            authenticateUser(token);
        }

        filterChain.doFilter(request, response);
    }

    private void authenticateUser(String token) {
        String email = tokenService.getEmailUser(token);
        Usuario user = repository.findByLogin(email).get();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String getToken(HttpServletRequest request) {

        String token = request.getHeader("Authorization");

        if(token == null || token.isEmpty() || !token.startsWith("Bearer ")){
            return  null;
        }

        return token.substring(7, token.length());
    }
}
