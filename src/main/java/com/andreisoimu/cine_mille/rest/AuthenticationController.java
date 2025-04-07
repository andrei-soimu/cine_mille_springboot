package com.andreisoimu.cine_mille.rest;

import com.andreisoimu.cine_mille.model.AuthenticationRequest;
import com.andreisoimu.cine_mille.model.AuthenticationResponse;
import com.andreisoimu.cine_mille.model.dao.User;
import com.andreisoimu.cine_mille.security.JwtUtilities;
import com.andreisoimu.cine_mille.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/authentication")
@CrossOrigin
public class AuthenticationController {
	
	@Autowired
	public AuthenticationController(AuthenticationManager authenticationManager, UserService service, JwtUtilities jwtUtilities) {
		this.authenticationManager = authenticationManager;
		this.service = service;
		this.jwtUtilities = jwtUtilities;
	}

	private final AuthenticationManager authenticationManager;

	private final UserService service;

	private final JwtUtilities jwtUtilities;
	
	@Value("${jwt.authentication-header}")
	private String authenticationHeader;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);

			final User user = service.loadUserByUsername(authenticationRequest.getUsername());

			return ResponseEntity.ok(new AuthenticationResponse(jwtUtilities.generateToken(user)));
		} catch (BadCredentialsException bce) {
			throw new Exception("Incorrect username or password", bce);
		}
	}
	
	@PostMapping("/authorize") 
	public String authorize(HttpServletRequest request) throws Exception {

		final String jwt = request.getHeader(authenticationHeader);
		String username = null;

		if (jwt != null)
			username = jwtUtilities.extractUsername(jwt);
		if (username != null) {
			User user = service.loadUserByUsername(username);

			if(jwtUtilities.validateToken(jwt, user)) {
				return "ok";
			}
		}
		throw new Exception("Incorrect token!");
	}

}
