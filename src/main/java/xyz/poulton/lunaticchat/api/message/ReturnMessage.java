// Copyright Lucy Poulton 2020. All rights reserved.
package xyz.poulton.lunaticchat.api.message;

import net.md_5.bungee.api.chat.BaseComponent;
import xyz.poulton.lunaticchat.api.channel.Channel;
import xyz.poulton.lunaticchat.api.encode.Encodable;

import java.util.UUID;

public class ReturnMessage implements Encodable {
    public BaseComponent[] message;
}
