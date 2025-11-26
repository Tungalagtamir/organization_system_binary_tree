package ca_2;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class EmployeeBinaryTree {
    private EmployeeTreeNode root;
    private int nodeCount;

    public EmployeeBinaryTree() {
        this.root = null;
        this.nodeCount = 0;
    }

    // Level-order insertion (breadth-first)
    public void insertLevelOrder(Employee employee) {
        EmployeeTreeNode newNode = new EmployeeTreeNode(employee);

        if (root == null) {
            root = newNode;
            nodeCount++;
            return;
        }

        Queue<EmployeeTreeNode> queue = new LinkedBlockingQueue<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            EmployeeTreeNode current = queue.poll();

            if (current.left == null) {
                current.left = newNode;
                nodeCount++;
                return;
            } else {
                queue.add(current.left);
            }

            if (current.right == null) {
                current.right = newNode;
                nodeCount++;
                return;
            } else {
                queue.add(current.right);
            }
        }
    }

    public int getHeight() {
        return calculateHeight(root);
    }

    private int calculateHeight(EmployeeTreeNode node) {
        if (node == null)
            return 0;
        return 1 + Math.max(calculateHeight(node.left), calculateHeight(node.right));
    }

    public int getNodeCount() {
        return nodeCount;
    }

    public void displayLevelOrder() {
        if (root == null) {
            System.out.println("Tree is empty!");
            return;
        }

        Queue<EmployeeTreeNode> queue = new LinkedBlockingQueue<>();
        queue.add(root);
        int level = 0;

        System.out.println("\n=== Employee Hierarchy (Level Order) ===");

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            System.out.println("\n--- Level " + level + " ---");

            for (int i = 0; i < levelSize; i++) {
                EmployeeTreeNode current = queue.poll();
                System.out.println(current.employee.getDetails());

                if (current.left != null)
                    queue.add(current.left);
                if (current.right != null)
                    queue.add(current.right);
            }
            level++;
        }
    }
}
