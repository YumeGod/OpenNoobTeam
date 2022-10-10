package noob.NoobTeam.modules.Combat;

import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import noob.NoobTeam.events.PacketEvent;
import noob.NoobTeam.modules.Category;
import noob.NoobTeam.modules.Module;
import noob.NoobTeam.settings.NumberSetting;
import noob.NoobTeam.utils.impl.MathUtil;
import org.lwjgl.input.Keyboard;

public class Wtap extends Module {
    public Wtap() {
        super("Wtap", Keyboard.KEY_NONE, Category.Combat, "");
    }

    private final NumberSetting chanceValue = new NumberSetting("Chance", 100.0, 1.0, 100.0, 1.0);

    @SubscribeEvent
    public void onPacket(PacketEvent e) {
        if (chanceValue.getValue() != 100.0 && MathUtil.getRandomInRange(0,100) > chanceValue.getValue()) {
            return;
        }
        if (e.getPacket() instanceof C02PacketUseEntity) {
            final C02PacketUseEntity packet = (C02PacketUseEntity)e.getPacket();
            if (packet.getAction() == C02PacketUseEntity.Action.ATTACK) {
                mc.thePlayer.setSprinting(false);
                mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                mc.thePlayer.setSprinting(true);
                mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
            }
        }
    }
}
