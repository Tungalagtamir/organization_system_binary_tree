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

    // Basic menu enum
    enum MenuOption {
        SORT, SEARCH, ADD_RECORDS, CREATE_BINARY_TREE, DISPLAY_ALL, EXIT
    }

    public MainOrganization() {
        this.scanner = new Scanner(System.in);
        this.employees = new ArrayList<>();
        this.employeeService = new EmployeeService();
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
