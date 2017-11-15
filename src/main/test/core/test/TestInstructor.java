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

public class TestInstructor {
	private IInstructor instructor;
	private IAdmin admin;
	private IStudent student;

    @Before
    public void setup() {
        this.instructor = new Instructor();
        this.admin = new Admin();
        this.student = new Student();
    }

    // Add Homework
    @Test
    public void testAddHomework() { // True, add hw assignment to existing class
        this.admin.createClass("ECS161", 2017, "Prim", 15);
        this.instructor.addHomework("Prim", "ECS161", 2017, "hw");
        assertTrue(this.instructor.homeworkExists("ECS161", 2017, "hw"));
    }
    
    @Test
    public void testAddHomework2() { // False, add hw assignment to inexistent class
    		//this.admin.createClass("Test", 2017, "Instructor", 15);
        this.instructor.addHomework("Prim", "ECS161", 2017, "hw");
        assertFalse(this.instructor.homeworkExists("ECS161", 2017, "hw"));
    }
    
    @Test
    public void testAddHomework3() { // False, add hw assignment by unauthorized teacher
    		this.admin.createClass("ECS161", 2017, "Prim", 15);
        this.instructor.addHomework("Sean", "ECS161", 2017, "hw");
        assertFalse(this.instructor.homeworkExists("ECS161", 2017, "hw"));
    	
    }
    
    @Test
    public void testAddHomework4() { // False, add hw assignment to wrong year
    		this.admin.createClass("ECS161", 2017, "Prim", 15);
    		this.admin.createClass("ECS161", 2018, "Prim", 15);
    		this.instructor.addHomework("Prim", "ECS161", 2018, "hw");
    		assertFalse(this.instructor.homeworkExists("ECS161", 2017, "hw"));
    }
    
    @Test
    public void testAddHomework5() { // False, add hw assignment to wrong class
		this.admin.createClass("ECS161", 2017, "Prim", 15);
		this.admin.createClass("ECS160", 2017, "Prim", 15);
		this.instructor.addHomework("Prim", "ECS160", 2017, "hw");
		assertFalse(this.instructor.homeworkExists("ECS161", 2017, "hw"));
    }
    
    @Test
    public void testAddHomework6() { // True, add hw assignment to future class
    		this.admin.createClass("ECS161", 2018, "Prim", 15);
    		this.instructor.addHomework("Prim", "ECS161", 2018, "hw");
    		assertTrue(this.instructor.homeworkExists("ECS161", 2018, "hw"));
    }
    
    @Test
    public void testAddHomework7() { // True, add multiple hw assignment, get new assignment
    		this.admin.createClass("ECS161", 2017, "Prim", 15);
    		this.admin.createClass("ECS160", 2017, "Prim", 15);
		this.instructor.addHomework("Prim", "ECS160", 2017, "hw");
		this.instructor.addHomework("Prim", "ECS161",  2017, "hw2");
		assertTrue(this.instructor.homeworkExists("ECS161", 2017, "hw2"));
		
    }
    
    @Test
    public void testAddHomework8() { // True, add multiple hw assignment and get original hw assignment
    		this.admin.createClass("ECS161", 2017, "Prim", 15);
    		this.admin.createClass("ECS160", 2017, "Prim", 15);
		this.instructor.addHomework("Prim", "ECS160", 2017, "hw");
		this.instructor.addHomework("Prim", "ECS161",  2017, "hw2");
		assertTrue(this.instructor.homeworkExists("ECS160", 2017, "hw"));
		
    }
    
    @Test
    public void testAddHomework9() { // False, grade assigned by instructor of future class but not current class
    		this.admin.createClass("ECS161", 2017, "Prim", 15);
    		this.admin.createClass("ECS161", 2018, "Sean", 15);
    		this.instructor.addHomework("Sean", "ECS160", 2017, "hw");
    		assertFalse(this.instructor.homeworkExists("ECS161", 2017, "hw"));
    	
    }

    // Assign Grade
    @Test
    public void assignGrade() { // True, assign grade to student in class of year
    		this.admin.createClass("ECS161", 2017, "Prim", 15);
    		this.instructor.addHomework("Prim", "ECS161", 2017, "hw1");
    		this.student.registerForClass("Josh", "ECS161", 2017);
    		this.student.submitHomework("Josh", "hw1", "a, b, c", "ECS161", 2017);
    		this.instructor.assignGrade("Prim", "ECS161", 2017, "hw1", "Josh", 100);
    		assertTrue(this.instructor.getGrade("ECS161", 2017, "hw1", "Josh") == 100);
    }
    
    @Test
    public void assignGrade2() { // False, Assign grade to student who has not submitted
    		this.admin.createClass("ECS161", 2017, "Prim", 15);
    		this.instructor.addHomework("Prim", "ECS161", 2017, "hw1");
    		this.student.registerForClass("Josh", "ECS161", 2017);
    		//this.student.submitHomework("Josh", "hw1", "a, b, c", "ECS161", 2017);
    		this.instructor.assignGrade("Prim", "ECS161", 2017, "hw1", "Josh", 100);
    		assertFalse(this.instructor.getGrade("ECS161", 2017, "hw1", "Josh") == 100);
    }
        
    @Test
    public void assignGrade4() { // False, Assign grade to student not in class
    		this.admin.createClass("ECS161", 2017, "Prim", 15);
    		this.instructor.addHomework("Prim", "ECS161", 2017, "hw1");
    		//this.student.registerForClass("Josh", "ECS161", 2017);
    		this.student.submitHomework("Josh", "hw1", "a, b, c", "ECS161", 2017);
    		this.instructor.assignGrade("Prim", "ECS161", 2017, "hw1", "Josh", 100);
    		assertFalse(this.instructor.getGrade("ECS161", 2017, "hw1", "Josh") == 100);
    }
    
    @Test
    public void assignGrade5() { // False, Assign grade of Negative Number
    		this.admin.createClass("ECS161", 2017, "Prim", 15);
    		this.instructor.addHomework("Prim", "ECS161", 2017, "hw1");
    		this.student.registerForClass("Josh", "ECS161", 2017);
    		this.student.submitHomework("Josh", "hw1", "a, b, c", "ECS161", 2017);
    		this.instructor.assignGrade("Prim", "ECS161", 2017, "hw1", "Josh", -50);
    		assertFalse(this.instructor.getGrade("ECS161", 2017, "hw1", "Josh") == -50);
    }
    
    @Test
    public void assignGrade6() { // True, Assign grade greater than 100 (Extra Credit)
    		this.admin.createClass("ECS161", 2017, "Prim", 15);
    		this.instructor.addHomework("Prim", "ECS161", 2017, "hw1");
    		this.student.registerForClass("Josh", "ECS161", 2017);
    		this.student.submitHomework("Josh", "hw1", "a, b, c", "ECS161", 2017);
    		this.instructor.assignGrade("Prim", "ECS161", 2017, "hw1", "Josh", 120);
    		assertTrue(this.instructor.getGrade("ECS161", 2017, "hw1", "Josh") == 120);
    }
    
    @Test
    public void assignGrade7() { // False, Unauthorized teacher assigns grade
    		this.admin.createClass("ECS161", 2017, "Prim", 15);
    		this.instructor.addHomework("Prim", "ECS161", 2017, "hw1");
    		this.student.registerForClass("Josh", "ECS161", 2017);
    		this.student.submitHomework("Josh", "hw1", "a, b, c", "ECS161", 2017);
    		this.instructor.assignGrade("Sean", "ECS161", 2017, "hw1", "Josh", 70);
    		assertFalse(this.instructor.getGrade("ECS161", 2017, "hw1", "Josh") == 70);
    }
   
    @Test
    public void assignGrade8() { // True, assign grades to multiple students, check first student grade
    		this.admin.createClass("ECS161", 2017, "Prim", 15);
    		this.instructor.addHomework("Prim", "ECS161", 2017, "hw1");
    		this.student.registerForClass("Josh", "ECS161", 2017);
    		this.student.registerForClass("Nick", "ECS161", 2017);
    		this.student.submitHomework("Josh", "hw1", "a, b, c", "ECS161", 2017);
    		this.student.submitHomework("Nick", "hw1", "a, b, c", "ECS161", 2017);
    		this.instructor.assignGrade("Prim", "ECS161", 2017, "hw1", "Josh", 100);
    		this.instructor.assignGrade("Prim", "ECS161", 2017, "hw1", "Nick", 30);
    		assertTrue(this.instructor.getGrade("ECS161", 2017, "hw1", "Josh") == 100);
    }
    
    @Test
    public void assignGrade9() { // True, assign grades to multiple students, check Second student grade
    	this.admin.createClass("ECS161", 2017, "Prim", 15);
		this.instructor.addHomework("Prim", "ECS161", 2017, "hw1");
		this.student.registerForClass("Josh", "ECS161", 2017);
		this.student.registerForClass("Nick", "ECS161", 2017);
		this.student.submitHomework("Josh", "hw1", "a, b, c", "ECS161", 2017);
		this.student.submitHomework("Nick", "hw1", "a, b, c", "ECS161", 2017);
		this.instructor.assignGrade("Prim", "ECS161", 2017, "hw1", "Josh", 100);
		this.instructor.assignGrade("Prim", "ECS161", 2017, "hw1", "Nick", 30);
		assertTrue(this.instructor.getGrade("ECS161", 2017, "hw1", "Nick") == 30);
    }
    
    @Test
    public void assignGrade10() { // False, Assign grade to student registered for future class, in current class
    		this.admin.createClass("ECS161", 2017, "Prim", 15);
    		this.admin.createClass("ECS161", 2018, "Prim", 15);
    		this.instructor.addHomework("Prim", "ECS161", 2017, "hw1");
    		this.instructor.addHomework("Prim", "ECS161", 2018, "hw1");
    		this.student.registerForClass("Josh", "ECS161", 2017);
    		this.student.registerForClass("Josh", "ECS161", 2018);
    		this.student.submitHomework("Josh", "hw1", "a, b, c", "ECS161", 2018);
    		this.instructor.assignGrade("Prim", "ECS161", 2017, "hw1", "Josh", 100);
    		assertFalse(this.instructor.getGrade("ECS161", 2017, "hw1", "Josh") == 100);
    }
    
    @Test
    public void assignGrade11() { // False, Assign grade to student registered for Different class of same teacher, and submitted assignment
    		this.admin.createClass("ECS161", 2017, "Prim", 15);
    		this.admin.createClass("ECS160", 2017, "Prim", 15);
    		this.instructor.addHomework("Prim", "ECS161", 2017, "hw1");
    		this.instructor.addHomework("Prim", "ECS160", 2017, "hw1");
    		this.student.registerForClass("Josh", "ECS160", 2017);
    		this.student.registerForClass("Josh", "ECS161", 2017);
    		this.student.submitHomework("Josh", "hw1", "a, b, c", "ECS160", 2017);
    		this.instructor.assignGrade("Prim", "ECS161", 2017, "hw1", "Josh", 100);
    		assertFalse(this.instructor.getGrade("ECS161", 2017, "hw1", "Josh") == 100);
    }
    
    @Test
    public void assignGrade12() { // True, assign grade to same student registered in multiple classes of same teacher
    		this.admin.createClass("ECS161", 2017, "Prim", 15);
    		this.admin.createClass("ECS160", 2017, "Prim", 15);
    		this.instructor.addHomework("Prim", "ECS161", 2017, "hw1");
    		this.instructor.addHomework("Prim", "ECS160", 2017, "hw1");
    		this.student.registerForClass("Josh", "ECS160", 2017);
    		this.student.registerForClass("Josh", "ECS161", 2017);
    		this.student.submitHomework("Josh", "hw1", "a, b, c", "ECS160", 2017);
    		this.student.submitHomework("Josh", "hw1", "a, b, c", "ECS161", 2017);
    		this.instructor.assignGrade("Prim", "ECS161", 2017, "hw1", "Josh", 100);
    		this.instructor.assignGrade("Prim", "ECS160", 2017, "hw1", "Josh", 30);
    		assertTrue(this.instructor.getGrade("ECS161", 2017, "hw1", "Josh") == 100);
    }
 
    
    @Test
    public void assignGrade13() { // True, assign multiple grades to multiple assignments, get first
    		this.admin.createClass("ECS161", 2017, "Prim", 15);
    		this.instructor.addHomework("Prim", "ECS161", 2017, "hw1");
    		this.instructor.addHomework("Prim", "ECS161", 2017, "hw2");
    		this.student.registerForClass("Josh", "ECS161", 2017);
    		this.student.submitHomework("Josh", "hw1", "a, b, c", "ECS161", 2017);
    		this.student.submitHomework("Josh", "hw2", "a, b, c", "ECS161", 2017);
    		this.instructor.assignGrade("Prim", "ECS161", 2017, "hw1", "Josh", 100);
    		this.instructor.assignGrade("Prim", "ECS161", 2017, "hw2", "Josh", 50);
    		assertTrue(this.instructor.getGrade("ECS161", 2017, "hw1", "Josh") == 100);
    }
    
    @Test
    public void assignGrade14() { // True, assign multiple grades to multiple assignments, get second
    	this.admin.createClass("ECS161", 2017, "Prim", 15);
		this.instructor.addHomework("Prim", "ECS161", 2017, "hw1");
		this.instructor.addHomework("Prim", "ECS161", 2017, "hw2");
		this.student.registerForClass("Josh", "ECS161", 2017);
		this.student.submitHomework("Josh", "hw1", "a, b, c", "ECS161", 2017);
		this.student.submitHomework("Josh", "hw2", "a, b, c", "ECS161", 2017);
		this.instructor.assignGrade("Prim", "ECS161", 2017, "hw1", "Josh", 100);
		this.instructor.assignGrade("Prim", "ECS161", 2017, "hw2", "Josh", 50);
		assertTrue(this.instructor.getGrade("ECS161", 2017, "hw2", "Josh") == 50);
    }
    
    @Test
    public void assignGrade15() { // False, assign multiple grades to multiple assignments, student only submitted first
    		this.admin.createClass("ECS161", 2017, "Prim", 15);
    		this.instructor.addHomework("Prim", "ECS161", 2017, "hw1");
    		this.instructor.addHomework("Prim", "ECS161", 2017, "hw2");
    		this.student.registerForClass("Josh", "ECS161", 2017);
    		this.student.submitHomework("Josh", "hw1", "a, b, c", "ECS161", 2017);
    		this.instructor.assignGrade("Prim", "ECS161", 2017, "hw1", "Josh", 100);
    		this.instructor.assignGrade("Prim", "ECS161", 2017, "hw2", "Josh", 35);
    		assertFalse(this.instructor.getGrade("ECS161", 2017, "hw2", "Josh") == 35);
    }
    
    @Test
    public void assignGrade16() { // False, assign grade to student who dropped class, and submitted hw
    		this.admin.createClass("ECS161", 2017, "Prim", 15);
    		this.instructor.addHomework("Prim", "ECS161", 2017, "hw1");
    		this.student.registerForClass("Josh", "ECS161", 2017);
    		this.student.submitHomework("Josh", "hw1", "a, b, c", "ECS161", 2017);
    		this.student.dropClass("Josh", "ECS161", 2017);
    		this.instructor.assignGrade("Prim", "ECS161", 2017, "hw1", "Josh", 100);
    		assertFalse(this.instructor.getGrade("ECS161", 2017, "hw1", "Josh") == 100);
    }
    
    @Test
    public void assignGrade17() { // True, assign grade to student who submitted hw, dropped class, readded.
    		this.admin.createClass("ECS161", 2017, "Prim", 15);
    		this.instructor.addHomework("Prim", "ECS161", 2017, "hw1");
    		this.student.registerForClass("Josh", "ECS161", 2017);
    		this.student.submitHomework("Josh", "hw1", "a, b, c", "ECS161", 2017);
    		this.student.dropClass("Josh", "ECS161", 2017);
    		this.student.registerForClass("Josh", "ECS161", 2017);
    		this.instructor.assignGrade("Prim", "ECS161", 2017, "hw1", "Josh", 100);
    		assertTrue(this.instructor.getGrade("ECS161", 2017, "hw1", "Josh") == 100);
    }
    
    @Test
    public void assignGrade18() { // True, Allow instructor to override grade
    		this.admin.createClass("ECS161", 2017, "Prim", 15);
    		this.instructor.addHomework("Prim", "ECS161", 2017, "hw1");
    		this.student.registerForClass("Josh", "ECS161", 2017);
    		this.student.submitHomework("Josh", "hw1", "a, b, c", "ECS161", 2017);
    		this.instructor.assignGrade("Prim", "ECS161", 2017, "hw1", "Josh", 60);
    		this.instructor.assignGrade("Prim", "ECS161", 2017, "hw1", "Josh", 80);
    		assertTrue(this.instructor.getGrade("ECS161", 2017, "hw1", "Josh") == 80);
    }
}
