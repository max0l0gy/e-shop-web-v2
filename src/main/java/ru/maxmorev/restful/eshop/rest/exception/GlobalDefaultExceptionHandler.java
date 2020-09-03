package ru.maxmorev.restful.eshop.rest.exception;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.maxmorev.restful.eshop.rest.response.Message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    /**
     * Other errors
     *
     * @param req
     * @param ex
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Message handleBadRequest(HttpServletRequest req, Exception ex) {
        log.error(ex.getLocalizedMessage(), ex);
        Message responseMessage = new Message(Message.ERROR, req.getRequestURL().toString(), ex.getMessage(), Collections.EMPTY_LIST);
        return responseMessage;
    }

    /**
     * Validation errors
     *
     * @param req request method
     * @param ex  MethodArgumentNotValidException
     * @return @see ru.maxmorev.restful.eshop.rest.response.Message
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Message validationException(HttpServletRequest req, MethodArgumentNotValidException ex) {
        log.debug("Errors: {}", ex.getBindingResult().getAllErrors());
        List<Message.ErrorDetail> fieldsErrorDetails = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> new Message.ErrorDetail(e.getField(), e.getDefaultMessage()))
                .collect(Collectors.toList());
        Message responseMessage = new Message(Message.ERROR, req.getRequestURL().toString(), "Validation error", fieldsErrorDetails);
        return responseMessage;
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseBody
    public Message handleIllegalArgumentException(HttpServletRequest req, Exception ex) {
        log.error(ex.getLocalizedMessage(), ex);
        Message responseMessage = new Message(Message.ERROR, req.getRequestURL().toString(), ex.getLocalizedMessage(), Collections.EMPTY_LIST);
        return responseMessage;
    }

    @ResponseBody
    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Message handleFeignStatusException(FeignException e, HttpServletRequest req, HttpServletResponse response) {
        response.setStatus(e.status());
        log.error("FeignException catch");
        Message responseMessage = Message.fromJsonString(e.contentUTF8());
        if (responseMessage == null) {
            responseMessage = new Message(Message.ERROR, req.getRequestURL().toString(), "FeignException", Collections.EMPTY_LIST);
        }
        return responseMessage;
    }
}
