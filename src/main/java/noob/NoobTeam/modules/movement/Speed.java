package noob.NoobTeam.modules.movement;

import net.minecraft.util.Timer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import noob.NoobTeam.Client;
import noob.NoobTeam.modules.Category;
import noob.NoobTeam.modules.Module;
import noob.NoobTeam.settings.ModeSetting;
import noob.NoobTeam.settings.NumberSetting;
import noob.NoobTeam.utils.impl.ReflectionUtil;
import org.lwjgl.input.Keyboard;

public class Speed extends Module {
    private final ModeSetting mode = new ModeSetting("SpeedMode", "ncp", "Legit", "Hop", "Ground", "ncp");
    private final NumberSetting timer = new NumberSetting("Timer", 1.2, 1.0, 5.0, 0.1);

    private float speed;
    private int stage;

    Timer Gametimer = (Timer) ReflectionUtil.getFieldValue(mc, "timer", "field_71428_T");

    public Speed() {
        super("Speed", Keyboard.KEY_NONE, Category.Movement, "这个也不知道可以紫砂");
    }

    @Override
    public void enable() {
        super.enable();
    }

    @Override
    public void disable() {
        Gametimer.timerSpeed = 1.0f;
        super.disable();
    }

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent e) {
        Strafe.strafe();
        if (Client.nullCheck())
            return;
        switch (mode.getMode().toLowerCase()) {
            case "ncp":
                if (isMoving()) {
                    if (mc.thePlayer.onGround) {
                        Gametimer.timerSpeed = 1.15f;
                        mc.thePlayer.jump();
                        Gametimer.timerSpeed = 1.0f;
                    }
                }
            case "legit":
                Gametimer.timerSpeed = 1.07f;
            case "ground":
                if (isMoving()) {
                    Gametimer.timerSpeed = (float) timer.getValue();
                }
        }
    }

    public static boolean isMoving() {
        if (mc.thePlayer == null) {
            return false;
        }
        return (mc.thePlayer.movementInput.moveForward != 0F || mc.thePlayer.movementInput.moveStrafe != 0F);
    }
}
