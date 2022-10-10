package noob.NoobTeam.modules.player;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import noob.NoobTeam.Client;
import noob.NoobTeam.modules.Category;
import noob.NoobTeam.modules.Module;
import noob.NoobTeam.settings.BooleanSetting;
import org.lwjgl.input.Keyboard;

public class NoFall extends Module {

    private final BooleanSetting noground = new BooleanSetting("NoGround", false);

    public NoFall() {
        super("NoFall", Keyboard.KEY_NONE, Category.Player, "make sure your won't take any fall damage.");
    }

    @SubscribeEvent
    public void onMotion(TickEvent.PlayerTickEvent e) {
        if (Client.nullCheck())
            return;
        if (mc.thePlayer.fallDistance >= 2.5) {
            if (noground.isEnabled()) {
                e.player.onGround = false;
            }
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
        }
    }

}
