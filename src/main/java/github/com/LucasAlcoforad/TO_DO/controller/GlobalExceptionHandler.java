package github.com.LucasAlcoforad.TO_DO.controller;

import github.com.LucasAlcoforad.TO_DO.dto.erro.FieldErro;
import github.com.LucasAlcoforad.TO_DO.dto.erro.ResponseErro;
import github.com.LucasAlcoforad.TO_DO.exception.EntityNotExistException;
import github.com.LucasAlcoforad.TO_DO.exception.BadLoginException;
import github.com.LucasAlcoforad.TO_DO.exception.UserPersistenceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseErro handleArgumentNotValidException(MethodArgumentNotValidException e){
        List<FieldErro> erroList = e.getFieldErrors().stream()
                .map(fe -> new FieldErro(fe.getField(), fe.getDefaultMessage())).toList();
        return new ResponseErro(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation Error", erroList);
    }

    @ExceptionHandler(UserPersistenceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseErro handleUserPersistenceException(UserPersistenceException e){
        return new ResponseErro(HttpStatus.CONFLICT.value(), e.getMessage(),List.of());
    }

    @ExceptionHandler(EntityNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseErro handleUserNotExistException(EntityNotExistException e){
        return new ResponseErro(HttpStatus.NOT_FOUND.value(), e.getMessage(), List.of());
    }

    @ExceptionHandler(BadLoginException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseErro handleBadLoginException(BadLoginException e){
        return new ResponseErro(HttpStatus.BAD_REQUEST.value(), e.getMessage(), List.of());
    }
}
