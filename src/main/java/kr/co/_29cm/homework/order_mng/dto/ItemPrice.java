package kr.co._29cm.homework.order_mng.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemPrice extends Fee{

    public ItemPrice(String name, Long value) {
        super(name, value);
    }

    public ItemPrice(Long value) {
        super("상품금액", value);
    }
}
