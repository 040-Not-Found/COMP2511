package staff;

import java.time.LocalDate;

/**
 * A lecturer
 * @name 
 * @salary
 * @hireDate
 * @endDate
 * @school
 * @academicStatus
 */
public class Lecturer extends StaffMember{
    private String school;
    private char academicStatus;

    public Lecturer(String name, int salary, LocalDate hirDate, 
                    LocalDate endDate, String school, 
                    char academicStatus) {
        super(name, salary, hirDate, endDate);
        this.school = school;
        this.academicStatus = academicStatus;
    }

    /**
     * getter function for school
     * @return school
     */
    public String getSchool() {
        return school;
    }

    /**
     * setter function for school
     * @param school
     */
    public void setSchool(String school) {
        this.school = school;
    }
    
    /**
     * getter function for academicStatus
     * @return academicStatus
     */
    public char getAcademicStatus() {
        return academicStatus;
    }

    /**
     * setter function for academicStatus
     * @param academicStatus
     */
    public void setAcademicStatus(char academicStatus) {
        this.academicStatus = academicStatus;
    }

    @Override
    public String toString() {
        return super.toString() + " [school=" + school + 
               ", academicStatus=" + academicStatus + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Lecturer other = (Lecturer) obj;
        if (school == null) {
            if (other.school != null)
                return false;
        } else if (!school.equals(other.school))
            return false;
        return true;
    }

    public static void main(String[] args) {
        Lecturer l = new Lecturer("name", 100, LocalDate.of(22, 12, 31), LocalDate.of(22, 12, 31), "school", 'A');
        System.out.println(l.toString());
    }
}
