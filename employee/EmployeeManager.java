
package employee;

import common.DatabaseUtil;
import enums.Department;
import enums.Role;
import annotations.TrackOperation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {

    @TrackOperation("Save employee")
    public void saveEmployee(Object obj) throws Exception {
        if (obj instanceof Employee emp) {
            Connection con = DatabaseUtil.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO employee VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, emp.getId());
            ps.setString(2, emp.getName());
            ps.setDouble(3, emp.getSalary());
            ps.setString(4, emp.getDepartment().toString());
            ps.setString(5, emp.getRole().toString());
            ps.executeUpdate();
            con.close();
        }
    }

    @TrackOperation("Update employee")
    public void updateEmployee(Object obj) throws Exception {
        if (obj instanceof Employee emp) {
            // Fetch existing record from DB (for deep copy)
            Connection con = DatabaseUtil.getConnection();
            PreparedStatement fetch = con.prepareStatement("SELECT * FROM employee WHERE id=?");
            fetch.setInt(1, emp.getId());
            ResultSet rs = fetch.executeQuery();
//            Employee deepCopy = null;
//
//            if (rs.next()) {
//                deepCopy = new Employee(
//                        rs.getInt("id"),
//                        rs.getString("name"),
//                        rs.getDouble("salary"),
//                        Department.valueOf(rs.getString("department")),
//                        Role.valueOf(rs.getString("role"))
//                );
//            }
//
//            System.out.println("Deep Copy (original record): " + deepCopy.getDepartment());

            // Create shallow copy of updated object
            Employee shallowCopy = (Employee) emp.clone();
                shallowCopy.id_update(200);
            System.out.println(shallowCopy);
            System.out.println(emp);
            System.out.println("Shallow Copy (updated): " + shallowCopy.getRole());
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE employee SET name=?, salary=?, department=?, role=? WHERE id=?");
            ps.setString(1, emp.getName());
            ps.setDouble(2, emp.getSalary());
            ps.setString(3, emp.getDepartment().toString());
            ps.setString(4, emp.getRole().toString());
            ps.setInt(5, emp.getId());
            ps.executeUpdate();
            con.close();
        }
    }

    @TrackOperation("Delete employee")
    public void deleteEmployee(int id) throws Exception {
        Connection con = DatabaseUtil.getConnection();
        PreparedStatement ps = con.prepareStatement("DELETE FROM employee WHERE id=?");
        ps.setInt(1, id);
        ps.executeUpdate();
        con.close();
    }

    @TrackOperation("Search employee")
    public Employee getEmployeeById(int id) throws Exception {
        Connection con = DatabaseUtil.getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM employee WHERE id=?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Employee emp = new Employee(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getDouble("salary"),
                Department.valueOf(rs.getString("department")),
                Role.valueOf(rs.getString("role"))
            );
            con.close();
            return emp;
        }
        con.close();
        return null;
    }

    @TrackOperation("Show all employees")
    public List<Employee> getAllEmployees() throws Exception {
        List<Employee> list = new ArrayList<>();
        Connection con = DatabaseUtil.getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM employee");
        while (rs.next()) {
            Employee emp = new Employee(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getDouble("salary"),
                Department.valueOf(rs.getString("department")),
                Role.valueOf(rs.getString("role"))
            );
            list.add(emp);
        }
        con.close();
        return list;
    }
}
