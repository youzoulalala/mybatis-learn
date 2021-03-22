package wyd.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import wyd.dto.PageInfoDTO;
import wyd.model.Department;
import wyd.model.Employee;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface EmployeeMapper {
    /* 可以使用注解的方式, 优点开发便捷，但是复杂的 SQL 语句可能导致混乱不堪
    @Select("        select id, last_name, email, gender\n" +
            "        from employee\n" +
            "        where id = #{id}")
    Employee getEmpById(Integer id);  */

    Employee getEmpById(Integer id);

    List<Employee> getEmpByDeptId(Integer did);

    Set<Employee> getEmpTotal();

    /**
     * 新增
     *
     * @param e
     */
    boolean addEmp(Employee e);

    /**
     * 更新
     *
     * @param e
     */
    void updateEmp(Employee e);

    /**
     * 删除
     *
     * @param e
     */
    void deleteEmp(Employee e);


    /**
     * 根据 id lastName 查询员工信息
     */
    //Param 注解明确使用参数名称（map中key的名称）
    //此时参数Map中的key: [lastName, id, param1, param2]
    //=======mapper方法参数封装的源码=======
    /*
     public Object convertArgsToSqlCommandParam(Object[] args) {
            int paramCount = this.params.size();
            if (args != null && paramCount != 0) {
                if (!this.hasNamedParameters && paramCount == 1) {//有且只有一个参数
                    return args[(Integer)this.params.keySet().iterator().next()];
                } else {
                    Map<String, Object> param = new MapperMethod.ParamMap();
                    int i = 0;

                    for(Iterator i$ = this.params.entrySet().iterator(); i$.hasNext(); ++i) {//多个参数
                        Entry<Integer, String> entry = (Entry)i$.next();
                        param.put(entry.getValue(), args[(Integer)entry.getKey()]);
                        String genericParamName = "param" + String.valueOf(i + 1);
                        if (!param.containsKey(genericParamName)) {
                            param.put(genericParamName, args[(Integer)entry.getKey()]);
                        }
                    }

                    return param;
                }
            } else {//无参数
                return null;
            }
        }
     */
    Employee getEmpByIdAndLastName(@Param("id") String id, @Param("lastName") String lastName);


    //对于查询对象不是 POJO，并且不经常使用，可以使用Map
    //传入的 Map ，相当于 getEmpByIdAndLastName 中组成的参数Map
    Employee getEmpByMap(Map<String, Object> map);

    //对于查询对象不是 POJO，并且经常使用，可以使用DTO
    Employee getEmpByDTO(PageInfoDTO pageInfoDTO);

    //======== 参数类型是集合类: List, Set, 数组 =======
    //封装为map的key：Set:collection, List：list, 数组:array
    Employee getEmpByList(@Param("employeeList") List<Employee> employeeList,@Param("tableName") String tableName);

    //返回集合
    List<Employee> getEmpLike(String likeName);

    //返回map（key 无序）
    //key 为列名
    //value 为数据
    Map<String, Object> getEmpByIdReturnMap(String id);

    @MapKey("id")//告诉mybatis将哪个字段作为map的key
    Map<Integer, Employee> getEmpByLikeNameReturnMap(String likeName);

    Employee getEmpAndDeptById (String id);

    Department getDepartmentById(String id);

    Department getDepartmentByIdPlus(String id);

    Department getDepartmentByIdStep(String id);


    Employee getEmpByStep(String id);

    Employee getEmpByCondition(Employee employee);

    Employee getEmpByCondition2(Employee employee);

    Employee getEmpByCondition3(Employee employee);

    void addEmps(@Param("emps") List<Employee> emps);
}
