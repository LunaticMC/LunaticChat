package xyz.poulton.lunaticchat.api.encode;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.chat.ComponentSerializer;
import xyz.poulton.lunaticchat.api.message.ChatMessage;
import xyz.poulton.lunaticchat.api.channel.Channel;

import java.util.UUID;

public class ChatMessageEncoder implements MessageEncoder {
    private final ChatMessage message;

    public ChatMessageEncoder(ChatMessage message) {
        this.message = message;
    }

    public byte[] encodeMessage() {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("LunaticChat");
        out.writeUTF("Message");
        out.writeUTF(message.senderUsername);
        out.writeUTF(message.sender.toString());
        out.writeUTF(message.channel);
        out.writeUTF(message.server);
        out.writeUTF(ComponentSerializer.toString(message.message));
        return out.toByteArray();
    }

    @Override
    public ChatMessage decodeMessage(ByteArrayDataInput in) {
        ChatMessage message = new ChatMessage();
        message.senderUsername = in.readUTF();
        message.sender = UUID.fromString(in.readUTF());
        message.channel = in.readUTF();
        message.server = in.readUTF();
        message.message = ComponentSerializer.parse(in.readUTF());
        return message;
    }
}
