package noob.NoobTeam.modules.movement;

import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import noob.NoobTeam.events.PacketEvent;
import noob.NoobTeam.modules.Category;
import noob.NoobTeam.modules.Module;
import org.lwjgl.input.Keyboard;

public class KeepSprint extends Module {
    public KeepSprint() {
        super("KeepSprint", Keyboard.KEY_NONE, Category.Movement, "It can make cancelled all stop sprint packet.");
    }

    @SubscribeEvent
    public void onPacket(PacketEvent e) {
        if (e.getPacket() instanceof C0BPacketEntityAction) {
            C0BPacketEntityAction packet = (C0BPacketEntityAction) e.getPacket();
            if (packet.getAction() == C0BPacketEntityAction.Action.STOP_SPRINTING) {
                e.setCanceled(true);
            }
        }
    }
}
