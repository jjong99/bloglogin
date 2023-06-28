package com.sparta.bloglogin.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ApiResult {

    private String msg;
    private int statusCode;

    public ApiResult(String msg, int statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }

}
