package kr.co._29cm.homework.order_mng.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_lm_item")
public class Item {
    @Id
    @Column(name = "item_id")
    private Long id;

    @Column(name = "item_nm")
    private String name;

    @Column(name = "item_price")
    private Long price;

    @Column(name = "item_inventory")
    private Long inventory;


    public void reduceInventory(Long value) {
        this.inventory -= value;
    }

}
