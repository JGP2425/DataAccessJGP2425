package entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "dept")
public class Department {

    @Id
    @Column(name = "deptno")
    private int deptno;

    @Column(name = "dname")
    private String dname;

    @Column(name = "loc")
    private String loc;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees;

    public int getDeptno() {
        return deptno;
    }

    public void setDeptno(int deptno) {
        this.deptno = deptno;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
