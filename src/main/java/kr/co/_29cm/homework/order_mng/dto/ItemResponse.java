package kr.co._29cm.homework.order_mng.dto;

public record ItemResponse(Long itemId, String itemName, Long itemPrice, Long itemInventory) {

    @Override
    public String toString() {

        return this.itemId + "\t" + this.itemName + "\t" + this.itemPrice + "\t" + this.itemInventory;
    }
}
