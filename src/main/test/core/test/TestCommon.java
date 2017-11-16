package core.test;

import core.api.IAdmin;
import core.api.IInstructor;
import core.api.IStudent;
import core.api.impl.Admin;
import core.api.impl.Instructor;
import core.api.impl.Student;

public class TestCommon {

	IAdmin admin;
	IInstructor instructor;
	IStudent student;
	
	void _setup() {
		this.admin = new Admin();
        this.instructor = new Instructor();
        this.student = new Student();
	}
	
	boolean createClassAndTest(String className, int year, String instructorName, int capacity) {
		this.admin.createClass(className, year, instructorName, capacity);
		
		return this.admin.classExists(className, year) && 
				this.admin.getClassInstructor(className, year).equals(instructorName) &&
				this.admin.getClassCapacity(className, year) == capacity;
	}
	
	boolean changeCapacityAndTest(String className, int year, int capacity) {
		this.admin.changeCapacity(className, year, capacity);
		
		return this.admin.classExists(className, year) && 
				this.admin.getClassCapacity(className, year) == capacity;
	}
	
	boolean addHomeworkAndTest(String instructorName, String className, int year, String homeworkName) {
		this.instructor.addHomework(instructorName, className, year, homeworkName);
		return this.instructor.homeworkExists(className, year, homeworkName);
	}
	
	boolean assignGradeAndTest(String instructorName, String className, int year, String homeworkName, String studentName, int grade) {
		this.instructor.assignGrade(instructorName, className, year, homeworkName, studentName, grade);
		
		Integer gradeResult = this.instructor.getGrade(className, year, homeworkName, studentName);
		
		return gradeResult != null && gradeResult == grade;
	}
	
	boolean registerAndTest(String studentName, String className, int year) {
		this.student.registerForClass(studentName, className, year);
		return this.student.isRegisteredFor(studentName, className, year);
	}

	// Return true if student successfully dropped
	boolean dropAndTest(String studentName, String className, int year) {
		this.student.dropClass(studentName, className, year);
		return !this.student.isRegisteredFor(studentName, className, year);
	}
	
	boolean submitHomeworkAndTest(String studentName, String homeworkName, String className, int year) {
		this.student.submitHomework(studentName, homeworkName, "", className, year);
		return this.student.hasSubmitted(studentName, homeworkName, className, year);
	}
}
