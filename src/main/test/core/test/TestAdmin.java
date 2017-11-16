package core.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestAdmin extends TestCommon  {
	

    @Before
    public void setup() {
        this._setup();
    }
	
	@Test
    public void testCreateClassYears() {
		// Everything from 2017 on should work, lets test 100 years
		for (int i = 2017; i < 2117; i++)
			assertTrue(createClassAndTest("Test" + i, i, "Instructor" + i, 10));

		// Test invalid dates
		for (int i = -1; i < 2017; i++)
			assertFalse(createClassAndTest("Test" + i, i, "Instructor" + i, 10));
    }
	
	@Test
	public void testUniqueClassNameYear() {
		assertTrue(createClassAndTest("Test", 2017, "Instructor", 10));
		
		// Same year, should fail
		assertFalse(createClassAndTest("Test", 2017, "Instructor", 8));
		
		// Same year, should fail
		assertFalse(createClassAndTest("Test", 2017, "Instructor2", 6));
		
		// Next year, should work
		assertTrue(createClassAndTest("Test", 2018, "Instructor3", 4));
	}
	
	@Test
	public void testMaxTwoCourses() {
		assertTrue(createClassAndTest("Test", 2017, "Instructor", 10));
		assertTrue(createClassAndTest("Test2", 2017, "Instructor", 10));
		
		// 3rd should fail
		assertFalse(createClassAndTest("Test3", 2017, "Instructor", 10));
		
		// Adding a class in a new year should work
		assertTrue(createClassAndTest("Test4", 2018, "Instructor", 10));
		assertTrue(createClassAndTest("Test5", 2018, "Instructor", 10));
		
		// 3rd should again fail
		assertFalse(createClassAndTest("Test6", 2018, "Instructor", 10));
	}
	
	@Test
    public void testConstructorCapacity() {
		assertFalse(createClassAndTest("Test", 2017, "Instructor", 0));
		assertFalse(createClassAndTest("Test2", 2017, "Instructor2", -1));
		assertTrue(createClassAndTest("Test3", 2017, "Instructor3", 1));
    }
	
	
	@Test
	public void testChangeCapacity() {
		// Class doesn't exist
		assertFalse(changeCapacityAndTest("FakeClass", 2017, 2));
		
		assertTrue(createClassAndTest("Test", 2017, "Instructor", 10));
		
		// Wrong year should fail
		assertFalse(changeCapacityAndTest("Test", 2018, 50));
		
		// Changing should work
		assertTrue(changeCapacityAndTest("Test", 2017, 5));
		
		// Setting it to <= 0 should not
		assertFalse(changeCapacityAndTest("Test", 2017, 0));
		assertFalse(changeCapacityAndTest("Test", 2017, -1));
		
		// Register 2 students for the class
		assertTrue(registerAndTest("StudentA", "Test", 2017));
		assertTrue(registerAndTest("StudentB", "Test", 2017));
        
		// Setting capacity at 2, 3 should work
		assertTrue(changeCapacityAndTest("Test", 2017, 3));
		assertTrue(changeCapacityAndTest("Test", 2017, 2));
		
		// Setting capacity at 1 shouldn't work
		assertFalse(changeCapacityAndTest("Test", 2017, 1));
		
		assertTrue(dropAndTest("StudentB", "Test", 2017));
		
		// Now # registered is 1, should be okay to set to 1
		assertTrue(changeCapacityAndTest("Test", 2017, 1));
		
		assertTrue(dropAndTest("StudentA", "Test", 2017));
		
		// Now # registered is 0, still shouldn't be able to set to 0
		assertFalse(changeCapacityAndTest("Test", 2017, 0));
	}
}
