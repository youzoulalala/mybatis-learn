package wyd;

import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Assert;
import org.junit.Test;
import wyd.dto.PageInfoDTO;
import wyd.mapper.EmployeeMapper;
import wyd.model.Employee;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

public class MybatisTest {
    //Mybatis https://blog.csdn.net/u011863024/article/details/107854866
    //1.持久层框架
    //2.定制化SQL，避免了几乎所有的 JDBC 代码和手动设置参数以及获取结果集
    //3.Mybatis的优势：SQL与Java代码分离，将SQL写到一个XML文件中，与JAVA代码解耦（Hibernate框架，不易处理复杂查询，不易优化，HQL学习成本高）

    //下载链接（github托管）：https://github.com/mybatis/mybatis-3/releases

    /*
        1、全局配置文件：数据源，事务等等
        2、sql映射文件：配置sql语句，以及封装规则
        3、将sql映射文件注册到全局配置文件中
        4、写代码
            4.1 根据全局配置文件创建 sqlSessionFactory
            4.2 根据 sqlSessionFactory 获取 SqlSession
            4.3 使用唯一标志（namespace.id）来告诉MyBatis执行哪个sql
     */

    private SqlSessionFactory getSqlSessionFactory(String mybatisConf) throws IOException {
        //都是以一个 SqlSessionFactory 的实例为核心的
        //Mybatis所提供的工具类，内置大量实用方法，可以类路径或其它位置加载资源文件
        final InputStream resourceAsStream = Resources.getResourceAsStream(mybatisConf);
        final SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();//方法作用域
        /*
        Properties properties = new Properties();
        properties.load(Resources.getResourceAsStream("dbconfig.properties"));
        final SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(resourceAsStream, properties);//应用作用域(单例)
         */
        final SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(resourceAsStream);//应用作用域(单例)
        return sqlSessionFactory;
    }

    /**
     * 通过java代码配置
     *
     * @throws IOException
     */
    private SqlSessionFactory getSqlSessionFactoryByJava(String mybatisConf) throws IOException {
        Properties properties = new Properties();
        properties.load(Resources.getResourceAsStream(mybatisConf));
        //获取数据源对象
        PooledDataSourceFactory pooledDataSourceFactory = new PooledDataSourceFactory();
        pooledDataSourceFactory.setProperties(properties);
        DataSource dataSource = pooledDataSourceFactory.getDataSource();
        //设置事务类型
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        //Environment
        Environment environment = new Environment("development", transactionFactory, dataSource);
        //Configuration
        Configuration configuration = new Configuration(environment);
        //Mapper
        configuration.addMapper(EmployeeMapper.class);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        return sqlSessionFactory;
    }

    @Test
    public void test01() throws IOException {
        String mybatisConf = "mybatis-config.xml";//java, resource 为类路径的根
        final SqlSessionFactory sqlSessionFactory = getSqlSessionFactory(mybatisConf);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {//非线程安全,每次使用时获取（请求或方法作用域）（每次收到 HTTP 请求，就可以打开一个 SqlSession，返回一个响应后，就关闭它）
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);//方法作用域
            System.out.println(mapper);//mybatis 提供一个代理对象（将接口和xml进行绑定）
            Employee e = mapper.getEmpById(1);
            System.out.println(e);
        }
    }

    @Test
    public void testAdd() throws IOException {
        String mybatisConfURL = "mybatis-config.xml";
        final SqlSessionFactory sqlSessionFactory = getSqlSessionFactory(mybatisConfURL);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {// SqlSession openSession(boolean autoCommit);可设置为自动提交
            EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);//获取代理对象
            Employee e = new Employee(null, "郭德纲", "gdg@123.com", "男", null);
            boolean res = employeeMapper.addEmp(e);
            sqlSession.commit();
            System.out.println(e);
        }
    }

    @Test
    public void testParam() throws IOException {
        String mybatisConfURL = "mybatis-config.xml";
        final SqlSessionFactory sqlSessionFactory = getSqlSessionFactory(mybatisConfURL);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);//获取代理对象
            Employee e = employeeMapper.getEmpByIdAndLastName("1", "Leborn James");
            sqlSession.commit();
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
            Employee e = employeeMapper.getEmpByDTO(new PageInfoDTO(2,3));
            sqlSession.commit();
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

    @Test
    public void testReturnSet() throws IOException {
        String mybatisConfURL = "mybatis-config.xml";
        final SqlSessionFactory sqlSessionFactory = getSqlSessionFactory(mybatisConfURL);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);//获取代理对象
            Set<Employee> e = employeeMapper.getEmpTotal();
            sqlSession.commit();
            System.out.println(e);
        }
    }
}
