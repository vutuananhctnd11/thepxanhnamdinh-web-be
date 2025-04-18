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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/vnpay")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VNPayController {

    VnpayService vnPayService;

    @GetMapping("/payment-info")
    public RedirectView handleVNPayReturn(@RequestParam(value = "vnp_ResponseCode") String status) {
        String redirectUrl;
        if ("00".equals(status)) {
            redirectUrl = "http://localhost:5173/payment-status?success=true";
        } else {
            redirectUrl = "http://localhost:5173/payment-status?success=false";
        }
        return new RedirectView(redirectUrl);
    }
}
