package core.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import core.api.IAdmin;
import core.api.IInstructor;
import core.api.IStudent;
import core.api.impl.Admin;
import core.api.impl.Instructor;
import core.api.impl.Student;


public class TestStudent {
	private IStudent student;
	
    @Before
    public void setup() {
        this.student = new Student();
    }
    
    @Test
    //Test should work for registerclass if all the relationships are inorder
    public void testRegisterClass() {
    	IAdmin admin = new Admin();
    	admin.createClass("Test",2017,"Inst",10);
    	this.student.registerForClass("Bill","Test",2017);
    	assertTrue(this.student.isRegisteredFor("Bill","Test",2017));	
    }
    
    @Test
    //registerclass if capacity is overloaded the student shouldn't be registered
    public void testRegisterClass2() {
    	IAdmin admin = new Admin();
    	admin.createClass("Test",2017,"Inst",1);
    	this.student.registerForClass("Bill","Test",2017);
    	this.student.registerForClass("Steven","Test",2017);
    	assertFalse(this.student.isRegisteredFor("Steven","Test",2017));	
    }
    
    @Test
    //registerclass if class doesn't exist the student shouldn't be registered
    public void testRegisterClass3() {
    	IAdmin admin = new Admin();
    	//admin.createClass("Test",2017,"Inst",10);
    	this.student.registerForClass("Bill","Test",2017);
    	assertFalse(this.student.isRegisteredFor("Bill","Test",2017));	
    }
    
    @Test
    //dropClass if everything in place should drop the class
    public void testDropClass() {
    	IAdmin admin = new Admin();
    	admin.createClass("Test",2017,"Inst",10);
    	this.student.registerForClass("Bill","Test",2017);
    	this.student.dropClass("Bill","Test",2017);
    	assertFalse(this.student.isRegisteredFor("Bill","Test",2017));	
    }
    
    @Test
    //submitHomework if everything in place should submit the hw
    public void testSubmitHomework() {
    	IAdmin admin = new Admin();
    	admin.createClass("Test",2017,"Inst",10);
    	IInstructor instructor = new Instructor();
    	instructor.addHomework("Inst","Test",2017,"Hw");
    	this.student.registerForClass("Bill","Test",2017);
    	this.student.submitHomework("Bill","Hw","Ans","Test",2017);
    	assertTrue(this.student.hasSubmitted("Bill","Hw","Test", 2017));	
    }
    
    @Test
    //submitHomework shouldn't mark as submitted if the homework doesn't exist
    public void testSubmitHomework2() {
    	IAdmin admin = new Admin();
    	admin.createClass("Test",2017,"Inst",10);
    	IInstructor instructor = new Instructor();
    	//instructor.addHomework("Inst","Test",2017,"Hw");
    	this.student.registerForClass("Bill","Test",2017);
    	this.student.submitHomework("Bill","Hw","Ans","Test",2017);
    	assertFalse(this.student.hasSubmitted("Bill","Hw","Test", 2017));	
    }
    
    @Test
    //submitHomework shouldn't mark as submitted if the student isn't registered for the class
    public void testSubmitHomework3() {
    	IAdmin admin = new Admin();
    	admin.createClass("Test",2017,"Inst",10);
    	IInstructor instructor = new Instructor();
    	instructor.addHomework("Inst","Test",2017,"Hw");
    	//this.student.registerForClass("Bill","Test",2017);
    	this.student.submitHomework("Bill","Hw","Ans","Test",2017);
    	assertFalse(this.student.hasSubmitted("Bill","Hw","Test", 2017));	
    }
    
}
