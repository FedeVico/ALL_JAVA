package dataStructures.ring;


public class EmptyRingException extends RuntimeException {

    private static final long serialVersionUID = 1893736210744176916L;

    public EmptyRingException() {
        super();
    }

    public EmptyRingException(String msg) {
        super(msg);
    }
}
