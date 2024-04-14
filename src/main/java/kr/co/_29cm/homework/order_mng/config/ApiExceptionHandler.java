package kr.co._29cm.homework.order_mng.config;

import kr.co._29cm.homework.order_mng.exception.NullItemException;
import kr.co._29cm.homework.order_mng.exception.SoldOutException;
import kr.co._29cm.homework.order_mng.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import static kr.co._29cm.homework.order_mng.utils.ApiUtils.error;


@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {

    @ExceptionHandler(NullItemException.class)
    public ApiUtils.ApiResult nullItemExceptionHandler(WebRequest request, NullItemException e) {
        return error(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SoldOutException.class)
    public ApiUtils.ApiResult soldOutExceptionHandler(WebRequest request, SoldOutException e) {
        return error(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
