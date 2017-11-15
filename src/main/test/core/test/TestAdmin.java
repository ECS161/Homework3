package core.test;

import core.api.IAdmin;
import core.api.impl.Admin;
import core.api.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Vincent on 23/2/2017.
 */
public class TestAdmin {

    private IAdmin admin;

    @Before
    public void setup() {
        this.admin = new Admin();
    }

    //createClass tests
    //testMakeClassCurrent tests both current and positive class capacity
    @Test
    public void testMakeClassCurrent() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }

    @Test
    public void testMakeClassInPast() {
        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    }
    
    @Test
    public void testMakeClassPlanned() {
    	this.admin.createClass("Test", 2019, "Instructor", 15);
    	assertTrue(this.admin.classExists("Test", 2019));
    }
    
    @Test
    public void testMakeClassZero() {
    	this.admin.createClass("Test", 2017, "Instructor", 0);
        assertFalse(this.admin.classExists("Test", 2017));
    }
    
    @Test
    public void testMakeClassNegative() {
    	this.admin.createClass("Test", 2017, "Instructor", -1);
        assertFalse(this.admin.classExists("Test", 2017));
    }
    
    @Test 
    public void testMakeClassTwo() {
    	this.admin.createClass("Test", 2017, "Instructor", 15);
    	this.admin.createClass("Test2", 2017, "Instructor", 35);
    	assertTrue(this.admin.classExists("Test", 2017));
    	assertTrue(this.admin.classExists("Test2", 2017));
    }
    
    @Test 
    public void testMakeClassThree() {
    	this.admin.createClass("Test", 2017, "Instructor", 15);
    	this.admin.createClass("Test2", 2017, "Instructor", 35);
    	this.admin.createClass("Test3", 2017, "Instructor", 35);
    	assertTrue(this.admin.classExists("Test", 2017));
    	assertTrue(this.admin.classExists("Test2", 2017));
    	assertFalse(this.admin.classExists("Test3", 2017));
    }
    
    @Test
    public void testMakeClassDuplicate() {
    	
    	this.admin.createClass("Test", 2017, "Instructor", 15);
    	String inst1 = this.admin.getClassInstructor("Test", 2017);
    	int cap1 = this.admin.getClassCapacity("Test", 2017);
    	//This should do nothing so no overwriting should occur
    	
    	this.admin.createClass("Test", 2017, "Instructor2", 35);   	
    	String inst2 = this.admin.getClassInstructor("Test", 2017);
    	int cap2 = this.admin.getClassCapacity("Test", 2017);
    	assertTrue( inst1.equals(inst2) );
    	assertTrue( cap1 == cap2 );
    }
    
    //changeCapacity tests
    //Class Exists
    @Test
    public void testChangeCapacityValidDecrease() {
    	this.admin.createClass("Test", 2017, "Instructor", 15);
    	Student s1 = new Student();
    	s1.registerForClass("Student1", "Test", 2017);
    	this.admin.changeCapacity("Test", 2017, 3);
    	assertEquals(3, this.admin.getClassCapacity("Test", 2017));
    }
    
    @Test
    public void testChangeCapacityValidIncrease() {
    	this.admin.createClass("Test", 2017, "Instructor", 15);
    	this.admin.changeCapacity("Test", 2017, 25);
    	assertEquals(25, this.admin.getClassCapacity("Test", 2017));
    }
    
    //Not a unit test, integration test
    @Test
    public void testChangeCapacityLessThanEnrolled() {
    	this.admin.createClass("Test", 2017, "Instructor", 10);
    	Student s1 = new Student();
    	Student s2 = new Student();
    	s1.registerForClass("Student1", "Test", 2017);
    	s2.registerForClass("Student2", "Test", 2017);
    	int classCap = this.admin.getClassCapacity("Test", 2017);
    	//This should do nothing
    	this.admin.changeCapacity("Test", 2017, 1);
    	assertEquals(classCap, this.admin.getClassCapacity("Test", 2017));
  
    }
    
    @Test
    public void testChangeCapacityInvalidCapacity() {
    	this.admin.createClass("Test", 2017, "Instructor", 15);
    	int startCap = this.admin.getClassCapacity("Test", 2017);
    	this.admin.changeCapacity("Test", 2017, -1);
    	//System.out.println(this.admin.getClassCapacity("Test", 2017));
    	assertEquals(startCap, this.admin.getClassCapacity("Test", 2017));
    }
    
    @Test
    public void testChangeCapacityNotExist() {
    	this.admin.changeCapacity("Test2", 2017, 12);
    	//&& this.admin.getClassCapacity("Test2", 2017) == 12, needed?
    	assertFalse( this.admin.classExists("Test2", 2017) );
    }
    
    //Testing NULL input values
    
}
