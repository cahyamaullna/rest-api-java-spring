/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freddxant.model.response;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author freddxant
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ResponseModel<T> implements Serializable {

    @ApiModelProperty(notes = "Code", required = true, example = "200", position = 0)
    private int code;

    @ApiModelProperty(notes = "Status", required = true, example = "OK", position = 1)
    private String status;

    @ApiModelProperty(notes = "Message", required = true, example = "Success", position = 2)
    private String message;

    @ApiModelProperty(notes = "Data", required = true, example = "[]", position = 3)
    private T data;
}
