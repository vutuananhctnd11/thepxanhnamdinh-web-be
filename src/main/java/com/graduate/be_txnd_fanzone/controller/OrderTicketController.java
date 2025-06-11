package com.graduate.be_txnd_fanzone.controller;

import com.graduate.be_txnd_fanzone.dto.ApiResponse;
import com.graduate.be_txnd_fanzone.dto.orderTicket.ListOrderTicketRequest;
import com.graduate.be_txnd_fanzone.model.OrderTicket;
import com.graduate.be_txnd_fanzone.service.OrderTicketService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order-ticket")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderTicketController {

    OrderTicketService orderTicketService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createOrderTicket(@RequestBody @Valid ListOrderTicketRequest request, HttpServletRequest httpRequest) {
        String url = orderTicketService.orderTicket(request, httpRequest);
        return new ResponseEntity<>(new ApiResponse<>(url), HttpStatus.CREATED);
    }

}
