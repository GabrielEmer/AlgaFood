package com.algaworks.algafood.infrastructure.service.report;

public class ReportException extends RuntimeException{

    public static final Long serialVersionUID = 1L;

    public ReportException(String message) {
        super(message);
    }

    public ReportException(String message, Throwable cause) {
        super(message, cause);
    }
}
