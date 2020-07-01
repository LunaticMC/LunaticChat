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


package xyz.poulton.lunaticchat.api.message;

import net.md_5.bungee.api.chat.BaseComponent;
import xyz.poulton.lunaticchat.api.channel.Channel;
import xyz.poulton.lunaticchat.api.encode.Encodable;

import java.util.UUID;

public class ReturnMessage implements Encodable {
    public BaseComponent[] message;
}
