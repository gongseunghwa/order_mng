package kr.co._29cm.homework.order_mng.repository;

import kr.co._29cm.homework.order_mng.dto.ItemResponse;
import kr.co._29cm.homework.order_mng.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select new kr.co._29cm.homework.order_mng.dto.ItemResponse(i.id, i.name, i.price, i.inventory) from Item i")
    List<ItemResponse> findAllItem();

    Optional<Item> findById(Long id);
}
