package io.github.libzeal.zeal.assertion.error;

public class PreconditionFailedException extends IllegalArgumentException {

    public PreconditionFailedException() {
    }

    public PreconditionFailedException(final String message) {
        super(message);
    }

    public PreconditionFailedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PreconditionFailedException(final Throwable cause) {
        super(cause);
    }
}
