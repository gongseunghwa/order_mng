package kr.co._29cm.homework.order_mng.repository;

import kr.co._29cm.homework.order_mng.dto.OrderResponse;
import kr.co._29cm.homework.order_mng.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    Order save(Order o);

    @Query("select new kr.co._29cm.homework.order_mng.dto.OrderResponse(o.orderId, i.itemId, i.itemName, o.itemCount) from Order o left join Item i on o.itemId = i.itemId")
    List<OrderResponse> findAllOrder();
}
