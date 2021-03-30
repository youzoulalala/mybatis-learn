package wyd;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wyd.mapper.EmployeeMapper;
import wyd.model.Employee;

import java.io.IOException;
import java.util.Set;

/**
 * spring 整合 mybatis
 */

@RunWith(SpringJUnit4ClassRunner.class)//使用改类启动spring容器
@ContextConfiguration(locations= "classpath:mybatis-spring/ApplicationContext.xml")//加载对应的配置
public class SpringMybatisTest {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * mybatis spring 整合
     */
    @Test
    public void testMybatisSpring() throws IOException {
        Set<Employee> empTotal = employeeMapper.getEmpTotal();
        System.out.println(empTotal);
    }
}
