package noob.NoobTeam.modules.render;

import noob.NoobTeam.modules.Category;
import noob.NoobTeam.modules.Module;
import org.lwjgl.input.Keyboard;

public class ClickGui extends Module {

    public ClickGui() {
        super("ClickGui", Keyboard.KEY_NONE, Category.Render, "outside clickgui");
        setHide(true);
    }

    @Override
    public void enable() {
        new noob.NoobTeam.outSideGui.ClickGui().init();
    }
}
