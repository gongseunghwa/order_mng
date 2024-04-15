package kr.co._29cm.homework.order_mng.exception;

public class EmptyOrderException extends RuntimeException{

    public EmptyOrderException(String message) {
        super(message);
    }

    public EmptyOrderException() {
        super("EmptyOrderException 발생. 주문 리스트가 비어있습니다.");
    }
}
