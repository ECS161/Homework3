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

public class TestInstructor {
	
	private IInstructor instructor;
	
	@Before
	public void setup()
	{
		
		this.instructor = new Instructor();
	}
	
	@Test
	public void checkHomework()// no intended error
	{
		IAdmin admin = new Admin();
		admin.createClass("ECS161", 2017, "Devanbu", 3);
		this.instructor.addHomework("Devanbu", "ECS161", 2017, "P3");
		assertTrue(this.instructor.homeworkExists("ECS161", 2017, "P3"));	
	}
	
	@Test
	public void checkHomework2()// wrong instructor
	{
		IAdmin admin = new Admin();
		admin.createClass("ECS161", 2017, "Devanbu", 3);
		this.instructor.addHomework("Vincent", "ECS161", 2017, "P3");
		assertFalse(this.instructor.homeworkExists("ECS161", 2017, "P3"));
	}
	
	@Test
	public void checkHomework3()// no course name
	{
		IAdmin admin = new Admin();
		admin.createClass("ECS161", 2017, "Devanbu", 3);
		this.instructor.addHomework("", "ECS161", 2017, "P3");
		assertFalse(this.instructor.homeworkExists("ECS161", 2017, "P3"));
	}
	
	@Test
	public void checkHomework4()// no instructor name
	{
		IAdmin admin = new Admin();
		admin.createClass("ECS161", 2017, "Devanbu", 3);
		this.instructor.addHomework("Devanbu", "", 2017, "P3");
		assertFalse(this.instructor.homeworkExists("", 2017, "P3"));
	}
	
	@Test
	public void checkHomework5()// no homework name
	{
		IAdmin admin = new Admin();
		admin.createClass("ECS161", 2017, "Devanbu", 3);
		this.instructor.addHomework("Devanbu", "ECS161", 2017, "");
		assertFalse(this.instructor.homeworkExists("ECS161", 2017, ""));
	}
	
	@Test
	public void checkHomework7()//wrong year
	{
		IAdmin admin = new Admin();
		admin.createClass("ECS161", 2017, "Devanbu", 3);
		this.instructor.addHomework("Devanbu", "ECS161", 2018, "P3");
		assertFalse(this.instructor.homeworkExists("ECS161", 2018, "P3"));
	}

	
	@Test
	public void checkGrade()//no intended error
	{
		IAdmin admin = new Admin();
		admin.createClass("ECS161", 2017, "Devanbu", 3);
		this.instructor.addHomework("Devanbu", "ECS161", 2017, "P3");
		IStudent student = new Student();
		student.registerForClass("David", "ECS161", 2017);
		student.submitHomework("David", "P3", "Done", "ECS161", 2017);
		assertTrue(student.hasSubmitted("David", "P3", "ECS161", 2017));
		this.instructor.assignGrade("Devanbu", "ECS161", 2017, "P3", "David", 100);
		assertEquals("Failure - Grades dont match", (Integer)100 , this.instructor.getGrade("ECS161", 2017, "P3", "David"));
	}
	
	@Test
	public void checkGrade2()//wrong homework grade
	{
		IAdmin admin = new Admin();
		admin.createClass("ECS161", 2017, "Devanbu", 3);
		this.instructor.addHomework("Devanbu", "ECS161", 2017, "P3");
		IStudent student = new Student();
		student.registerForClass("David", "ECS161", 2017);
		student.submitHomework("David", "P3", "Done", "ECS161", 2017);
		this.instructor.assignGrade("Devanbu", "ECS161", 2017, "P3", "David", 100);
		assertNotEquals("Failure - Grades dont match", (Integer)90 , this.instructor.getGrade("ECS161", 2017, "P3", "David"));
	}
	
	@Test
	public void checkGrade3()//submitted homework to wrong class
	{
		IAdmin admin = new Admin();
		admin.createClass("ECS161", 2017, "Devanbu", 3);
		this.instructor.addHomework("Devanbu", "ECS161", 2017, "P3");
		IStudent student = new Student();
		student.registerForClass("David", "ECS161", 2017);
		student.submitHomework("David", "P3", "Done", "ECS160", 2017);
		this.instructor.assignGrade("Devanbu", "ECS161", 2017, "P3", "David", 100);
		assertEquals("Failure - Grades dont match", null , this.instructor.getGrade("ECS160", 2017, "P3", "David"));
	}
	
	@Test
	public void checkGrade4()//student registered and submitted to wrong homework submission
	{
		IAdmin admin = new Admin();
		admin.createClass("ECS161", 2017, "Devanbu", 3);
		this.instructor.addHomework("Devanbu", "ECS161", 2017, "P3");
		IStudent student = new Student();
		student.registerForClass("David", "ECS160", 2017);
		student.submitHomework("David", "P3", "Done", "ECS160", 2017);
		this.instructor.assignGrade("Devanbu", "ECS161", 2017, "P3", "David", 100);
		assertEquals("Failure - Grades dont match", null , this.instructor.getGrade("ECS161", 2017, "P3", "David"));
	}

	@Test
	public void checkGrade5()//wrong homework name
	{
		IAdmin admin = new Admin();
		admin.createClass("ECS161", 2017, "Devanbu", 3);
		this.instructor.addHomework("Devanbu", "ECS161", 2017, "P3");
		IStudent student = new Student();
		student.registerForClass("David", "ECS161", 2017);
		student.submitHomework("David", "P3", "Done", "ECS161", 2017);
		this.instructor.assignGrade("Devanbu", "ECS161", 2017, "P3", "", 100);
		assertEquals("Failure - Grades dont match", null , this.instructor.getGrade("ECS161", 2017, "P3", "David"));
	}
	
	@Test
	public void checkGrade6()//wrong instructor
	{
		IAdmin admin = new Admin();
		admin.createClass("ECS161", 2017, "Devanbu", 3);
		this.instructor.addHomework("Devanbu", "ECS161", 2017, "P3");
		IStudent student = new Student();
		student.registerForClass("David", "ECS161", 2017);
		student.submitHomework("David", "P3", "Done", "ECS161", 2017);
		this.instructor.assignGrade("Vincent", "ECS161", 2017, "P3", "David", 100);
		assertEquals("Failure - Grades dont match", null, this.instructor.getGrade("ECS161", 2017, "P3", "David"));
	}
	
	@Test
	public void checkGrade7()//assigned grade to wrong class
	{
		IAdmin admin = new Admin();
		admin.createClass("ECS161", 2017, "Devanbu", 3);
		this.instructor.addHomework("Devanbu", "ECS161", 2017, "P3");
		IStudent student = new Student();
		student.registerForClass("David", "ECS161", 2017);
		student.submitHomework("David", "P3", "Done", "ECS161", 2017);
		this.instructor.assignGrade("Devanbu", "ECS16", 2017, "P3", "David", 100);
		assertEquals("Failure - Grades dont match", null , this.instructor.getGrade("ECS161", 2017, "P3", "David"));
	}
	@Test
	public void checkGrade8()//wrong year
	{
		IAdmin admin = new Admin();
		admin.createClass("ECS161", 2017, "Devanbu", 3);
		this.instructor.addHomework("Devanbu", "ECS161", 2017, "P3");
		IStudent student = new Student();
		student.registerForClass("David", "ECS161", 2017);
		student.submitHomework("David", "P3", "Done", "ECS161", 2017);
		this.instructor.assignGrade("Devanbu", "ECS160", 2017, "P3", "David", 100);
		assertEquals("Failure - Grades dont match", null , this.instructor.getGrade("ECS161", 2018, "P3", "David"));
	}
	
	@Test
	public void checkGrade9()//submitted to wrong class
	{
		IAdmin admin = new Admin();
		admin.createClass("ECS161", 2017, "Devanbu", 3);
		this.instructor.addHomework("Vincent", "ECS161", 2017, "P3");
		IStudent student = new Student();
		student.registerForClass("David", "ECS161", 2017);
		student.submitHomework("David", "P3", "Done", "ECS160", 2017);
		this.instructor.assignGrade("Devanbu", "ECS161", 2017, "P3", "David", 100);
		assertEquals("Failure - Grades dont match", null , this.instructor.getGrade("ECS160", 2017, "P3", "David"));
	}
	@Test
	public void checkGrade10()//wrong assignment class
	{
		IAdmin admin = new Admin();
		admin.createClass("ECS161", 2017, "Devanbu", 3);
		this.instructor.addHomework("Devanbu", "ECS16", 2017, "P3");
		IStudent student = new Student();
		student.registerForClass("David", "ECS161", 2017);
		student.submitHomework("David", "P3", "Done", "ECS161", 2017);
		this.instructor.assignGrade("Devanbu", "ECS161", 2017, "P3", "David", 100);
		assertEquals("Failure - Grades dont match", null , this.instructor.getGrade("ECS161", 2017, "P3", "David"));
	}
	
	@Test
	public void checkGrade11()//wrong assignment year
	{
		IAdmin admin = new Admin();
		admin.createClass("ECS161", 2017, "Devanbu", 3);
		this.instructor.addHomework("Devanbu", "ECS160", 201, "P3");
		IStudent student = new Student();
		student.registerForClass("David", "ECS161", 2017);
		student.submitHomework("David", "P3", "Done", "ECS161", 2017);
		this.instructor.assignGrade("Devanbu", "ECS161", 2017, "P3", "David", 100);
		assertEquals("Failure - Grades dont match", null , this.instructor.getGrade("ECS161", 2017, "P3", "David"));
	}
	
	@Test
	public void checkGrade12()//wrong assignment name
	{
		IAdmin admin = new Admin();
		admin.createClass("ECS161", 2017, "Devanbu", 3);
		this.instructor.addHomework("Devanbu", "ECS160", 2017, "P");
		IStudent student = new Student();
		student.registerForClass("David", "ECS161", 2017);
		student.submitHomework("David", "P3", "Done", "ECS161", 2017);
		this.instructor.assignGrade("Devanbu", "ECS161", 2017, "P3", "David", 100);
		assertEquals("Failure - Grades dont match", null , this.instructor.getGrade("ECS161", 2017, "P3", "David"));
	}
	
	@Test
	public void checkGrade13()// Wrong graded student name
	{
		IAdmin admin = new Admin();
		admin.createClass("ECS161", 2017, "Devanbu", 3);
		this.instructor.addHomework("Devanbu", "ECS161", 2017, "P3");
		IStudent student = new Student();
		student.registerForClass("David", "ECS161", 2017);
		student.submitHomework("David", "P3", "Done", "ECS161", 2017);
		this.instructor.assignGrade("Devanbu", "ECS161", 2017, "P3", "Davd", 100);
		assertEquals("Failure - Grades dont match", null , this.instructor.getGrade("ECS161", 2017, "P3", "David"));
	}
	
	@Test
	public void checkGrade14()//negative percent
	{
		IAdmin admin = new Admin();
		admin.createClass("ECS161", 2017, "Devanbu", 3);
		this.instructor.addHomework("Devanbu", "ECS161", 2017, "P3");
		IStudent student = new Student();
		student.registerForClass("David", "ECS161", 2017);
		student.submitHomework("David", "P3", "Done", "ECS161", 2017);
		assertTrue(student.hasSubmitted("David", "P3", "ECS161", 2017));
		this.instructor.assignGrade("Devanbu", "ECS161", 2017, "P3", "David", -1);
		assertNotEquals("Failure - Grades dont match", (Integer)(-1) , this.instructor.getGrade("ECS161", 2017, "P3", "David"));
	}
}
