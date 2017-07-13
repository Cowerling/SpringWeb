package spittr.web.exception;

/**
 * Created by dell on 2017-7-11.
 */
public class SpittleNotFoundRuntimeException extends RuntimeException {
    private long spittleId;

    public SpittleNotFoundRuntimeException(long spittleId) {
        this.spittleId = spittleId;
    }

    public long getSpittleId() {
        return spittleId;
    }
}
