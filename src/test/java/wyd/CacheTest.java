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
import java.util.Set;

/**
 * 两级缓存:
 * 一级缓存:(本地缓存, sqlSession级别)
 *      与数据库同一次会话期间,查询到的数据会放到本地缓存中,以后从本地获取相同的数据
 *
 * 二级缓存:(全局缓存, namespace级别)
 *
 * 缓存顺序: 二级缓存, 一级缓存
 */
public class CacheTest {

    private SqlSessionFactory getSqlSessionFactory(String mybatisConf) throws IOException {
        final InputStream resourceAsStream = Resources.getResourceAsStream(mybatisConf);
        final SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();//方法作用域
        final SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(resourceAsStream);//应用作用域(单例)
        return sqlSessionFactory;
    }

    /**
     * 一级缓存
     * 默认开启,但是可以clearCache清空(只清空一级缓存),sqlSession级别的缓存(map),不同的sqlSession 不能共用缓存
     * 对于同一次会话中,相同的查询逻辑,mybatis从本地获取相同的数据,而不会从数据库中查询并封装对象
     *
     * 一级缓存失效的情况:
     * 1 不同的sqlSession
     * 2 不同的查询逻辑
     * 3 两次查询期间执行了增删改(可能改变当前数据)
     * 4 手动清空一级缓存
     * @throws IOException
     */
    @Test
    public void testCacheFirst() throws IOException {
        String mybatisConfURL = "mybatis-config.xml";
        final SqlSessionFactory sqlSessionFactory = getSqlSessionFactory(mybatisConfURL);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);//获取代理对象
            Employee e1 = employeeMapper.getEmpById("1");
//            sqlSession.clearCache();//清空一级缓存，此时一级缓存失效
//            employeeMapper.addEmp(new Employee("dashjkfghsad","郭富城",null,null,null));//有增删改操作，一级缓存失效
            Employee e2 = employeeMapper.getEmpById("1");//由于一级缓存，相同的sqlSession, mapper, parameters 不会再向数据库发送sql
            System.out.println(e1);
        }
    }

    /**
     * 二级缓存(全局缓存, 基于 namespace 级别,一个 namespace 对于一个二级缓存)
     * 工作机制:
     * 1 一个会话,查询一条数据,该数据就会被放在当前会话的一级缓存中
     * 2 如果会话"关闭"或"提交"(注意),一级缓存会被保存在二级缓存中.新会话可以参照二级缓存
     * 3 不同的二级缓存会保存在各自的 namespace 中
     *
     * 使用方式:
     * 1 mybatis-config.xml 开启全局二级缓存
     * 2 mapper 中配置二级缓存 <cache></cache>
     * 3 select 标签中的属性 useCache
     * 4 每个增删改 标签中的属性 flushCache (清空一 二级缓存)
     * 5 pojo 实现序列化接口
     *
     * localCacheScope: 本地缓存作业域 SESSION 一级缓存存在会话中, STATEMENT 关闭一级缓存
     */
    @Test
    public void testCacheSecond() throws IOException {
        String mybatisConfURL = "mybatis-config.xml";
        final SqlSessionFactory sqlSessionFactory = getSqlSessionFactory(mybatisConfURL);
        try (SqlSession sqlSession01 = sqlSessionFactory.openSession()) {//sqlSession01
            EmployeeMapper employeeMapper = sqlSession01.getMapper(EmployeeMapper.class);//获取代理对象
            Employee e1 = employeeMapper.getEmpById("1");
            System.out.println(e1);
        }
        try (SqlSession sqlSession02 = sqlSessionFactory.openSession()) {//sqlSession01
            EmployeeMapper employeeMapper = sqlSession02.getMapper(EmployeeMapper.class);//获取代理对象
            Employee e1 = employeeMapper.getEmpById("1");//由于二级缓存，将不会再次从数据库中查询
            System.out.println(e1);
        }
    }


    /**
     * 二级缓存相关配置：
     * mybatis-config.xml -  lazyLoadingEnabled 全局开关设置;
     * cache 标签中的相关设置;
     * select 标签中的 useCache, 该方法是否使用二级缓存;
     * update delete insert 标签中的 flushCache,是否清空"一 二级"缓存;
     */
    @Test
    public void test03(){

    }

    /**
     * 第三方缓存,ehcache
     * ehcache, slf4j-api, slf4j-log4f12
     *
     * 1 第三方缓存包
     * 2 适配包
     * 3 配置文件
     * 4 设置缓存
     */
    @Test
    public void test04(){

    }
}
