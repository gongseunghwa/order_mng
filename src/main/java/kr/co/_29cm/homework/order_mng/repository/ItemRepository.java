package kr.co._29cm.homework.order_mng.repository;

import kr.co._29cm.homework.order_mng.dto.ItemResponse;
import kr.co._29cm.homework.order_mng.entity.Item;
import org.springframework.cache.annotation.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@CacheConfig(cacheNames = "item")
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Cacheable(value = "'all'", sync = true)
    @Query("select new kr.co._29cm.homework.order_mng.dto.ItemResponse(i.itemId, i.itemName, i.itemPrice, i.itemInventory) from Item i")
    List<ItemResponse> findAllItem();

    @Cacheable(value = "#id", unless = "#result == null")
    Optional<Item> findById(Long id);

    @Caching(
        evict = @CacheEvict(value = "'all'", allEntries = true),
        put = @CachePut(key = "#i.itemId")
    )
    Item save(Item i);

}
