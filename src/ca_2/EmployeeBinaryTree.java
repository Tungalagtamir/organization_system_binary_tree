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
}
