package unsw.enrolment.test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.DayOfWeek;
import java.time.LocalTime;

import unsw.enrolment.Course;
import unsw.enrolment.CourseOffering;
import unsw.enrolment.Enrolment;
import unsw.enrolment.exceptions.InvalidEnrolmentException;
import unsw.enrolment.Student;
import unsw.enrolment.Grade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import org.junit.jupiter.api.Test;

/**
 * Simple tests for the enrolment system
 * @author Nick Patrikeos + @your name
 */
public class EnrolmentTest {

    private List<Student> parseStudentsCSV(String path) {
        File file = new File(path);
        CSVParser csvParser = null;

        try {
            csvParser = CSVParser.parse(file, Charset.defaultCharset(), CSVFormat.RFC4180);
        } catch (IOException e) {
            return null;
        }

        List<Student> students = new ArrayList<Student>();
        csvParser.forEach(record -> {
            if (record.getRecordNumber() == 1) return;
            students.add(new Student(record.get(0), record.get(1), 
                         Integer.parseInt(record.get(2)), record.get(3).split(" ")));
        });

        return students;
    }

    @Test
    public void testIntegration() {
        // Create courses
        Course cs1511 = new Course("COMP1511", "Programming Fundamentals");
        Course cs1531 = new Course("COMP1531", "Software Engineering Fundamentals");
        cs1531.addPrereq(cs1511);
        Course cs2521 = new Course("COMP2521", "Data Structures and Algorithms");
        cs2521.addPrereq(cs1511);

        CourseOffering cs1511Offering = new CourseOffering(cs1511, "19T1");
        CourseOffering cs1531Offering = new CourseOffering(cs1531, "19T1");
        CourseOffering cs2521Offering = new CourseOffering(cs2521, "19T2");

        // Create a student
        Student student1 = new Student("z5555555", "Jon Snow", 3707, new String[] {"SENGAH"});

        // Enrol the student in COMP1511 for T1 (this should succeed)
        assertDoesNotThrow(() -> {
            cs1511Offering.addEnrolment(student1);
        });
        assertTrue(student1.isEnrolled(cs1511Offering));

        // Enrol the student in COMP1531 for T1 (this should fail as they
        // have not met the prereq)
        assertThrows(InvalidEnrolmentException.class, () -> {
           cs1531Offering.addEnrolment(student1);
        });

        // Give the student a passing grade for COMP1511
        Grade student1comp1511grade = new Grade(cs1511Offering, 98, "HD");
        student1.setGrade(student1comp1511grade);

        // Enrol the student in 2521 & 1531 (this should succeed as they have met
        // the prereqs)
        assertDoesNotThrow(() -> { 
            cs2521Offering.addEnrolment(student1);
            cs1531Offering.addEnrolment(student1);
        });

        assertTrue(student1.isEnrolled(cs2521Offering));
        assertTrue(student1.isEnrolled(cs1531Offering));
    }

    @Test
    public void testComparator() {
        List<Student> students = parseStudentsCSV("bin/unsw/enrolment/test/students.csv");
        List<Student> studentsInOrder = students.stream().sorted(Comparator.comparing(Student::getProgram)
                                            .thenComparing(Student :: getName)
                                            .thenComparing(Student :: getNumberOfStreams)
                                            .thenComparing(Student :: getZid))
                                            .collect(Collectors.toList()); 
        Course cs2511 = new Course("COMP2511", "Object-Oriented Design & Programming");
        CourseOffering cs2511Offering = new CourseOffering(cs2511, "21T3");

        students.stream()
            .forEach (student -> {
                try {
                    cs2511Offering.addEnrolment(student);
                } catch (InvalidEnrolmentException e) {
                    e.printStackTrace();
                }
            });

        assertIterableEquals(cs2511Offering.studentsEnrolledInCourse(), studentsInOrder);   
    }

}
