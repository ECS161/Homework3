package core.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestInstructor extends TestCommon {

    @Before
    public void setup() {
        this._setup();
        
        this.admin.createClass("Class", 2017, "Instructor", 2);
    }
	
    @Test
    public void testBadAddHomework() {
    		// Instructor not assigned to class
    		assertFalse(addHomeworkAndTest("FakeInstructor", "Class", 2017, "notthistime"));
    }
    
    @Test
    public void testAddGrade() {
    		assertTrue(addHomeworkAndTest("Instructor", "Class", 2017, "hw1"));
    		assertTrue(registerAndTest("StudentA", "Class", 2017));
    		
    		// Homework not assigned
    		assertFalse(assignGradeAndTest("Instructor", "Class", 2017, "fakehw", "StudentA", 10));
    		
    		// Homework not submitted
    		assertFalse(assignGradeAndTest("Instructor", "Class", 2017, "hw1", "StudentA", 90));
    		
    		// Turn in homework
    		assertTrue(submitHomeworkAndTest("StudentA", "hw1", "Class", 2017));
    		
    		// Can assign a grade
    		assertTrue(assignGradeAndTest("Instructor", "Class", 2017, "hw1", "StudentA", 50));
    		
    		// Student not assigned
    		assertFalse(assignGradeAndTest("Instructor", "Class", 2017, "hw1", "FakeStudent", 1));
    		
    		// Instructor not assigned
    		assertFalse(assignGradeAndTest("FakeInstructor", "Class", 2017, "hw1", "StudentA", 99));
    }
}
