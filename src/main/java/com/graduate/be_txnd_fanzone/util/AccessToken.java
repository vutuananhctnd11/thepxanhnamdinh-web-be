package com.graduate.be_txnd_fanzone.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.apache.commons.codec.binary.Base64;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccessToken {

    public static class Privileges {
        public static final int kJoinChannel = 1;
        public static final int kPublishAudioStream = 2;
        public static final int kPublishVideoStream = 3;
        public static final int kPublishDataStream = 4;
    }

    String appId;
    String appCertificate;
    String channelName;
    String uid;
    int salt;
    int ts;

    Map<Integer, Integer> privileges = new HashMap<>();

    public AccessToken(String appId, String appCertificate, String channelName, String uid) {
        this.appId = appId;
        this.appCertificate = appCertificate;
        this.channelName = channelName == null ? "" : channelName;
        this.uid = uid == null ? "" : uid;
        this.salt = (int) (Math.random() * Integer.MAX_VALUE);
        this.ts = (int) (System.currentTimeMillis() / 1000 + 24 * 3600); // expire 24h
    }

    public void addPrivilege(int privilege, int expireTimestamp) {
        privileges.put(privilege, expireTimestamp);
    }

    private byte[] pack() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        putString(buffer, channelName);
        putString(buffer, uid);
        buffer.putInt(salt);
        buffer.putInt(ts);

        buffer.putShort((short) privileges.size());
        for (Map.Entry<Integer, Integer> entry : privileges.entrySet()) {
            buffer.putShort(entry.getKey().shortValue());
            buffer.putInt(entry.getValue());
        }

        buffer.flip();
        byte[] data = new byte[buffer.limit()];
        buffer.get(data);
        return data;
    }

    private static void putString(ByteBuffer buffer, String s) {
        byte[] b = s.getBytes();
        buffer.putShort((short) b.length);
        buffer.put(b);
    }

    private byte[] hmacSha256(byte[] key, byte[] message) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(key, "HmacSHA256"));
        return mac.doFinal(message);
    }

    public String build() throws Exception {
        byte[] content = pack();
        byte[] signature = hmacSha256(appCertificate.getBytes(), content);

        ByteBuffer buffer = ByteBuffer.allocate(signature.length + content.length);
        buffer.put(signature);
        buffer.put(content);

        byte[] result = buffer.array();
        String version = "006"; // version token Agora AccessToken V1
        // Token = version + appId + base64(signature+content)
        return version + appId + Base64.encodeBase64String(result);
    }
}