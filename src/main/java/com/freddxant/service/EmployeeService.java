package com.freddxant.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.freddxant.common.AppServer;
import com.freddxant.model.request.EmployeeReq;
import com.freddxant.model.response.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmployeeService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ResponseModel getEmployee() throws SQLException {
        ResponseModel res = new ResponseModel();
        Connection con = null;
        CallableStatement call = null;

        try {
            con = jdbcTemplate.getDataSource().getConnection();
            con.setAutoCommit(false);
            String sql = "";
            sql = "{ ? = call LATIHANSQL_DEV.pkg_employee.get_all_employee }";
            call = con.prepareCall(sql);
            call.registerOutParameter(1, OracleTypes.CURSOR);
            call.execute();

            ResultSet rs = (ResultSet) call.getObject(1);
            List<Map<String, String>> lmap = AppServer.convertResultsetToListStr(rs);

            call.close();
            con.close();

            if (!lmap.isEmpty()) {
                res = new ResponseModel(200, "OK", "Sukses", lmap);
            } else {
                res = new ResponseModel(404, "NOT_FOUND", "Employee not found", null);
            }

            log.info("GET EMPLOYEE");
        } catch (Exception ex) {
            log.error("ERROR GET EMPLOYEE : {}", ex.getLocalizedMessage());
            throw new RuntimeException("ERROR GET EMPLOYEE : " + ex.getLocalizedMessage());
        } finally {
            call.close();
            con.close();
        }

        return res;
    }

    public ResponseModel insertEmployee(EmployeeReq employeeReq) throws SQLException {
        ResponseModel res = new ResponseModel();
        Connection con = null;
        CallableStatement call = null;

        try {
            con = jdbcTemplate.getDataSource().getConnection();
            con.setAutoCommit(false);
            String sql = "";
            sql = "{ ? = call LATIHANSQL_DEV.pkg_employee.insert_employee(?,?,?) }";
            call = con.prepareCall(sql);
            call.registerOutParameter(1, OracleTypes.NUMBER);
            call.setString(2, employeeReq.getName());
            call.setString(3, employeeReq.getEmail());
            call.registerOutParameter(4, OracleTypes.VARCHAR);
            call.execute();

            if (call.getInt(1) == 1) {
                res = new ResponseModel(200, "OK", call.getString(4), null);
            } else {
                res = new ResponseModel(422, "UNPROCESSABLE_ENTITY", call.getString(4), null);
            }

            call.close();
            con.close();

            log.info("ADD EMPLOYEE");
        } catch (Exception ex) {
            log.error("ERROR ADD EMPLOYEE : {}", ex.getLocalizedMessage());
            throw new RuntimeException("ERROR ADD EMPLOYEE : " + ex.getLocalizedMessage());
        } finally {
            call.close();
            con.close();
        }

        return res;
    }

    public ResponseModel updateEmployee(String id, EmployeeReq employeeReq) throws SQLException {
        ResponseModel res = new ResponseModel();
        Connection con = null;
        CallableStatement call = null;

        try {
            con = jdbcTemplate.getDataSource().getConnection();
            con.setAutoCommit(false);
            String sql = "";
            sql = "{ ? = call LATIHANSQL_DEV.pkg_employee.update_employee(?,?,?,?) }";
            call = con.prepareCall(sql);
            call.registerOutParameter(1, OracleTypes.NUMBER);
            call.setString(2, id);
            call.setString(3, employeeReq.getName());
            call.setString(4, employeeReq.getEmail());
            call.registerOutParameter(5, OracleTypes.VARCHAR);
            call.execute();

            if (call.getInt(1) == 1) {
                res = new ResponseModel(200, "OK", call.getString(5), null);
            } else {
                res = new ResponseModel(422, "UNPROCESSABLE_ENTITY", call.getString(5), null);
            }

            call.close();
            con.close();

            log.info("UPDATE EMPLOYEE");
        } catch (Exception ex) {
            log.error("ERROR UPDATE EMPLOYEE : {}", ex.getLocalizedMessage());
            throw new RuntimeException("ERROR UPDATE EMPLOYEE : " + ex.getLocalizedMessage());
        } finally {
            call.close();
            con.close();
        }

        return res;
    }
    
    public ResponseModel deleteEmployee(String id) throws SQLException {
        ResponseModel res = new ResponseModel();
        Connection con = null;
        CallableStatement call = null;

        try {
            con = jdbcTemplate.getDataSource().getConnection();
            con.setAutoCommit(false);
            String sql = "";
            sql = "{ ? = call LATIHANSQL_DEV.pkg_employee.delete_employee(?,?) }";
            call = con.prepareCall(sql);
            call.registerOutParameter(1, OracleTypes.NUMBER);
            call.setString(2, id);
            call.registerOutParameter(3, OracleTypes.VARCHAR);
            call.execute();

            if (call.getInt(1) == 1) {
                res = new ResponseModel(200, "OK", call.getString(3), null);
            } else {
                res = new ResponseModel(422, "UNPROCESSABLE_ENTITY", call.getString(3), null);
            }

            call.close();
            con.close();

            log.info("DELETE EMPLOYEE");
        } catch (Exception ex) {
            log.error("ERROR DELETE EMPLOYEE : {}", ex.getLocalizedMessage());
            throw new RuntimeException("ERROR DELETE EMPLOYEE : " + ex.getLocalizedMessage());
        } finally {
            call.close();
            con.close();
        }

        return res;
    }

}
