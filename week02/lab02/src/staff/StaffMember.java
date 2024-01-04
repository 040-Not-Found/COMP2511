package staff;

import java.time.LocalDate;

/**
 * A staff member
 * @name 
 * @salary
 * @hireDate
 * @endDate
 */

public class StaffMember {

    private String name;
    private int salary;
    private LocalDate hireDate;
    private LocalDate endDate;

    public StaffMember(String name, int salary, LocalDate hirDate, LocalDate endDate) {
        this.name = name;
        this.salary = salary;
        this.hireDate = hirDate;
        this.endDate = endDate;
    }
    /**
     * getter function for name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * setter function for name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter function for salary
     * @return salary
     */
    public int getSalary() {
        return salary;
    }

    /**
     * setter function for salary
     * @param salary
     */
    public void setSalary(int salary) {
        this.salary = salary;
    }
    
    /**
     * getter function for hire date
     * @return hire date
     */
    public LocalDate getHireDate() {
        return hireDate;
    }
    
    /**
     * setter function for hire date
     * @param hireDate
     */
    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    /**
     * getter function for end date
     * @return end date
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * setter function for end date
     * @param endDate
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return getClass().getName() + " [name=" + name + ", salary=" + salary + ", hireDate=" + hireDate + ", endDate=" + endDate
                + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        StaffMember other = (StaffMember) obj;
        if (endDate == null) {
            if (other.endDate != null)
                return false;
        } else if (!endDate.equals(other.endDate)) {
            return false;
        }
        if (hireDate == null) {
            if (other.hireDate != null)
                return false;
        } else if (!hireDate.equals(other.hireDate))
            return false;

        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;

        if (salary != other.salary)
            return false;
        return true;
    }

}
