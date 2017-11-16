package core.test;

import core.api.IAdmin;
import core.api.impl.Admin;
import core.api.impl.Student;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import core.api.IStudent; 
/**
 * Created by Vincent on 23/2/2017.
 */
public class AdminTest {

    private IAdmin admin;
    private IStudent student; 

    @Before
    public void setup() {
        this.admin = new Admin();
        this.student = new Student(); 
    }

    @Test
    //this test works because its in the present
    //also representative of capacity
    public void testMakeClass() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }

    @Test
    //this test must fail because year is in the past
    public void testMakeClass2() {
        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    }
    
    @Test
    //this test must pass bc capacity is greater than 0
    public void testMakeClassCapacityA() {
        this.admin.createClass("Test", 2017, "Instructor", 10);
        assertEquals(10, this.admin.getClassCapacity("Test", 2017));
    }
    
    @Test
    //this test must fail bc capacity is equal to 0
    //so class does not exist
    public void testMakeClassCapacityB() {
        this.admin.createClass("Test", 2017, "Instructor", 0);
        assertFalse(this.admin.classExists("Test", 2017));
    }
    
    @Test
    //this test must fail bc capacity is less than 1
    //so class does not exist
    public void testMakeClassCapacityC() {
        this.admin.createClass("Test", 2017, "Instructor", -1);
        assertFalse(this.admin.classExists("Test", 2017));
    }
    
    @Test
    //this test must fail bc classname/year is not unique; only instructors are
    //instructor or size is different -- can choose just one
    //when we run --> we get an x in the log bc there is a bug in the code
    public void testMakeClass5() {
        this.admin.createClass("Test", 2017, "InstructorA", 15);
        this.admin.createClass("Test", 2017, "InstructorB", 15);
        
        assertEquals("InstructorA", this.admin.getClassInstructor("Test", 2017)); 
        assertNotSame("InstructorB", this.admin.getClassInstructor("Test", 2017)); 
    }
    
    @Test
    //this test must fail bc no instructor can teach more than 2 classes
    public void testMakeClass6() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.createClass("Test2", 2017, "Instructor", 15);
        this.admin.createClass("Test3", 2017, "Instructor", 15);
        assertFalse(this.admin.classExists("Test3", 2017));
    }
    
    @Test
    //this test must fail bc no instructor can teach exactly 2 classes
    public void testMakeClass7() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.createClass("Test2", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test2", 2017));
    }
    
    /******* END OF createClass TESTS *******/
    
    /******* START of changeCapacity TESTS ********/
    
    @Test
    //3 cases
    //2 cases pass
    //class size is equal to or greater than
    //1 case fails
    //class size is less than registered
    
    //have class size of 2
    //only 1 student is registered
    //change class size to 1
    //should return true
    //class size is equal to registered students
    public void testChangeCapacity1() {
    		this.admin.createClass("testClass", 2017, "Instructor", 2);
    		this.student.registerForClass("StudentA", "testClass", 2017);
    		this.admin.changeCapacity("testClass", 2017, 1);
    		assertEquals(1, this.admin.getClassCapacity("testClass", 2017));
    }
    
    @Test
    //class size is greater than registered students
    public void testChangeCapacity2() {
		this.admin.createClass("testClass", 2017, "Instructor", 3);
		this.student.registerForClass("StudentA", "testClass", 2017);
		this.admin.changeCapacity("testClass", 2017, 2);
		assertEquals(2, this.admin.getClassCapacity("testClass", 2017));
    }
    
    @Test
    //class size is less than registered students
    public void testChangeCapacity3() {
		this.admin.createClass("testClass", 2017, "Instructor", 2);
		this.student.registerForClass("StudentA", "testClass", 2017);
		this.student.registerForClass("StudentB", "testClass", 2017);
		this.admin.changeCapacity("testClass", 2017, 1);
		assertEquals(2, this.admin.getClassCapacity("testClass", 2017));
    }
    
    /******* END of changeCapacity TESTS ********/
 
    /** extra **/
	@Test
	//testing that instructor is in the right class
	public void testCorrectInstructor() {
		this.admin.createClass("testClass", 2017, "Instructor", 15);
		assertEquals("Instructor", admin.getClassInstructor("testClass", 2017));
	}
}
