package com.graduate.be_txnd_fanzone.controller;

import com.graduate.be_txnd_fanzone.configuration.VNPayConfig;
import com.graduate.be_txnd_fanzone.dto.ApiResponse;
import com.graduate.be_txnd_fanzone.service.OrderTicketService;
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
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/vnpay")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VNPayController {

    OrderTicketService orderTicketService;

    @GetMapping("/payment-info")
    public ResponseEntity<ApiResponse<Object>> vnpayReturn(HttpServletRequest request) throws Exception {
        Map<String, String> fields = new HashMap<>();
        for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements();) {
            String fieldName = params.nextElement();
            String fieldValue = request.getParameter(fieldName);
            fields.put(fieldName, fieldValue);
        }

        String receivedHash = fields.remove("vnp_SecureHash");
        fields.remove("vnp_SecureHashType");
        String calculatedHash = VNPayConfig.hashAllFields(fields);

        if (!calculatedHash.equalsIgnoreCase(receivedHash)) {
            return new ResponseEntity<>(new ApiResponse<>("error", "Sai chữ ký", null), HttpStatus.BAD_REQUEST);
        }

        String responseCode = fields.get("vnp_ResponseCode");
        String transactionStatus = fields.get("vnp_TransactionStatus");
        String orderInfo = fields.get("vnp_OrderInfo");
        Long orderTicketId = null;

        if (orderInfo != null) {
            orderTicketId = Long.valueOf(orderInfo.split(":")[1]);
        }
        if ("00".equals(responseCode) && "00".equals(transactionStatus)) {
            orderTicketService.paymentSuccess(orderTicketId);
            return new ResponseEntity<>(new ApiResponse<>("success", "Thanh toán thành công", null), HttpStatus.OK);
        } else {
            orderTicketService.paymentError(orderTicketId);
            return new ResponseEntity<>(new ApiResponse<>("error", "Thanh toán thất bại", null), HttpStatus.BAD_REQUEST);
        }
    }

}
