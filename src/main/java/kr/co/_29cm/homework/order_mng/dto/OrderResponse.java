package kr.co._29cm.homework.order_mng.dto;

import java.util.List;

public record OrderResponse(List<OrderDetail> orderDetails, List<Fee> fees, Long totalFee) {
}
