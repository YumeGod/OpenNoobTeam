package noob.NoobTeam.modules.movement;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import noob.NoobTeam.modules.Category;
import noob.NoobTeam.modules.Module;
import org.lwjgl.input.Keyboard;

public class Strafe extends Module {
    public Strafe() {
        super("Strafe", Keyboard.KEY_NONE, Category.Movement, "");
    }
    
    public static void strafe() {
        Strafe.strafe(Strafe.getSpeed());
    }

    public static void strafe(float f) {
        if (!Strafe.isMoving()) {
            return;
        }
        double d = Strafe.getDirection();
        mc.thePlayer.motionX = -Math.sin(d) * (double)f;
        mc.thePlayer.motionZ = Math.cos(d) * (double)f;
    }

    public static float getSpeed() {
        return (float)Math.sqrt(mc.thePlayer.motionX * Strafe.mc.thePlayer.motionX + Strafe.mc.thePlayer.motionZ * Strafe.mc.thePlayer.motionZ);
    }

    public static double getDirection() {
        float f = mc.thePlayer.rotationYaw;
        if (mc.thePlayer.moveForward < 0.0f) {
            f += 180.0f;
        }
        float f2 = 1.0f;
        if (Strafe.mc.thePlayer.moveForward < 0.0f) {
            f2 = -0.5f;
        } else if (Strafe.mc.thePlayer.moveForward > 0.0f) {
            f2 = 0.5f;
        }
        if (Strafe.mc.thePlayer.moveStrafing > 0.0f) {
            f -= 90.0f * f2;
        }
        if (Strafe.mc.thePlayer.moveStrafing < 0.0f) {
            f += 90.0f * f2;
        }
        return Math.toRadians(f);
    }


    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent playerTickEvent) {
        Strafe.strafe();
    }

    public static boolean isMoving() {
        return Strafe.mc.thePlayer != null && (Strafe.mc.thePlayer.movementInput.moveForward != 0.0f || Strafe.mc.thePlayer.movementInput.moveStrafe != 0.0f);
    }
}
