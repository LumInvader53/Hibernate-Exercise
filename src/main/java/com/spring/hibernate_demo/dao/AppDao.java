package com.spring.hibernate_demo.dao;

import java.util.List;

import com.spring.hibernate_demo.entity.Course;
import com.spring.hibernate_demo.entity.Instructor;
import com.spring.hibernate_demo.entity.InstructorDetail;
import com.spring.hibernate_demo.entity.Student;

public interface AppDao {
    void save(Instructor theInstructor);
    void save(Course theCourse);
    Instructor findInstructorById(int theId);
    void deleteInstructorById(int theId);
    void deleteStudentById(int theId);
    InstructorDetail findInstructorDetailById(int theId);
    void deleteInstructorDetail(int theId);
    List<Course> findCoursesByInstructorId(int theId);
    Instructor findInstructorByIdJoinFetch(int theId);
    void update(Instructor in);
    void update(Student student);
    void deleteCourseById(int theId);
    Course findCourseAndReviewsByCourseId(int theId);
    Course findCourseAndStudentByCourseId(int theId);
    Student findStudentAndCoursesByStudentId(int theId);
}
