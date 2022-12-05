package com.udacity.jdnd.course3.critter.Error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public
class CustomerNotFoundError extends RuntimeException {
    public CustomerNotFoundError(String message) {
        super(message);
    }
}

