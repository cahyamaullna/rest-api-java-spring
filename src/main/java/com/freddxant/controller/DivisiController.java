package com.freddxant.controller;

import com.freddxant.common.AppServer;
import com.freddxant.model.request.DivisiReq;
import com.freddxant.model.response.ResponseModel;
import com.freddxant.service.DivisiService;
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
@RequestMapping(value = "/divisi")
@Api(value = "Divisi API", produces = MediaType.APPLICATION_JSON_VALUE, tags = {"Divisi"})
public class DivisiController {
    @Autowired
    private DivisiService divisiService;

    @GetMapping(value = "/getAlldivisi", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get All Divisi", notes = "Get All Divisi", response = ResponseModel.class)
    private ResponseEntity<?> getAllDivisi() throws Exception {
        ResponseModel res = new ResponseModel();
        res = divisiService.getDivisi();
        return new ResponseEntity<ResponseModel>(res, AppServer.getHttpStatus(res.getCode()));
    }

    @PostMapping(value = "/addDivisi", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add Divisi", notes = "Add Divisi", response = ResponseModel.class)
    private ResponseEntity<?> addDivisi(@RequestBody DivisiReq divisiReq)
            throws Exception  {
        ResponseModel res = new ResponseModel();

        if (StringUtils.isBlank(divisiReq.getNama_divisi())) {
            res = new ResponseModel(400, "BAD_REQUEST", "Divisi must be not null", null);
            return new ResponseEntity<ResponseModel>(res, AppServer.getHttpStatus(res.getCode()));
        } else if (StringUtils.isBlank(divisiReq.getUnit())) {
            res = new ResponseModel(400, "BAD_REQUEST", "Unit must be not null", null);
            return new ResponseEntity<ResponseModel>(res, AppServer.getHttpStatus(res.getCode()));
        }

        res = divisiService.insertDivisi(divisiReq);
        return new ResponseEntity<ResponseModel>(res, AppServer.getHttpStatus(res.getCode()));
    }
    
    @PutMapping(value = "/editDivisi/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Edit Divisi", notes = "Edit Divisi", response = ResponseModel.class)
    private ResponseEntity<?> editDivisi(@PathVariable String id, @RequestBody DivisiReq divisiReq)
            throws Exception  {
        ResponseModel res = new ResponseModel();

        if (id == null || id == "") {
            res = new ResponseModel(400, "BAD_REQUEST", "ID must be not null", null);
            return new ResponseEntity<ResponseModel>(res, AppServer.getHttpStatus(res.getCode()));
        } else if (StringUtils.isBlank(divisiReq.getNama_divisi())) {
            res = new ResponseModel(400, "BAD_REQUEST", "Divisi Name must be not null", null);
            return new ResponseEntity<ResponseModel>(res, AppServer.getHttpStatus(res.getCode()));
        } else if (StringUtils.isBlank(divisiReq.getUnit())) {
            res = new ResponseModel(400, "BAD_REQUEST", "Unit must be not null", null);
            return new ResponseEntity<ResponseModel>(res, AppServer.getHttpStatus(res.getCode()));
        }

        res = divisiService.updateDivisi(id, divisiReq);
        return new ResponseEntity<ResponseModel>(res, AppServer.getHttpStatus(res.getCode()));
    }
    
    @DeleteMapping(value = "/deleteDivisi/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete Divisi", notes = "Delete Divisi", response = ResponseModel.class)
    private ResponseEntity<?> deleteDivisi(@PathVariable String id)
            throws Exception  {
        ResponseModel res = new ResponseModel();

        if (id == null || id == "") {
            res = new ResponseModel(400, "BAD_REQUEST", "ID must be not null", null);
            return new ResponseEntity<ResponseModel>(res, AppServer.getHttpStatus(res.getCode()));
        }

        res = divisiService.deleteDivisi(id);
        return new ResponseEntity<ResponseModel>(res, AppServer.getHttpStatus(res.getCode()));
    }

}
