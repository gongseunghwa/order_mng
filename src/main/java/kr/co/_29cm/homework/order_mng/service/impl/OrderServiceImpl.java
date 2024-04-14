package kr.co._29cm.homework.order_mng.service.impl;

import kr.co._29cm.homework.order_mng.dto.ItemResponse;
import kr.co._29cm.homework.order_mng.dto.OrderRequest;
import kr.co._29cm.homework.order_mng.entity.Item;
import kr.co._29cm.homework.order_mng.entity.Order;
import kr.co._29cm.homework.order_mng.exception.NullItemException;
import kr.co._29cm.homework.order_mng.exception.SoldOutException;
import kr.co._29cm.homework.order_mng.repository.ItemRepository;
import kr.co._29cm.homework.order_mng.repository.OrderRepository;
import kr.co._29cm.homework.order_mng.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "item")
public class OrderServiceImpl implements OrderService {
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    @Override
//    @Cacheable(key = "#")
    public List<ItemResponse> items() {
        return itemRepository.findAllItem();
    }

    @Override
    @CacheEvict(key = "'all'")
    @CachePut(key = "#orderRequest.itemId()")
    public Item checkOrder(OrderRequest orderRequest) {
        Item item = itemRepository.findById(orderRequest.itemId()).orElseThrow(NullItemException::new);
        if(orderRequest.itemCount() > item.getInventory()) {
            throw new SoldOutException();
        }
        item.reduceInventory(orderRequest.itemCount());
        return item;
    }

    @Override
    @Transactional
    public Order saveOrder(OrderRequest orderRequest) {
        return orderRepository.save(Order.of(orderRequest));
    }
}
