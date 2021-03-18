package wyd.mapper;

import org.apache.ibatis.annotations.Select;
import wyd.model.Employee;

public interface EmployeeMapper {
    /* 可以使用注解的方式, 优点开发便捷，但是复杂的 SQL 语句可能导致混乱不堪
    @Select("        select id, last_name, email, gender\n" +
            "        from employee\n" +
            "        where id = #{id}")
    Employee getEmpById(Integer id);  */

    Employee getEmpById(Integer id);
}
