package org.formation.jsf.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.formation.jsf.model.Student;

public class StudentDao implements IStudentDao {

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");

	@Override
	public List<Student> getStudents() throws Exception {

		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();

		txn.begin();
		List<Student> reList = new ArrayList<>();

		TypedQuery<Student> query = em.createQuery("from Student", Student.class);
		reList = query.getResultList();

		txn.commit();
		em.close();

		return reList;
	}

	@Override
	public void addStudent(Student theStudent) throws Exception {

		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();

		txn.begin();
		em.persist(theStudent);
		txn.commit();
		em.close();
	}

	@Override
	public Student getStudent(int studentId) throws Exception {
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();
		
		txn.begin();
		Student s = em.find(Student.class , studentId);
		txn.commit();
		em.close();
		return s;
	}

	@Override
	public void updateStudent(Student theStudent) throws Exception {
		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();

		txn.begin();
		em.merge(theStudent);
		txn.commit();
		em.close();

	}

	@Override
	public void deleteStudent(int studentId) throws Exception {

		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();
		
		txn.begin();
		Student s = em.find(Student.class, studentId);
		em.remove(s);
		txn.commit();
		em.close();
	}

}
