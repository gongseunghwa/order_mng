package kr.co._29cm.homework.order_mng.repository;

import kr.co._29cm.homework.order_mng.dto.OrderDetail;
import kr.co._29cm.homework.order_mng.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    Order save(Order o);

}
