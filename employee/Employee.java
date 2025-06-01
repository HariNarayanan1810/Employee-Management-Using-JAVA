
package employee;

import enums.Department;
import enums.Role;

public class Employee implements Cloneable {
    private int id;
    private String name;
    private double salary;
    private Department department;
    private Role role;

    public Employee(int id, String name, double salary, Department department, Role role) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.department = department;
        this.role = role;
    }
public void id_update(int n){
        this.id = n;
}
    public int getId() { return id; }
    public String getName() { return name; }
    public double getSalary() { return salary; }
    public Department getDepartment() { return department; }
    public Role getRole() { return role; }

    public void setName(String name) { this.name = name; }
    public void setSalary(double salary) { this.salary = salary; }
    public void setDepartment(Department department) { this.department = department; }
    public void setRole(Role role) { this.role = role; }

    // Shallow Copy (uses Object.clone)
    @Override
    public Employee clone() {
        try {
            return (Employee) super.clone(); // Shallow copy — good for immutable fields
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Deep Copy (manual reconstruction)
    public Employee deepClone() {
        return new Employee(
                this.id,
                new String(this.name),
                this.salary,
                Department.valueOf(this.department.name()),
                Role.valueOf(this.role.name())
        );
    }

    @Override
    public String toString() {
        return ""+this.id;
    }
}
