package com.spt.app.spring;

import com.spt.app.model.ResponseErrorModel;
import com.spt.app.util.CustomException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log4j2
@ControllerAdvice
public class SystemExceptionHandler {

    @ExceptionHandler({Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseErrorModel handleException(Exception ex) {
        log.error("Error : {} ", ex.getMessage(), ex);
        ResponseErrorModel response = new ResponseErrorModel();
        response.setStatus("error");
        response.setMessage(ex.getMessage());
        response.setCode("500");
        return response;
    }

    @ExceptionHandler({CustomException.class})
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ResponseErrorModel handleException(CustomException ex) {
        ResponseErrorModel response = new ResponseErrorModel();
        response.setStatus("error");
        response.setMessage(ex.getMessage());
        response.setCode(ex.getCode());
        response.setInfo(ex.getInfo());
        log.error("Error : {} ", response);
        return response;
    }
}
