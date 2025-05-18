package com.graduate.be_txnd_fanzone.service;

import io.agora.media.RtcTokenBuilder2;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
        int expireTime = (int)(System.currentTimeMillis() / 1000) + 3600;
        RtcTokenBuilder2 builder = new RtcTokenBuilder2();
        return builder.buildTokenWithUid(appId, appCertificate, channelName, uid,
                RtcTokenBuilder2.Role.ROLE_PUBLISHER, 3600, expireTime );
    }


}
