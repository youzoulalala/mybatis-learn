package wyd;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import wyd.mapper.EmployeeMapper;
import wyd.model.Employee;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * 动态sql测试
 */
public class DynamicSqlTest {

    private SqlSessionFactory getSqlSessionFactory(String mybatisConf) throws IOException {
        final InputStream resourceAsStream = Resources.getResourceAsStream(mybatisConf);
        final SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();//方法作用域
        final SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(resourceAsStream);//应用作用域(单例)
        return sqlSessionFactory;
    }

    /**
     * 标签：
     * where
     * if
     * sql
     * include
     *
     * @throws IOException
     */
    @Test
    public void testDynamicSql() throws IOException {
        String mybatisConfURL = "mybatis-config.xml";
        final SqlSessionFactory sqlSessionFactory = getSqlSessionFactory(mybatisConfURL);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);//获取代理对象
            final Employee employee = new Employee("1", null, null, null, null);
            List<Employee> emps = employeeMapper.getEmpByCondition(employee);
            System.out.println(emps);
        }
    }

    /**
     * 标签
     * trim
     *
     * @throws IOException
     */
    @Test
    public void testDynamicSql2() throws IOException {
        String mybatisConfURL = "mybatis-config.xml";
        final SqlSessionFactory sqlSessionFactory = getSqlSessionFactory(mybatisConfURL);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);//获取代理对象
            final Employee employee = new Employee("1", null, null, null, null);
            List<Employee> emps = employeeMapper.getEmpByCondition2(employee);
            System.out.println(emps);
        }
    }

    /**
     * 标签
     * choose
     *
     * @throws IOException
     */
    @Test
    public void testDynamicSql3() throws IOException {
        String mybatisConfURL = "mybatis-config.xml";
        final SqlSessionFactory sqlSessionFactory = getSqlSessionFactory(mybatisConfURL);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);//获取代理对象
            final Employee employee = new Employee("1", null, null, null, null);
            List<Employee> emps = employeeMapper.getEmpByCondition3(employee);
            System.out.println(emps);
        }
    }

    /**
     * 批量添加 begin end
     *
     * 标签
     * set (去除最后的“,”  可以用 trim 代替)
     * foreach
     *
     * @throws IOException
     */
    @Test
    public void testDynamicAddBatch() throws IOException {
        String mybatisConfURL = "mybatis-config.xml";
        final SqlSessionFactory sqlSessionFactory = getSqlSessionFactory(mybatisConfURL);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);//获取代理对象
            final Employee employee1 = new Employee(UUID.randomUUID().toString(), null, null, null, null);
            final Employee employee2 = new Employee(UUID.randomUUID().toString(), null, null, null, null);
            final Employee employee3 = new Employee(UUID.randomUUID().toString(), null, null, null, null);
            employeeMapper.addEmps(Arrays.asList(employee1, employee2, employee3));
            sqlSession.commit();
        }
    }

    /**
     * 批量添加 insert select
     *
     * 标签
     * set (去除最后的“,”  可以用 trim 代替)
     * foreach
     *
     * @throws IOException
     */
    @Test
    public void testDynamicAddBatchSelect() throws IOException {
        String mybatisConfURL = "mybatis-config.xml";
        final SqlSessionFactory sqlSessionFactory = getSqlSessionFactory(mybatisConfURL);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);//获取代理对象
            final Employee employee1 = new Employee(UUID.randomUUID().toString(), "111", "111@123.com", null, null);
            final Employee employee2 = new Employee(UUID.randomUUID().toString(), "222", "222@123.com", null, null);
            final Employee employee3 = new Employee(UUID.randomUUID().toString(), "333", "333@123.com", null, null);
            employeeMapper.addEmps2(Arrays.asList(employee1, employee2, employee3));
            sqlSession.commit();
        }
    }
}
