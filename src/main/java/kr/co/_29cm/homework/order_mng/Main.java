package kr.co._29cm.homework.order_mng;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co._29cm.homework.order_mng.dto.ItemResponse;
import kr.co._29cm.homework.order_mng.dto.OrderRequest;
import kr.co._29cm.homework.order_mng.dto.OrderResponse;
import kr.co._29cm.homework.order_mng.utils.ApiUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.List;


@Slf4j
public class Main {
    private static final String ANNOUNCEMENT_COMMENT = "입력(o[order]: 주문, q[quit]: 종료) :";
    private static final String EXIT_COMMENT = "고객님의 주문 감사합니다.";
    private static final String TABLE_LABEL = "상품번호\t상품명\t\t\t\t\t\t\t판매가격\t\t재고수";
    private static final String RESULT_COMMENT =
                """
                주문 내역 : 
                =================================================
                """;
    private static final DecimalFormat formatter = new DecimalFormat("###,###");
    private static final String BASE_URL = "http://localhost:8080/api/v1/item";
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final ObjectMapper mapper = new ObjectMapper();
    public static void main(String[] args) {
        while (true) {
            System.out.print(ANNOUNCEMENT_COMMENT);
            String input;
            try{
                input = br.readLine();
                if(isExitCode(input)) {
                    System.out.println(EXIT_COMMENT);
                    break;
                }

                if(isOrderCode(input)) {
                    printMenu();
                    OrderProcess();
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    private static void OrderProcess() throws IOException {
        System.out.print("상품번호 : ");
        Long itemId = Long.valueOf(br.readLine());
        System.out.println("수량 : ");
        Long itemCount = Long.valueOf(br.readLine());

        ResponseEntity<ApiUtils.ApiResult> result = doOrder(itemId, itemCount);
        if(result.getBody().isSuccess()) {
            OrderResponse orderResponse = mapper.convertValue(result.getBody().response(), OrderResponse.class);
            prettyPrintOrderResponse(orderResponse);
        } else {
            System.out.println(result.getBody().error().getMessage());
        }

    }

    private static void prettyPrintOrderResponse(OrderResponse orderResponse) {
        StringBuilder sb = new StringBuilder();
        sb.append(RESULT_COMMENT);
        orderResponse.orderDetails().forEach(item ->sb.append(item.itemName() + " - " + item.itemCount() + "개\n"));
        sb.append("=================================================\n");
        orderResponse.fees().forEach(item -> sb.append(item.getName() + " : " + formatter.format(item.getValue()) + "원\n"));
        sb.append("=================================================\n");
        sb.append("지불금액 : " + formatter.format(orderResponse.totalFee()) + "원\n");
        sb.append("=================================================\n");
        System.out.println(sb);
    }

    private static ResponseEntity<ApiUtils.ApiResult> doOrder(Long itemId, Long itemCount) throws JsonProcessingException {
        OrderRequest request = new OrderRequest(itemId, itemCount);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(mapper.writeValueAsString(request), headers);

        return restTemplate.postForEntity(BASE_URL, requestEntity , ApiUtils.ApiResult.class);


    }

    private static void printMenu() throws JsonProcessingException {
        ResponseEntity<String> result = restTemplate.getForEntity(BASE_URL, String.class);
        List<ItemResponse> itemResponseList = mapper.readValue(result.getBody(), new TypeReference<>() {});
        System.out.println(TABLE_LABEL);
        itemResponseList.forEach(System.out::println);
    }

    private static boolean isOrderCode(String input) {
        return input.equals("o");
    }

    private static boolean isExitCode(String input) {
        return input.equals("quit") || input.equals("q");
    }
}
