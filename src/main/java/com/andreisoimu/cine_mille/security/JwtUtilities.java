package com.andreisoimu.cine_mille.security;

import com.andreisoimu.cine_mille.model.dao.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtUtilities {

	@Value("${jwt.secret-key}")
	private String jwtSecret;
	
	public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
				.setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
				.build()
				.parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(User userDetails) {
        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());

		claims.put("auth", Arrays.stream(userDetails.getRoles().split(",")).map(SimpleGrantedAuthority::new)
				.filter(Objects::nonNull).collect(Collectors.toList()));

        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Claims claims, String subject) {
    	Date now = new Date();
		Date expiryDate = new Date(now.getTime() + 1000 * 60 * 60 * 12);

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date())
				.setExpiration(expiryDate)
				.signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS256).compact();
    }

    public Boolean validateToken(String token, User userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
