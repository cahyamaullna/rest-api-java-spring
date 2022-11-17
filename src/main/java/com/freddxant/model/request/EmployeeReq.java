package com.freddxant.model.request;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeReq implements Serializable {

    @ApiModelProperty(notes = "Name", required = true, example = "John Doe", position = 1)
    private String name;

    @ApiModelProperty(notes = "Email", required = true, example = "john.doe@email.com", position = 2)
    private String email;

}
