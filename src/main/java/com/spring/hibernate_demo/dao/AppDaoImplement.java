package com.spring.hibernate_demo.dao;

import com.spring.hibernate_demo.entity.Course;
import com.spring.hibernate_demo.entity.Instructor;
import com.spring.hibernate_demo.entity.InstructorDetail;
import com.spring.hibernate_demo.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * Repository implementation for managing instructor, course, student, and
 * instructor-detail persistence using JPA.
 */
@Repository
public class AppDaoImplement implements AppDao {

  private EntityManager em;

  /**
   * Constructs a DAO implementation using the provided JPA EntityManager.
   *
   * @param em the EntityManager used to interact with the persistence context
   */
  public AppDaoImplement(EntityManager em) {
    this.em = em;
  }

  /**
   * Saves an instructor entity to the database.
   *
   * @param theInstructor the instructor to persist
   */
  @Override
  @Transactional
  public void save(Instructor theInstructor) {
    em.persist(theInstructor);
  }

  /**
   * Saves a course entity to the database.
   *
   * @param theCourse the course to persist
   */
  @Override
  @Transactional
  public void save(Course theCourse) {
    em.persist(theCourse);
  }

  /**
   * Updates an existing instructor record in the database.
   *
   * @param in the instructor entity with updated information
   */
  @Override
  @Transactional
  public void update(Instructor in) {
    em.merge(in);
  }

  /**
   * Updates an existing student record in the database.
   *
   * @param student the student entity with updated information
   */
  @Override
  @Transactional
  public void update(Student student) {
    em.merge(student);
  }

  /**
   * Finds an instructor by its unique identifier.
   *
   * @param theId the instructor ID
   * @return the matching Instructor object, or null if not found
   */
  @Override
  public Instructor findInstructorById(int theId) {
    return em.find(Instructor.class, theId);
  }

  /**
   * Deletes an instructor and clears the instructor reference from each associated course.
   *
   * @param theId the instructor ID to delete
   */
  @Override
  @Transactional
  public void deleteInstructorById(int theId) {
    Instructor in = em.find(Instructor.class, theId);

    List<Course> courses = in.getCourses();

    System.out.println("TESTINGGa: " + courses);
    for (Course c : courses) {
      c.setInstructor(null);
    }
    em.remove(in);
  }

  /**
   * Finds an instructor detail record by its unique identifier.
   *
   * @param theId the instructor detail ID
   * @return the matching InstructorDetail object, or null if not found
   */
  @Override
  public InstructorDetail findInstructorDetailById(int theId) {
    return em.find(InstructorDetail.class, theId);
  }

  /**
   * Deletes an instructor detail and removes the association from its instructor.
   *
   * @param theId the instructor detail ID to delete
   */
  @Override
  @Transactional
  public void deleteInstructorDetail(int theId) {
    InstructorDetail details = em.find(InstructorDetail.class, theId);

    details.getInstructor().setInstructorDetail(null);

    em.remove(details);
  }

  /**
   * Retrieves all courses taught by a specific instructor.
   *
   * @param theId the instructor ID
   * @return a list of courses associated with the instructor
   */
  @Override
  public List<Course> findCoursesByInstructorId(int theId) {
    TypedQuery<Course> query =
        em.createQuery("FROM Course WHERE instructor.id=:data", Course.class);
    query.setParameter("data", theId);

    List<Course> courses = query.getResultList();
    return courses;
  }

  /**
   * Finds an instructor together with all of their courses using a JOIN FETCH query.
   *
   * @param theId the instructor ID
   * @return the instructor with eagerly loaded courses
   */
  @Override
  public Instructor findInstructorByIdJoinFetch(int theId) {
    TypedQuery<Instructor> query = em.createQuery(
        "SELECT i from Instructor i "
            + "JOIN FETCH i.courses "
            + "WHERE i.id=:data",
        Instructor.class);
    query.setParameter("data", theId);

    Instructor in = query.getSingleResult();
    return in;
  }

  /**
   * Deletes a course by ID and removes its association with an instructor if present.
   *
   * @param theId the course ID to delete
   */
  @Override
  @Transactional
  public void deleteCourseById(int theId) {
    Course c = em.find(Course.class, theId);

    if (c.getInstructor() != null) {
      c.setInstructor(null);
    }

    em.remove(c);
  }

  /**
   * Finds a course along with all of its reviews.
   *
   * @param theId the course ID
   * @return the course with its review list loaded
   */
  @Override
  public Course findCourseAndReviewsByCourseId(int theId) {
    TypedQuery<Course> query = em.createQuery(
        "SELECT c FROM Course c "
            + "JOIN FETCH c.review "
            + "WHERE c.id=:data",
        Course.class);
    query.setParameter("data", theId);

    Course course = query.getSingleResult();

    return course;
  }

  /**
   * Finds a course together with all of its enrolled students.
   *
   * @param theId the course ID
   * @return the course with its students loaded
   */
  @Override
  public Course findCourseAndStudentByCourseId(int theId) {
    TypedQuery<Course> query = em.createQuery(
        "SELECT c from Course c "
            + "JOIN FETCH c.students "
            + "WHERE c.id=:data ",
        Course.class);
    query.setParameter("data", theId);

    Course course = query.getSingleResult();

    return course;
  }

  /**
   * Finds a student together with all of the courses they are enrolled in.
   *
   * @param theId the student ID
   * @return the student with their courses loaded
   */
  @Override
  public Student findStudentAndCoursesByStudentId(int theId) {
    TypedQuery<Student> query = em.createQuery(
        "SELECT s FROM Student s "
            + "JOIN FETCH s.courses "
            + "WHERE s.id=:data",
        Student.class);
    query.setParameter("data", theId);

    Student student = query.getSingleResult();

    return student;
  }

  /**
   * Deletes a student and removes the student from each associated course.
   *
   * @param theId the student ID to delete
   */
  @Override
  @Transactional
  public void deleteStudentById(int theId) {
    Student student = em.find(Student.class, theId);

    if (student != null) {
      List<Course> courses = student.getCourses();

      for (Course c : courses) {
        c.getStudents().remove(student);
      }
    }

    em.remove(student);
  }
}
