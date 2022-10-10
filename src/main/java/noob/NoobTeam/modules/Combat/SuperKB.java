package noob.NoobTeam.modules.Combat;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import noob.NoobTeam.events.PacketEvent;
import noob.NoobTeam.modules.Category;
import noob.NoobTeam.modules.Module;
import noob.NoobTeam.settings.NumberSetting;
import org.lwjgl.input.Keyboard;

public class SuperKB extends Module {
    private final NumberSetting packets = new NumberSetting("Packets", 2.0, 1.5, 10.0, 0.5);

    public SuperKB() {
        super("SuperKB", Keyboard.KEY_NONE, Category.Combat, "It can make more knock back when you attack entity.");
    }

    private EntityLivingBase target;

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (mc.thePlayer.getDistanceToEntity(target) <= 1.0f || mc.thePlayer.getEntityBoundingBox().intersectsWith(target.getEntityBoundingBox())) {
            int i = 0;
            while ((double)i < this.packets.getValue() * 10.0) {
                if (mc.thePlayer.onGround) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
                }
                ++i;
            }
        }
    }

    @SubscribeEvent
    public void getEntity(PacketEvent e) {
        if (e.getPacket() instanceof C02PacketUseEntity) {
            this.target = (EntityLivingBase) ((C02PacketUseEntity) e.getPacket()).getEntityFromWorld(mc.theWorld);
        }
    }
}
