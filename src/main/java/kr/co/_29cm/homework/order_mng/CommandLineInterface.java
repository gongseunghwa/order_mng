package kr.co._29cm.homework.order_mng;

import kr.co._29cm.homework.order_mng.dto.ItemResponse;
import kr.co._29cm.homework.order_mng.entity.Order;
import kr.co._29cm.homework.order_mng.exception.NullItemException;
import kr.co._29cm.homework.order_mng.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Component
@RequiredArgsConstructor
public class CommandLineInterface implements CommandLineRunner {
    private final String ORDER_CODE = "o";
    private final String EXIT_CODE = "q";
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private final String ANNOUNCEMENT = "입력(o[order]: 주문, q[quit]: 종료) :";
    private final String EXIT_MESSAGE = "고객님의 주문 감사합니다.";
    private final Queue<Order> basket = new LinkedList<>();
    private final OrderService orderService;

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            String command = commandInterface();

            if(command.equals(ORDER_CODE)) {
                List<ItemResponse> items =  orderService.items();
                //TODO print Item List
                System.out.println("상품번호\t 상품명\t 판매가격\t 재고 수\t");
                items.forEach(item -> System.out.println(item.toString()));

                System.out.print("상품번호 : ");
                Long itemCode = Long.valueOf(br.readLine());
                try{
                    items.stream().filter(item -> item.itemId().equals(itemCode)).findAny().orElseThrow(NullItemException::new);
                }catch (NullItemException e) {
                    System.out.println(e.getMessage());
                }
               //TODO INPUT CODE IS VALID
               //TODO INPUT ITEM CONUNT
               //TODO CHECK ITEM INVENTORY -> CHECK ITEM LIST AND BASKET
               //TODO ADD ITEM IN MY BASKET
               //TODO PRINT MY BASKET WITH DELIVERY FEE
            }

            if(command.equals(EXIT_CODE)) {
                System.out.println(EXIT_MESSAGE);
                break;
            }
        }
    }

    public String commandInterface() throws IOException {
        System.out.printf(ANNOUNCEMENT);
        return br.readLine();
    }
}
