package com.fullstack.dao;

import com.fullstack.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

@Component
public class EmployeeDaoImpl implements IEmployeeDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    String INSERT_SQL = "insert into employee(empid, empname, empcontactnumber, empsalary, empemailid, emppassword) values(?, ?, ?, ?, ?, ?)";

    String SELECT_ALL_SQL = "select * from employee";

    String SELECT_SQL_BY_ID = "select * from employee where empid=?";

    String UPDATE_SQL = "update employee set empname=?, empcontactnumber=?, empsalary=?, empemailid=?, emppassword=? where empid=?";

    String DELETE_BY_ID_SQL = "delete from employee where empid=?";

    private Employee employee(ResultSet resultSet, int numRow) throws SQLException {
        return Employee.builder().empId(resultSet.getInt(1)).empName(resultSet.getString(2)).empContactNumber(resultSet.getLong(3)).empSalary(resultSet.getDouble(4)).empEmailId(resultSet.getString(5)).empPassword(resultSet.getString(6)).build();
    }

    @Override
    public void signUp(Employee employee) {

        jdbcTemplate.update(INSERT_SQL, employee.getEmpId(), employee.getEmpName(), employee.getEmpContactNumber(), employee.getEmpSalary(), employee.getEmpEmailId(), employee.getEmpPassword());


    }

    @Override
    public boolean signIn(String empEmailId, String empPassword) {

        boolean flag = false;

        for (Employee employee : findAll()) {
            if (employee.getEmpEmailId().equals(empEmailId) && employee.getEmpPassword().equals(empPassword)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    @Override
    public Employee findById(int empId) {
        return jdbcTemplate.query(SELECT_SQL_BY_ID, this::employee, empId).get(0);
    }

    @Override
    public List<Employee> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, this::employee);
    }

    @Override
    public void update(int empId, Employee employee) {

        jdbcTemplate.update(UPDATE_SQL, employee.getEmpName(), employee.getEmpContactNumber(), employee.getEmpSalary(), employee.getEmpEmailId(), employee.getEmpPassword(), empId);
    }

    @Override
    public void deleteById(int empId) {
        jdbcTemplate.update(DELETE_BY_ID_SQL, empId);
    }
}
