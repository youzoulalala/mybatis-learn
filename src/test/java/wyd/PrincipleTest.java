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

/**
 * 源码测试
 */
public class PrincipleTest {

    /**
     * 创建对象，sqlSessionFactoryBuilder
     * 调用方法 sqlSessionFactoryBuilder.build(resourceAsStream)
     * XMLConfigBuilder parser
     *
     * @param mybatisConf
     * @return
     * @throws IOException
     */
    private SqlSessionFactory getSqlSessionFactory(String mybatisConf) throws IOException {
        final InputStream resourceAsStream = Resources.getResourceAsStream(mybatisConf);
        final SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();//方法作用域
        final SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(resourceAsStream);//应用作用域(单例)
        return sqlSessionFactory;
    }

    @Test
    public void testSelect() throws IOException {
        String mybatisConf = "mybatis-config.xml";//java, resource 为类路径的根
        final SqlSessionFactory sqlSessionFactory = getSqlSessionFactory(mybatisConf);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {//非线程安全,每次使用时获取（请求或方法作用域）（每次收到 HTTP 请求，就可以打开一个 SqlSession，返回一个响应后，就关闭它）
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);//方法作用域
            System.out.println(mapper);//mybatis 提供一个代理对象（将接口和xml进行绑定）
            Employee e = mapper.getEmpById("1");
            System.out.println(e);
        }
    }
}
