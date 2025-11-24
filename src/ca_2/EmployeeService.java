package ca_2;

import java.util.ArrayList;
import java.util.List;

public class EmployeeService {

    public List<Employee> mergeSort(List<Employee> employees) {
        if (employees.size() <= 1) {
            return employees;
        }

        int mid = employees.size() / 2;
        List<Employee> left = mergeSort(new ArrayList<>(employees.subList(0, mid)));
        List<Employee> right = mergeSort(new ArrayList<>(employees.subList(mid, employees.size())));

        return merge(left, right);
    }

    private List<Employee> merge(List<Employee> left, List<Employee> right) {
        List<Employee> merged = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            if (left.get(i).getName().compareToIgnoreCase(right.get(j).getName()) <= 0) {
                merged.add(left.get(i++));
            } else {
                merged.add(right.get(j++));
            }
        }

        while (i < left.size())
            merged.add(left.get(i++));
        while (j < right.size())
            merged.add(right.get(j++));

        return merged;
    }

}
