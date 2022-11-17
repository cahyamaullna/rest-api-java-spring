package com.freddxant.controller;

import com.freddxant.common.AppServer;
import com.freddxant.model.request.EmployeeReq;
import com.freddxant.model.response.ResponseModel;
import com.freddxant.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping(value = "/employee")
@Api(value = "Employee API", produces = MediaType.APPLICATION_JSON_VALUE, tags = {"Employee"})
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(value = "/getAllEmployee", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get All Employee", notes = "Get All Employee", response = ResponseModel.class)
    private ResponseEntity<?> getAllEmployee() throws Exception {
        ResponseModel res = new ResponseModel();
        res = employeeService.getEmployee();
        return new ResponseEntity<ResponseModel>(res, AppServer.getHttpStatus(res.getCode()));
    }

    @PostMapping(value = "/addEmployee", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add Employee", notes = "Add Employee", response = ResponseModel.class)
    private ResponseEntity<?> addEmployee(@RequestBody EmployeeReq employeeReq)
            throws Exception  {
        ResponseModel res = new ResponseModel();

        if (StringUtils.isBlank(employeeReq.getName())) {
            res = new ResponseModel(400, "BAD_REQUEST", "Name must be not null", null);
            return new ResponseEntity<ResponseModel>(res, AppServer.getHttpStatus(res.getCode()));
        } else if (StringUtils.isBlank(employeeReq.getEmail())) {
            res = new ResponseModel(400, "BAD_REQUEST", "Email must be not null", null);
            return new ResponseEntity<ResponseModel>(res, AppServer.getHttpStatus(res.getCode()));
        }

        res = employeeService.insertEmployee(employeeReq);
        return new ResponseEntity<ResponseModel>(res, AppServer.getHttpStatus(res.getCode()));
    }
    
    @PutMapping(value = "/editEmployee/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Edit Employee", notes = "Edit Employee", response = ResponseModel.class)
    private ResponseEntity<?> editEmployee(@PathVariable String id, @RequestBody EmployeeReq employeeReq)
            throws Exception  {
        ResponseModel res = new ResponseModel();

        if (id == null || id == "") {
            res = new ResponseModel(400, "BAD_REQUEST", "ID must be not null", null);
            return new ResponseEntity<ResponseModel>(res, AppServer.getHttpStatus(res.getCode()));
        } else if (StringUtils.isBlank(employeeReq.getName())) {
            res = new ResponseModel(400, "BAD_REQUEST", "Name must be not null", null);
            return new ResponseEntity<ResponseModel>(res, AppServer.getHttpStatus(res.getCode()));
        } else if (StringUtils.isBlank(employeeReq.getEmail())) {
            res = new ResponseModel(400, "BAD_REQUEST", "Email must be not null", null);
            return new ResponseEntity<ResponseModel>(res, AppServer.getHttpStatus(res.getCode()));
        }

        res = employeeService.updateEmployee(id, employeeReq);
        return new ResponseEntity<ResponseModel>(res, AppServer.getHttpStatus(res.getCode()));
    }
    
    @DeleteMapping(value = "/deleteEmployee/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete Employee", notes = "Delete Employee", response = ResponseModel.class)
    private ResponseEntity<?> deleteEmployee(@PathVariable String id)
            throws Exception  {
        ResponseModel res = new ResponseModel();

        if (id == null || id == "") {
            res = new ResponseModel(400, "BAD_REQUEST", "ID must be not null", null);
            return new ResponseEntity<ResponseModel>(res, AppServer.getHttpStatus(res.getCode()));
        }

        res = employeeService.deleteEmployee(id);
        return new ResponseEntity<ResponseModel>(res, AppServer.getHttpStatus(res.getCode()));
    }

}
