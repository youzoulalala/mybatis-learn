package wyd.model;

import lombok.Data;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Data
@ToString
@Alias("emp")//这是mybatis别名，优先级最高
public class Employee {
    private Integer id;
    private String lastName;
    private String email;
    private String gender;
}
