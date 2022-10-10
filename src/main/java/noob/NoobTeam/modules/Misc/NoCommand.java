package noob.NoobTeam.modules.Misc;

import noob.NoobTeam.modules.Category;
import noob.NoobTeam.modules.Module;
import org.lwjgl.input.Keyboard;

public class NoCommand extends Module {
    public NoCommand() {
        super("NoCommand", Keyboard.KEY_NONE, Category.Misc, "NoCommand Module");
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
