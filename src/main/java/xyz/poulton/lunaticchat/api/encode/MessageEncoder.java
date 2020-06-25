package xyz.poulton.lunaticchat.api.encode;

import com.google.common.io.ByteArrayDataInput;

import java.lang.reflect.Type;

public interface MessageEncoder {
     byte[] encodeMessage();

     Encodable decodeMessage(ByteArrayDataInput message);
}
