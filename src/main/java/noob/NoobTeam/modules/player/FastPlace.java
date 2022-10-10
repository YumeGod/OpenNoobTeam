package noob.NoobTeam.modules.player;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import noob.NoobTeam.modules.Category;
import noob.NoobTeam.modules.Module;
import noob.NoobTeam.utils.impl.ReflectionUtil;
import org.lwjgl.input.Keyboard;

public class FastPlace extends Module {
    public FastPlace() {
        super("FastPlace", Keyboard.KEY_NONE, Category.Player, "");
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        ReflectionUtil.setFieldValue(mc, 0, "rightClickDelayTimer", "field_71467_ac");
    }
}
