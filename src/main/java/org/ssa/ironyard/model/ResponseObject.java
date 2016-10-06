package org.ssa.ironyard.model;

public class ResponseObject {

    private final STATUS status;
    private final String msg;
    private final Object obj;

    private ResponseObject(STATUS status, String msg, Object obj) {
	this.status = status;
	this.msg = msg;
	this.obj = obj;
    }

    public static ResponseObject instanceOf(STATUS status, String msg, Object obj) {
	return new ResponseObject(status, msg, obj);
    }

    public String getMsg() {
	return msg;
    }

    public STATUS getStatus() {
	return status;
    }

    public Object getData() {
	return obj;
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
