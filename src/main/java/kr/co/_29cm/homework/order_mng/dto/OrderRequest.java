package kr.co._29cm.homework.order_mng.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderRequest(@NotNull(message = "상품번호는 필수로 입력되어야합니다") Long itemId,
                           @Min(value = 1, message = "수량은 1개 이상이어야 합니다.") Long itemCount) {
}
