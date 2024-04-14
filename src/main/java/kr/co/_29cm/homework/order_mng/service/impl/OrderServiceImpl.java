package kr.co._29cm.homework.order_mng.service.impl;

import kr.co._29cm.homework.order_mng.dto.ItemResponse;
import kr.co._29cm.homework.order_mng.dto.OrderResponse;
import kr.co._29cm.homework.order_mng.entity.Item;
import kr.co._29cm.homework.order_mng.exception.NullItemException;
import kr.co._29cm.homework.order_mng.exception.SoldOutException;
import kr.co._29cm.homework.order_mng.repository.ItemRepository;
import kr.co._29cm.homework.order_mng.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
//@CacheConfig(cacheNames = "item")
public class OrderServiceImpl implements OrderService {
    private final ItemRepository itemRepository;
    @Override
//    @Cacheable(key = "#")
    public List<ItemResponse> items() {
        return itemRepository.findAllItem();
    }

    @Override
    public Item checkOrder(OrderResponse orderResponse) {
        Item item = itemRepository.findById(orderResponse.itemId()).orElseThrow(NullItemException::new);

        if(orderResponse.itemNumber() > item.getInventory()) {
            throw new SoldOutException();
        }

        return item;
    }

}
