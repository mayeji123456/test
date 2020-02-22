package com.springtest.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.springtest.exception.CustomizeException;

@ControllerAdvice
public class ErrorController {

	@ExceptionHandler(Exception.class)
	    
    ModelAndView handleControllerException(HttpServletRequest request, Throwable ex,Model model) {
        HttpStatus status = getStatus(request);
        
        if(ex instanceof CustomizeException) {
        	model.addAttribute("errormessage",ex.getMessage());
        }else {
        	model.addAttribute("errormessage", status.getReasonPhrase());
        }
        
        return new ModelAndView("error");
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
