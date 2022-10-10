package noob.NoobTeam.utils.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import noob.NoobTeam.Client;

public class Helper {

    public static Minecraft mc = Minecraft.getMinecraft();

    public static void sendMessage(String msg) {
        msg = "["+ Client.name +"] " + msg;
        new ChatUtil.ChatMessageBuilder(true, true).appendText(msg).setColor(EnumChatFormatting.DARK_AQUA).build().displayClientSided();
    }

}
