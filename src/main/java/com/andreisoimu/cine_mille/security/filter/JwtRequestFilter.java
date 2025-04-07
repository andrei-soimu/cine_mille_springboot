package com.andreisoimu.cine_mille.security.filter;

import com.andreisoimu.cine_mille.model.dao.User;
import com.andreisoimu.cine_mille.security.JwtUtilities;
import com.andreisoimu.cine_mille.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Value("${jwt.authentication-header}")
    private String authenticationHeader;

	@Autowired
	public JwtRequestFilter(UserService userService, JwtUtilities jwtUtilities) {
		this.userService = userService;
		this.jwtUtilities = jwtUtilities;
	}

	private final UserService userService;

	private final JwtUtilities jwtUtilities;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String servletPath = request.getServletPath();
		if(servletPath.equals("/api/v1/authentication/login")){ // DO NOTHING IF LOGGING IN
			filterChain.doFilter(request,response);
		} else {
			String jwt = getJwtFromRequest(request);
			String username = null;

			if (jwt != null && !jwt.isEmpty()) {
				username = jwtUtilities.extractUsername(jwt);
			}
			if (username != null) {
				User user = userService.loadUserByUsername(username);

				if(jwtUtilities.validateToken(jwt, user)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
							new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

					usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
			filterChain.doFilter(request, response);
		}

	}

	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader(authenticationHeader);
		if (StringUtils.hasText(bearerToken)) {
			if (bearerToken.startsWith("Bearer ")) {
				return bearerToken.substring(7);
			}
			return bearerToken;
		}
		return null;
	}

}
