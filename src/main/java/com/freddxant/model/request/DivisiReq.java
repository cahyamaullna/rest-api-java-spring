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

public class DivisiReq implements Serializable {
    @ApiModelProperty(notes = "Nama Divisi", required = true, example = "Masukkan Nama Divisi", position = 1)
    private String nama_divisi;

    @ApiModelProperty(notes = "Unit", required = true, example = "Masukkan Kode Unit", position = 2)
    private String unit;
}
