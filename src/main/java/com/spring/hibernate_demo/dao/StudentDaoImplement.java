package com.spring.hibernate_demo.dao;

import org.springframework.stereotype.Repository;

import com.spring.hibernate_demo.entity.Student;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Repository
public class StudentDaoImplement implements StudentDao{

    private EntityManager em;

    public StudentDaoImplement(EntityManager em){
        this.em = em;
    }

    @Override
    @Transactional
    public void save(Student theStudent) {
        em.persist(theStudent);
    }

    @Override
    public Student findById(Integer id) {
        return em.find(Student.class, id);
    }

}
