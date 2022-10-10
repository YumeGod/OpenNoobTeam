package noob.NoobTeam.utils.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import noob.NoobTeam.utils.Util;

public class PlayerUtil implements Util {

    public static EntityPlayerSP player = mc.thePlayer;

    public static void syncCurrentPlayItem() {
        int i = mc.thePlayer.inventory.currentItem;
        int currentPlayerItem = (int) ReflectionUtil.getFieldValue(mc.playerController, "currentPlayerItem", "field_78777_l");

        if (i != currentPlayerItem) {
            ReflectionUtil.setFieldValue(mc.playerController, i, "currentPlayerItem", "field_78777_l");
            mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange((int) ReflectionUtil.getFieldValue(mc.playerController, "currentPlayerItem", "field_78777_l")));
        }
    }

}
