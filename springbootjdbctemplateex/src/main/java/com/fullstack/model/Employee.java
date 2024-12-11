package com.fullstack.model;

import java.io.Serializable;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Employee{
    
    private int empId;
    
    private String empName;
    
    private long empContactNumber;
    
    private double empSalary;
    
    private String empEmailId;
    
    private String empPassword;
}
