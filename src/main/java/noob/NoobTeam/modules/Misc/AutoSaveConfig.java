package noob.NoobTeam.modules.Misc;

import noob.NoobTeam.modules.Category;
import noob.NoobTeam.modules.Module;
import org.lwjgl.input.Keyboard;

public class AutoSaveConfig extends Module {
    public AutoSaveConfig() {
        super("AutoSaveConfig", Keyboard.KEY_NONE, Category.Misc, "Auto-Save Config Module");
        hide = true;
    }

    @Override
    public void enable() {
        super.enable();
    }

    @Override
    public void disable() {
        super.disable();
    }
}
