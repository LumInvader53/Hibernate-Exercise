package com.spring.hibernate_demo.dao;

import com.spring.hibernate_demo.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * Implementation of the StudentDao interface for managing Student entities.
 */
@Repository
public class StudentDaoImplement implements StudentDao {

  private EntityManager em;

  public StudentDaoImplement(EntityManager em) {
    this.em = em;
  }

  /**
   * Schedules SQL statement to be executed on
   * the database when transaction commits.
   */
  @Override
  @Transactional
  public void save(Student theStudent) {
    em.persist(theStudent);
  }

  /**
   * Uses find function from EntityManager to search by ID.
   */
  @Override
  public Student findById(Integer id) {
    return em.find(Student.class, id);
  }

  /**
   * Gets result set from query and returns List of all students.
   */
  @Override
  public List<Student> findAll() {
    TypedQuery<Student> query = em.createQuery(
        "FROM Student order by lastName", Student.class);

    return query.getResultList();
  }

  /**
   * Gets result set from query and searches by last name.
   */
  @Override
  public List<Student> findByLastName(String lastname) {
    TypedQuery<Student> query = em.createQuery(
        "FROM Student WHERE lastName=:data", Student.class);

    query.setParameter("data", lastname);
    return query.getResultList();
  }

  /**
   * Creates transaction to query an update to set data.
   */
  @Override
  @Transactional
  public void update(Student theStudent) {
    em.merge(theStudent);
  }

  /**
   * Deletes element based on id.
   */
  @Override
  @Transactional
  public void delete(Integer id) {
    Student student = em.find(Student.class, id);

    em.remove(student);
  }

  /**
   * Deletes all elements in the database and returns how many were deleted.
   */
  @Override
  @Transactional
  public int deleteAll() {
    int numRows = em.createQuery("DELETE FROM Student").executeUpdate();
    return numRows;
  }
}
