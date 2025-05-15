package com.graduate.be_txnd_fanzone.controller;

import com.graduate.be_txnd_fanzone.dto.ApiResponse;
import com.graduate.be_txnd_fanzone.dto.callVideo.CallMessage;
import com.graduate.be_txnd_fanzone.service.CallVideoService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CallVideoController {

    CallVideoService callVideoService;
    SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/call")
    @SendToUser("/queue/call")
    public CallMessage handleCall(@Payload CallMessage message, Principal principal) {
        switch (message.getType()) {
            case "CALL_OFFER":
                // gửi tới người nhận
                messagingTemplate.convertAndSendToUser(String.valueOf(message.getToUserId()), "/queue/call", message);
                break;
            case "CALL_ACCEPT":
            case "CALL_REJECT":
                // gửi lại cho người gọi
                messagingTemplate.convertAndSendToUser(String.valueOf(message.getToUserId()), "/queue/call", message);
                break;
        }
        return null;
    }

    @GetMapping("/call/token")
    public ResponseEntity<ApiResponse<String>> getAgoraToken(@RequestParam String channel, @RequestParam int uid) {
        ApiResponse<String> apiResponse = new ApiResponse<>(callVideoService.generateCallToken(channel, uid));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
