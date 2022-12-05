package com.udacity.jdnd.course3.critter.Error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public
class EmployeeNotFoundError extends RuntimeException {
    public EmployeeNotFoundError(String message) {
        super(message);
    }
}