package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.model.AuthRequest;
import com.example.model.Employee;
import com.example.service.DepartmentService;
import com.example.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    
    //to fetch the form or page of employess
    @GetMapping("/employees")
    public String showEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employee-list";
    }

    @GetMapping("/employee/new")
    public String showCreateEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", departmentService.getAllDepartments()); // ✅ Load departments
        return "employee-form";
    }

    //to save the  new employee data in db
    @PostMapping("/employee/save")
    public String saveEmployee(@ModelAttribute Employee employee) {
        employeeService.saveEmployee(employee);
        return "redirect:/employees";
    }
    
    // Show form to edit existing employee
    @GetMapping("/employee/edit/{id}")
    public String showEditEmployeeForm(@PathVariable("id") Long id, Model model) {
        Employee existingEmployee = employeeService.getEmployeeById(id);
        if (existingEmployee != null) {
            model.addAttribute("employee", existingEmployee);
            model.addAttribute("departments", departmentService.getAllDepartments());
            return "employee-form"; // reuse the same form
        } else {
            return "redirect:/employees"; // fallback if ID is invalid
        }
    }
    
    
}

