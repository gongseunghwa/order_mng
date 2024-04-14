package kr.co._29cm.homework.order_mng.service;

import kr.co._29cm.homework.order_mng.dto.ItemResponse;
import kr.co._29cm.homework.order_mng.dto.OrderResponse;
import kr.co._29cm.homework.order_mng.entity.Item;

import java.util.List;

public interface OrderService {
    List<ItemResponse> items();


    Item checkOrder(OrderResponse orderResponse);

}
