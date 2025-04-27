package com.example.diss.service;

import com.example.diss.dto.RegisterRequest;
import com.example.diss.model.DepartmentType;
import com.example.diss.model.User;
import com.example.diss.model.UserType;
import com.example.diss.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;


    public String register(RegisterRequest request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            return "Email already exists.";
        }

        UserType userType;
        try {
            userType = UserType.valueOf(request.getUserType().toUpperCase());
        } catch (IllegalArgumentException e) {
            return "Invalid user type. Must be MANAGER or EMPLOYEE.";
        }

        // Validate email domain based on userType
        String email = request.getEmail().toLowerCase();
        if (userType == UserType.MANAGER && !email.endsWith("@manager.com")) {
            return "Manager email must end with @manager.com.";
        }
        if (userType == UserType.EMPLOYER && !email.endsWith("@employer.com")) {
            return "Employee email must end with @employee.com.";
        }

        DepartmentType departmentType;
        try {
            departmentType = DepartmentType.valueOf(request.getDepartmentType().toUpperCase());
        } catch (IllegalArgumentException e) {
            return "Invalid department type. Must be one of the available company departments.";
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setUserType(UserType.valueOf(request.getUserType()));
        user.setDepartmentType(departmentType);
        userRepository.save(user);

        return user.getId().toString();
    }

    public String login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return "User not found.";
        }

        User user = userOptional.get();
        if (!user.getPassword().equals(password)) {
            return "Invalid credentials.";
        }

        return user.getName();
    }
}
