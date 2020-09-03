package ru.maxmorev.restful.eshop.rest.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class Message {

    public final static String SUCCES = "success";
    public final static String ERROR = "error";
    private static  ObjectMapper MAPPER = new ObjectMapper();
    public static Message fromJsonString(String json){
        try {
            return MAPPER.readValue(json, Message.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    private String status;
    private String url;
    private String message;
    private List<ErrorDetail> errors;

    public static class ErrorDetail {
        final String field;
        final String message;

        public ErrorDetail(String f, String m) {
            this.field = f;
            this.message = m;
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }
    }

    protected Message(){
        super();
    };

    public Message(String status, String url, Exception message, List<ErrorDetail> errors) {
        this.status = status;
        this.url = url;
        this.message = message.getMessage();
        this.errors = errors;
    }

    public Message(String status, String url, String message, List<ErrorDetail> errors) {
        this.status = status;
        this.url = url;
        this.message = message;
        this.errors = errors;
    }

    public Message(String status, String message) {
        this.status = status;
        this.message = message;
        this.url = null;
    }

    public String getStatus() {
        return status;
    }

    public String getUrl() {
        return url;
    }

    public String getMessage() {
        return message;
    }

    public List<ErrorDetail> getErrors() {
        return errors;
    }



}
