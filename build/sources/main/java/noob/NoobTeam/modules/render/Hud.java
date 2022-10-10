package noob.NoobTeam.modules.render;

import noob.NoobTeam.managers.ModuleManager;
import noob.NoobTeam.modules.Category;
import noob.NoobTeam.modules.Module;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import noob.NoobTeam.utils.impl.ColorUtil;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class Hud extends Module {
    public Hud() {
        super("HUD", Keyboard.KEY_H, Category.Render, "Show Module information");
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Text event) {
        ScaledResolution s = new ScaledResolution(mc);
        int width = new ScaledResolution(mc).getScaledWidth();
        int height = new ScaledResolution(mc).getScaledHeight();
        int y = 1;
        if (mc.currentScreen != null && !(mc.currentScreen instanceof GuiMainMenu)) return;
        ArrayList<Module> enabledModules = new ArrayList<>();
        for (Module m : ModuleManager.getModules()) {
            if (m.state) {
                enabledModules.add(m);
            }
        }
        for (Module m : ModuleManager.getModules()) {
            if (m.hide) {
                enabledModules.remove(m);
            }
        }
        enabledModules.sort((o1, o2) -> mc.fontRendererObj.getStringWidth(o2.getName()) - mc.fontRendererObj.getStringWidth(o1.getName()));

        int r = 0;
        for (Module m : enabledModules) {
            if (m != null && m.isEnabled()) {
                String name = m.suffix.isEmpty() ? m.name : m.name + " [" + m.suffix + "]";
                int moduleWidth = mc.fontRendererObj.getStringWidth(name);
                mc.fontRendererObj.drawString(name, width - moduleWidth - 1, y, ColorUtil.rainbow(1) + r);
                y += mc.fontRendererObj.FONT_HEIGHT;
                r+=10;
            }
        }
    }

}
