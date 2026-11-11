package com.spring.hibernate_demo;

import com.spring.hibernate_demo.dao.AppDao;
import com.spring.hibernate_demo.dao.StudentDao;
import com.spring.hibernate_demo.entity.Course;
import com.spring.hibernate_demo.entity.Instructor;
import com.spring.hibernate_demo.entity.InstructorDetail;
import com.spring.hibernate_demo.entity.Review;
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

  public static void main(String[] args) {
    SpringApplication.run(HibernateDemoApplication.class, args);
  }

  /**
   * Starts the demo runners for both student
   * and instructor-related data examples.
   *
   * @param studentDao DAO used for basic student persistence examples
   * @param app        DAO used for instructor, course, and relationship demos
   * @return a command-line runner that executes the selected demo methods
   */
  @Bean
  CommandLineRunner commandLineRunner(StudentDao studentDao, AppDao app) {
    return runner -> {
      System.out.println("Student Runner");
      createStudent(studentDao);
      readStudent(studentDao);
      queryStudents(studentDao);
      queryStudentLastName(studentDao);
      updateStudent(studentDao);
      deleteStudent(studentDao);
      deleteAllStudent(studentDao);

      System.out.println("App Runner");

      createInstructor(app);
      findInstructor(app);
      deleteInstructor(app);
      findInstructorDetail(app);
      deleteInstructorDetail(app);
      createInstructorWithCourses(app);
      findInstructorWithCourses(app);
      findCoursesForInstructor(app);
      findInstructorWithCoursesJoinFetch(app);
      updateInstructor(app);
      deleteInstructorOnly(app);
      deleteCourseAndReviews(app);
      createCourseAndReviews(app);
      retrieveCourseAndReviews(app);
      createCourseAndStudents(app);
      findCourseAndStudents(app);
      findStudentsAndCourses(app);
      addMoreCoursesForStudent(app);
      deleteCourseById(app);
      deleteStudent(app);
    };
  }

  /**
   * Creates a new instructor record with a corresponding instructor detail.
   *
   * @param app DAO used to persist the instructor entity
   */
  private void createInstructor(AppDao app) {
    Instructor ins = new Instructor("Vicky",
        "Magtatangol", "vicky.mags@example.com");

    InstructorDetail details = new InstructorDetail(
        "Math", "Solving problems");

    ins.setInstructorDetail(details);

    System.out.println("Information saved!");
    app.save(ins);
  }

  /**
   * Retrieves an instructor by their ID and prints the related detail record.
   *
   * @param app DAO used to fetch the instructor entity
   */
  private void findInstructor(AppDao app) {
    int id = 1;
    System.out.println("Find id: " + id);

    Instructor ins = app.findInstructorById(id);

    System.out.println("Instructor: " + ins);
    System.out.println("Details: " + ins.getInstructorDetail());
  }

  /**
   * Deletes an instructor record using the given ID.
   *
   * @param app DAO used to remove the instructor entity
   */
  private void deleteInstructor(AppDao app) {
    int id = 1;
    System.out.println("Delete id: " + id);

    app.deleteInstructorById(id);
    System.out.println("Deleted successfully!");
  }

  /**
   * Finds the instructor detail linked to a specific instructor detail ID.
   *
   * @param app DAO used to fetch the instructor detail entity
   */
  private void findInstructorDetail(AppDao app) {
    int id = 4;
    InstructorDetail inDetail = app.findInstructorDetailById(id);

    System.out.println("Details: " + inDetail);
    System.out.println("Instructor: " + inDetail.getInstructor());

  }

  /**
   * Deletes an instructor detail record by ID.
   *
   * @param app DAO used to remove the instructor detail entity
   */
  private void deleteInstructorDetail(AppDao app) {
    int id = 4;
    System.out.println("Delete id: " + id);

    app.deleteInstructorDetail(id);
    System.out.println("Deleted successfully");
  }

  /**
   * Creates an instructor with multiple courses assigned to them.
   *
   * @param app DAO used to save the instructor and course associations
   */
  private void createInstructorWithCourses(AppDao app) {
    Instructor ins = new Instructor(
        "Peri", "Winkle", "peri.winkle@example.com");
    InstructorDetail detail = new InstructorDetail(
        "Science", "Sprinkling dust");

    ins.setInstructorDetail(detail);

    Course course1 = new Course("Physics");
    Course course2 = new Course("Biology");

    ins.add(course1);
    ins.add(course2);

    System.out.println("Courses saved: " + ins.getCourses());
    app.save(ins);
  }

  /**
   * Retrieves an instructor together with the courses assigned to them.
   *
   * @param app DAO used to fetch the instructor and course list
   */
  private void findInstructorWithCourses(AppDao app) {
    int id = 11;
    System.out.println("Find instructor id: " + id);

    Instructor ins = app.findInstructorById(id);

    System.out.println("Instructor: " + ins);
    System.out.println("Courses: " + ins.getCourses());
  }

  /**
   * Loads the courses associated with a specific instructor ID.
   *
   * @param app DAO used to fetch the instructor's course list
   */
  private void findCoursesForInstructor(AppDao app) {
    int id = 11;

    System.out.println("Find instructor id: " + id);
    Instructor ins = app.findInstructorById(id);

    System.out.println("Instructor: " + ins);

    System.out.println("Courses from id: " + id);
    List<Course> courses = app.findCoursesByInstructorId(id);

    ins.setCourses(courses);

    System.out.println(ins.getCourses());
  }

  /**
   * Retrieves an instructor and their course relationships using a join fetch
   * query.
   *
   * @param app DAO used to fetch instructor details with eager course loading
   */
  private void findInstructorWithCoursesJoinFetch(AppDao app) {
    int id = 13;
    System.out.println("Find instructor id: " + id);

    Instructor ins = app.findInstructorByIdJoinFetch(id);

    System.out.println("Instructor: " + ins);
    System.out.println("Courses: " + ins.getCourses());
  }

  /**
   * Updates an instructor's last name and persists the change.
   *
   * @param app DAO used to update the instructor entity
   */
  private void updateInstructor(AppDao app) {
    int id = 6;
    System.out.println("Find instructor id: " + id);

    Instructor ins = app.findInstructorById(id);

    ins.setLastName("Cosma");

    System.out.println("Updated!");
    app.update(ins);
  }

  /**
   * Deletes an instructor while leaving the remaining data intact.
   *
   * @param app DAO used to remove the instructor entity
   */
  private void deleteInstructorOnly(AppDao app) {
    int id = 11;
    System.out.println("Delete instructor: " + id);

    app.deleteInstructorById(id);
    System.out.println("Deleted successfully!");
  }

  /**
   * Deletes a course and all of its associated reviews.
   *
   * @param app DAO used to remove the course and review entries
   */
  private void deleteCourseAndReviews(AppDao app) {
    int id = 3;
    System.out.println("Delete course: " + id);

    app.deleteCourseById(id);
    System.out.println("Deleted sucessfully!");
  }

  /**
   * Creates a new course and attaches multiple review entries to it.
   *
   * @param app DAO used to save the course and reviews
   */
  private void createCourseAndReviews(AppDao app) {
    Course course = new Course("Hibernate missionz");

    course.addReview(new Review("すごいですね"));
    course.addReview(new Review("Understood well"));
    course.addReview(new Review("Nice kaaaaa"));

    System.out.println("Course saved!");
    app.save(course);
  }

  /**
   * Retrieves a course and its reviews using the course ID.
   *
   * @param app DAO used to fetch the course and associated reviews
   */
  private void retrieveCourseAndReviews(AppDao app) {
    int id = 5;
    System.out.println("Get course and reviews from id: " + id);

    Course course = app.findCourseAndReviewsByCourseId(id);

    System.out.println(course);
    System.out.println(course.getReview());
  }

  /**
   * Creates a course with two students enrolled in it.
   *
   * @param app DAO used to persist the course and student relationship
   */
  private void createCourseAndStudents(AppDao app) {
    Course course = new Course("Suntukan Online");

    Student student1 = new Student(
        "Light", "Yagami", "light.yagami@example.com");
    Student student2 = new Student(
        "Leslie", "Andrews", "leslie.andrews@example.com");

    course.addStudent(student1);
    course.addStudent(student2);

    app.save(course);
    System.out.println("Successfully enrolled");
  }

  /**
   * Retrieves a course together with all enrolled students.
   *
   * @param app DAO used to load the course and student list
   */
  private void findCourseAndStudents(AppDao app) {
    int id = 6;
    Course course = app.findCourseAndStudentByCourseId(id);

    System.out.println("Course: " + course);
    System.out.println("Students: " + course.getStudents());
  }

  /**
   * Retrieves a student and the courses they are enrolled in.
   *
   * @param app DAO used to fetch the student and course mapping
   */
  private void findStudentsAndCourses(AppDao app) {
    int id = 8;
    Student student = app.findStudentAndCoursesByStudentId(id);

    System.out.println("Student: " + student);
    System.out.println("Courses: " + student.getCourses());
  }

  /**
   * Adds more courses to an existing student enrollment.
   *
   * @param app DAO used to update the student's course list
   */
  private void addMoreCoursesForStudent(AppDao app) {
    int id = 6;
    Student student = app.findStudentAndCoursesByStudentId(id);

    Course course1 = new Course("Manligaw Tutorial");
    Course course2 = new Course("Paano mag tahal ng lapis");
    Course course3 = new Course("Spring boots");

    student.addCourse(course1);
    student.addCourse(course2);
    student.addCourse(course3);

    System.out.println("Successfully enrolled to new courses!");

    app.update(student);
  }

  /**
   * Deletes a course record by ID.
   *
   * @param app DAO used to remove the course entity
   */
  private void deleteCourseById(AppDao app) {
    int id = 10;
    System.out.println("Delete course: " + id);

    app.deleteCourseById(id);
    System.out.println("Deleted successfully!");
  }

  /**
   * Deletes a student record by ID.
   *
   * @param app DAO used to remove the student entity
   */
  private void deleteStudent(AppDao app) {
    int id = 7;
    System.out.println("Delete student: " + id);

    app.deleteStudentById(id);
    System.out.println("Deleted successfully !");
  }

  /**
   * Removes student from database based on ID.
   *
   * @param studentDao DAO used to delete student entity
   */
  private void deleteStudent(StudentDao studentDao) {
    int id = 4;
    System.out.println("Delete ID: " + id);

    studentDao.delete(id);
  }

  /**
   * Creates multiple demo student data.
   *
   * @param studentDao DAO used to add student entity
   */
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

  /**
   * Retrieves student data from id.
   *
   * @param studentDao DAO used to retrieve student entity
   */
  private void readStudent(StudentDao studentDao) {
    System.out.println("Retrieving student id 2...");
    Student student = studentDao.findById(2);

    System.out.println("Found the student " + student);
  }

  /**
   * Fetches all student data.
   *
   * @param studentDao DAO used to retrieve student entity
   */
  private void queryStudents(StudentDao studentDao) {
    List<Student> students = studentDao.findAll();

    for (Student s : students) {
      System.out.println(s);
    }
  }

  /**
   * Fetches student data based on last name.
   *
   * @param studentDao DAO used to retrieve student entity
   */
  private void queryStudentLastName(StudentDao studentDao) {
    List<Student> students = studentDao.findByLastName("Miranda");

    for (Student s : students) {
      System.out.println(s);
    }
  }

  /**
   * Updates student data.
   * Needs id and field to change.
   *
   * @param studentDao DAO used to update student entity
   */
  private void updateStudent(StudentDao studentDao) {
    int id = 1;
    System.out.println("Student ID: " + id);
    Student student = studentDao.findById(id);

    System.out.println("Updating student name...");
    student.setFirstName("Dark");

    studentDao.update(student);

    System.out.println("Updated student information: " + student);
  }

  /**
   * Deletes all students and prints how many were deleted.
   *
   * @param studentDao DAO used to delete student entity
   */
  private void deleteAllStudent(StudentDao studentDao) {
    System.out.println("Deleting all student information...");
    int rowsDeleted = studentDao.deleteAll();
    System.out.println("Number of deleted rows: " + rowsDeleted);
  }
}
