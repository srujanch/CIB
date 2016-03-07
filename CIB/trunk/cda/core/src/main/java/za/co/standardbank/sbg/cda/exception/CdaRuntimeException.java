package za.co.standardbank.sbg.cda.exception;

public class CdaRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CdaRuntimeException(final String msg) {
		super(msg);
	}

	public CdaRuntimeException(final String msg, final Throwable e) {
		super(msg, e);
	}

	public CdaRuntimeException(final Throwable e) {
		super(e);
	}

}
