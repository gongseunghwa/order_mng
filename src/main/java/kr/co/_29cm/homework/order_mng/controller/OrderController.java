package kr.co._29cm.homework.order_mng.controller;

import jakarta.servlet.http.HttpSession;
import kr.co._29cm.homework.order_mng.dto.ItemResponse;
import kr.co._29cm.homework.order_mng.dto.OrderResponse;
import kr.co._29cm.homework.order_mng.entity.Item;
import kr.co._29cm.homework.order_mng.entity.Order;
import kr.co._29cm.homework.order_mng.exception.NullItemException;
import kr.co._29cm.homework.order_mng.service.OrderService;
import kr.co._29cm.homework.order_mng.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static kr.co._29cm.homework.order_mng.utils.ApiUtils.success;

@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/item")
    public List<ItemResponse> itemList() {
        return orderService.items();
    }

    @PostMapping("/item")
    public ApiUtils.ApiResult order(OrderResponse orderResponse, HttpSession session) {
        orderService.checkOrder(orderResponse);
        List<Order> orders = new ArrayList<>();
        try {
            orders.addAll((List<Order>) session.getAttribute("orders"));
        } catch (NullPointerException e) {
         log.info("기존 주문이 없어 추가되지 않음");
        }
        orders.add(Order.of(orderResponse));
        return success(orders);
    }
}
