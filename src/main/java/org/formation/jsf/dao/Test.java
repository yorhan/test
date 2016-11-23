package org.formation.jsf.dao;

import java.util.List;

import org.formation.jsf.model.Student;

public class Test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		StudentDao sdao = new StudentDao();
		Student theStudent = new Student("firstName", "lastName", "email");
		sdao.addStudent(theStudent);
		
		sdao.addStudent(new Student("a", "b", "e"));
		List<Student> liste = sdao.getStudents();
		System.out.println(liste);
		sdao.deleteStudent(1);
		List<Student> liste1 = sdao.getStudents();
		System.out.println(liste1);
	}

}
