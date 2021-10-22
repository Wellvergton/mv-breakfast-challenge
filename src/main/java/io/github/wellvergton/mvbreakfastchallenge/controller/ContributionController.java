package io.github.wellvergton.mvbreakfastchallenge.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.wellvergton.mvbreakfastchallenge.model.Contribution;
import io.github.wellvergton.mvbreakfastchallenge.repository.ContributionRepository;

@CrossOrigin
@RestController
@RequestMapping("/contribution")
public class ContributionController {
  @Autowired
  private ContributionRepository contributionRepository;
  
  @GetMapping(path = "/list")
  public List<Contribution> list() {
    List<Contribution> contributions = contributionRepository.findAll();
    
    return contributions;
  }
  
  @PostMapping(path = "/save/{employee_id}")
  public ResponseEntity<Contribution> save(
    @PathVariable(name = "employee_id") Integer employeeId, @RequestBody Contribution contribution
  ) {
    String contributionName = contribution.getName().toLowerCase();
    Optional<Contribution> dbContribution = contributionRepository.findByName(contributionName);
    
    if (dbContribution.isPresent()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(contribution);
    }
    
    if (contribution.getName().isBlank()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(contribution);
    }
    
    contributionRepository.saveContribution(contributionName, employeeId);
    
    return ResponseEntity.ok(contributionRepository.findByName(contributionName).get());
  }
  
  @PutMapping(path = "/update")
  public ResponseEntity<Contribution> update(@RequestBody Contribution contribution) {
    String contributionName = contribution.getName().toLowerCase();
    Optional<Contribution> contributionById = contributionRepository.findById(contribution.getId());
    Optional<Contribution> contributionByName = contributionRepository.findByName(contributionName);
    
    if (contributionById.isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(contribution);
    }
    
    if (contributionByName.isPresent()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(contribution);
    }
    
    if (contribution.getName().isBlank() || contribution.getId() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(contribution);
    }
    
    contributionRepository.updateContribution(contribution.getId(), contributionName);
    
    return ResponseEntity.ok(contribution);
  }
  
  @DeleteMapping(path = "/delete")
  public ResponseEntity<Integer> delete(@RequestBody Contribution contribution) {
    return ResponseEntity.ok(contributionRepository.deleteContribution(contribution.getId()));
  }
}
