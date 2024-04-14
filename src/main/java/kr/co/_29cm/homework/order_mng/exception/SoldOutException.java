package kr.co._29cm.homework.order_mng.exception;

public class SoldOutException extends RuntimeException{
    public SoldOutException(String message) {
        super(message);
    }

    public SoldOutException() {
        super("SoldOutException 발생. 주문한 상품량이 재고량보다 큽니다.");
    }
}
