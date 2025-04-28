package com.example.diss.service;

import com.example.diss.dto.RegisterRequest;
import com.example.diss.model.DepartmentType;
import com.example.diss.model.User;
import com.example.diss.model.UserType;
import com.example.diss.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByName(String name) {
        return userRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public String removeUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return "User not found.";
        }

        userRepository.deleteById(userId);
        return "User deleted successfully.";
    }

    public String removeUserByName(String name) {
        Optional<User> userOptional = userRepository.findByName(name);
        if (userOptional.isEmpty()) {
            return "User not found.";
        }
        Long id = userOptional.get().getId();
        userRepository.deleteById(id);
        return "User deleted successfully.";
    }

    public String editUser(Long userId, RegisterRequest request) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return "User not found.";
        }

        User user = userOptional.get();

        // Validate userType
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
        if (userType == UserType.EMPLOYEE && !email.endsWith("@employer.com")) {
            return "Employee email must end with @employer.com.";
        }

        DepartmentType departmentType = DepartmentType.valueOf(request.getDepartmentType().toUpperCase());
        // Update user fields
        user.setName(request.getName());
        user.setEmail(email);
        user.setPassword(request.getPassword());
        user.setUserType(userType);
        user.setDepartmentType(departmentType);
        userRepository.save(user);

        return "User updated successfully.";
    }


}