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

public class TestStudent {

	private IStudent studentA, studentB;
	private IAdmin admin;
	private IInstructor instructor;
	
	@Before
	public void setup() {
		this.studentA = new Student();
		this.studentB = new Student();
		this.instructor = new Instructor();
		this.admin = new Admin();
	}
	
	@Test
	public void testRegisterForClassNormal() {
		this.admin.createClass("class", 2017, "instructor", 10);
		this.studentA.registerForClass("studentA", "class", 2017);
		assertTrue(this.studentA.isRegisteredFor("studentA", "class", 2017));
	}
	
	@Test
	public void testRegisterForNonExistingClass() {
		this.studentA.registerForClass("studentA", "class", 2017);
		assertFalse(this.studentA.isRegisteredFor("studentA", "class", 2017));
	}
	
	@Test
	public void testRegisterForFullClass() {
		this.admin.createClass("class", 2017, "instructor", 1);
		this.studentA.registerForClass("studentA", "class", 2017);
		this.studentB.registerForClass("studentB", "class", 2017);
		assertFalse(this.studentB.isRegisteredFor("studentB", "class", 2017));
	}
	
	@Test
	public void testRegisterForClassWithEmptyString() {
		this.admin.createClass("", 2017, "instructor", 10);
		this.studentA.registerForClass("studentA", "", 2017);
		assertFalse(this.studentA.isRegisteredFor("studentA", "", 2017));
	}
	
	//////////////////////////////////
	
	@Test
	public void testDropClassNormal() {
		this.admin.createClass("class", 2017, "instructor", 1);
		this.studentA.registerForClass("studentA", "class", 2017);
		this.studentA.dropClass("studentA", "class", 2017);
		assertFalse(this.studentA.isRegisteredFor("studentA", "class", 2017));
	}
	
	@Test
	public void testDropClassButStudentWasntRegisteredForThatClass() {
		this.admin.createClass("classA", 2017, "instructor", 1);
		this.admin.createClass("classB", 2017, "instructor", 1);
		this.studentA.registerForClass("studentA", "classA", 2017);
		this.studentA.dropClass("studentA", "classB", 2017);
		assertTrue(this.studentA.isRegisteredFor("studentA", "classA", 2017));
	}
	
	/////////////////////////////////
	
	@Test
	public void testSubmitHomeworkNormal() {
		this.admin.createClass("class", 2017, "instructor", 10);
		this.instructor.addHomework("instructor", "class", 2017, "hw1");
		this.studentA.registerForClass("studentA", "class", 2017);
		this.studentA.submitHomework("studentA", "hw1", "answer", "class", 2017);
		assertTrue(this.studentA.hasSubmitted("studentA", "hw1", "class", 2017));
	}
	
	@Test
	public void testSubmitHomeworkButHomeworkDoesntExist() {
		this.admin.createClass("class", 2017, "instructor", 10);
		this.instructor.addHomework("instructor", "class", 2017, "hw1");
		this.studentA.submitHomework("studentAfake", "hwfake", "answer", "classfake", 2016);
		assertFalse(this.studentA.hasSubmitted("studentA", "hw1", "class", 2017));
	}
	
	@Test
	public void testSubmitHomeworkButStudentNotRegistered() {
		this.admin.createClass("class", 2017, "instructor", 10);
		this.instructor.addHomework("instructor", "class", 2017, "hw1");
		this.studentA.submitHomework("studentA", "hw1", "answer", "class", 2017);
		assertFalse(this.studentA.hasSubmitted("studentA", "hw1", "class", 2017));
	}
	
	@Test
	public void testSubmitHomeworkButClassNotTaughtThisYear() {
		this.admin.createClass("class", 2018, "instructor", 10);
		this.instructor.addHomework("instructor", "class", 2018, "hw1");
		this.studentA.registerForClass("studentA", "class", 2018);
		this.studentA.submitHomework("studentA", "hw1", "answer", "class", 2018);
		assertFalse(this.studentA.hasSubmitted("studentA", "hw1", "class", 2018));
	}
}
