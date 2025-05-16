package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.model.Department;
import com.example.service.DepartmentService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class DepartmentController {
    
    @Autowired
    private DepartmentService departmentService;

    private String extractTokenFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwtToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null; // Added return statement to handle the case when no token is found
    } // **Fixed: Added closing bracket here**

    @GetMapping("/departments")
    public String showDepartments(Model model) {
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "department-list";
    }

    @GetMapping("/department/new")
    public String showCreateDepartmentForm(Model model, HttpServletRequest request) {
//        String token = extractTokenFromCookies(request);
//        
//        if (token == null) {
//            model.addAttribute("error", "Unauthorized: No token found!");
//            return "welcome";
//        }
        model.addAttribute("department", new Department());
        return "department-form";
    }

    @PostMapping("/department/save")
    public String saveDepartment(@RequestBody Department department,Model model) {
        departmentService.saveDepartment(department);
//        model.addAttribute("departments", departmentService.getAllDepartments());
       return "redirect:/departments";
        //return "department-list";
        
    }
}
