package com.freddxant.common;

import com.fasterxml.jackson.databind.ObjectMapper;
//import com.iconplus.sakti.model.ResponseStatusConstant;
//import com.iconplus.sakti.model.response.ResponseModel;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;

@Slf4j
public class AppServer {

    public static List<Map<String, String>> convertResultsetToListStr(ResultSet rs) {
        List<Map<String, String>> lst = new ArrayList<Map<String, String>>();
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int colCount = rsmd.getColumnCount();
            String value = "";

            while (rs.next()) {
                HashMap<String, String> map = new HashMap<String, String>();
                for (int i = 1; i <= colCount; i++) {
                    try {
                        if (rs.getObject(i).toString().equals("") || rs.getObject(i).toString().equals("null")) {
                            value = "";
                        } else {
                            value = rs.getObject(i).toString();
                        }
                    } catch (Exception e) {
                        value = "";
                    }
                    map.put(rsmd.getColumnName(i).toLowerCase(), value);
                }
                lst.add(map);
            }

        } catch (Exception ex) {
            log.info("AppServer :" + ex.getMessage());
            ex.printStackTrace();
        }

        return lst;
    }

    public static HttpStatus getHttpStatus(int code) {
        HttpStatus status = HttpStatus.OK;

        if (code == 400) {
            status = HttpStatus.BAD_REQUEST;
        } else if (code == 404) {
            status = HttpStatus.NOT_FOUND;
        } else if (code == 422) {
            status = HttpStatus.UNPROCESSABLE_ENTITY;
        }

        return status;
    }

}
