package com.graduate.be_txnd_fanzone.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    String status ;
    String message;
    T data;

    public ApiResponse(T data) {
        this.data = data;
        this.status = "success";
        this.message = null;
    }

    public ApiResponse(String status, String message) {
        this.status = status;
        this.message = message;
        this.data = null;
    }
}
