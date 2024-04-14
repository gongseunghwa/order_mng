package kr.co._29cm.homework.order_mng.service;

import kr.co._29cm.homework.order_mng.dto.ItemResponse;
import kr.co._29cm.homework.order_mng.dto.OrderRequest;
import kr.co._29cm.homework.order_mng.entity.Item;
import kr.co._29cm.homework.order_mng.entity.Order;

import java.util.List;

public interface OrderService {
    List<ItemResponse> items();


    Item checkOrder(OrderRequest orderRequest);

    Order saveOrder(OrderRequest orderRequest);
}
