package za.co.standardbank.sbg.cma.exception;

public class CmaRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CmaRuntimeException(final String msg) {
		super(msg);
	}

	public CmaRuntimeException(final String msg, final Throwable e) {
		super(msg, e);
	}

	public CmaRuntimeException(final Throwable e) {
		super(e);
	}

}
