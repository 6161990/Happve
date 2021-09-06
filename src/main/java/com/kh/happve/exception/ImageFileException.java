package com.kh.happve.exception;

//serialVersionUID를 정의해주지 않은 경우 나타나는 warnning을 체크하지 않음
@SuppressWarnings("serial")
public class ImageFileException extends RuntimeException{
	
	public ImageFileException(String message) {
		super(message);
	}
	public ImageFileException(String message, Throwable cause) {
		super(message,cause);
	}

}
