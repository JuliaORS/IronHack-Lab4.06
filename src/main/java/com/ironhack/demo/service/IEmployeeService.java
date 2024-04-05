package com.ironhack.demo.service;

import com.ironhack.demo.model.Employee;
import com.ironhack.demo.model.Status;
import com.ironhack.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService {

    public List<Employee> getAllEmployees();
    public Employee getDoctorById(String id);
    public List<Employee> getDoctorByStatus(Status status);
    public List<Employee> getDoctorByDepartment(String department);
    public Employee addNewEmployee(Employee employee);
    public void updateEmployeeStatus(String id, Status status);
    public void updateEmployeeDepartment(String id, String department);
    public void deleteDoctor(String id);
}
