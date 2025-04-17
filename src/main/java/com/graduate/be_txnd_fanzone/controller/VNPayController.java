package com.graduate.be_txnd_fanzone.controller;

import com.graduate.be_txnd_fanzone.dto.ApiResponse;
import com.graduate.be_txnd_fanzone.service.VnpayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/vnpay")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VNPayController {

    VnpayService vnPayService;

    @GetMapping("/payment-info")
    public ResponseEntity<?> handleVNPayReturn(HttpServletRequest request) {
        String status = vnPayService.getPaymentInfo(request);
        if ( "00".equals(status)) {
            return new ResponseEntity<>(new ApiResponse<>("success","Thành công" ), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse<>("error","Thất bại" ), HttpStatus.BAD_REQUEST);
        }
    }
}
