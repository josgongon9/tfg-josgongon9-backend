package com.josgongon9.tfgwebbackend.controller;

import com.josgongon9.tfgwebbackend.exception.ServiceRsException;
import com.josgongon9.tfgwebbackend.utils.AccesorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class BaseController {

    /*@Autowired
    private AccesorMessage accesorMessage;

    public ResponseEntity<ServiceRsException> generateErrorResponse(HttpStatus e) {
        ServiceRsException serviceException = new ServiceRsException(accesorMessage, e);
        return new ResponseEntity<>(serviceException, new HttpHeaders(), HttpStatus.NO_CONTENT);
    }


    public ResponseEntity<ServiceRsException> generateGeneralResponse(HttpStatus e) {
        GeneralResponseSearchObject<?> response = new GeneralResponseSearchObject<>();

        if (Objects.isNull(result)) {
            return ResponseEntity.noContent().build();
        }
        if (CollectionUtils.isEmpty(result.getData())) {
            result.setData(Collections.emptyList());
        }

        response.setData(result.getData());
        response.setErrors(result.getErrors());
        response.setMeta(new MetaDTO(result.getData().size()));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Object> generateGeneralResponse(List result) {
        GeneralResponseSearchObject<?> response = new GeneralResponseSearchObject<>();

        if (CollectionUtils.isEmpty(result.getData())) {
            ServiceRsException serviceException = new ServiceRsException(accesorMessage, HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(serviceException, new HttpHeaders(), HttpStatus.NO_CONTENT);
        }

        response.setData(result);
        //response.setErrrors(this.getErrors(resul))
        response.setMeta(new MetaDTO(result.getData().size()));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Object> generateSimpleResponse(Object result) {
        GeneralResponseObject<Object> response = new GeneralResponseObject<>();

        if (Objects.isNull(result)) {
            ServiceRsException serviceException = new ServiceRsException(accesorMessage, HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(serviceException, new HttpHeaders(), HttpStatus.NO_CONTENT);
        }

        response.setData(result);
        //response.setErrrors(this.getErrors(resul))
        response.setMeta(new MetaDTO(1));
        return ResponseEntity.ok(response);
    }

*/

}