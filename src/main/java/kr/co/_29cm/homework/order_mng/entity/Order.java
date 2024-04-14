package kr.co._29cm.homework.order_mng.entity;

import kr.co._29cm.homework.order_mng.dto.OrderResponse;
import lombok.Getter;
import org.aspectj.weaver.ast.Or;

@Getter
public class Order {
    private Long itemId;
    private Long itemCount;

    public Order(Long itemId, Long itemCount) {
        this.itemId = itemId;
        this.itemCount = itemCount;
    }

    public static Order of(OrderResponse orderResponse) {
        return new Order(orderResponse.itemId(), orderResponse.itemNumber());
    }
}
