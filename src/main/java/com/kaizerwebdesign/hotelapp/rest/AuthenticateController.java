package com.kaizerwebdesign.hotelapp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaizerwebdesign.hotelapp.config.MyUserDetailService;
import com.kaizerwebdesign.hotelapp.jwt.AuthenticationRequest;
import com.kaizerwebdesign.hotelapp.jwt.AuthenticationResponse;
import com.kaizerwebdesign.hotelapp.jwt.JwtUtil;

@RestController
@RequestMapping("/api")
public class AuthenticateController {
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailService UserDetailsService;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	
	@PostMapping("/authentication")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		try {
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}
		final UserDetails userDetails = UserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
		
	}
}
