package nl.mfarr.supernova.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nl.mfarr.supernova.services.CustomUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, CustomUserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userId;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        userId = jwtTokenProvider.extractUserId(jwt);

        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Dit vereist een extra methode als je users ook kunt laden via ID
            // Voor nu blijft het bij email gebaseerde auth
            // Je kunt een service toevoegen zoals: loadUserById(userId)

            // Simpel voorbeeld (niet aanbevolen):
            // UserDetails userDetails = userDetailsService.loadUserByUsername(emailFromUserId(userId));

            filterChain.doFilter(request, response);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
