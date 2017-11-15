package core.test;

import core.api.IAdmin;
import core.api.IInstructor;
import core.api.IStudent;
import core.api.impl.Admin;
import core.api.impl.Instructor;
import core.api.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestStudent {
	private IInstructor instructor;
	private IAdmin admin;
	private IStudent student;

    @Before
    public void setup() {
        this.instructor = new Instructor();
        this.admin = new Admin();
        this.student = new Student();
    }
    
    @Test
    public void testAddStudent() { // True, Register for existing class
    		this.admin.createClass("ECS161", 2017, "Prim", 5);
    		this.student.registerForClass("Josh", "ECS161", 2017);
    		assertTrue(this.student.isRegisteredFor("Josh", "ECS161", 2017));
    }
    
    @Test
    public void testAddStudent2() { // True, Register for class in future
    		this.admin.createClass("ECS161", 2018, "Prim", 5);
    		this.student.registerForClass("Josh", "ECS161", 2018);
    		assertTrue(this.student.isRegisteredFor("Josh", "ECS161", 2018));
    }
    
    @Test
    public void testAddStudent3() { // False, Register for inexistent class
    		//this.admin.createClass("ECS161", 2018, "Prim", 5);
    		this.student.registerForClass("Josh", "ECS161", 2018);
    		assertFalse(this.student.isRegisteredFor("Josh", "ECS161", 2018));
    }
    
    @Test
    public void testAddStudent4() { // Register for class wrong year
    		this.admin.createClass("ECS161", 2018, "Prim", 5);
    		this.student.registerForClass("Josh", "ECS161", 2017);
    		assertFalse(this.student.isRegisteredFor("Josh", "ECS161", 2018));
    }
    
    @Test
    public void testAddStudent5() { // Add Multiple students, get first
    		this.admin.createClass("ECS161", 2017, "Prim", 3);
    		this.student.registerForClass("Josh", "ECS161", 2017);
    		this.student.registerForClass("Nick", "ECS161", 2017);
    		assertTrue(this.student.isRegisteredFor("Josh", "ECS161", 2017));
    }
    
    @Test
    public void testAddStudent6() { // Add Multiple students, get second
    		this.admin.createClass("ECS161", 2017, "Prim", 3);
    		this.student.registerForClass("Josh", "ECS161", 2017);
    		this.student.registerForClass("Nick", "ECS161", 2017);
    		assertTrue(this.student.isRegisteredFor("Nick", "ECS161", 2017));
    }
    
    @Test
    public void testAddStudent7() { // False, same professor different class
    		this.admin.createClass("ECS161", 2017, "Prim", 5);
    		this.admin.createClass("ECS160", 2017, "Prim", 5);
    		this.student.registerForClass("Josh", "ECS160", 2017);
    		assertFalse(this.student.isRegisteredFor("Josh", "ECS161", 2017));
    }
    
    @Test
    public void testAddStudent8() { // False, Student drops class, no longer registered
    		this.admin.createClass("ECS161", 2017, "Prim", 5);
    		this.student.registerForClass("Josh", "ECS161", 2017);
    		this.student.dropClass("Josh", "ECS161", 2017);
    		assertFalse(this.student.isRegisteredFor("Josh", "ECS161", 2017));
    }
    
    @Test
    public void testAddStudent9() { // True, Student drops current class, but in class of future
    		this.admin.createClass("ECS161", 2017, "Prim", 5);
    		this.admin.createClass("ECS161", 2018, "Prim", 5);
    		this.student.registerForClass("Josh", "ECS161", 2017);
    		this.student.registerForClass("Josh", "ECS161", 2018);
    		this.student.dropClass("Josh", "ECS161", 2017);
    		assertTrue(this.student.isRegisteredFor("Josh", "ECS161", 2018));
    }
    @Test
    public void testAddStudent10() { // True, Student drops other class of same professor
    		this.admin.createClass("ECS161", 2017, "Prim", 5);
    		this.admin.createClass("ECS160", 2017, "Prim", 5);
    		this.student.registerForClass("Josh", "ECS161", 2017);
    		this.student.registerForClass("Josh", "ECS160", 2017);
    		this.student.dropClass("Josh", "ECS161", 2017);
    		assertTrue(this.student.isRegisteredFor("Josh", "ECS160", 2017));
    }
    
    @Test
    public void testAddStudent11() { // True Student drops and readds class
    		this.admin.createClass("ECS161", 2017, "Prim", 5);
    		this.student.registerForClass("Josh", "ECS161", 2017);
    		this.student.dropClass("Josh", "ECS161", 2017);
    		this.student.registerForClass("Josh", "ECS161", 2017);
    		assertTrue(this.student.isRegisteredFor("Josh", "ECS161", 2017));
    }
    
    @Test
    public void testAddStudent12() { // True, Add Students to enrollment limit
    		this.admin.createClass("ECS161", 2017, "Prim", 3);
    		this.student.registerForClass("Josh", "ECS161", 2017);
    		this.student.registerForClass("Nick", "ECS161", 2017);
    		this.student.registerForClass("Jose", "ECS161", 2017);
    		assertTrue(this.student.isRegisteredFor("Jose", "ECS161", 2017));
    }
    
    @Test
    public void testAddStudent13() { // False, add students over enrollment limit
    		this.admin.createClass("ECS161", 2017, "Prim", 3);
    		this.student.registerForClass("Josh", "ECS161", 2017);
    		this.student.registerForClass("Nick", "ECS161", 2017);
    		this.student.registerForClass("Jose", "ECS161", 2017);
    		this.student.registerForClass("Katie", "ECS161", 2017);
    		assertFalse(this.student.isRegisteredFor("Katie", "ECS161", 2017));
    }
    
    @Test
    public void testAddStudent14() { // True, add to enrollment limit, drop a student, add a new one
    		this.admin.createClass("ECS161", 2017, "Prim", 3);
    		this.student.registerForClass("Josh", "ECS161", 2017);
    		this.student.registerForClass("Nick", "ECS161", 2017);
    		this.student.registerForClass("Jose", "ECS161", 2017);
    		this.student.dropClass("Nick", "ECS161", 2017);
    		this.student.registerForClass("Katie", "ECS161", 2017);
    		assertTrue(this.student.isRegisteredFor("Katie", "ECS161", 2017));
    }
    @Test
    public void testAddStudent15() { // True, Change enrollment limit, add more students
    		this.admin.createClass("ECS161", 2017, "Prim", 3);
    		this.student.registerForClass("Josh", "ECS161", 2017);
    		this.student.registerForClass("Nick", "ECS161", 2017);
    		this.student.registerForClass("Jose", "ECS161", 2017);
    		this.admin.changeCapacity("ECS161", 2017, 5);
    		this.student.registerForClass("Katie", "ECS161", 2017);
    		this.student.registerForClass("Isabel", "ECS161", 2017);
    		assertTrue(this.student.isRegisteredFor("Isabel", "ECS161", 2017));
    }
    
    @Test
    public void testAddStudent16() { // False, Change enrollment limit, add more students, go over limit
    		this.admin.createClass("ECS161", 2017, "Prim", 3);
    		this.student.registerForClass("Josh", "ECS161", 2017);
    		this.student.registerForClass("Nick", "ECS161", 2017);
    		this.student.registerForClass("Jose", "ECS161", 2017);
    		this.admin.changeCapacity("ECS161", 2017, 5);
    		this.student.registerForClass("Katie", "ECS161", 2017);
    		this.student.registerForClass("Isabel", "ECS161", 2017);
    		this.student.registerForClass("Aimee", "ECS161", 2017);
    		assertFalse(this.student.isRegisteredFor("Aimee", "ECS161", 2017));
    }
    
    
    @Test
    public void testAddStudent17() { // False, Change enrollment limit, add more students, go over limit, check preexisting student
    		this.admin.createClass("ECS161", 2017, "Prim", 3);
    		this.student.registerForClass("Josh", "ECS161", 2017);
    		this.student.registerForClass("Nick", "ECS161", 2017);
    		this.student.registerForClass("Jose", "ECS161", 2017);
    		this.admin.changeCapacity("ECS161", 2017, 5);
    		this.student.registerForClass("Katie", "ECS161", 2017);
    		this.student.registerForClass("Isabel", "ECS161", 2017);
    		this.student.registerForClass("Aimee", "ECS161", 2017);
    		assertTrue(this.student.isRegisteredFor("Katie", "ECS161", 2017));
    }
	
    // Test Submit Assignment
    @Test
    public void testSubmitHomework() { // True submit homework of current year and assigned
    		this.admin.createClass("ECS161", 2017, "Prim", 15);
    		this.student.registerForClass("Josh", "ECS161", 2017);
    		this.instructor.addHomework("Prim", "ECS161", 2017, "hw1");
    		this.student.submitHomework("Josh", "hw1", "Some real smart answers", "ECS161", 2017);
    		assertTrue(this.student.hasSubmitted("Josh", "hw1", "ECS161", 2017));
    }
    
    @Test
    public void testSubmitHomework2() { // False, Submit homework of future class
    		this.admin.createClass("ECS161", 2018, "Prim", 15);
    		this.student.registerForClass("Josh", "ECS161", 2018);
    		this.instructor.addHomework("Prim", "ECS161", 2018, "hw1");
    		this.student.submitHomework("Josh", "hw1", "Some real smart answers", "ECS161", 2018);
    		assertFalse(this.student.hasSubmitted("Josh", "hw1", "ECS161", 2018));
    }
    
    @Test
    public void testSubmitHomework3() { // False, submit homework, not enrolled
    		this.admin.createClass("ECS161", 2017, "Prim", 15);
    		//this.student.registerForClass("Josh", "ECS161", 2017);
    		this.instructor.addHomework("Prim", "ECS161", 2017, "hw1");
    		this.student.submitHomework("Josh", "hw1", "Some real smart answers", "ECS161", 2017);
    		assertFalse(this.student.hasSubmitted("Josh", "hw1", "ECS161", 2017));
    }
	
    @Test
    public void testSubmitHomework4() { // False, submit homework, not assigned
    		this.admin.createClass("ECS161", 2017, "Prim", 15);
    		this.student.registerForClass("Josh", "ECS161", 2017);
    		//this.instructor.addHomework("Prim", "ECS161", 2017, "hw1");
    		this.student.submitHomework("Josh", "hw1", "Some real smart answers", "ECS161", 2017);
    		assertFalse(this.student.hasSubmitted("Josh", "hw1", "ECS161", 2017));
    }
    
    @Test
    public void testSubmitHomework5() { // False, wrong homework, check hw1
    		this.admin.createClass("ECS161", 2017, "Prim", 15);
    		this.student.registerForClass("Josh", "ECS161", 2017);
    		this.instructor.addHomework("Prim", "ECS161", 2017, "hw1");
    		this.student.submitHomework("Josh", "hw2", "Some real smart answers", "ECS161", 2017);
    		assertFalse(this.student.hasSubmitted("Josh", "hw1", "ECS161", 2017));
    }
    
    @Test
    public void testSubmitHomework6() { // False, wrong homework, check hw2
    		this.admin.createClass("ECS161", 2017, "Prim", 15);
    		this.student.registerForClass("Josh", "ECS161", 2017);
    		this.instructor.addHomework("Prim", "ECS161", 2017, "hw1");
    		this.student.submitHomework("Josh", "hw2", "Some real smart answers", "ECS161", 2017);
    		assertFalse(this.student.hasSubmitted("Josh", "hw2", "ECS161", 2017));
    }

    @Test
    public void testSubmitHomework7() { // False, submitted in wrong class of same prof
    		this.admin.createClass("ECS161", 2017, "Prim", 15);
    		this.admin.createClass("ECS160", 2017, "Prim", 15);
    		this.student.registerForClass("Josh", "ECS161", 2017);
    		this.student.registerForClass("Josh", "ECS160", 2017);
    		this.instructor.addHomework("Prim", "ECS161", 2017, "hw1");
    		this.student.submitHomework("Josh", "hw1", "Some real smart answers", "ECS160", 2017);
    		assertFalse(this.student.hasSubmitted("Josh", "hw1", "ECS160", 2017));
    }
	
    @Test
    public void testSubmitHomework8() { // True, submit multiple assignments check first
    		this.admin.createClass("ECS161", 2017, "Prim", 15);
    		this.student.registerForClass("Josh", "ECS161", 2017);
    		this.instructor.addHomework("Prim", "ECS161", 2017, "hw1");
    		this.instructor.addHomework("Prim", "ECS161", 2017, "hw2");
    		this.student.submitHomework("Josh", "hw1", "Some real smart answers", "ECS161", 2017);
    		this.student.submitHomework("Josh", "hw2", "Some real smart answers", "ECS161", 2017);
    		assertTrue(this.student.hasSubmitted("Josh", "hw1", "ECS161", 2017));
    }
    
    @Test
    public void testSubmitHomework9() { // True, submit multiple assignments check second
    	this.admin.createClass("ECS161", 2017, "Prim", 15);
		this.student.registerForClass("Josh", "ECS161", 2017);
		this.instructor.addHomework("Prim", "ECS161", 2017, "hw1");
		this.instructor.addHomework("Prim", "ECS161", 2017, "hw2");
		this.student.submitHomework("Josh", "hw1", "Some real smart answers", "ECS161", 2017);
		this.student.submitHomework("Josh", "hw2", "Some real smart answers", "ECS161", 2017);
		assertTrue(this.student.hasSubmitted("Josh", "hw2", "ECS161", 2017));
    }
    
    @Test
    public void testSubmitHomework10() { // False, Multiple assignments, only submitted one
    	this.admin.createClass("ECS161", 2017, "Prim", 15);
		this.student.registerForClass("Josh", "ECS161", 2017);
		this.instructor.addHomework("Prim", "ECS161", 2017, "hw1");
		this.instructor.addHomework("Prim", "ECS161", 2017, "hw2");
		//this.student.submitHomework("Josh", "hw1", "Some real smart answers", "ECS161", 2017);
		this.student.submitHomework("Josh", "hw2", "Some real smart answers", "ECS161", 2017);
		assertFalse(this.student.hasSubmitted("Josh", "hw1", "ECS161", 2017));
    }

	


}
