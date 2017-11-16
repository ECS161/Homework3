package core.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import core.api.IAdmin;
import core.api.IStudent;
import core.api.impl.Admin;
import core.api.impl.Student;

public class TestAdmin{
	
	//I didn't need to use getClassInstructor?

	private IAdmin admin;
	private IStudent studentA;
	private IStudent studentB;
	
	@Before
    public void setup() {
        this.admin = new Admin();
        this.studentA = new Student();
        this.studentB = new Student();
    }

    @Test
    public void testCreateClassNormal() {
        this.admin.createClass("class", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("class", 2017));
    }
    
    @Test
    public void testCreateClassOldYear() {
        this.admin.createClass("class", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("class", 2016));
    }
    
    @Test
    public void testCreateClassEmptyInstructor() {
        this.admin.createClass("class", 2017, "", 15);
        assertFalse(this.admin.classExists("class", 2017));
    }
    
    @Test
    public void testCreateClassEmptyClassName() {
        this.admin.createClass("", 2017, "Instructor", 15);
        assertFalse(this.admin.classExists("", 2017));
    }
    
    @Test
    public void testCreateClassCapacityZero() {
        this.admin.createClass("class", 2017, "Instructor", 0);
        assertFalse(this.admin.classExists("class", 2017));
    }
    
    @Test
    public void testCreateClassNegative() {
        this.admin.createClass("class", 2017, "Instructor", -1);
        assertFalse(this.admin.classExists("class", 2017));
    }
    
    @Test
    public void testCreateClassTooManyAssignedToInstructor() {
        this.admin.createClass("classA", 2017, "Instructor", 15);
        this.admin.createClass("classB", 2017, "Instructor", 15);
        this.admin.createClass("classC", 2017, "Instructor", 15);
        assertFalse(this.admin.classExists("classC", 2017));
    }
    
    @Test
    public void testCreateClassWithSameNameAndYear() {
        this.admin.createClass("class", 2017, "Instructor", 15);
        this.admin.createClass("class", 2017, "Instructor", 30);
        assertTrue(15 == this.admin.getClassCapacity("class", 2017));
    }
    
    //////////////////
    
    @Test
    public void testChangeCapacityNormal() {
    		this.admin.createClass("class", 2017, "Instructor", 15);
    		this.admin.changeCapacity("class", 2017, 5);
    		assertTrue(5 == this.admin.getClassCapacity("class", 2017));
    }
    
    @Test
    public void testChangeCapacityNegative() {
		this.admin.createClass("class", 2017, "Instructor", 15);
		this.admin.changeCapacity("class", 2017, -1);
		assertFalse(-1 == this.admin.getClassCapacity("class", 2017));
    }
    
    @Test
    public void testChangeCapacityToLessThanNumStudents() {
		this.admin.createClass("class", 2017, "Instructor", 15);
		this.studentA.registerForClass("studentA", "class", 2017);
		this.studentB.registerForClass("studentB", "class", 2017);
		this.admin.changeCapacity("class", 2017, 1);
		assertFalse(1 == this.admin.getClassCapacity("class", 2017));
    }
}
