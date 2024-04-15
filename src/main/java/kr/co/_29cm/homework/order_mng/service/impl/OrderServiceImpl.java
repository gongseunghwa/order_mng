package kr.co._29cm.homework.order_mng.service.impl;

import kr.co._29cm.homework.order_mng.dto.*;
import kr.co._29cm.homework.order_mng.entity.Item;
import kr.co._29cm.homework.order_mng.entity.Order;
import kr.co._29cm.homework.order_mng.exception.EmptyOrderException;
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
    private final Long FREE_DELIVERY_PRICE = 50000l;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    @Override
    public List<ItemResponse> items() {
        return itemRepository.findAllItem();
    }

    @Override
    @Transactional
    public synchronized OrderResponse orderProcess(List<OrderRequest> orderRequests) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        if (orderRequests.isEmpty()) {
            throw new EmptyOrderException();
        }
        orderRequests.forEach(orderRequest -> {
            Item item = itemRepository.findById(orderRequest.itemId()).orElseThrow(ItemExistException::new);
            if(orderRequest.itemCount() > item.getItemInventory()) {
                throw new SoldOutException();
            }
            item.reduceInventory(orderRequest.itemCount());
            OrderDetail detail = OrderDetail.of(itemRepository.save(item), orderRepository.save(Order.of(orderRequest)));
            orderDetails.add(detail);
        });

        Long sumPrice = orderDetails.stream().mapToLong(item -> item.itemPrice() * item.itemCount()).sum();
        List<Fee> feeList = new ArrayList<>();
        feeList.add(new ItemPrice(sumPrice));
        if(sumPrice < FREE_DELIVERY_PRICE) {
            DeliveryFee deliveryFee = new DeliveryFee();
            feeList.add(deliveryFee);
            sumPrice += deliveryFee.getValue();
        }
        return new OrderResponse(orderDetails, feeList, sumPrice);
    }

}
