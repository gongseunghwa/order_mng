package kr.co._29cm.homework.order_mng.entity;

import jakarta.persistence.*;
import kr.co._29cm.homework.order_mng.dto.OrderRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "tb_lm_item_order")
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "item_count")
    private Long count;

    @Column(name = "order_create_date")
    @CreatedDate
    private LocalDateTime orderCreatedDate;

    public Order(Long itemId, Long itemCount) {
        this.itemId = itemId;
        this.count = itemCount;
    }

    public static Order of(OrderRequest orderRequest) {
        return new Order(orderRequest.itemId(), orderRequest.itemCount());
    }
}
