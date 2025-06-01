
package gui;

import employee.Employee;
import employee.EmployeeManager;
import enums.Department;
import enums.Role;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class EmployeeUI extends Frame {
    private TextField idField, nameField, salaryField;
    private Choice departmentChoice, roleChoice;
    private TextArea outputArea;
    private EmployeeManager manager = new EmployeeManager();

    public EmployeeUI() {
        setTitle("Employee Management System");
        setLayout(new BorderLayout());

        Panel formPanel = new Panel(new GridLayout(6, 2, 10, 10));
        formPanel.setBackground(Color.LIGHT_GRAY);
        formPanel.setPreferredSize(new Dimension(300, 200));

        formPanel.add(new Label("ID:"));
        idField = new TextField();
        formPanel.add(idField);

        formPanel.add(new Label("Name:"));
        nameField = new TextField();
        formPanel.add(nameField);

        formPanel.add(new Label("Salary:"));
        salaryField = new TextField();
        formPanel.add(salaryField);

        formPanel.add(new Label("Department:"));
        departmentChoice = new Choice();
        for (Department d : Department.values()) departmentChoice.add(d.name());
        formPanel.add(departmentChoice);

        formPanel.add(new Label("Role:"));
        roleChoice = new Choice();
        for (Role r : Role.values()) roleChoice.add(r.name());
        formPanel.add(roleChoice);

        Panel buttonPanel = new Panel(new FlowLayout());
        Button addBtn = new Button("Add");
        Button showBtn = new Button("Show All");
        Button searchBtn = new Button("Search");
        Button updateBtn = new Button("Update");
        Button deleteBtn = new Button("Delete");
        buttonPanel.add(addBtn);
        buttonPanel.add(showBtn);
        buttonPanel.add(searchBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);

        outputArea = new TextArea(10, 60);
        outputArea.setEditable(false);

        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(outputArea, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> addEmployee());
        showBtn.addActionListener(e -> showEmployees());
        searchBtn.addActionListener(e -> searchEmployee());
        updateBtn.addActionListener(e -> updateEmployee());
        deleteBtn.addActionListener(e -> deleteEmployee());

        setSize(700, 500);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    private void addEmployee() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            double salary = Double.parseDouble(salaryField.getText());
            Department dept = Department.valueOf(departmentChoice.getSelectedItem());
            Role role = Role.valueOf(roleChoice.getSelectedItem());
            manager.saveEmployee(new Employee(id, name, salary, dept, role));
            outputArea.setText("Employee added.");
        } catch (Exception e) {
            outputArea.setText("Error: " + e.getMessage());
        }
    }

    private void showEmployees() {
        try {
            List<Employee> list = manager.getAllEmployees();
            outputArea.setText("");
            for (Employee e : list) {
                outputArea.append(e.getId() + " " + e.getName() + " " + e.getSalary() + " " +
                        e.getDepartment() + " " + e.getRole() + "\n");
            }
        } catch (Exception e) {
            outputArea.setText("Error: " + e.getMessage());
        }
    }

    private void searchEmployee() {
        try {
            int id = Integer.parseInt(idField.getText());
            Employee emp = manager.getEmployeeById(id);
            if (emp != null) {
                nameField.setText(emp.getName());
                salaryField.setText(String.valueOf(emp.getSalary()));
                departmentChoice.select(emp.getDepartment().name());
                roleChoice.select(emp.getRole().name());
                outputArea.setText("Employee found.");
            } else {
                outputArea.setText("Employee not found.");
            }
        } catch (Exception e) {
            outputArea.setText("Error: " + e.getMessage());
        }
    }

    private void updateEmployee() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            double salary = Double.parseDouble(salaryField.getText());
            Department dept = Department.valueOf(departmentChoice.getSelectedItem());
            Role role = Role.valueOf(roleChoice.getSelectedItem());
            manager.updateEmployee(new Employee(id, name, salary, dept, role));
            outputArea.setText("Employee updated.");
        } catch (Exception e) {
            outputArea.setText("Error: " + e.getMessage());
        }
    }

    private void deleteEmployee() {
        try {
            int id = Integer.parseInt(idField.getText());
            manager.deleteEmployee(id);
            outputArea.setText("Employee deleted.");
        } catch (Exception e) {
            outputArea.setText("Error: " + e.getMessage());
        }
    }
}
