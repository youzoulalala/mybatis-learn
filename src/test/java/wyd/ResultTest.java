package wyd;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import wyd.mapper.DeptMapper;
import wyd.mapper.EmployeeMapper;
import wyd.model.Department;
import wyd.model.Employee;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

/**
 * 返回值测试
 */
public class ResultTest {

    private SqlSessionFactory getSqlSessionFactory(String mybatisConf) throws IOException {
        final InputStream resourceAsStream = Resources.getResourceAsStream(mybatisConf);
        final SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();//方法作用域
        final SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(resourceAsStream);//应用作用域(单例)
        return sqlSessionFactory;
    }

    /**
     * 返回结果为集合
     * @throws IOException
     */
    @Test
    public void testReturnSet() throws IOException {
        String mybatisConfURL = "mybatis-config.xml";
        final SqlSessionFactory sqlSessionFactory = getSqlSessionFactory(mybatisConfURL);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);//获取代理对象
            Set<Employee> e = employeeMapper.getEmpTotal();
            System.out.println(e);
        }
    }

    /**
     * 返回map
     * key: 列名
     * value: 列值
     * @throws IOException
     */
    @Test
    public void testReturnMap() throws IOException {
        String mybatisConfURL = "mybatis-config.xml";
        final SqlSessionFactory sqlSessionFactory = getSqlSessionFactory(mybatisConfURL);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);//获取代理对象
            Map<String, Object> m = employeeMapper.getEmpByIdReturnMap("1");
            System.out.println(m);
        }
    }

    /**
     * 返回 map
     * key: 对象主键
     * value: 对象
     * @throws IOException
     */
    @Test
    public void testReturnMapObject() throws IOException {
        String mybatisConfURL = "mybatis-config.xml";
        final SqlSessionFactory sqlSessionFactory = getSqlSessionFactory(mybatisConfURL);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);//获取代理对象
            Map<String, Employee> m = employeeMapper.getEmpByLikeNameReturnMap("郭德纲");
            System.out.println(m);
        }
    }

    /**
     * 封装复杂对象类型
     * 平铺方式
     * @throws IOException
     */
    @Test
    public void testReturnComplexObject01() throws IOException {
        String mybatisConfURL = "mybatis-config.xml";
        final SqlSessionFactory sqlSessionFactory = getSqlSessionFactory(mybatisConfURL);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);//获取代理对象
            Employee e = employeeMapper.getEmpAndDeptById("1");
            System.out.println(e);
        }
    }

    /**
     * 封装复杂对象类型
     * 分步方式
     * @throws IOException
     */
    @Test
    public void testReturnComplexObject02() throws IOException {
        String mybatisConfURL = "mybatis-config.xml";
        final SqlSessionFactory sqlSessionFactory = getSqlSessionFactory(mybatisConfURL);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);//获取代理对象
            Employee emp = employeeMapper.getEmpByStep("1");
            System.out.println(emp.getLastName());
        }
    }

    /**
     * 封装复杂对象类型
     * 对象中的集合类型
     * @throws IOException
     */
    @Test
    public void testReturnComplexObjectCollection() throws IOException {
        String mybatisConfURL = "mybatis-config.xml";
        final SqlSessionFactory sqlSessionFactory = getSqlSessionFactory(mybatisConfURL);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            DeptMapper deptMapper = sqlSession.getMapper(DeptMapper.class);//获取代理对象
            Department dept = deptMapper.getDepartmentByIdPlus("1");
            System.out.println(dept);
        }
    }

    /**
     * 封装复杂对象类型
     * 对象中的集合类型（分步）
     * @throws IOException
     */
    @Test
    public void testReturnComplexObjectCollectionStep() throws IOException {
        String mybatisConfURL = "mybatis-config.xml";
        final SqlSessionFactory sqlSessionFactory = getSqlSessionFactory(mybatisConfURL);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            DeptMapper deptMapper = sqlSession.getMapper(DeptMapper.class);//获取代理对象
            Department dept = deptMapper.getDepartmentByIdStep("1");
            System.out.println(dept);
        }
    }
}
