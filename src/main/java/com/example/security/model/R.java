package com.example.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * grant
 * 2024/10/9 14:30
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class R<T> {
    private int code;
    private String msg;
    private T body;

    public static R judge(boolean isOk) {
        return isOk ? success(null) : fail(null);
    }

    public static <T> R<T> success(T body) {
        return new R(200, "success", body);
    }

    public static <T> R<T> fail(T body) {
        return new R(500, "fail", body);
    }

    @JsonIgnore
    public boolean isSuccess() {
        return code == 200;
    }

    @JsonIgnore
    public boolean isFail() {
        return code != 200;
    }
}
