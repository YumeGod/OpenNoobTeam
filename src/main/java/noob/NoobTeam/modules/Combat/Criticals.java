package noob.NoobTeam.modules.Combat;

import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import noob.NoobTeam.managers.ModuleManager;
import noob.NoobTeam.modules.Category;
import noob.NoobTeam.modules.Module;
import noob.NoobTeam.settings.ModeSetting;
import noob.NoobTeam.settings.NumberSetting;
import org.lwjgl.input.Keyboard;

public class Criticals extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", "Packet", "Watchdog", "Packet", "Legit");
    private final NumberSetting delay = new NumberSetting("Delay", 5,20, 0, 1);

    public Criticals() {
        super("Criticals", Keyboard.KEY_NONE, Category.Combat, "Crit attacks");
        this.addSettings(mode, delay);
    }

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent e) {
        this.setSuffix(mode.getMode());
        switch (mode.getMode().toLowerCase()) {
            case "watchdog":
                if (ModuleManager.getModule("KillAura").isEnabled() && e.player.onGround) {
                    if (KillAura.target.hurtTime > (int) delay.getValue()) {
                        for (double offset : new double[]{0.06f, 0.01f}) {
                            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + offset + (Math.random() * 0.001), mc.thePlayer.posZ, false));
                        }
                    }
                }
                break;
            case "packet":
                if (mc.objectMouseOver.entityHit != null && mc.thePlayer.onGround) {
                    if (mc.objectMouseOver.entityHit.hurtResistantTime > (int) delay.getValue()) {
                        for (double offset : new double[]{0.006253453, 0.002253453, 0.001253453}) {
                            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + offset, mc.thePlayer.posZ, false));
                        }
                    }
                }
                break;
            case "legit":
                if (mc.thePlayer.onGround && KillAura.attacking) {
                    mc.thePlayer.jump();
                    mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(KillAura.target, C02PacketUseEntity.Action.ATTACK));
                }
        }
    }

}