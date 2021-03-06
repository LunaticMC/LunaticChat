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
import xyz.poulton.lunaticchat.api.message.ReturnMessage;
import xyz.poulton.lunaticchat.api.message.SpyToggleMessage;

import java.util.UUID;


public class SpyToggleMessageEncoder implements MessageEncoder {
    private final SpyToggleMessage message;

    public SpyToggleMessageEncoder(SpyToggleMessage message) {
        this.message = message;
    }

    public byte[] encodeMessage() {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("LunaticChat");
        out.writeUTF("SpyToggle");
        out.writeUTF(message.uuid.toString());
        return out.toByteArray();
    }

    @Override
    public SpyToggleMessage decodeMessage(ByteArrayDataInput in) {
        SpyToggleMessage rtnMessage = new SpyToggleMessage();
        rtnMessage.uuid = UUID.fromString(in.readUTF());
        return rtnMessage;
    }
}
