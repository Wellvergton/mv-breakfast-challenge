package io.github.wellvergton.mvbreakfastchallenge.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  
  @Column(unique = true, nullable = false)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String cpf;
  
  @Column(nullable = false)
  private String name;
  
  @OneToMany(mappedBy = "employee")
  private List<Contribution> contributions;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Contribution> getContributions() {
    return contributions;
  }

  public void setContributions(List<Contribution> contributions) {
    this.contributions = contributions;
  }
}
