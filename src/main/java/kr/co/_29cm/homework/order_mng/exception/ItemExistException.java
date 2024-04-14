package kr.co._29cm.homework.order_mng.exception;

public class ItemExistException extends RuntimeException{
    public ItemExistException(String message) {
        super(message);
    }

    public ItemExistException() {
        super("NullItemException 발생. 해당 상품이 존재하지 않습니다.");
    }
}
