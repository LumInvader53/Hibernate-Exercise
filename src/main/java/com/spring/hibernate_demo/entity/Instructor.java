package com.spring.hibernate_demo.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Instructor entity persisted in the database.
 */
@Entity
@Table(name = "Instructor")
public class Instructor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "email")
  private String email;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "instructor_detail_id")
  private InstructorDetail instructorDetail;

  @OneToMany(mappedBy = "instructor", fetch = FetchType.LAZY, cascade = {
      CascadeType.DETACH,
      CascadeType.MERGE,
      CascadeType.PERSIST,
      CascadeType.REFRESH
  })
  private List<Course> courses;

  public Instructor() {
  }

  /**
   * Constructor for creating necessary elements of Instructor.
   * Requires first name, last name, and email
   *
   * @param firstName Instructor's first name
   * @param lastName  Instructor's last name
   * @param email     Instructor's email
   */
  public Instructor(String firstName, String lastName, String email) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  /**
   * Helper for adding course to list of courses.
   * Also helps assign an instructor to course.
   *
   * @param tcourse Course to be added to courses
   */
  public void add(Course tcourse) {
    if (this.courses == null) {
      this.courses = new ArrayList<>();
    }

    this.courses.add(tcourse);

    tcourse.setInstructor(this);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public InstructorDetail getInstructorDetail() {
    return instructorDetail;
  }

  public void setInstructorDetail(InstructorDetail instructorDetail) {
    this.instructorDetail = instructorDetail;
  }

  @Override
  public String toString() {
    return "Instructor [id=" + id + ", firstName="
        + firstName + ", lastName=" + lastName + ", email=" + email + "]";
    // + ", instructorDetail=" + instructorDetail + "]";
  }

  public List<Course> getCourses() {
    return courses;
  }

  public void setCourses(List<Course> courses) {
    this.courses = courses;
  }

}
