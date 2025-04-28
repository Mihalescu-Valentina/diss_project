package com.example.diss.dto;

import com.example.diss.model.DepartmentType;
import com.example.diss.model.UserType;
import lombok.Data;
@Data
public class UserDTO {
    private String name;
    private String email;
    private UserType userType;
    private DepartmentType departmentType;
}