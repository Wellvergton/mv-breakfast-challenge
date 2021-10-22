package io.github.wellvergton.mvbreakfastchallenge.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.wellvergton.mvbreakfastchallenge.model.Contribution;
import io.github.wellvergton.mvbreakfastchallenge.model.Employee;
import io.github.wellvergton.mvbreakfastchallenge.repository.ContributionRepository;
import io.github.wellvergton.mvbreakfastchallenge.repository.EmployeeRepository;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
  @Autowired
  private EmployeeRepository employeeRepository;
  
  @Autowired
  private ContributionRepository contributionRepository;

  @GetMapping(path = "/list")
  public List<Employee> list() {
    List<Employee> employees = employeeRepository.findAll();
    
    return employees;
  }
  
  @PostMapping(path = "/save")
  public ResponseEntity<Employee> save(@RequestBody Employee employee) {
    Optional<Employee> dbEmployee = employeeRepository.findByCpf(employee.getCpf());

  	if (dbEmployee.isPresent()) {
  	  return ResponseEntity.status(HttpStatus.CONFLICT).body(employee);
  	}
  	
  	if (employee.getCpf().isBlank() || employee.getName().isBlank()) {
  	  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(employee);
  	}
  	
  	employeeRepository.saveEmployee(employee.getName(), employee.getCpf());
  	
    return ResponseEntity.ok(employeeRepository.findByCpf(employee.getCpf()).get());
  }
  
  @PutMapping("/update")
  public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {    
    Optional<Employee> dbEmployee = employeeRepository.findByCpf(employee.getCpf());

    if (dbEmployee.isPresent() || dbEmployee.isEmpty()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(employee);
    }
    
    if (employee.getCpf().isBlank() || employee.getName().isBlank()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(employee);
    }
    
    employeeRepository.updateEmployee(employee.getId(), employee.getCpf(), employee.getName());
    
    return ResponseEntity.ok(employeeRepository.findByCpf(employee.getCpf()).get());
  }
  
  @DeleteMapping("/delete")
  public ResponseEntity<Integer> deleteEmployee(@RequestBody Employee employee) {
    Optional<Employee> dbEmployee = employeeRepository.findById(employee.getId());
    List<Contribution> contributions;
    
    if (dbEmployee.isPresent()) {
      contributions = dbEmployee.get().getContributions();
      
      if (!contributions.isEmpty()) {
        for (Contribution c : contributions) {
          contributionRepository.deleteContribution(c.getId());
        }
      }
    }
    
    return ResponseEntity.ok(employeeRepository.deleteEmployee(employee.getId()));
  }
}
