package ca_2;

public class EmployeeTreeNode {

    Employee employee;
    EmployeeTreeNode left;
    EmployeeTreeNode right;

    public EmployeeTreeNode(Employee employee) {
        this.employee = employee;
        this.left = null;
        this.right = null;
    }
}
