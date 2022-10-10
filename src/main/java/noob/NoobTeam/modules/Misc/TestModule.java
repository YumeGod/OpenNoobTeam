package noob.NoobTeam.modules.Misc;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import noob.NoobTeam.modules.Category;
import noob.NoobTeam.modules.Module;
import noob.NoobTeam.utils.impl.Timer;
import org.lwjgl.input.Keyboard;

public class TestModule extends Module {


    Timer timer = new Timer();

    public TestModule() {
        super("TestModule", Keyboard.KEY_NONE, Category.Misc, "");
    }

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent e) {
        if (timer.hasTimeElapsed(500, true)) {
            System.out.println("--------------------------");
            System.out.println(Minecraft.getMinecraft().displayHeight);
            System.out.println(Minecraft.getMinecraft().displayWidth);
            System.out.println("--------------------------");
        }
    }

}
