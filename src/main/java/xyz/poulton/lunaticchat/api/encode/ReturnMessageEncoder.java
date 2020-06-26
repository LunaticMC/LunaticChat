package xyz.poulton.lunaticchat.api.encode;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.chat.ComponentSerializer;
import xyz.poulton.lunaticchat.api.message.ReturnMessage;


public class ReturnMessageEncoder implements MessageEncoder {
    private final ReturnMessage message;

    public ReturnMessageEncoder(ReturnMessage message) {
        this.message = message;
    }

    public byte[] encodeMessage() {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("LunaticChat");
        out.writeUTF("Return");
        out.writeUTF(ComponentSerializer.toString(message.message));
        return out.toByteArray();
    }

    @Override
    public ReturnMessage decodeMessage(ByteArrayDataInput in) {
        ReturnMessage rtnMessage = new ReturnMessage();
        rtnMessage.message = ComponentSerializer.parse(in.readUTF());
        return rtnMessage;
    }
}
