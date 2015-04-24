package com.iamcontent.io.usb;

/**
 * A {@link RuntimeException} to wrap checked exceptions from USB operations.
 * @author Greg Elderfield
 */
public class UsbRuntimeException extends RuntimeException {
	public UsbRuntimeException(String message) {
		super(message);
	}

	public UsbRuntimeException(Throwable cause) {
		super(cause);
	}
	
	private static final long serialVersionUID = 1L;
}