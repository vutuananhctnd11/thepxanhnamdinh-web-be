package com.graduate.be_txnd_fanzone.dto.vnpay;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VNPayResponse implements Serializable {

    String status;
    String message;
    String url;
}
