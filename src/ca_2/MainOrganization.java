/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ca_2;

import java.util.*;
import java.io.*;

/**
 *
 * @author tamir
 */

public class MainOrganization {
    private Scanner scanner;
    private List<Employee> employees;
    private EmployeeService employeeService;
    private List<Employee> newlyAddedEmployees;
    private EmployeeBinaryTree employeeTree;

    // Basic menu enum
    enum MenuOption {
        SORT, SEARCH, ADD_RECORDS, CREATE_BINARY_TREE, DISPLAY_ALL, EXIT
    }

    public MainOrganization() {
        this.scanner = new Scanner(System.in);
        this.employees = new ArrayList<>();
        this.employeeService = new EmployeeService();
        this.employeeTree = new EmployeeBinaryTree();
        initializeSystem();
    }

    private void initializeSystem() {
        System.out.println("=== Bank Organization Management System ===");
        while (true) {
            System.out.print("Please enter the filename to read (Applicants_Form.txt): ");
            String filename = scanner.nextLine().trim();

            if (filename.isEmpty()) {
                filename = "Applicants_Form.txt";
            }

            if (loadDataFromFile(filename)) {
                return;
            } else {
                System.out.println("\nFile '" + filename + "' not found! Try again.");
            }
        }
    }

    private boolean loadDataFromFile(String filename) {
        try {
            File file = new File(filename);
            if (!file.exists()) {
                return false;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            Random random = new Random();

            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    Department dept = Department.values()[random.nextInt(Department.values().length)];
                    ManagerType mgrType = ManagerType.values()[random.nextInt(ManagerType.values().length)];
                    employees.add(new Employee(line.trim(), dept, mgrType));
                }
            }
            reader.close();
            System.out.println("File '" + filename + "' read successfully! Loaded " + employees.size() + " employees.");
            return true;

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return false;
        }
    }

    public void sortAndDisplay() {
        System.out.println("\n=== Sorting Employees (Using Merge Sort) ===");

        if (employees.isEmpty()) {
            System.out.println("No employees to sort.");
            return;
        }

        List<Employee> sortedEmployees = employeeService.mergeSort(employees);

        System.out.println("\nFirst 20 sorted employee names:");
        System.out.println("================================");
        for (int i = 0; i < Math.min(20, sortedEmployees.size()); i++) {
            System.out.printf("%2d. %s\n", (i + 1), sortedEmployees.get(i).getName());
        }

        employees = sortedEmployees;
    }

    public void searchEmployee() {
        System.out.println("\n=== Search Employee (Using Binary Search) ===");

        if (employees.isEmpty()) {
            System.out.println("No employees to search. Please load data first.");
            return;
        }
        List<Employee> sortedEmployees = employeeService.mergeSort(employees);
        employees = sortedEmployees;

        System.out.print("Enter employee name to search: ");
        String searchName = scanner.nextLine();

        Employee result = employeeService.binarySearch(employees, searchName);

        if (result != null) {
            System.out.println("\n Employee Found!");
            System.out.println("====================");
            System.out.println(result.getDetails());
        } else {
            System.out.println("\n Employee '" + searchName + "' not found in the system.");
        }
    }

    public void addNewEmployee() {
        System.out.println("\n=== Add New Bank Employee ===");

        // Get employee name
        System.out.print("Enter employee full name: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Invalid name! Please enter a valid name.");
            return;
        }

        // Display and get manager type
        System.out.println("\nAvailable Manager Types:");
        for (int i = 0; i < ManagerType.values().length; i++) {
            ManagerType type = ManagerType.values()[i];
            System.out.printf("%d. %s - %s (Level %d)\n",
                    i + 1, type, type.getDescription(), type.getHierarchyLevel());
        }
        System.out.print("Choose manager type (1-" + ManagerType.values().length + "): ");

        ManagerType managerType;
        try {
            int mgrChoice = Integer.parseInt(scanner.nextLine());
            managerType = ManagerType.values()[mgrChoice - 1];
        } catch (Exception e) {
            System.out.println("Invalid manager type selection!");
            return;
        }

        // Display and get department
        System.out.println("\nAvailable Departments:");
        for (int i = 0; i < Department.values().length; i++) {
            Department dept = Department.values()[i];
            System.out.printf("%d. %s - %s\n", i + 1, dept.getFullName(), dept.getDescription());
        }
        System.out.print("Choose department (1-" + Department.values().length + "): ");

        Department department;
        try {
            int deptChoice = Integer.parseInt(scanner.nextLine());
            department = Department.values()[deptChoice - 1];
        } catch (Exception e) {
            System.out.println("Invalid department selection!");
            return;
        }

        // Create and add new employee
        Employee newEmployee = new Employee(name, department, managerType);
        employees.add(newEmployee);
        newlyAddedEmployees.add(newEmployee);

        System.out.println("\n Employee added successfully!");
        System.out.println(newEmployee.getDetails());
    }

    public void displayMenu() {
        System.out.println("\n=== Bank Organization System Menu ===");
        int optionNumber = 1;
        for (MenuOption option : MenuOption.values()) {
            System.out.println(optionNumber + ". " + formatMenuOption(option));
            optionNumber++;
        }
        System.out.print("Choose an option: ");
    }

    private String formatMenuOption(MenuOption option) {
        switch (option) {
            case SORT:
                return "Sort Employee List";
            case SEARCH:
                return "Search Employee";
            case ADD_RECORDS:
                return "Add New Employee";
            case CREATE_BINARY_TREE:
                return "Create Employee Hierarchy Tree";
            case DISPLAY_ALL:
                return "Display All Employees";
            case EXIT:
                return "Exit System";
            default:
                return option.toString();
        }
    }

    public void run() {
        System.out.println("\n Bank Organization System Started!");
        System.out.println("Managing: " + employees.size() + " employees");

        while (true) {
            try {
                displayMenu();
                String input = scanner.nextLine();

                if (input.isEmpty()) {
                    System.out.println("Please enter a valid option number.");
                    continue;
                }

                int choice = Integer.parseInt(input);
                MenuOption[] options = MenuOption.values();

                if (choice < 1 || choice > options.length) {
                    System.out.println("Invalid option! Please choose between 1 and " + options.length);
                    continue;
                }

                MenuOption selectedOption = options[choice - 1];

                switch (selectedOption) {
                    case SORT:
                        sortAndDisplay();
                        break;
                    case SEARCH:
                        searchEmployee();
                        break;
                    case ADD_RECORDS:
                        addNewEmployee();
                        break;
                    case DISPLAY_ALL:
                        displayAllEmployees();
                        break;
                    case EXIT:
                        System.out.println("Thank you for using Bank Organization System! Goodbye!");
                        return;
                    default:
                        System.out.println("Feature not implemented yet - selected: " + selectedOption);
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }

    public void displayAllEmployees() {
        System.out.println("\n=== All Employees (" + employees.size() + ") ===");
        if (employees.isEmpty()) {
            System.out.println("No employees in the system.");
            return;
        }

        for (Employee emp : employees) {
            System.out.println("  " + emp.getDetails());
        }
    }

    public static void main(String[] args) {
        MainOrganization system = new MainOrganization();
        system.run();
    }
}
