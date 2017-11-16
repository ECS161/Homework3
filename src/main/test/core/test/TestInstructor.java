package core.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import core.api.IAdmin;
import core.api.IInstructor;
import core.api.IStudent;
import core.api.impl.Admin;
import core.api.impl.Instructor;
import core.api.impl.Student;

public class TestInstructor {

	private IStudent studentA;
	private IAdmin admin;
	private IInstructor instructor;
	
	@Before
	public void setup() {
		this.studentA = new Student();
		this.instructor = new Instructor();
		this.admin = new Admin();
	}
	
	@Test
	public void testAddHomeworkNormal() {
		this.admin.createClass("class", 2017, "instructor", 15);
		this.instructor.addHomework("instructor", "class", 2017, "hw1");
		assertTrue(this.instructor.homeworkExists("class", 2017, "hw1"));
	}
	
	@Test
	public void testAddHomeworkButClassDoesntExistYet() {
		this.instructor.addHomework("instructor", "class", 2017, "hw1");
		assertFalse(this.instructor.homeworkExists("class", 2017, "hw1"));
	}
	
	@Test
	public void testAddHomeworkToWrongClassYear() {
		this.admin.createClass("class", 2017, "instructor", 15);
		this.instructor.addHomework("instructor", "class", 2016, "hw1");
		assertFalse(this.instructor.homeworkExists("class", 2017, "hw1"));
	}
	
	@Test
	public void testAddHomeworkToWrongClassName() {
		this.admin.createClass("class", 2017, "instructor", 15);
		this.instructor.addHomework("instructor", "classfake", 2017, "hw1");
		assertFalse(this.instructor.homeworkExists("class", 2017, "hw1"));
	}
	
	@Test
	public void testAddHomeworkToWrongInstructorName() {
		this.admin.createClass("class", 2017, "instructor", 15);
		this.instructor.addHomework("instructorfake", "class", 2017, "hw1");
		assertFalse(this.instructor.homeworkExists("class", 2017, "hw1"));
	}
	
	@Test
	public void testAddHomeworkWithEmptyHWName() {
		this.admin.createClass("class", 2017, "instructor", 15);
		this.instructor.addHomework("instructor", "class", 2017, "");
		assertFalse(this.instructor.homeworkExists("class", 2017, ""));
	}
	
	@Test
	public void testAddHomeworkWithEmptyInstructorName() {
		this.admin.createClass("class", 2017, "", 15);
		this.instructor.addHomework("", "class", 2017, "hw1");
		assertFalse(this.instructor.homeworkExists("class", 2017, "hw1"));
	}
	
	@Test
	public void testAddHomeworkHasntBeenAssignedYet() {
		this.admin.createClass("class", 2017, "instructor", 15);
		assertFalse(this.instructor.homeworkExists("class", 2017, "hw1"));
	}
	
	//////////////
	
	@Test
	public void testAssignGrade() {
		this.admin.createClass("class", 2017, "instructor", 15);
		this.instructor.addHomework("instructor", "class", 2017, "hw");
		this.studentA.submitHomework("studentA", "hw", "answer", "class", 2017);
		this.instructor.assignGrade("instructor", "class", 2017, "hw", "studentA", 100);
		assertTrue(100 == this.instructor.getGrade("class", 2017, "hw", "studentA"));
	}
	
	@Test
	public void testAssignGradeButInstructorHasntBeenAddedToClass() {
		this.admin.createClass("class", 2017, "instructor", 15);
		this.instructor.addHomework("instructorfake", "class", 2017, "hw");
		this.studentA.submitHomework("studentA", "hw", "answer", "class", 2017);
		this.instructor.assignGrade("instructorfake", "class", 2017, "hw", "studentA", 100);
		assertTrue(null == this.instructor.getGrade("class", 2017, "hw", "studentA"));
	}
	
	@Test
	public void testAssignGradeButHWHasNotBeenAssigned() {
		this.admin.createClass("class", 2017, "instructor", 15);
		this.studentA.submitHomework("studentA", "hw", "answer", "class", 2017);
		this.instructor.assignGrade("instructor", "class", 2017, "hw", "studentA", 100);
		assertTrue(null == this.instructor.getGrade("class", 2017, "hw", "studentA"));
	}
	
	@Test
	public void testAssignGradeStudentHasntSubmitted() {
		this.admin.createClass("class", 2017, "instructor", 15);
		this.instructor.addHomework("instructor", "class", 2017, "hw");
		this.instructor.assignGrade("instructor", "class", 2017, "hw", "studentA", 100);
		assertTrue(null == this.instructor.getGrade("class", 2017, "hw", "studentA"));
	}
	
	@Test
	public void testAssignGradeNegativeGrade() {
		this.admin.createClass("class", 2017, "instructor", 15);
		this.instructor.addHomework("instructor", "class", 2017, "hw");
		this.studentA.submitHomework("studentA", "hw", "answer", "class", 2017);
		this.instructor.assignGrade("instructor", "class", 2017, "hw", "studentA", -1);
		assertFalse(-1 == this.instructor.getGrade("class", 2017, "hw", "studentA"));
	}
}
