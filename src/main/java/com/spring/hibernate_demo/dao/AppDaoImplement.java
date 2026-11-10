package com.spring.hibernate_demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.spring.hibernate_demo.entity.Course;
import com.spring.hibernate_demo.entity.Instructor;
import com.spring.hibernate_demo.entity.InstructorDetail;
import com.spring.hibernate_demo.entity.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class AppDaoImplement implements AppDao {

    private EntityManager em;

    public AppDaoImplement(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public void save(Instructor theInstructor) {
        em.persist(theInstructor);
    }

    @Override
    public Instructor findInstructorById(int theId) {
        return em.find(Instructor.class, theId);
    }

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

    @Override
    public InstructorDetail findInstructorDetailById(int theId) {
        return em.find(InstructorDetail.class, theId);
    }

    @Override
    @Transactional
    public void deleteInstructorDetail(int theId) {
        InstructorDetail details = em.find(InstructorDetail.class, theId);

        details.getInstructor().setInstructorDetail(null);

        em.remove(details);
    }

    @Override
    public List<Course> findCoursesByInstructorId(int theId) {
        TypedQuery<Course> query = em.createQuery("FROM Course WHERE instructor.id=:data", Course.class);
        query.setParameter("data", theId);

        List<Course> courses = query.getResultList();
        return courses;
    }

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

    @Override
    @Transactional
    public void update(Instructor in) {
        em.merge(in);
    }

    @Override
    @Transactional
    public void deleteCourseById(int theId) {
        Course c = em.find(Course.class, theId);

        if (c.getInstructor() != null) {
            c.setInstructor(null);
        }

        em.remove(c);
    }

    @Override
    @Transactional
    public void save(Course theCourse) {
        em.persist(theCourse);
    }

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



    @Override
    @Transactional
    public void update(Student student) {
        em.merge(student);
    }

    @Override
    public Student findStudentAndCoursesByStudentId(int theId) {
        TypedQuery<Student> query = em.createQuery(
            "SELECT s FROM Student s "
            +"JOIN FETCH s.courses "
            +"WHERE s.id=:data",Student.class);
        query.setParameter("data", theId);

        Student student = query.getSingleResult();

        return student;
    }

    @Override
    @Transactional 
    public void deleteStudentById(int theId) {
        Student student = em.find(Student.class, theId);

        if(student != null){
            List<Course> courses = student.getCourses();

            for(Course c: courses){
                c.getStudents().remove(student);
            }
        }

        em.remove(student);
    }

}
