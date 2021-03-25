package wyd.mapper;

import wyd.model.Department;

public interface DeptMapper {

    Department getDepartmentById(String id);

    //查询部门，以及该部门下的所有员工信息
    //封装复杂对象中的集合类
    Department getDepartmentByIdPlus(String id);

    //查询部门，以及该部门下的所有员工信息
    //封装复杂对象中的集合类（分步）
    Department getDepartmentByIdStep(String id);
}
