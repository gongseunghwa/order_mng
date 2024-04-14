package kr.co._29cm.homework.order_mng.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Fee {
    private String name;
    private Long value;

    public Fee(String name, Long value) {
        this.name = name;
        this.value = value;
    }

}
