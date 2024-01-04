package staff.test;

import java.time.LocalDate;
import staff.Lecturer;
import staff.StaffMember;

public class StaffTest {

    public static void printStaffDetails(StaffMember sm) {
        System.out.println(sm.toString());
    }
    
    public static void main(String[] args) {
        StaffMember Josh_Kim = new StaffMember("Josh Kim", 10000, LocalDate.of(2020, 01, 01), 
                                                LocalDate.of(2022, 12, 31));
        Lecturer Alice_Avo = new Lecturer("Alice Avo", 15000, LocalDate.of(2018, 01, 01), 
                                           LocalDate.of(2024, 12, 31), "CSE", 'A');
        printStaffDetails(Josh_Kim);
        printStaffDetails(Alice_Avo);

        // test for reflexive
        System.out.println("x.equals(x): " + Josh_Kim.equals(Josh_Kim) + ", "+ Alice_Avo.equals(Alice_Avo));
        // test for symmetric
        StaffMember Josh2 = new StaffMember("Josh Kim", 10000, LocalDate.of(2020, 01, 01), 
        LocalDate.of(2022, 12, 31));
        System.out.println("x.equals(y): " + Josh_Kim.equals(Josh2) + ", y.equals(x):" + Josh2.equals(Josh_Kim));
        Josh2.setName("Kim");
        System.out.println("x.equals(y) with x != y: " + Josh_Kim.equals(Josh2));
        Lecturer Alice2 = new Lecturer("Alice Avo", 15000, LocalDate.of(2018, 01, 01), 
        LocalDate.of(2024, 12, 31), "CSE", 'A');
        System.out.println("x.equals(y): " + Alice_Avo.equals(Alice2) + ", y.equals(x):" + Alice2.equals(Alice_Avo));
        Alice2.setSalary(10000);
        System.out.println("x.equals(y) with x != y: " + Alice_Avo.equals(Alice2));
        // test for consistent
        System.out.println("x.equals(null): " + Josh_Kim.equals(null) + ", "+ Alice_Avo.equals(null));
        
    }
}