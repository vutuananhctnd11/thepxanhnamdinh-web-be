package com.graduate.be_txnd_fanzone.service;

import com.google.zxing.WriterException;
import com.graduate.be_txnd_fanzone.enums.ErrorCode;
import com.graduate.be_txnd_fanzone.exception.CustomException;
import com.graduate.be_txnd_fanzone.util.QRCodeGenerator;
import io.jsonwebtoken.io.IOException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {

    JavaMailSender mailSender;
    TemplateEngine templateEngine;

    @NonFinal
    @Value("${spring.mail.username}")
    String from;

    public void sendForgotPasswordEmail(String to, String username, String otp) {
        try {
            Context context = new Context();
            context.setVariable("username", username);
            context.setVariable("otp", otp);

            String html = templateEngine.process("forgot-password-email", context);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("[QUAN TRỌNG] Xác nhận tài khoản TXND FanZone của bạn!");
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setText(html, true);
            mailSender.send(mimeMessage);

        } catch (Exception e) {
            throw new CustomException(ErrorCode.CAN_NOT_SEND_EMAIL);
        }
    }

    public void sendDeleteUserEmail(String to, String userFullName, String userDelete, String emailSupport) {
        try {
            Context context = new Context();
            context.setVariable("userFullName", userFullName);
            context.setVariable("userDelete", userDelete);
            context.setVariable("emailSupport", emailSupport);

            String html = templateEngine.process("delete-user-email", context);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("[QUAN TRỌNG] Tài khoản TXND FanZone của bạn đã bị xóa!");
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setText(html, true);
            mailSender.send(mimeMessage);

        } catch (Exception e) {
            throw new CustomException(ErrorCode.CAN_NOT_SEND_EMAIL);
        }
    }

    public void sendCreateUserEmail(String to, String username, String password) {
        try {
            Context context = new Context();
            context.setVariable("username", username);
            context.setVariable("password", password);

            String html = templateEngine.process("create-user-email", context);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("[QUAN TRỌNG] Bạn đã được tạo một tài khoản trên hệ thống TXND FanZone!");
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setText(html, true);
            mailSender.send(mimeMessage);

        } catch (Exception e) {
            throw new CustomException(ErrorCode.CAN_NOT_SEND_EMAIL);
        }
    }

    public void sendOrderTicketEmail(String to, String fullName, LocalDateTime createDate, List<String> orderTicketDetailIds) {
        try {
            Context context = new Context();
            context.setVariable("fullName", fullName);
            context.setVariable("createDate", createDate);
            context.setVariable("orderTicketDetailIds", orderTicketDetailIds);

            // Chuẩn bị QR codes
            Map<String, byte[]> qrCodeMap = new HashMap<>();
            for (int i = 0; i < orderTicketDetailIds.size(); i++) {
                String cid = "qrCode_" + i;
                byte[] qrImage = QRCodeGenerator.generateQRCodeImage(orderTicketDetailIds.get(i), 300, 300);
                qrCodeMap.put(cid, qrImage);
            }

            String html = templateEngine.process("order-ticket-email", context);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("[TXND FanZone] Đặt vé thành công!");
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setText(html, true);

            for (Map.Entry<String, byte[]> entry : qrCodeMap.entrySet()) {
                mimeMessageHelper.addInline(entry.getKey(), new ByteArrayResource(entry.getValue()), "image/png");
            }

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.CAN_NOT_SEND_EMAIL);
        }
    }



}
