package wyd.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import wyd.dto.PageInfoDTO;
import wyd.model.Employee;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface EmployeeMapper {

    //==============快速开始===============
    /* 可以使用注解的方式, 优点开发便捷，但是复杂的 SQL 语句可能导致混乱不堪
    @Select("        select id, last_name, email, gender\n" +
            "        from employee\n" +
            "        where id = #{id}")
    Employee getEmpById(Integer id);  */

    Employee getEmpById(Integer id);

    List<Employee> getEmpByDeptId(Integer did);

    Set<Employee> getEmpTotal();

    boolean addEmp(Employee e);

    void updateEmp(Employee e);

    void deleteEmpById(Employee e);



    //==============参数===============
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

    // 多个参数时，mybatis 会将参数列表映射为map;
    // 不通过注解 @Param 指定别名，则map.keySet() = [1, 2, param1, param2]
    // 通过注解 @Param 指定别名，则map.keySet() = [id, lastName, param1, param2]
    Employee getEmpByIdAndLastName(@Param("id") String id, @Param("lastName") String lastName);

    //对于查询对象不是 POJO，并且不经常使用，可以使用Map (对于经常使用的查询条件，应当封装为 DTO)
    //传入的 Map ，相当于 getEmpByIdAndLastName 中组成的参数Map
    //sql 语句中通过 #{keyName} ${keyName} 获取参数值
    Employee getEmpByMap(Map<String, Object> map);

    //对于查询对象不是 POJO，并且经常使用，可以使用DTO
    List<Employee> getEmpByDTO(PageInfoDTO pageInfoDTO);

    //======== 参数类型是集合类: List, Set, 数组 =======
    //封装为map的key：Set:collection, List：list, 数组:array
    Employee getEmpByList(@Param("employeeList") List<Employee> employeeList, @Param("tableName") String tableName);




    //==============返回值===============
    //返回集合， resultType为集合中存储对象的类型
    List<Employee> getEmpLike(String likeName);

    //返回map（key 无序）
    //key 为列名
    //value 为数据
    Map<String, Object> getEmpByIdReturnMap(String id);

    //该返回结果便于后续处理
    @MapKey("id")//告诉mybatis将哪个字段作为map的key
    Map<String, Employee> getEmpByLikeNameReturnMap(@Param("likeName") String likeName);

    //平铺、层级封装
    Employee getEmpAndDeptById(String id);

    //分步封装（执行两次SQL,可配置懒加载）
    Employee getEmpByStep(String id);



    //===============动态sql================
    List<Employee> getEmpByCondition(Employee employee);

    List<Employee> getEmpByCondition2(Employee employee);

    List<Employee> getEmpByCondition3(Employee employee);

    //批量添加 begin-end
    void addEmps(@Param("emps") List<Employee> emps);

    void addEmps2(@Param("emps") List<Employee> emps);
}
