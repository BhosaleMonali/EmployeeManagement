package com.example.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        // ✅ Delete JWT token by setting MaxAge = 0
        Cookie jwtCookie = new Cookie("jwtToken", "");
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0); // ✅ Immediately delete the cookie
        response.addCookie(jwtCookie);

        return "/"; // ✅ Redirect to login page
    }
}
