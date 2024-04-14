package kr.co._29cm.homework.order_mng.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co._29cm.homework.order_mng.dto.OrderRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("멀티스레드 활용 재고관리 테스트")
    public void 여러주문이_동시에_들어올_때_재고관리가_정상적으로되는지_테스트() throws InterruptedException {

        int numRequests = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(numRequests);
        CountDownLatch latch = new CountDownLatch(numRequests);

        for (int i = 0; i < numRequests; i++) {
            executorService.submit(() -> {
                try {
                    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/item")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(new OrderRequest(377169l, 60l))))
                            .andExpect(MockMvcResultMatchers.status().isOk())
                            .andDo(handler -> System.out.println(handler.getResponse().getContentAsString()))
                            .andReturn();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(); // 모든 요청이 완료될 때까지 대기
        executorService.shutdown();
    }
}