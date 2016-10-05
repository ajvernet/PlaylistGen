package org.ssa.ironyard.controller.model;

public class ResponseObject {

    private final STATUS status;
    private final String msg;
    private final Object data;

    private ResponseObject(STATUS status, String msg, Object data) {
	this.status = status;
	this.msg = msg;
	this.data = data;
    }

    public static ResponseObject instanceOf(STATUS status, String msg, Object data) {
	return new ResponseObject(status, msg, data);
    }

    public String getMsg() {
	return msg;
    }

    public STATUS getStatus() {
	return status;
    }

    public Object getData() {
	return data;
    }

    public enum STATUS {
	SUCCESS("OK"), ERROR("ERR");

	public final String abbrev;

	private STATUS(String abbrev) {
	    this.abbrev = abbrev;
	}

	public static STATUS getInstance(String abbrev) {
	    for (STATUS status : values()) {
		if (status.abbrev.equals(abbrev))
		    return status;
	    }
	    return null;
	}
    }

}
