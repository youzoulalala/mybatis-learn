package wyd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Objects;

@Data
//@ToString
@Alias("emp")//这是mybatis别名(不区分大小写)，优先级最高
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable {
    private String id;
    private String lastName;
    private String email;
    private String gender;
    private Department dept;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(lastName, employee.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName);
    }
}
