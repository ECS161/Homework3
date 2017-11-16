package core.test;

import core.api.IAdmin;
import core.api.impl.Admin;
import core.api.IInstructor;
import core.api.impl.Instructor;
import core.api.IStudent;
import core.api.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestStudent {
	private IStudent student;
	private IAdmin admin;
	private IInstructor instructor;
	
	@Before
	public void setup()
	{
		this.student = new Student();
		this.admin = new Admin();
		this.instructor = new Instructor();
	}
	
	@Test
	public void testReg1()//no intended issues
	{
		this.admin.createClass("ECS161", 2017, "Devanbu", 2);
		this.student.registerForClass("David", "ECS161", 2017);
		assertTrue(this.student.isRegisteredFor("David", "ECS161", 2017));
	}
	@Test
	public void testRe2()//class doesnt exist
	{
		this.admin.createClass("ECS161", 2017, "Devanbu", 2);
		this.student.registerForClass("David", "ECS11", 2017);
		assertFalse(this.student.isRegisteredFor("David", "ECS11", 2017));
	}
	@Test
	public void testReg3()//reg for class out of date
	{
		this.admin.createClass("ECS161", 2017, "Devanbu", 2);
		this.student.registerForClass("David", "ECS161", 2016);
		assertFalse(this.student.isRegisteredFor("David", "ECS161", 2016));
	}
	@Test
	public void testReg4()//wrong name
	{
		this.admin.createClass("ECS161", 2017, "Devanbu", 2);
		this.student.registerForClass("David", "ECS161", 2017);
		assertFalse(this.student.isRegisteredFor("Davi", "ECS161", 2017));
	}
	
	@Test
	public void testReg5()//no capacity
	{
		this.admin.createClass("ECS161", 2017, "Devanbu", 2);
		IStudent student1 = new Student();
		IStudent student2 = new Student();
		student1.registerForClass("student1", "ECS161", 2017);
		student2.registerForClass("student2", "ECS161", 2017);
		this.student.registerForClass("David", "ECS161", 2017);
		assertFalse(this.student.isRegisteredFor("David", "ECS161", 2017));
	}
	
	@Test
	public void testReg6()//no student name
	{
		this.admin.createClass("ECS161", 2017, "Devanbu", 2);
		this.student.registerForClass("", "ECS161", 2017);
		assertFalse(this.student.isRegisteredFor("", "ECS161", 2017));
	}
	
	@Test
	public void testReg7()//no class name
	{
		this.admin.createClass("ECS161", 2017, "Devanbu", 2);
		this.student.registerForClass("David", "", 2017);
		assertFalse(this.student.isRegisteredFor("David", "", 2017));
	}
	@Test
	public void testDrop1()//no intended issues
	{
		this.admin.createClass("ECS161", 2017, "Devanbu", 2);
		this.student.registerForClass("David", "ECS161", 2017);
		this.student.dropClass("David", "ECS161", 2017);
		assertFalse(this.student.isRegisteredFor("David", "ECS161", 2017));
	}
	@Test
	public void testDrop2()//student dropped wrong class
	{
		this.admin.createClass("ECS161", 2017, "Devanbu", 2);
		this.student.registerForClass("David", "ECS161", 2017);
		this.student.dropClass("David", "ECS16", 2017);
		assertTrue(this.student.isRegisteredFor("David", "ECS161", 2017));
	}
	@Test
	public void testDrop3()//student dropped class from different year
	{
		this.admin.createClass("ECS161", 2017, "Devanbu", 2);
		this.student.registerForClass("David", "ECS161", 2017);
		this.student.dropClass("David", "ECS161", 2018);
		assertTrue(this.student.isRegisteredFor("David", "ECS161", 2017));
	}
	@Test
	public void testDrop4()//wrong dropped student name
	{
		this.admin.createClass("ECS161", 2017, "Devanbu", 2);
		this.student.registerForClass("David", "ECS161", 2017);
		this.student.dropClass("Davi", "ECS161", 2018);
		assertTrue(this.student.isRegisteredFor("David", "ECS161", 2017));
	}
	@Test
	public void testSubmit1()//no intended issues
	{
		this.admin.createClass("ECS161", 2017, "Devanbu", 2);
		this.instructor.addHomework("Devanbu", "ECS161", 2017, "P3");
		this.student.registerForClass("David", "ECS161", 2017);
		this.student.submitHomework("David", "P3", "fin", "ECS161", 2017);
		assertTrue(this.student.hasSubmitted("David", "P3", "ECS161", 2017));
	}
	@Test
	public void testSubmit2()//homework doesnt exist
	{
		this.admin.createClass("ECS161", 2017, "Devanbu", 2);
		//this.instructor.addHomework("Devanbu", "ECS161", 2017, "P3");
		this.student.registerForClass("David", "ECS161", 2017);
		this.student.submitHomework("David", "P3", "fin", "ECS161", 2017);
		assertFalse(this.student.hasSubmitted("David", "P3", "ECS161", 2017));
	}
	@Test
	public void testSubmit3()//Student not registered for class
	{
		this.admin.createClass("ECS161", 2017, "Devanbu", 2);
		this.instructor.addHomework("Devanbu", "ECS161", 2017, "P3");
		//this.student.registerForClass("David", "ECS161", 2017);
		this.student.submitHomework("David", "P3", "fin", "ECS161", 2017);
		assertFalse(this.student.hasSubmitted("David", "P3", "ECS161", 2017));
	}
	@Test
	public void testSubmit4()//class is taught in the future
	{
		this.admin.createClass("ECS161", 2018, "Devanbu", 2);
		this.instructor.addHomework("Devanbu", "ECS161", 2018, "P3");
		this.student.registerForClass("David", "ECS161", 2018);
		this.student.submitHomework("David", "P3", "fin", "ECS161", 2018);
		assertFalse(this.student.hasSubmitted("David", "P3", "ECS161", 2018));
	}
	@Test
	public void testSubmit5()//class is taught in the past
	{
		this.admin.createClass("ECS161", 2016, "Devanbu", 2);
		this.instructor.addHomework("Devanbu", "ECS161", 2016, "P3");
		this.student.registerForClass("David", "ECS161", 2016);
		this.student.submitHomework("David", "P3", "fin", "ECS161", 2016);
		assertFalse(this.student.hasSubmitted("David", "P3", "ECS161", 2016));
	}
	@Test
	public void testSubmit6()//class is current but homework submitted is for class that is taught in the future
	{
		this.admin.createClass("ECS161", 2017, "Devanbu", 2);
		this.instructor.addHomework("Devanbu", "ECS161", 2017, "P3");
		this.student.registerForClass("David", "ECS161", 2017);
		this.student.submitHomework("David", "P3", "fin", "ECS161", 2018);
		assertFalse(this.student.hasSubmitted("David", "P3", "ECS161", 2018));
	}
	@Test
	public void testSubmit7()//no intended issues
	{
		this.admin.createClass("ECS161", 2017, "Devanbu", 2);
		this.instructor.addHomework("Devanbu", "ECS161", 2017, "P3");
		this.student.registerForClass("David", "ECS160", 2017);
		this.student.submitHomework("David", "P3", "fin", "ECS160", 2017);
		assertFalse(this.student.hasSubmitted("David", "P3", "ECS160", 2017));
	}
	@Test
	public void testSubmit8()//submitted wrong homework name
	{
		this.admin.createClass("ECS161", 2017, "Devanbu", 2);
		this.instructor.addHomework("Devanbu", "ECS161", 2017, "P3");
		this.student.registerForClass("David", "ECS161", 2017);
		this.student.submitHomework("David", "P", "fin", "ECS161", 2017);
		assertFalse(this.student.hasSubmitted("David", "P", "ECS161", 2017));
	}
	@Test
	public void testSubmit9()//submitted to wrong class
	{
		this.admin.createClass("ECS161", 2017, "Devanbu", 2);
		this.instructor.addHomework("Devanbu", "ECS161", 2017, "P3");
		this.student.registerForClass("David", "ECS161", 2017);
		this.student.submitHomework("David", "P3", "fin", "ECS16", 2017);
		assertFalse(this.student.hasSubmitted("David", "P3", "ECS16", 2017));
	}
	@Test
	public void testSubmit10()//submitted to wrong class year
	{
		this.admin.createClass("ECS161", 2017, "Devanbu", 2);
		this.instructor.addHomework("Devanbu", "ECS161", 2017, "P3");
		this.student.registerForClass("David", "ECS161", 2017);
		this.student.submitHomework("David", "P3", "fin", "ECS16", 2018);
		assertFalse(this.student.hasSubmitted("David", "P3", "ECS16", 2018));
	}
	
}

