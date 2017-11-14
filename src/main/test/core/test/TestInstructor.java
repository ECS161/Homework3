package core.test;
import core.api.IInstructor;
import core.api.impl.Instructor;
import core.api.impl.Student;
import core.api.IAdmin;
import core.api.impl.Admin;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;



public class TestInstructor {
	
	private IInstructor instructor;
	private IAdmin admin;
	
	@Before
	public void setup() {
		this.instructor = new Instructor();
		this.admin = new Admin();
	}
	
	//Add homework tests
	/*@Test
	public void testAddHomeworkValidClass() {
		this.admin.createClass("Test", 2017, "Instructor" ,15);
		instructor.addHomework("Instructor", "Test", 2017, "HW1");
		assertTrue(instructor.homeworkExists("Test", 2017, "HW1"));
	}*/
	
	@Test
	public void testAddHomeworkInvalidClass() {
		//Class does not exist
		instructor.addHomework("Instructor", "Test", 2017, "HW1");
		assertFalse(instructor.homeworkExists("Test", 2017, "HW1"));
	}
	
	@Test
	public void testAddHomeworkSameInstructor() {
		this.admin.createClass("Test", 2017, "Instructor" ,15);
		instructor.addHomework("Instructor", "Test", 2017, "HW1");
		assertTrue(instructor.homeworkExists("Test", 2017, "HW1"));
	}
	
	@Test
	public void testAddHomeworkDiffInstructor() {
		this.admin.createClass("Test", 2017, "Instructor" ,15);
		instructor.addHomework("Instructor2", "Test", 2017, "HW1");
		assertFalse(instructor.homeworkExists("Test", 2017, "HW1"));
	}
	
	//Assign grade
	@Test
	//Test same instructor, implicitly test if homework exists,student exists, course exists
	public void testAssignGradeInstructor() {
		this.admin.createClass("Test", 2017, "Instructor", 10);
		Student s1 = new Student();
		s1.registerForClass("Student1", "Test", 2017);
		instructor.addHomework("Instructor", "Test", 2017, "HW1");
		s1.submitHomework("Student1", "HW1", "Hello World", "Test", 2017);
		instructor.assignGrade("Instructor", "Test", 2017, "HW1", "Student1", 90);
		assertEquals(new Integer(90), instructor.getGrade("Test", 2017, "HW1", "Student1") );
	}
	
	@Test
	public void testAssignGradeNotInstructor() {
		this.admin.createClass("Test", 2017, "Instructor", 10);
		Student s1 = new Student();
		s1.registerForClass("Student1", "Test", 2017);
		instructor.addHomework("Instructor", "Test", 2017, "HW1");
		s1.submitHomework("Student1", "HW1", "Hello World", "Test", 2017);
		instructor.assignGrade("Instructor2", "Test", 2017, "HW1", "Student1", 90);
		//Should return null since grade shouldn't exist
		assertNull( instructor.getGrade("Test", 2017, "HW1", "Student1") );
	}
	
	@Test
	public void testAssignGradeInvalidHomework() {
		this.admin.createClass("Test", 2017, "Instructor", 10);
		(new Student()).registerForClass("Student1", "Test", 2017);
		instructor.assignGrade("Instructor", "Test", 2017 , "FakeHW1", "Student1", 50);
		assertNull( instructor.getGrade("Test", 2017, "FakeHW1", "Student1") );
	}
	
	//Student not enrolled in class
	@Test
	public void testAssignGradeInvalidStudent() {
		
		this.admin.createClass("Test", 2017, "Instructor", 10);
		instructor.addHomework("Instructor", "Test", 2017, "HW1");
		
		Student s1 = new Student();
		s1.submitHomework("Student1", "HW1", "Hello World", "Test", 2017);
		instructor.assignGrade("Instructor", "Test", 2017 , "HW1", "Student1", 110);
		
		assertNull( instructor.getGrade("Test", 2017, "HW1", "Student1") );
	}
	
	@Test
	public void testAssignGradeNotSubmitted() {
		
		this.admin.createClass("Test", 2017, "Instructor", 10);
		instructor.addHomework("Instructor", "Test", 2017, "HW1");
		
		Student s1 = new Student();
		s1.registerForClass("Student1", "Test", 2017);
		instructor.assignGrade("Instructor", "Test", 2017 , "HW1", "Student1", 70);

		assertNull( instructor.getGrade("Test", 2017, "HW1", "Student1") );		
	}
	
	@Test
	public void testAssignGradeValidScore() {
		
		this.admin.createClass("Test", 2017, "Instructor", 10);
		instructor.addHomework("Instructor", "Test", 2017, "HW1");
		
		Student s1 = new Student();
		s1.registerForClass("Student1", "Test", 2017);
		s1.submitHomework("Student1", "HW1", "Hello World", "Test", 2017);
		instructor.assignGrade("Instructor", "Test", 2017 , "HW1", "Student1", 70);
		
		assertEquals(new Integer(70), instructor.getGrade("Test", 2017, "HW1", "Student1") );
		
	}
	
	@Test
	public void testAssignGradeInvalidScore() {
		
		this.admin.createClass("Test", 2017, "Instructor", 10);
		instructor.addHomework("Instructor", "Test", 2017, "HW1");
		
		Student s1 = new Student();
		s1.registerForClass("Student1", "Test", 2017);
		s1.submitHomework("Student1", "HW1", "Hello World", "Test", 2017);
		instructor.assignGrade("Instructor", "Test", 2017 , "HW1", "Student1", -10);
		
		assertNull( instructor.getGrade("Test", 2017, "HW1", "Student1") );
		
	}
	
	/* seems unnecessary, if class doesnt exist, adding homework fails meaning this fails
	@Test
	public void testAssignGradeInvalidCourse() {
		this.admin.createClass("Test", 2017, "Instructor", 10);
		instructor.addHomework("Instructor", "Test", 2017, "HW1");
		instructor.assignGrade("Instructor", "Test", 2017 , "HW1", "Student1", 110);
		assertNull( instructor.getGrade("Test", 2017, "FakeHW1", "Student1") );
	}*/

}
