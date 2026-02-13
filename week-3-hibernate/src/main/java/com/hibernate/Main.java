package com.hibernate;



import com.hibernate.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();

        Session session = factory.openSession();


        try {

            Student student = new Student();
            student.setFirstName("John");
            student.setLastName("Doe");
            student.setEmail("john@example.com");
            student.setPhone("123456789");
            student.setDateOfBirth(LocalDate.of(2000, 1, 1));
            student.setGender("Male");
            student.setAddress("New York");
            student.setEnrollmentDate(LocalDate.now());
            student.setGpa(3.8);
            student.setStatus(Student.Status.ACTIVE);

            session.beginTransaction();
            session.persist(student);
            session.getTransaction().commit();

            System.out.println("Student saved successfully!");

        } finally {
            factory.close();
        }
    }
}
