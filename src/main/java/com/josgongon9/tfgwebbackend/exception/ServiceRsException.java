package com.josgongon9.tfgwebbackend.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.josgongon9.tfgwebbackend.utils.AccesorMessage;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ServiceRsException {

    @ApiModelProperty
    @JsonProperty("code")
    private Integer code;

    @ApiModelProperty
    @JsonProperty("description")
    private String description;

    private List<String> data;

    public ServiceRsException(ServiceException serviceException) {
        this.code = serviceException.getCode();
        this.description = serviceException.getDescription();
        this.data = new ArrayList<>();

    }

    public ServiceRsException(AccesorMessage accesorMessage, HttpStatus httpStatus) {
        this.code = httpStatus.value();
        this.description = accesorMessage.get("error.http." + httpStatus.toString());
        this.data = new ArrayList<>();

    }

    public ServiceRsException(Integer code, String description) {
        this.code = code;
        this.description = description;

    }

}
