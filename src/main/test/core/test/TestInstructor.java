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

public class TestInstructor {
    private IInstructor instructor;

    @Before
    public void setup() {
        this.instructor = new Instructor();
    }
    
    @Test
    //Tests if add homework works properly when class is assigned to instructor
    public void testAddHomework() {
    	IAdmin admin = new Admin();
        admin.createClass("Test", 2017, "Inst", 10);
        this.instructor.addHomework("Inst","Test",2017,"hw");
        assertTrue(this.instructor.homeworkExists("Test",2017,"hw"));
    }
    
    @Test
    //Add homework shouldn't work when class is not assigned to instructor
    public void testAddHomework2() {
    	IAdmin admin = new Admin();
        admin.createClass("Test", 2017, "Inst", 10);
        this.instructor.addHomework("Instructor","Test",2017,"hw");
        assertFalse(this.instructor.homeworkExists("Test",2017,"hw"));
    }
    
    @Test
    //Test assign grade should work fine with all student, class, and instructor relationships in order
    public void testAssignGrade() {
    	IAdmin admin = new Admin();
        admin.createClass("Test", 2017, "Inst", 10);
        IStudent student = new Student();
        student.registerForClass("Bill","Test",2017);
        this.instructor.addHomework("Inst","Test",2017,"Hw");
        student.submitHomework("Bill","Hw","Ans","Test",2017);
        this.instructor.assignGrade("Inst","Test",2017,"Hw","Bill",90);
        assertTrue(this.instructor.getGrade("Test",2017,"Hw","Bill") == 90);
    }
    
    @Test
    //Assign Grade shouldn't work if the student hasn't turned in the homework
    public void testAssignGrade2() {
    	IAdmin admin = new Admin();
        admin.createClass("Test", 2017, "Inst", 10);
        IStudent student = new Student();
        student.registerForClass("Bill","Test",2017);
        this.instructor.addHomework("Inst","Test",2017,"Hw");
        //student.submitHomework("Bill","Hw","Ans","Test",2017);
        this.instructor.assignGrade("Inst","Test",2017,"Hw","Bill",90);
        assertFalse(this.instructor.getGrade("Test",2017,"Hw","Bill") == 90);
    }
    
    @Test
    //Assign Grade shouldn't work if the homework hasn't been assigned to student
    public void testAssignGrade3() {
    	IAdmin admin = new Admin();
        admin.createClass("Test", 2017, "Inst", 10);
        admin.createClass("T", 2017,"I",10);
        IStudent student = new Student();
        student.registerForClass("Bill","T",2017);
        this.instructor.addHomework("Inst","Test",2017,"Hw");
        student.submitHomework("Bill","Hw","Ans","Test",2017);
        this.instructor.assignGrade("Inst","Test",2017,"Hw","Bill",90);
        assertFalse(this.instructor.getGrade("Test",2017,"Hw","Bill") == 90);
    }
}
