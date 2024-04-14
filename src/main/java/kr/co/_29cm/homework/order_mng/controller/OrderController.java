package kr.co._29cm.homework.order_mng.controller;

import jakarta.validation.Valid;
import kr.co._29cm.homework.order_mng.dto.ItemResponse;
import kr.co._29cm.homework.order_mng.dto.OrderRequest;
import kr.co._29cm.homework.order_mng.dto.OrderResponse;
import kr.co._29cm.homework.order_mng.entity.Item;
import kr.co._29cm.homework.order_mng.exception.ItemExistException;
import kr.co._29cm.homework.order_mng.repository.ItemRepository;
import kr.co._29cm.homework.order_mng.service.OrderService;
import kr.co._29cm.homework.order_mng.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    public ApiUtils.ApiResult order(@RequestBody @Valid OrderRequest orderRequest) {
        orderService.orderProcess(orderRequest);

        return success(orderService.orderList());
    }

}
