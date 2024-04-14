package kr.co._29cm.homework.order_mng.dto;


import jakarta.validation.constraints.Min;

public record OrderRequest(Long itemId, @Min(value = 1, message = "수량은 1개 이상이어야 합니다.") Long itemCount) {
}
