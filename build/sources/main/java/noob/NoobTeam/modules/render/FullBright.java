package noob.NoobTeam.modules.render;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import noob.NoobTeam.modules.Category;
import noob.NoobTeam.modules.Module;
import org.lwjgl.input.Keyboard;

public class FullBright extends Module {
    public FullBright() {
        super("FullBright", Keyboard.KEY_NONE, Category.Render, "changes the game brightness");
    }

    @SubscribeEvent
    public void onMotion(TickEvent.ClientTickEvent e) {
        if (mc.theWorld == null)
            return;
        mc.gameSettings.gammaSetting = 100;
    }

    @Override
    public void disable() {
        mc.gameSettings.gammaSetting = 0;
        super.disable();
    }
}
