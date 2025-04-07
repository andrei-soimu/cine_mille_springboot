package com.andreisoimu.cine_mille.rest;

import com.andreisoimu.cine_mille.model.AuthenticationRequest;
import com.andreisoimu.cine_mille.model.dao.Screening;
import com.andreisoimu.cine_mille.model.dao.User;
import com.andreisoimu.cine_mille.security.JwtUtilities;
import com.andreisoimu.cine_mille.service.ScreeningService;
import com.andreisoimu.cine_mille.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/screening")
public class ScreeningController {

	@Autowired
	public ScreeningController(ScreeningService service) {

		this.service = service;
	}

	private final ScreeningService service;

	@GetMapping
	public ResponseEntity<Page<Screening>> getCurrentScreenings(
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		return ResponseEntity.ok(service.getCurrentScreenings(startDate, endDate, page, size));
	}

	@GetMapping("/past_screenings")
	public ResponseEntity<Page<Screening>> getPastScreenings(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		return ResponseEntity.ok(service.getPastScreenings(page, size));
	}

}
