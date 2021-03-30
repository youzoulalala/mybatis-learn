package wyd.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Department implements Serializable {
    private String id;
    private String deptName;
    private List<Employee> employeeList;
}
