package com.spring.hibernate_demo.dao;

import com.spring.hibernate_demo.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * Implementation of the {@link StudentDao} interface for managing Student entities.
 */
@Repository
public class StudentDaoImplement implements StudentDao {

  private EntityManager em;

  public StudentDaoImplement(EntityManager em) {
    this.em = em;
  }

  /**  (non-Javadoc)
   * Schedules SQL statement to be executed on the database when transaction commits.
   * @see com.spring.hibernate_demo.dao.StudentDao#save(com.spring.hibernate_demo.entity.Student)
   */
  @Override
  @Transactional
  public void save(Student theStudent) {
    em.persist(theStudent);
  }

  /**  (non-Javadoc)
   * Uses find function from EntityManager to search by ID.
   * @see com.spring.hibernate_demo.dao.StudentDao#findById(java.lang.Integer)
   */
  @Override
  public Student findById(Integer id) {
    return em.find(Student.class, id);
  }

  /**  (non-Javadoc)
   * Gets result set from query and returns List of all students.
   * @see com.spring.hibernate_demo.dao.StudentDao#findAll()
   */
  @Override
  public List<Student> findAll() {
    TypedQuery<Student> query = em.createQuery("FROM Student order by lastName", Student.class);

    return query.getResultList();
  }

  /**  (non-Javadoc)
   * Gets result set from query and searches by last name.
   * @see com.spring.hibernate_demo.dao.StudentDao#findByLastName(java.lang.String)
   */
  @Override
  public List<Student> findByLastName(String lastname) {
    TypedQuery<Student> query = em.createQuery("FROM Student WHERE lastName=:data", Student.class);

    query.setParameter("data", lastname);
    return query.getResultList();
  }

  /**  (non-Javadoc)
   * Creates transaction to query an update to set data
   * @see com.spring.hibernate_demo.dao.StudentDao#update(com.spring.hibernate_demo.entity.Student)
   */
  @Override
  @Transactional
  public void update(Student theStudent) {
    em.merge(theStudent);
  }

  /**  (non-Javadoc)
   * Deletes element based on id.
   * @see com.spring.hibernate_demo.dao.StudentDao#delete(java.lang.Integer)
   */
  @Override
  @Transactional
  public void delete(Integer id) {
    Student student = em.find(Student.class, id);

    em.remove(student);
  }

  /**  (non-Javadoc)
   * Deletes all elements in the database and returns how many were deleted.
   * @see com.spring.hibernate_demo.dao.StudentDao#deleteAll()
   */
  @Override
  @Transactional
  public int deleteAll() {
    int numRows = em.createQuery("DELETE FROM Student").executeUpdate();
    return numRows;
  }
}
