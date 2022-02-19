package com.josgongon9.tfgwebbackend.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServiceException extends RuntimeException{

    @ApiModelProperty
    @JsonProperty("code")
    private Integer code;

    @ApiModelProperty
    @JsonProperty("description")
    private String description;
}
