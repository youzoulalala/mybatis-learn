package wyd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Data
//@ToString
@Alias("emp")//这是mybatis别名(不区分大小写)，优先级最高
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private String id;
    private String lastName;
    private String email;
    private String gender;
    private Department dept;
}
