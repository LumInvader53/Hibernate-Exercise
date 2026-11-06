package com.spring.hibernate_demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.spring.hibernate_demo.dao.StudentDao;
import com.spring.hibernate_demo.entity.Student;

@SpringBootApplication
public class HibernateDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(HibernateDemoApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StudentDao studentDao) {
		return runner -> {
			System.out.println("hello world");
			// createStudent(studentDao);
			readStudent(studentDao);
		};
	}

	private void createStudent(StudentDao studentDao) {
		System.out.println("Creating student information...");
		Student student = new Student("Light",
				"Yagami",
				"light.yagami@example.com");
		System.out.println(student);
		System.out.println("Saving student information...");
		studentDao.save(student);

		System.out.println("Saved! Generated id: " + student.getId());
	}

	private void readStudent(StudentDao studentDao) {
		System.out.println("Retrieving student id 2...");
		Student student = studentDao.findById(2);

		System.out.println("Found the student " + student);
	}

}
