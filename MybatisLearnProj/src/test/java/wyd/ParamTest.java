package wyd;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import wyd.dto.PageInfoDTO;
import wyd.mapper.EmployeeMapper;
import wyd.model.Employee;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试参数列表
 */
public class ParamTest {
    private SqlSessionFactory getSqlSessionFactory(String mybatisConf) throws IOException {
        final InputStream resourceAsStream = Resources.getResourceAsStream(mybatisConf);
        final SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();//方法作用域
        final SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(resourceAsStream);//应用作用域(单例)
        return sqlSessionFactory;
    }

    @Test
    public void testParam() throws IOException {
        String mybatisConfURL = "mybatis-config.xml";
        final SqlSessionFactory sqlSessionFactory = getSqlSessionFactory(mybatisConfURL);
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);//获取代理对象
            Employee e = employeeMapper.getEmpByIdAndLastName("1", "Leborn James");
            System.out.println(e);
        }
    }

    @Test
    public void testParamMap() throws IOException {
        String mybatisConfURL = "mybatis-config.xml";
        final SqlSessionFactory sqlSessionFactory = getSqlSessionFactory(mybatisConfURL);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);//获取代理对象
            Map<String, Object> map = new HashMap<>();
            map.put("id", "1");
            map.put("lastName", "Leborn James");
            Employee e = employeeMapper.getEmpByMap(map);
            sqlSession.commit();
            System.out.println(e);
        }
    }

    @Test
    public void testParamDTO() throws IOException {
        String mybatisConfURL = "mybatis-config.xml";
        final SqlSessionFactory sqlSessionFactory = getSqlSessionFactory(mybatisConfURL);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);//获取代理对象
            List<Employee> e = employeeMapper.getEmpByDTO(new PageInfoDTO(2,3));
            System.out.println(e);
        }
    }

    @Test
    public void testParamList() throws IOException {
        String mybatisConfURL = "mybatis-config.xml";
        final SqlSessionFactory sqlSessionFactory = getSqlSessionFactory(mybatisConfURL);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);//获取代理对象
            List<Employee> employeeList = new ArrayList<>();
            employeeList.add(new Employee("1", null, null, null, null));
            employeeList.add(new Employee("2", null, null, null, null));

            Employee e = employeeMapper.getEmpByList(employeeList, "employee");
            sqlSession.commit();
            System.out.println(e);
        }
    }
}
