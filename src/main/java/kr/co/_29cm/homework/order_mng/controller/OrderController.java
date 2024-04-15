package kr.co._29cm.homework.order_mng.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import kr.co._29cm.homework.order_mng.dto.*;
import kr.co._29cm.homework.order_mng.service.OrderService;
import kr.co._29cm.homework.order_mng.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    public List<ItemResponse> items() {
        return orderService.items();
    }

    @PostMapping("/item")
    public ApiUtils.ApiResult order(@RequestBody @Valid List<OrderRequest> orderRequest) {
        return success(orderService.orderProcess(orderRequest));
    }

}
