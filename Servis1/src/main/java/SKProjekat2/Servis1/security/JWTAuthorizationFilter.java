package SKProjekat2.Servis1.security;


import SKProjekat2.Servis1.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepository userRepo;

    @Autowired
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepo) {
        super(authenticationManager);
        this.userRepo = userRepo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String token = request.getHeader("Authorization");

        UsernamePasswordAuthenticationToken auth = getAuthentication(request, token);

        SecurityContextHolder.getContext().setAuthentication(auth);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req, String token) {
        if (token != null) {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC512("SKProjekat2".getBytes()))
                    .build().verify(token.replace("login ", ""));

            String email = jwt.getSubject();

            if (userRepo.existsByEmail(email) == false) {
                return null;
            }

            if (email != null) {
                return new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());
            }
            return null;
        }

        return null;
    }
}
