package kr.co._29cm.homework.order_mng.utils;

import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.HttpStatus;

public class ApiUtils {

    public static <T> ApiResult<T> success(T response) {
        return new ApiResult<>(true, response, null);
    }

    public static ApiResult<?> error(Throwable throwable, HttpStatus status) {
        return new ApiResult<>(false, null, new ApiError(throwable, status));
    }

    public static ApiResult<?> error(String message, HttpStatus status) {
        return new ApiResult<>(false, null, new ApiError(message, status));
    }

    public static class ApiError {
        private String message;
        private int status;

        ApiError(Throwable throwable, HttpStatus status) {
            this(throwable.getMessage(), status);
        }

        ApiError(String message, HttpStatus status) {
            this.message = message;
            this.status = status.value();
        }

        ApiError() {

        }

        public String getMessage() {
            return message;
        }

        public int getStatus() {
            return status;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("message", message)
                    .append("status", status)
                    .toString();
        }
    }

    public record ApiResult<T>(boolean success, T response, ApiError error) {

        @Override
            public String toString() {
                return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                        .append("success", success)
                        .append("response", response)
                        .append("error", error)
                        .toString();
            }

            public boolean isSuccess() {
                return this.success;
            }
        }

}