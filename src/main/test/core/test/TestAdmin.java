package core.test;

import core.api.IAdmin;
import core.api.impl.Admin;
import core.api.IStudent;
import core.api.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestAdmin {
    private IAdmin admin;

    @Before
    public void setup() {
        this.admin = new Admin();
    }

    @Test
    public void testMakeClass() //no intended error
    {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }

    @Test
    public void testMakeClass2() // class from past
    {
        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    }
    
    @Test
    public void testMakeClass3() //no class name
    {
        this.admin.createClass("", 2017, "Devanbu", 15);
        assertFalse(this.admin.classExists("", 2016));
    }
    
    @Test
    public void testMakeClass4() // no professor name
    {
        this.admin.createClass("ECS161", 2017, "", 15);
        assertFalse(this.admin.classExists("ECS161", 2016));
    }
    

    @Test
    public void testUniqueClass()//made dup (class,year) with diff instructor
    {
    	this.admin.createClass("ECS161", 2017, "Devanbu", 4);
    	assertEquals("Failure - create class failed",this.admin.getClassInstructor("ECS161", 2017),"Devanbu");
    	assertEquals("Failure - create class failed",this.admin.getClassCapacity("ECS161", 2017),4);
    	this.admin.createClass("ECS161", 2017, "Vincent", 4);
    	assertEquals("Failure - a duplicate class was added",this.admin.getClassInstructor("ECS161", 2017),"Devanbu");

    }
    
    @Test
    public void testUniqueClass2()//made dup (class,year) with diff cap
    {
    	this.admin.createClass("ECS161", 2017, "Devanbu", 4);
    	assertEquals("Failure - a duplicate class was added",this.admin.getClassInstructor("ECS161", 2017),"Devanbu");
    	assertEquals("Failure - a duplicate class was added",this.admin.getClassCapacity("ECS161", 2017),4);
    	this.admin.createClass("ECS161", 2017, "Devabu", 5);
    	assertEquals("Failure - a duplicate class was added",this.admin.getClassCapacity("ECS161", 2017),4);
    }
    
    @Test
    public void test1InstructorClass()//no intended error 2 diff classes 1 instructor
    {
    	this.admin.createClass("ECS161", 2017, "Devanbu", 4);
    	this.admin.createClass("ECS162", 2017, "Devanbu", 4);
    	assertTrue(this.admin.classExists("ECS161", 2017));
    	assertTrue(this.admin.classExists("ECS162", 2017));
    }
    
    @Test
    public void test1InstructorClass2()//3 classes 1 instructor
    {
    	this.admin.createClass("ECS161", 2017, "Devanbu", 4);
    	this.admin.createClass("ECS162", 2017, "Devanbu", 4);
    	this.admin.createClass("ECS163", 2017, "Devanbu", 4);
    	assertTrue(this.admin.classExists("ECS161", 2017));
    	assertTrue(this.admin.classExists("ECS162", 2017));
    	assertFalse(this.admin.classExists("ECS163", 2017));
    }
	
    @Test
    public void testClassCap2()//make class cap -1
    {
    	this.admin.createClass("ECS161", 2017, "Devanbu", -1);
    	assertFalse(this.admin.classExists("ECS161", 2017));
    }
    @Test
    public void testClassCap3()//make class cap 0
    {
    	this.admin.createClass("ECS161", 2017, "Devanbu", 0);
    	assertFalse(this.admin.classExists("ECS161", 2017));
    }
    
    @Test
    public void testClassCapChange()//no intended error new cap  = old cap
    {
    	IStudent student1 = new Student();
    	IStudent student2 = new Student();
    	this.admin.createClass("ECS161", 2017, "Devanbu", 2);
    	assertTrue(this.admin.classExists("ECS161", 2017));
    	student1.registerForClass("David1", "ECS161", 2017);
    	student2.registerForClass("David2", "ECS161", 2017);
    	assertTrue(student1.isRegisteredFor("David1","ECS161", 2017));
    	assertTrue(student2.isRegisteredFor("David2","ECS161", 2017));
    	this.admin.changeCapacity("ECS161", 2017, 2);
    	assertEquals("failue - Class capacity change, invalid",this.admin.getClassCapacity("ECS161", 2017),2);
    }
    
    @Test
    public void testClassCapChange2()//no intended error new cap  > old cap
    {
    	IStudent student1 = new Student();
    	IStudent student2 = new Student();
    	this.admin.createClass("ECS161", 2017, "Devanbu", 2);
    	assertTrue(this.admin.classExists("ECS161", 2017));
    	student1.registerForClass("David1", "ECS161", 2017);
    	student2.registerForClass("David2", "ECS161", 2017);
    	assertTrue(student1.isRegisteredFor("David1","ECS161", 2017));
    	assertTrue(student2.isRegisteredFor("David2","ECS161", 2017));
    	this.admin.changeCapacity("ECS161", 2017, 3);
    	assertEquals("failue - Class capacity change, invalid",this.admin.getClassCapacity("ECS161", 2017),3);
    }
    
    @Test
    public void testClassCapChange3()//drop cap to less than enrolled
    {
    	IStudent student1 = new Student();
    	IStudent student2 = new Student();
    	this.admin.createClass("ECS161", 2017, "Devanbu", 2);
    	assertTrue(this.admin.classExists("ECS161", 2017));
    	student1.registerForClass("David1", "ECS161", 2017);
    	student2.registerForClass("David2", "ECS161", 2017);
    	assertTrue(student1.isRegisteredFor("David1","ECS161", 2017));
    	assertTrue(student2.isRegisteredFor("David2","ECS161", 2017));
    	this.admin.changeCapacity("ECS161", 2017, 1);
    	assertNotEquals("failue - Class capacity change, invalid",this.admin.getClassCapacity("ECS161", 2017),1);
    }
    
    @Test
    public void testEnrollment()
    {
    	IStudent student1 = new Student();
    	IStudent student2 = new Student();
    	IStudent student3 = new Student();
    	this.admin.createClass("ECS161", 2017, "Devanbu", 2);
    	assertTrue(this.admin.classExists("ECS161", 2017));
    	student1.registerForClass("David1", "ECS161", 2017);
    	student2.registerForClass("David2", "ECS161", 2017);
    	student3.registerForClass("David3", "ECS161", 2017);
    	assertTrue(student1.isRegisteredFor("David1","ECS161", 2017));
    	assertTrue(student2.isRegisteredFor("David2","ECS161", 2017));
    	assertFalse(student3.isRegisteredFor("David3","ECS161", 2017));
    	
    }
}
