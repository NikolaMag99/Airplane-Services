package SKProjekat2.Servis1.security;


import SKProjekat2.Servis1.Entites.User;
import SKProjekat2.Servis1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private PasswordEncoder encoder;
    private UserRepository userRepo;

    @Autowired
    public CustomAuthenticationProvider(UserRepository userRepo) {
        super();
        this.userRepo = userRepo;
    }

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String email = auth.getName();
        String password = auth.getCredentials().toString();

        User user = userRepo.findByEmail(email);

        if (user == null)
            throw new BadCredentialsException("Authentication failed");

        if (encoder.matches(password, user.getPassword()))
            return new UsernamePasswordAuthenticationToken(email, password, Collections.emptyList());

        throw new BadCredentialsException("Authentication failed");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    public void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }
}
