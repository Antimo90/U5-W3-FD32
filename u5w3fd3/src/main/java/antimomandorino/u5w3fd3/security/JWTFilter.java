package antimomandorino.u5w3fd3.security;

import antimomandorino.u5w3fd3.entities.User;
import antimomandorino.u5w3fd3.exceptions.UnauthorizedException;
import antimomandorino.u5w3fd3.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTFilter extends OncePerRequestFilter {
    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Inserire il token nell'authorization header nel formato giusto!");

        }

        String accesToken = authHeader.replace("Bearer ", "");

        jwtTools.verifyToken(accesToken);

        UUID dipendenteId = jwtTools.extractIdFromToken(accesToken);

        User found = userService.findById(dipendenteId);

        Authentication authentication = new UsernamePasswordAuthenticationToken(found, null, found.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);


        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
