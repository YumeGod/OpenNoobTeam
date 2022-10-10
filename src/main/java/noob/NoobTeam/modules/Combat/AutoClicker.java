package noob.NoobTeam.modules.Combat;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import noob.NoobTeam.modules.Category;
import noob.NoobTeam.modules.Module;
import noob.NoobTeam.settings.BooleanSetting;
import noob.NoobTeam.settings.NumberSetting;
import noob.NoobTeam.utils.impl.Timer;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Random;

public class AutoClicker extends Module {
    private final Timer timer = new Timer();
    static Robot robot = null;
    private final BooleanSetting blockAttack = new BooleanSetting("BlockAttack", true);
    private final NumberSetting cps = new NumberSetting("CPSMax",  12.0, 1.0, 20.0, 1.0);
    private final NumberSetting cpsMin = new NumberSetting("CPSMin", 10.0, 1.0, 20.0, 1.0);

    public AutoClicker() {
        super("AutoClicker", Keyboard.KEY_NONE, Category.Combat, "这个都不知道自杀把");
        this.addSettings(blockAttack, cps, cpsMin);
        this.setSuffix(cpsMin.getValue() + " - " + cps.getValue());
    }

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent event) {
        try {
            int key = mc.gameSettings.keyBindAttack.getKeyCode();
            if (mc.gameSettings.keyBindAttack.isKeyDown()) {
                float delays = 100 / (float) getRandomDoubleInRange(cpsMin.getValue(), cps.getValue()) + 2;
                if (timer.hasTimeElapsed((long) (delays * 10), true)) {
                    mc.thePlayer.swingItem();
                    KeyBinding.onTick(key);
                    try {
                        if(!mc.gameSettings.keyBindAttack.isKeyDown())
                            return;
                        robot.mousePress(InputEvent.BUTTON1_MASK);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (NullPointerException e){}
    }

    public static double getRandomDoubleInRange(double min, double max) {
        return min >= max ? min : new Random().nextDouble() * (max - min) + min;
    }

}
