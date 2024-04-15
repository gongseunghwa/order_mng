package kr.co._29cm.homework.order_mng;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co._29cm.homework.order_mng.dto.ItemResponse;
import kr.co._29cm.homework.order_mng.dto.OrderRequest;
import kr.co._29cm.homework.order_mng.dto.OrderResponse;
import kr.co._29cm.homework.order_mng.utils.ApiUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@Profile("!test")
public class CommandLineRunner implements org.springframework.boot.CommandLineRunner {


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
    private static final List<OrderRequest> orderRequests = new ArrayList<>();
    @Override
    public void run(String... args) throws Exception {
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
                    while (true) {
                        if(orderProcess()) {
                            break;
                        }
                    }
                    doPayment();
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            orderRequests.clear();
        }
    }

    private static boolean orderProcess() throws IOException {
        System.out.print("상품번호 : ");
        String itemId = br.readLine();
        System.out.print("수량 : ");
        String itemCount = br.readLine();

        if(itemId.equals(" ") && itemCount.equals(" ")) {
            return true;
        }
        try {
            orderRequests.add(new OrderRequest(Long.valueOf(itemId), Long.valueOf(itemCount)));
        }catch (Exception e) {
            return false;
        }
        return false;
    }

    private static void doPayment() throws IOException {

        ResponseEntity<ApiUtils.ApiResult> result = doOrder();
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

    private static ResponseEntity<ApiUtils.ApiResult> doOrder() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(mapper.writeValueAsString(orderRequests), headers);

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
