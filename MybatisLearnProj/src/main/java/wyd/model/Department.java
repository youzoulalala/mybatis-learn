package wyd.model;

import lombok.Data;

import java.util.List;

@Data
public class Department {
    private String id;
    private String deptName;
    private List<Employee> employeeList;
}
