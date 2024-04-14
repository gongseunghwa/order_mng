package kr.co._29cm.homework.order_mng.dto;

import java.util.UUID;

public record OrderResponse(UUID orderId, Long itemId, String itemName, Long itemCount) {
}
