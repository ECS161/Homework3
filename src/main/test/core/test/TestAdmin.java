package core.test;

import core.api.IAdmin;
import core.api.impl.Admin;
import org.junit.Before;
import org.junit.Test;
import java.util.Random;
import core.api.IStudent;
import core.api.impl.Student;

import static org.junit.Assert.*;

public class TestAdmin {
	private Random rand; 
	IAdmin admin;
	IStudent student;
	
	@Before
	public void setup() {
		this.admin = new Admin();
		this.student = new Student();
		this.rand = new Random();
	}
	
	// Test Make Class
	@Test
	public void testMakeClass() {  // True makes class
		this.admin.createClass("Test", 2017, "Instructor", 15);
		assertTrue(this.admin.classExists("Test", 2017));
	}
	
	@Test
	public void testMakeClass2() { // False, date in past
		this.admin.createClass("Test", 2016, "Instructor", 15);
		assertFalse(this.admin.classExists("Test", 2016));
	}
	
	@Test
	public void testMakeClass3() { // False, size less than 0
		this.admin.createClass("Test", 2017, "Instructor", -1);
		assertFalse(this.admin.classExists("Test", 2017));
	}
	
	@Test
	public void testMakeClass4() { // False, size equals zero
		this.admin.createClass("Test", 2017, "Instructor", 0);
		assertFalse(this.admin.classExists("Test", 2017));
	}
	
	@Test
	public void testMakeClass5() { // True, Check if teacher was created
		this.admin.createClass("Test", 2017, "Instructor", 15);
		assertTrue(this.admin.getClassInstructor("Test", 2017).equals("Instructor"));
	}
		
	@Test
	public void testMakeClass7() { // True, Check if class size was set to correct number
		
		int randomNum = rand.nextInt(100)+1;
		this.admin.createClass("Test", 2017, "Instructor", randomNum);
		assertTrue(this.admin.getClassCapacity("Test", 2017) == randomNum);
	}
	
	@Test
	public void testMakeClass8() { // False, cannot allow a teacher to be in 3+ classes in given year
		this.admin.createClass("First", 2017, "Instructor", 15);
		this.admin.createClass("Second", 2017, "Instructor", 15);
		this.admin.createClass("Third", 2017, "Instructor", 15);
		assertFalse(this.admin.classExists("Third", 2017));
		
	}
	
	@Test 
	public void testMakeClass9() { // True, Teacher can be in 3+ class if different years
		this.admin.createClass("First", 2017, "Instructor", 15);
		this.admin.createClass("Second", 2017, "Instructor", 15);
		this.admin.createClass("Third", 2018, "Instructor", 15);
		assertTrue(this.admin.classExists("Third", 2018));
	}
	
	@Test
	public void testMakeClass11() { // True, Duplicate class name different year
		this.admin.createClass("Test", 2017, "Instructor", 15);
		this.admin.createClass("Test", 2018, "Instructor", 15);
		assertTrue(this.admin.classExists("Test", 2018));
	}
	
	@Test
	public void testMakeClass12() { // True, create class in the future
		this.admin.createClass("Test", 2018, "Instructor", 15);
		assertTrue(this.admin.classExists("Test", 2018));
		
	}
	
	@Test
	public void testMakeClass13() { // True, Test that name, year pair is unique
		this.admin.createClass("ECS161", 2017, "Instructor", 15);
		this.admin.createClass("ECS161", 2017, "Instructor2", 5);
		assertTrue(this.admin.getClassInstructor("ECS161", 2017).equals("Instructor"));
		
	}
	
	// Test change Capacity
	@Test
	public void testChangeCapacity() { // False, Change capacity to 0
		this.admin.createClass("Test", 2017, "Instructor", 15);
		this.admin.changeCapacity("Test", 2017, 0);
		assertFalse(this.admin.getClassCapacity("Test", 2017) == 0);
	
	}
	@Test
	public void testChangeCapacity2() { // False, cannot change capacity to negative
		this.admin.createClass("Test", 2017, "Instructor", 2017);
		this.admin.changeCapacity("Test", 2017, -5);
		assertFalse(this.admin.getClassCapacity("Test", 2017) == -5);
		
	}
	@Test
	public void testChangeCapacity3() { // True change capacity to greater than total students in class
		this.admin.createClass("Test", 2017, "Instructor", 5);
		this.student.registerForClass("apple", "Test", 2017);
		this.student.registerForClass("bliss", "Test", 2017);
		this.student.registerForClass("crystal", "Test", 2017);
		this.student.registerForClass("dolphine", "Test", 2017);
		this.admin.changeCapacity("Test", 2017, 10);
		assertTrue(this.admin.getClassCapacity("Test", 2017) == 10);
	}
	@Test
	public void testChangeCapacity4() { // True change capacity to equivalent of num students
		this.admin.createClass("Test", 2017, "Instructor", 5);
		this.student.registerForClass("apple", "Test", 2017);
		this.student.registerForClass("bliss", "Test", 2017);
		this.student.registerForClass("crystal", "Test", 2017);
		this.student.registerForClass("dolphine", "Test", 2017);
		this.admin.changeCapacity("Test", 2017, 4);
		assertTrue(this.admin.getClassCapacity("Test", 2017) == 4);
	}
	@Test
	public void testChangeCapacity5() { // False, change capacity less than total students enrolled in class
		this.admin.createClass("Test", 2017, "Instructor", 5);
		this.student.registerForClass("apple", "Test", 2017);
		this.student.registerForClass("bliss", "Test", 2017);
		this.student.registerForClass("crystal", "Test", 2017);
		this.student.registerForClass("dolphine", "Test", 2017);
		this.admin.changeCapacity("Test", 2017, 3);
		assertFalse(this.admin.getClassCapacity("Test", 2017) == 3);
	}
	
	@Test
	public void testChangeCapacity6() { //False, change capacity of inexistent class
		this.admin.changeCapacity("Test", 2017, 7);
		assertFalse(this.admin.getClassCapacity("Test", 2017) == 7);
		
	}
	
	@Test
	public void testChangeCapacity7() { //False, change capacity of class of wrong year
		this.admin.createClass("Test", 2017, "Prim", 10);
		this.admin.changeCapacity("Test", 2018, 7);
		assertFalse(this.admin.getClassCapacity("Test", 2017) == 7);
		
	}
	
	
	
	
}
