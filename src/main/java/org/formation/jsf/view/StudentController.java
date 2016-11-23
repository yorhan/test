package org.formation.jsf.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.formation.jsf.model.Student;
import org.formation.jsf.service.IStudentService;

@Named
@SessionScoped
public class StudentController implements Serializable{

	private static final long serialVersionUID = 3774463683041113840L;

	private List<Student> students;
	private Logger logger = Logger.getLogger(getClass().getName());

	@Inject
	private IStudentService service;

	public StudentController() throws Exception {
		students = new ArrayList<>();
	
	}

	@PostConstruct
	public void initService() {
		System.out.println(this.getClass().getName()+"je suis ;;construit !"+service);
	}

	public List<Student> getStudents() {
		return students;
	}

	public void loadStudents() {

		logger.info("Loading students");

		students.clear();

		try {

			// get all students from database
			students = service.getStudents();

		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading students", exc);

			// add error message for JSF page
			addErrorMessage(exc);
		}
	}

	public String addStudent(Student theStudent) {

		logger.info("Adding student: " + theStudent);

		try {

			// add student to the database
			service.addStudent(theStudent);

		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error adding students", exc);

			// add error message for JSF page
			addErrorMessage(exc);

			return null;
		}

		return "list-students?faces-redirect=true";
	}

	public String loadStudent(int studentId) {

		logger.info("loading student: " + studentId);

		try {
			// get student from database
			Student theStudent = service.getStudent(studentId);

			// put in the request attribute ... so we can use it on the form
			// page
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("student", theStudent);

		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading student id:" + studentId, exc);

			// add error message for JSF page
			addErrorMessage(exc);

			return null;
		}

		return "update-student-form.xhtml";
	}

	public String updateStudent(Student theStudent) {

		logger.info("updating student: " + theStudent);

		try {

			// update student in the database
			service.updateStudent(theStudent);

		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error updating student: " + theStudent, exc);

			// add error message for JSF page
			addErrorMessage(exc);

			return null;
		}

		return "list-students?faces-redirect=true";
	}

	public String deleteStudent(int studentId) {

		logger.info("Deleting student id: " + studentId);

		try {

			// delete the student from the database
			service.deleteStudent(studentId);

		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error deleting student id: " + studentId, exc);

			// add error message for JSF page
			addErrorMessage(exc);

			return null;
		}

		return "list-students";
	}

	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

}
