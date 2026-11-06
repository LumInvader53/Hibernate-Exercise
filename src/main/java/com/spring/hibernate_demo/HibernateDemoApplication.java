package com.spring.hibernate_demo;

import com.spring.hibernate_demo.dao.StudentDao;
import com.spring.hibernate_demo.dao.StudentDaoImplement;
import com.spring.hibernate_demo.entity.Student;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Entry point for the Hibernate demo application.
 */
@SpringBootApplication
public class HibernateDemoApplication {

  private final StudentDaoImplement studentDaoImplement;

  HibernateDemoApplication(StudentDaoImplement studentDaoImplement) {
    this.studentDaoImplement = studentDaoImplement;
  }

  public static void main(String[] args) {
    SpringApplication.run(HibernateDemoApplication.class, args);
  }

  @Bean
  CommandLineRunner commandLineRunner(StudentDao studentDao) {
    return runner -> {
      System.out.println("hello world");
      createStudent(studentDao);
      readStudent(studentDao);
      queryStudents(studentDao);
      queryStudentLastName(studentDao);
      updateStudent(studentDao);
      deleteStudent(studentDao);
      deleteAllStudent(studentDao);
    };
  }

  private void createStudent(StudentDao studentDao) {
    System.out.println("Creating student information...");
    Student student = new Student("Light",
        "Yagami",
        "light.yagami@example.com");
    Student student2 = new Student("Ritchie",
        "Mapulak",
        "ritchie.mapulak@example.com");
    Student student3 = new Student("Chito",
        "Miranda",
        "chito.miranda@example.com");
    Student student4 = new Student("Nemawashi",
        "San",
        "nemawashi.san@example.com");
    Student student5 = new Student("Lum",
        "Invader",
        "lum.invader@example.com");
    System.out.println(student);
    System.out.println(student2);
    System.out.println(student3);
    System.out.println(student4);
    System.out.println(student5);
    System.out.println("Saving student information...");
    studentDao.save(student);
    studentDao.save(student2);
    studentDao.save(student3);
    studentDao.save(student4);
    studentDao.save(student5);

    System.out.println("Saved! Generated id: " + student.getId());
    System.out.println("Saved! Generated id: " + student2.getId());
    System.out.println("Saved! Generated id: " + student3.getId());
    System.out.println("Saved! Generated id: " + student4.getId());
    System.out.println("Saved! Generated id: " + student5.getId());
  }

  private void readStudent(StudentDao studentDao) {
    System.out.println("Retrieving student id 2...");
    Student student = studentDao.findById(2);

    System.out.println("Found the student " + student);
  }

  private void queryStudents(StudentDao studentDao) {
    List<Student> students = studentDao.findAll();

    for (Student s : students) {
      System.out.println(s);
    }
  }

  private void queryStudentLastName(StudentDao studentDao) {
    List<Student> students = studentDao.findByLastName("Miranda");

    for (Student s : students) {
      System.out.println(s);
    }
  }

  private void updateStudent(StudentDao studentDao) {
    int id = 1;
    System.out.println("Student ID: " + id);
    Student student = studentDao.findById(id);

    System.out.println("Updating student name...");
    student.setFirstName("Dark");

    studentDao.update(student);

    System.out.println("Updated student information: " + student);
  }

  private void deleteStudent(StudentDao studentDao) {
    int id = 4;
    System.out.println("Delete ID: " + id);

    studentDao.delete(id);
  }

  private void deleteAllStudent(StudentDao studentDao) {
    System.out.println("Deleting all student information...");
    int rowsDeleted = studentDao.deleteAll();
    System.out.println("Number of deleted rows: " + rowsDeleted);
  }
}
