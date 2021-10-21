package io.github.wellvergton.mvbreakfastchallenge.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import io.github.wellvergton.mvbreakfastchallenge.model.Contribution;

public interface ContributionRepository extends CrudRepository<Contribution, Integer> {
  @Query(value = "SELECT * FROM contribution", nativeQuery = true)
  public List<Contribution> findAll();
  
  @Query(value = "SELECT * FROM contribution WHERE id = :id", nativeQuery = true)
  public Optional<Contribution> findById(@Param("id") Integer id);
  
  @Query(value = "SELECT * FROM contribution WHERE name = :name", nativeQuery = true)
  public Optional<Contribution> findByName(@Param("name") String name);
  
  @Modifying
  @Query(
    value = "INSERT INTO contribution (name, employee_id) VALUES (:name, :employee_id)",
    nativeQuery = true
  )
  @Transactional
  public Integer saveContribution(
    @Param("name") String name, @Param("employee_id") Integer employeeId
  );
  
  @Modifying
  @Query(value = "UPDATE contribution SET name = :name WHERE id = :id", nativeQuery = true)
  @Transactional
  public Integer updateContribution(@Param("id") Integer id, @Param("name") String name);
  
  @Modifying
  @Query(value = "DELETE FROM contribution WHERE id = :id", nativeQuery = true)
  @Transactional
  public Integer deleteContribution(@Param("id") Integer id);
}
