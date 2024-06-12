package com.example.flowglobalassignment.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    REQUEST_PARAMETER_BIND_EXCEPTION(HttpStatus.BAD_REQUEST, "REQ_001", "PARAMETER_BIND_FAILED"),
    METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION(HttpStatus.BAD_REQUEST, "REQ_002", "METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION"),
    UNAUTHORIZED_EXCEPTION(HttpStatus.UNAUTHORIZED, "REQ_003", "접근 권한이 없습니다."),
    NOT_FOUND_EXCEPTION (HttpStatus.NOT_FOUND, "REQ_004", "NOT_FOUND_EXCEPTION"),
    JSON_PASING_EXCEPTION(HttpStatus.BAD_REQUEST, "REQ_005", "JSON_PARSING_EXCEPTION"),
    IP_RULE_MAXIMUM_EXCEPTION(HttpStatus.BAD_REQUEST, "IPROLE_001", "IP_RULE_MAXIMUM_EXCEPTION");
    private final String code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(final HttpStatus status, final String code, final String message){
        this.status = status;
        this.message = message;
        this.code = code;
    }
}