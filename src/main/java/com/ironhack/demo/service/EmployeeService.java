package com.ironhack.demo.service;

import com.ironhack.demo.model.Employee;
import com.ironhack.demo.model.Status;
import com.ironhack.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements IEmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;


    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    //CODE 400
    @Override
    public Employee getDoctorById(String id){
        return employeeRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee not found"));
    }
    @Override
    public List<Employee> getDoctorByStatus(Status status) {
        List<Employee> employeeList = employeeRepository.findByStatus(status);
        if (employeeList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not found");
        }
        return employeeList;
    }

    @Override
    public List<Employee> getDoctorByDepartment(String department) {
        List<Employee> employeeList = employeeRepository.findByDepartment(department);
        if (employeeList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not found");
        }
        return employeeList;
    }

    @Override
    public Employee addNewEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void updateEmployeeStatus(String id, Status status) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            employee.setStatus(status);
            employeeRepository.save(employee);
        }
    }

    @Override
    public void updateEmployeeDepartment(String id, String department) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            employee.setDepartment(department);
            employeeRepository.save(employee);
        }
    }

    @Override
    public void deleteDoctor(String id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            employeeRepository.delete(employee.get());
        }
    }
}
