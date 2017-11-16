package core.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestStudent extends TestCommon {

    @Before
    public void setup() {
        this._setup();
        
        this.admin.createClass("Class", 2017, "Instructor", 2);
        this.instructor.addHomework("Instructor", "Class", 2017, "hw1");
    }
	
	@Test
    public void testRegisterDrop() {
        // Class doesn't exist
        assertFalse(registerAndTest("FakeStudent", "fakeclass", 2017));
        
		// Can register up to capacity
        assertTrue(registerAndTest("StudentA", "Class", 2017));
        assertTrue(registerAndTest("StudentB", "Class", 2017));
        
        // Capacity is 2, this should fail
        assertFalse(registerAndTest("StudentC", "Class", 2017));
        
        // Drop should work
        assertTrue(dropAndTest("StudentB", "Class", 2017));
        
        // Now we should be able to register new student
        assertTrue(registerAndTest("StudentC", "Class", 2017));
        
        // At capacity, this should fail
        assertFalse(registerAndTest("StudentD", "Class", 2017));
        
        assertTrue(changeCapacityAndTest("Class", 2017, 3));
        
        // Increase capacity, should work
        assertTrue(registerAndTest("StudentD", "Class", 2017));
        
        // Drop invalid student, assertTrue because student wont be in the class
        assertTrue(dropAndTest("NotRealStudent", "Class", 2017));
        
        // Still at capacity, should fail
        assertFalse(registerAndTest("StudentE", "Class", 2017));
    }
	
	@Test
    public void testHomework() {
		// Student not registered
		assertFalse(submitHomeworkAndTest("NotRegisteredStudent", "hw1", "Class", 2017));
		
		assertTrue(registerAndTest("StudentA", "Class", 2017));
		
		// HW doesn't exist
		assertFalse(submitHomeworkAndTest("StudentA", "fakeHW", "Class", 2017));
		
		// HW exists
		assertTrue(submitHomeworkAndTest("StudentA", "hw1", "Class", 2017));
    }
	
	@Test
    public void testFutureHomework() {
		assertTrue(createClassAndTest("Class2018", 2018, "Instructor", 10));
		assertTrue(registerAndTest("StudentA", "Class2018", 2018));
		assertTrue(addHomeworkAndTest("Instructor", "Class2018", 2018, "hw1"));
		
		// Can't submit homework from the future
		assertFalse(submitHomeworkAndTest("StudentA", "hw1", "Class2018", 2018));
    }

}
