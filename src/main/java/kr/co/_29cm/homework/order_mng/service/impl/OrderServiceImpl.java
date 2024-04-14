package kr.co._29cm.homework.order_mng.service.impl;

import kr.co._29cm.homework.order_mng.dto.*;
import kr.co._29cm.homework.order_mng.entity.Item;
import kr.co._29cm.homework.order_mng.entity.Order;
import kr.co._29cm.homework.order_mng.exception.ItemExistException;
import kr.co._29cm.homework.order_mng.exception.SoldOutException;
import kr.co._29cm.homework.order_mng.repository.ItemRepository;
import kr.co._29cm.homework.order_mng.repository.OrderRepository;
import kr.co._29cm.homework.order_mng.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    @Override
    public List<ItemResponse> items() {
        return itemRepository.findAllItem();
    }

    @Override
    @Transactional
    public synchronized void orderProcess(OrderRequest orderRequest) {
        Item item = itemRepository.findById(orderRequest.itemId()).orElseThrow(ItemExistException::new);
        if(orderRequest.itemCount() > item.getItemInventory()) {
            throw new SoldOutException();
        }
        item.reduceInventory(orderRequest.itemCount());
        itemRepository.save(item);
        orderRepository.save(Order.of(orderRequest));
    }

    @Override
    public OrderResponse orderList() {
        List<OrderDetail> orderDetail = orderRepository.findAllOrder();
        Long sumPrice = orderDetail.stream().mapToLong(item -> item.itemPrice() * item.itemCount()).sum();
        List<Fee> feeList = new ArrayList<>();
        feeList.add(new ItemPrice(sumPrice));
        if(sumPrice < 50000l) {
            feeList.add(new DeliveryFee());
        }

        return new OrderResponse(orderDetail, feeList, sumPrice);
    }
}
