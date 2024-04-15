package kr.co._29cm.homework.order_mng.dto;
import kr.co._29cm.homework.order_mng.entity.Item;
import kr.co._29cm.homework.order_mng.entity.Order;

import java.util.UUID;

public record OrderDetail(UUID orderId, Long itemId, String itemName, Long itemPrice, Long itemCount) {

    public static OrderDetail of(Item item, Order order) {
        return new OrderDetail(order.getOrderId(),item.getItemId(), item.getItemName() ,item.getItemPrice(), order.getItemCount());
    }
}
