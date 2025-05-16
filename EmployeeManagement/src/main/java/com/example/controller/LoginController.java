package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.example.model.AuthRequest;
import com.example.model.User;
import com.example.service.JwtService;
import com.example.service.UserService;

import jakarta.validation.Valid;

@Controller
public class LoginController {
	
	@Autowired
	UserService userService;
	@Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

	
	@GetMapping("/")
	public String  HomePage(Model model)
	{
		model.addAttribute("AuthRequest", new AuthRequest());
		return "index";
	}
	@GetMapping("/register")
	public String RegisterPage(Model model) {
	    model.addAttribute("user", new User());
	    return "register";
	}
	
	
	
	@GetMapping("/welcome")
	public String  WelcomePage()
	{
		return "welcome";
	}
	
	/*
	 * @PostMapping("/signup") public String Singup(@ModelAttribute User
	 * user,BindingResult result ) {
	 * 
	 * if(userService.SaveUser(user)) return "/"; return "register";
	 * 
	 * }
	 */

	
	@PostMapping("/signup")
	public String signup(@ModelAttribute("user") @Valid User user, BindingResult result, Model model) {
	    if (result.hasErrors()) {
	    	model.addAttribute("error", "Registration failed. Please try again.");
	        return "register"; // Return to the registration page if validation fails
	    }
	    
	    if (userService.saveUser(user)) {
	        return "redirect:/"; // Redirect to home page after successful registration
	    }
	    
	    model.addAttribute("error", "Registration failed. Please try again.");
	    return "register"; // Stay on the registration page if saving fails
	}

	/*
	 * @PostMapping("/login") public String
	 * authenticateAndGetToken(@ModelAttribute("authRequest")AuthRequest
	 * authRequest,Model model) { Authentication authentication =
	 * authenticationManager.authenticate( new
	 * UsernamePasswordAuthenticationToken(authRequest.getUsername(),
	 * authRequest.getPassword()) ); if (authentication.isAuthenticated()) { return
	 * jwtService.generateToken(authRequest.getUsername()); } else { throw new
	 * UsernameNotFoundException("Invalid user request!"); } }
	 */
	
	
	
	@PostMapping("/login")
	public String authenticateUser(@ModelAttribute AuthRequest authRequest, Model model,HttpSession session,HttpServletResponse response) {
	    try {
	    	  Authentication authentication = authenticationManager.authenticate(
	    	            new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
	    	        );
	    	        if (authentication.isAuthenticated()) {
	    	            String token= jwtService.generateToken(authRequest.getUsername());
	    	            session.setAttribute("jwtToken", token);
	    	            model.addAttribute("token", token);
//	    	            session.setAttribute("jwtToken", token);
	    	         // ✅ Set JWT as an HttpOnly cookie (No need for session)
	                    Cookie jwtCookie = new Cookie("jwtToken", token);
	                    jwtCookie.setHttpOnly(true); // ✅ Secure against XSS
	                    jwtCookie.setSecure(true);   // ✅ Enable only for HTTPS
	                    jwtCookie.setPath("/");      // ✅ Available across all endpoints
	                    jwtCookie.setMaxAge(60 * 60); // ✅ Expire in 1 hour
	                    response.addCookie(jwtCookie);
	    	            return "welcome";
	    	            
	    	        } else {
	    	            throw new UsernameNotFoundException("Invalid user request!");
	    	        }
	    	    }catch (BadCredentialsException e) {
	        model.addAttribute("error", "Invalid username or password");
	        return "index"; // ✅ Stay on login page if authentication fails
	    }

	}

}
