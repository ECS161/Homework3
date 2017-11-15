package core.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalMatchers;
import org.mockito.Mockito;
import org.mockito.Spy;

import core.api.ICourseManager;
import core.api.impl.Admin;
import core.api.impl.CourseManager;

/**
 * Tests course manager. Since the Admin implementation is known to be bugging. 
 * 
 * @author Vincent
 *
 */
public class TestCourseManager {

	@Spy
	private Admin admin;
	private ICourseManager courseManager;
	
	@Before
	public void setup() {
		this.admin = Mockito.spy(new Admin());
		this.courseManager = new CourseManager(this.admin);
		setupMocking();
	}

	/*
	 * Shows some initial set-up for the mocking of Admin.
	 * This includes fixing a known bug (year in past is not correctly checked) in the Admin class by Mocking its behavior.
	 * Not all fixes to Admin can be made from here, so for the more complex constraints you can simply Mock the
	 * specific calls to Admin's createClass to yield the correct behavior in the unit test itself.
	 */
	public void setupMocking() {
		Mockito.doNothing().when(this.admin).createClass(Mockito.anyString(), AdditionalMatchers.lt(2017), Mockito.anyString(), Mockito.anyInt());
		Mockito.doNothing().when(this.admin).createClass(Mockito.anyString(), Mockito.anyInt(), Mockito.anyString(), AdditionalMatchers.lt(1));
	}

	@Test
	public void testCreateClassCorrect() {
		this.courseManager.createClass("ECS161", 2017, "Instructor", 1);
		assertTrue(this.courseManager.classExists("ECS161", 2017));
	}
	
	@Test
	public void testCreateClassInPast() {
		this.courseManager.createClass("ECS161", 2016, "Instructor", 1);
		assertFalse(this.courseManager.classExists("ECS161", 2016));
	}

	@Test
	public void testCreateClassInFuture() {
		this.courseManager.createClass("ECS161", 2018, "Instructor", 1);
		Mockito.verify(this.admin, Mockito.never()).createClass(Mockito.anyString(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyInt());
	}
	
	@Test
	public void testCreateClassLessThanZero() {
		this.courseManager.createClass("ECS161", 2017, "Instructor", 0);
		assertFalse(this.courseManager.classExists("ECS161", 2017));
	}
	
	@Test
	public void testCreateClassGreaterThanLimit() {
		this.courseManager.createClass("ECS161", 2017, "Instructor", 5000);
		Mockito.verify(this.admin, Mockito.never()).createClass(Mockito.anyString(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyInt());
	}
	
	@Test
	public void testCreateClassDuplicates() {
		String course = "ECS161";
		int year = 2017;
		
		// First call real method
		// Then do nothing when input is of same type
		Mockito.doCallRealMethod().doNothing().when(this.admin).createClass(Mockito.matches(course), Mockito.eq(year), Mockito.anyString(), Mockito.anyInt());
		this.courseManager.createClass(course, year, "Instructor", 5);
		this.courseManager.createClass(course, year, "Instructor2", 3);
		
		assertFalse(this.courseManager.getClassInstructor(course, year).equals("Instructor2"));
		
	} 
	
	@Test
	public void testCreateMultipleClasses() {
		String course = "ECS161";
		int year = 2017;
		Mockito.doCallRealMethod().doNothing().when(this.admin).createClass(Mockito.matches(course), Mockito.eq(year), Mockito.anyString(), Mockito.anyInt());
		this.courseManager.createClass(course, year, "Instructor", 5);
		this.courseManager.createClass("ECS160", year, "Instructor", 9);
		
		assertTrue(this.courseManager.classExists("ECS160", year));
	}
	
	@Test
	public void testCreateTooManyClassesForTeacher() {
		String teacher = "Instructor";
		int year = 2017;
		Mockito.doCallRealMethod().doCallRealMethod().doNothing().when(this.admin).createClass(Mockito.anyString(), Mockito.eq(year), Mockito.matches(teacher), Mockito.anyInt());;
		this.courseManager.createClass("Class1", year, teacher, 5);
		this.courseManager.createClass("Class2", year, teacher, 10);
		this.courseManager.createClass("Class3", year, "Instructor2", 10);
		this.courseManager.createClass("Class4", year, teacher, 15);
		
		assertFalse(this.courseManager.classExists("Class4", year));
	
	}
	
	@Test
	public void testCreateAltTeacher() { // Make sure alternative professor was added
		String teacher = "Instructor";
		int year = 2017;
		Mockito.doCallRealMethod().doCallRealMethod().doNothing().when(this.admin).createClass(Mockito.anyString(), Mockito.eq(year), Mockito.matches(teacher), Mockito.anyInt());;
		this.courseManager.createClass("Class1", year, teacher, 5);
		this.courseManager.createClass("Class2", year, teacher, 10);
		this.courseManager.createClass("Class3", year, "Instructor2", 10);
		this.courseManager.createClass("Class4", year, teacher, 15);
		
		assertTrue(this.courseManager.classExists("Class3", year));
	
	}
	
	
}
