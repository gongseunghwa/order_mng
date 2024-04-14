package kr.co._29cm.homework.order_mng.dto;

import lombok.Getter;

@Getter
public class DeliveryFee extends Fee{

    public DeliveryFee(String name) {
        super(name, 2500l);
    }

    public DeliveryFee() {
        super("배송비", 2500l);
    }

    public DeliveryFee(String name, Long value) {
        super(name, value);
    }

}
