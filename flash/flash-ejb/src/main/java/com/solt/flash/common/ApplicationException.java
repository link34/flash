package com.solt.flash.common;

public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private ErrorType errorType;

    public ApplicationException(String message, ErrorType error) {
		super(message);
		this.errorType = error;
	}

	public ApplicationException(Throwable cause) {
		super(cause);
		this.errorType = ErrorType.Error;
	}

	public ErrorType getErrorType() {
		return errorType;
	}
    
    public enum ErrorType {
        Info,
        Warning,
        Error
    }

}