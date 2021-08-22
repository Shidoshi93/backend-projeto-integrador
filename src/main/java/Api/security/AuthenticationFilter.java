package Api.security;

import Api.model.User;
import Api.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Date;

import static Api.constants.SecurityConstants.EXPIRATION_TIME;
import static Api.constants.SecurityConstants.KEY;


public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    public AuthenticationFilter(AuthenticationManager manager){
        this.authenticationManager = manager;
    }

    @Override
    @Nullable
    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter("email");
    }

    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            User user = new ObjectMapper().readValue(req.getInputStream(), User.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(),
                            user.getPassword())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        Date exp = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        Key key = Keys.hmacShaKeyFor(KEY.getBytes());
        Claims claims = Jwts.claims().setSubject(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername());
        String token = Jwts.builder().setClaims(claims).signWith(key, SignatureAlgorithm.HS512).setExpiration(exp).compact();
        res.addHeader("token", token);
        res.addHeader("email", auth.getName());

    }
}
