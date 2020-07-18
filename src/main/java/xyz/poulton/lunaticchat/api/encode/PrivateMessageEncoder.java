//    Copyright © Lucy Poulton 2020.
//    This file is part of LunaticChat.
//
//    LunaticChat is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    LunaticChat is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with LunaticChat. If not, see <https://www.gnu.org/licenses/>.

package xyz.poulton.lunaticchat.api.encode;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.chat.ComponentSerializer;
import xyz.poulton.lunaticchat.api.message.PrivateMessage;

import java.util.UUID;

public class PrivateMessageEncoder implements MessageEncoder {
    private final PrivateMessage message;

    public PrivateMessageEncoder(PrivateMessage message) {
        this.message = message;
    }

    public byte[] encodeMessage() {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("LunaticChat");
        out.writeUTF("Private");
        out.writeUTF(message.sender.toString());
        out.writeUTF(message.target);
        out.writeUTF(ComponentSerializer.toString(message.message));
        return out.toByteArray();
    }

    @Override
    public PrivateMessage decodeMessage(ByteArrayDataInput in) {
        PrivateMessage message = new PrivateMessage();
        message.sender = UUID.fromString(in.readUTF());
        message.target = in.readUTF();
        message.message = ComponentSerializer.parse(in.readUTF());
        return message;
    }
}
