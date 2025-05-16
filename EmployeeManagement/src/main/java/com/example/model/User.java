package com.example.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id; // Use a numeric ID for primary key

	    @Column(nullable = false, unique = true)
	    @NotBlank(message = "Username cannot be empty")
	    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
	    private String username;

	    @Column(nullable = false, unique = true)
	    @Email(message = "Invalid email format")
	    private String email;

	    @Column(nullable = false)
	    @NotBlank(message = "Password cannot be empty")
	    @Size(min = 6, message = "Password must be at least 6 characters long")
	    private String password;
	    @Column
	    private String roles;

}
