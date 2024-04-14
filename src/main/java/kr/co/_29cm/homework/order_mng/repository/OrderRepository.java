package kr.co._29cm.homework.order_mng.repository;

import kr.co._29cm.homework.order_mng.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    Order save(Order o);
}
