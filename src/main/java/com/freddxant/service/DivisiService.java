package com.freddxant.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.freddxant.common.AppServer;
import com.freddxant.model.request.DivisiReq;
import com.freddxant.model.response.ResponseModel;

import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.OracleTypes;

@Service
@Slf4j
public class DivisiService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ResponseModel getDivisi() throws SQLException {
        ResponseModel res = new ResponseModel();
        Connection con = null;
        CallableStatement call = null;

        try {
            con = jdbcTemplate.getDataSource().getConnection();
            con.setAutoCommit(false);
            String sql = "";
            sql = "{ ? = call LATIHANSQL_DEV.pkg_divisi.get_all_divisi }";
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
                res = new ResponseModel(404, "NOT_FOUND", "Divisi not found", null);
            }

            log.info("GET DIVISI");
        } catch (Exception ex) {
            log.error("ERROR GET DIVISI : {}", ex.getLocalizedMessage());
            throw new RuntimeException("ERROR GET DIVISI : " + ex.getLocalizedMessage());
        } finally {
            call.close();
            con.close();
        }

        return res;
    }

    public ResponseModel insertDivisi(DivisiReq divisiReq) throws SQLException {
        ResponseModel res = new ResponseModel();
        Connection con = null;
        CallableStatement call = null;

        try {
            con = jdbcTemplate.getDataSource().getConnection();
            con.setAutoCommit(false);
            String sql = "";
            sql = "{ ? = call LATIHANSQL_DEV.pkg_divisi.insert_divisi(?,?,?) }";
            call = con.prepareCall(sql);
            call.registerOutParameter(1, OracleTypes.NUMBER);
            call.setString(2, divisiReq.getNama_divisi());
            call.setString(3, divisiReq.getUnit());
            call.registerOutParameter(4, OracleTypes.VARCHAR);
            call.execute();

            if (call.getInt(1) == 1) {
                res = new ResponseModel(200, "OK", call.getString(4), null);
            } else {
                res = new ResponseModel(422, "UNPROCESSABLE_ENTITY", call.getString(4), null);
            }

            call.close();
            con.close();

            log.info("ADD DIVISI");
        } catch (Exception ex) {
            log.error("ERROR ADD DIVISI : {}", ex.getLocalizedMessage());
            throw new RuntimeException("ERROR ADD DIVISI : " + ex.getLocalizedMessage());
        } finally {
            call.close();
            con.close();
        }

        return res;
    }

    public ResponseModel updateDivisi(String id, DivisiReq divisiReq) throws SQLException {
        ResponseModel res = new ResponseModel();
        Connection con = null;
        CallableStatement call = null;

        try {
            con = jdbcTemplate.getDataSource().getConnection();
            con.setAutoCommit(false);
            String sql = "";
            sql = "{ ? = call LATIHANSQL_DEV.pkg_divisi.update_divisi(?,?,?,?) }";
            call = con.prepareCall(sql);
            call.registerOutParameter(1, OracleTypes.NUMBER);
            call.setString(2, id);
            call.setString(3, divisiReq.getNama_divisi());
            call.setString(4, divisiReq.getUnit());
            call.registerOutParameter(5, OracleTypes.VARCHAR);
            call.execute();

            if (call.getInt(1) == 1) {
                res = new ResponseModel(200, "OK", call.getString(5), null);
            } else {
                res = new ResponseModel(422, "UNPROCESSABLE_ENTITY", call.getString(5), null);
            }

            call.close();
            con.close();

            log.info("UPDATE DIVISI");
        } catch (Exception ex) {
            log.error("ERROR UPDATE DIVISI : {}", ex.getLocalizedMessage());
            throw new RuntimeException("ERROR UPDATE DIVISI : " + ex.getLocalizedMessage());
        } finally {
            call.close();
            con.close();
        }

        return res;
    }
    
    public ResponseModel deleteDivisi(String id) throws SQLException {
        ResponseModel res = new ResponseModel();
        Connection con = null;
        CallableStatement call = null;

        try {
            con = jdbcTemplate.getDataSource().getConnection();
            con.setAutoCommit(false);
            String sql = "";
            sql = "{ ? = call LATIHANSQL_DEV.pkg_divisi.delete_divisi(?,?) }";
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

            log.info("DELETE DIVISI");
        } catch (Exception ex) {
            log.error("ERROR DELETE DIVISI : {}", ex.getLocalizedMessage());
            throw new RuntimeException("ERROR DELETE DIVISI : " + ex.getLocalizedMessage());
        } finally {
            call.close();
            con.close();
        }

        return res;
    }
}
