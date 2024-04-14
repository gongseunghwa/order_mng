package kr.co._29cm.homework.order_mng.exception;

public class NullItemException extends RuntimeException{
    public NullItemException(String message) {
        super(message);
    }

    public NullItemException() {
        super("NullItemException 발생. 해당 상품이 존재하지 않습니다.");
    }
}
