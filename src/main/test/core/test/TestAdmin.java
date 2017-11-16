package core.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import core.api.IAdmin;
import core.api.impl.Admin;

public class TestAdmin {
    private IAdmin admin;

    @Before
    public void setup() {
        this.admin = new Admin();
    }

    @Test
    public void testMakeClass() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }

    @Test
    public void testMakeClass2() {
        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    }
    
    @Test
    // Test if a professor can have to classes in the same year
    public void testTwoClassesOneYear() {
    	this.admin.createClass("Test",2017,"Instructor",10);
    	this.admin.createClass("Test2",2017,"Instructor",20);
    	assertFalse(this.admin.classExists("Test", 2017) && this.admin.classExists("Test2", 2017));
    }
    
   @Test
   //Test if a class and instructor pair can override another one
   public void testUniqueClassInstructorPair() {
	   this.admin.createClass("Test",2017,"Instructor",10);
	   this.admin.createClass("Test",2017,"Instructor",20);
	   assertTrue(this.admin.getClassCapacity("Test", 2017) == 10);
   }

   @Test
   //Class with zero or less capacity shouldn't be created
   public void testZeroCapacity(){
	   this.admin.createClass("Test", 2017,"Instructor", 0);
	   assertFalse(this.admin.classExists("Test", 2017));
   }
   
   @Test
   //Tests if class capacity changes when the function is invoked
   public void testChangeClassCapacity() {
	   this.admin.createClass("Test", 2017, "Instructor", 10);
	   this.admin.changeCapacity("Test",2017,20);
	   assertTrue(this.admin.getClassCapacity("Test", 2017) == 20);
   }
   
   @Test
   //The change capacity function shouldn't change capacity if new capacity is less than old
   public void testChangeCapacitySmaller() {
	   this.admin.createClass("Test", 2017,"Inst",50);
	   this.admin.changeCapacity("Test",2017,20);
	   assertTrue(this.admin.getClassCapacity("Test",2017)==50);
   }
   
}
