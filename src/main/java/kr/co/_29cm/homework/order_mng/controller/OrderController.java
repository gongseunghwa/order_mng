package kr.co._29cm.homework.order_mng.controller;

import kr.co._29cm.homework.order_mng.dto.ItemResponse;
import kr.co._29cm.homework.order_mng.dto.OrderRequest;
import kr.co._29cm.homework.order_mng.dto.OrderResponse;
import kr.co._29cm.homework.order_mng.entity.Item;
import kr.co._29cm.homework.order_mng.exception.NullItemException;
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

    private final ItemRepository itemRepository;

    @GetMapping("/item")
    public List<ItemResponse> itemList() {
        return orderService.items();
    }

    @PostMapping("/item")
    public ApiUtils.ApiResult order(OrderRequest orderRequest) {
        orderService.orderProcess(orderRequest);

        OrderResponse orderResponse = orderService.orderList();
        return success(orderResponse);
    }

    /**
     * 테스트용 캐시를 통해 정상적으로 재고를 확인하는지 체크하기 위해 생성
     */
    @GetMapping("/item/{itemId}")
    public Item item(@PathVariable Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(NullItemException::new);
    }
}
