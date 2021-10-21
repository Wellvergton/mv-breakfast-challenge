package io.github.wellvergton.mvbreakfastchallenge.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.github.wellvergton.mvbreakfastchallenge.model.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
  @Query(value = "SELECT * FROM employee", nativeQuery = true)
  public List<Employee> findAll();
  
  @Query(value = "SELECT * FROM employee WHERE cpf=:cpf", nativeQuery = true)
  public Optional<Employee> findByCpf(@Param("cpf") String cpf);
  
  @Modifying
  @Query(value = "INSERT INTO employee (name, cpf) VALUES (:name, :cpf)", nativeQuery = true)
  @Transactional
  public Integer saveEmployee(@Param("name") String name, @Param("cpf") String cpf);
  
  @Modifying
  @Query(value = "UPDATE employee SET cpf = :cpf, name = :name WHERE id = :id", nativeQuery = true)
  @Transactional
  public Integer updateEmployee(@Param("id") Integer id, @Param("cpf") String cpf, @Param("name") String name);
  
  @Modifying
  @Query(value = "DELETE FROM employee WHERE id = :id", nativeQuery = true)
  @Transactional
  public Integer deleteEmployee(@Param("id") Integer id);
}
