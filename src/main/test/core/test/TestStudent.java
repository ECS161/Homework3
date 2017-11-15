package core.test;
import core.api.IInstructor;
import core.api.IStudent;
import core.api.impl.Student;
import core.api.IAdmin;
import core.api.impl.Admin;
import core.api.impl.Instructor;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestStudent {
	
	private IInstructor instructor;
	private IAdmin admin;
	private IStudent student;
	
	@Before
	public void setup() {
		admin = new Admin();
		instructor = new Instructor();
		student = new Student();
	}
	
	@Test
	public void testRegisterClassExists() {
		this.admin.createClass("Test", 2017, "Instructor", 10);
		this.student.registerForClass("Student1", "Test", 2017);
		assertTrue(this.student.isRegisteredFor("Student1", "Test", 2017));
	}
	
	@Test
	public void testRegisterClassDoesNotExist() {

		this.student.registerForClass("Student1", "NoClass", 2017);
		assertFalse(this.student.isRegisteredFor("Student1", "NoClass", 2017));
		
	}
	
	@Test 
	public void testRegisterClassWithSpace() {
		this.admin.createClass("Test", 2017, "Instructor", 10);
		this.student.registerForClass("Student1", "Test" , 2017);
		Student other = new Student();
		other.registerForClass("Student2", "Test" , 2017);
		//this.student.isRegisteredFor("Student1", "Test", 2017) &&
		assertTrue( this.student.isRegisteredFor("Student1", "Test", 2017) );
		assertTrue( other.isRegisteredFor("Student2", "Test" , 2017) );
	}
	
	@Test 
	public void testRegisterClassWithNoSpace() {
		this.admin.createClass("Test", 2017, "Instructor", 1);
		this.student.registerForClass("Student1", "Test" , 2017);
		Student other = new Student();
		other.registerForClass("Student2", "Test" , 2017);
		assertFalse( other.isRegisteredFor("Student2", "Test" , 2017) );
	}
	
	//Class has not ended and student has registered
	@Test
	public void testDropClassRegistered() {
		this.admin.createClass("Test", 2018, "Instructor", 1);
		this.student.registerForClass("Student1", "Test" , 2018);
		this.student.dropClass("Student1", "Test", 2018);
		assertFalse( this.student.isRegisteredFor("Student1", "Test", 2018) );
	}
	
	//Seems redundant
	@Test
	public void testDropClassNotRegistered() {
		this.admin.createClass("Test", 2017, "Instructor", 1);
		this.student.dropClass("Student1", "Test", 2017);
		assertFalse( this.student.isRegisteredFor("Student1", "Test", 2017) );
	}
	
	@Test
	public void testDropClassNotExist() {
		this.student.dropClass("Student1", "Test", 2017);
		assertFalse( this.student.isRegisteredFor("Student1", "Test", 2017) );
	}
	
	@Test
	public void testSubmitHomeworkExists() {
		this.admin.createClass("Test", 2017, "Instructor", 1);
		this.student.registerForClass("Student1", "Test" , 2017);
		this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
		this.student.submitHomework("Student1", "HW1", "Hello World", "Test", 2017);
		assertTrue( this.student.hasSubmitted("Student1", "HW1", "Test", 2017) );
	}
	
	@Test
	public void testSubmitHomeworkDoesNotExist() {
		this.admin.createClass("Test", 2017, "Instructor", 1);
		this.student.registerForClass("Student1", "Test" , 2017);
		this.student.submitHomework("Student1", "HW1", "Hello World", "Test", 2017);
		assertFalse( this.student.hasSubmitted("Student1", "HW1", "Test", 2017) );
	}
	
	@Test 
	public void testSubmitHomeworkNotRegistered() {
		this.admin.createClass("Test", 2017, "Instructor", 1);
		this.student.submitHomework("Student1", "HW1", "Hello World", "Test", 2017);
		assertFalse( this.student.hasSubmitted("Student1", "HW1", "Test", 2017) );
	}
	
	@Test 
	public void testSubmitHomeworkClassInvalid() {
		this.student.submitHomework("Student1", "HW1", "Hello World", "Test", 2017);
		assertFalse( this.student.hasSubmitted("Student1", "HW1", "Test", 2017) );
	}
	
	@Test
	public void testSubmitHomeworkFuture() {
		this.admin.createClass("Test", 2018, "Instructor", 1);
		this.student.registerForClass("Student1", "Test" , 2018);
		this.instructor.addHomework("Instructor", "Test", 2018, "HW1");
		this.student.submitHomework("Student1", "HW1", "Hello World", "Test", 2018);
		assertFalse( this.student.hasSubmitted("Student1", "HW1", "Test", 2018) );		
	}
	
}
