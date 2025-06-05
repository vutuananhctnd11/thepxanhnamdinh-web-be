package com.graduate.be_txnd_fanzone.exception;

import com.graduate.be_txnd_fanzone.dto.ApiResponse;
import jakarta.validation.ConstraintViolation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.security.Principal;
import java.util.Map;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WebsocketExceptionHandler {

    SimpMessagingTemplate messagingTemplate;

    @MessageExceptionHandler(MethodArgumentNotValidException.class)
    public void handleValidation(MethodArgumentNotValidException ex, Principal principal) {
        String errorMsg = "Trường không hợp lệ!";
        FieldError fieldError = ex.getFieldError();

        if (fieldError != null) {
            try {
                ConstraintViolation<?> violation = fieldError.unwrap(ConstraintViolation.class);
                Map<String, Object> attributes = violation.getConstraintDescriptor().getAttributes();

                Object nameAttr = attributes.get("name");
                if (nameAttr != null) {
                    errorMsg = "Trường " + nameAttr + " không được để trống!";
                }
            } catch (Exception e) {
                errorMsg = fieldError.getDefaultMessage();
            }
        }

        ApiResponse<Object> response = new ApiResponse<>("error", errorMsg);
        messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/messages", response);
    }

    @MessageExceptionHandler(CustomException.class)
    public void handleCustom(CustomException ex, Principal principal) {
        ApiResponse<Object> response = new ApiResponse<>("error", ex.getMessage());

        messagingTemplate.convertAndSendToUser(
                principal.getName(), "/queue/messages", response);
    }
}
