package kr.co._29cm.homework.order_mng.repository;

import kr.co._29cm.homework.order_mng.dto.ItemResponse;
import kr.co._29cm.homework.order_mng.entity.Item;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@CacheConfig(cacheNames = "items")
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Cacheable(value = "`all`", sync = true)
    @Query("select new kr.co._29cm.homework.order_mng.dto.ItemResponse(i.id, i.name, i.price, i.inventory) from Item i")
    List<ItemResponse> findAllItem();

//    @Cacheable(value = "#id", unless = "#result == null", sync = true)
    Optional<Item> findById(Long id);

}
