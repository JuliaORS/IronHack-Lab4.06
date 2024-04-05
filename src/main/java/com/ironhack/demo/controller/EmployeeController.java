package com.ironhack.demo.controller;

import com.ironhack.demo.model.Employee;
import com.ironhack.demo.model.Status;
import com.ironhack.demo.repository.EmployeeRepository;
import com.ironhack.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeService employeeService;


    @GetMapping("/doctors")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getDoctors() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/doctors/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee getDoctorById(@PathVariable(name="id") String employeeID) {
        //return employeeRepository.findById(employeeID).get();
        return employeeService.getDoctorById(employeeID);
    }

    @GetMapping("/doctors/status/{status}")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getDoctorsByStatus(@PathVariable(name="status") Status status) {
        //return employeeRepository.findByStatus(status);
        return employeeService.getDoctorByStatus(status);
    }

    @GetMapping("/doctors/department/{department}")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getDoctorsByDepartment(@PathVariable(name="department") String department) {
        //return employeeRepository.findByDepartment(department);
        return employeeService.getDoctorByDepartment(department);
    }

    @PostMapping("/doctors")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addDoctor(@RequestBody Employee doctor) {
        //employeeRepository.save(doctor);
        return employeeService.addNewEmployee(doctor);
    }

    @PatchMapping("/doctors/status/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateEmployeeStatus(@PathVariable String id, @RequestBody Status status) {
        employeeService.updateEmployeeStatus(id, status);

    }

    @PatchMapping("/doctors/department/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateEmployeeDepartment(@PathVariable String id, @RequestBody String department) {
        employeeService.updateEmployeeDepartment(id, department);

    }

    @DeleteMapping("/doctors/{id}")
    public void deleteDoctor(@PathVariable("id") String id) {
        employeeService.deleteDoctor(id);
    }

}