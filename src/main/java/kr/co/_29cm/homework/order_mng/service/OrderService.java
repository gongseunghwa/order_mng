package kr.co._29cm.homework.order_mng.service;

import kr.co._29cm.homework.order_mng.dto.ItemResponse;
import kr.co._29cm.homework.order_mng.dto.OrderRequest;
import kr.co._29cm.homework.order_mng.dto.OrderDetail;
import kr.co._29cm.homework.order_mng.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    List<ItemResponse> items();


    void orderProcess(OrderRequest orderRequest);

    OrderResponse orderList();
}
