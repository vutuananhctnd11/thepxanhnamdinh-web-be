package com.graduate.be_txnd_fanzone.service;

import com.graduate.be_txnd_fanzone.dto.callVideo.CallRequest;
import com.graduate.be_txnd_fanzone.util.RtcTokenBuilder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CallVideoService {

    @NonFinal
    @Value("${agora.appId}")
    String appId;

    @NonFinal
    @Value("${agora.appCertificate}")
    String appCertificate;

    public String generateCallToken(String channelName, int uid) {
        RtcTokenBuilder builder = new RtcTokenBuilder();
        int expireTime = (int)(System.currentTimeMillis() / 1000) + 3600; // 1h

        return builder.buildTokenWithUid(appId, appCertificate, channelName, uid, RtcTokenBuilder.Role.Role_Publisher, expireTime);
    }



}
