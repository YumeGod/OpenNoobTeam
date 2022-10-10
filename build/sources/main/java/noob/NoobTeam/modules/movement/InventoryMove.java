package noob.NoobTeam.modules.movement;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import noob.NoobTeam.modules.Category;
import noob.NoobTeam.modules.Module;
import noob.NoobTeam.settings.ModeSetting;
import noob.NoobTeam.utils.impl.Timer;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;
import java.util.List;

// skid
public class InventoryMove extends Module {

    private final Timer delayTimer = new Timer();
    private final ModeSetting mode = new ModeSetting("Mode", "Vanilla", "Vanilla", "Spoof", "Delay");

    public InventoryMove() {
        super("InventoryMove", Keyboard.KEY_NONE, Category.Movement, "lets you move in your inventory");
        this.addSettings(mode);
    }

    private static final List<KeyBinding> keys = Arrays.asList(
            mc.gameSettings.keyBindForward,
            mc.gameSettings.keyBindBack,
            mc.gameSettings.keyBindLeft,
            mc.gameSettings.keyBindRight,
            mc.gameSettings.keyBindJump
    );

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (!(mc.currentScreen instanceof GuiContainer)) {
            return;
        }
        double speed = 0.05;
        if (!mc.thePlayer.onGround) {
            speed /= 4.0;
        }
        this.handleJump();
        this.handleForward(speed);
        if (!mc.thePlayer.onGround) {
            speed /= 2.0;
        }
        this.handleBack(speed);
        this.handleLeft(speed);
        this.handleRight(speed);
    }

    void moveForward(double speed) {
        float direction = getDirection();
        mc.thePlayer.motionX -= (double) MathHelper.sin(direction) * speed;
        mc.thePlayer.motionZ += (double) MathHelper.cos(direction) * speed;
    }

    void moveBack(double speed) {
        float direction = getDirection();
        mc.thePlayer.motionX += (double) MathHelper.sin(direction) * speed;
        mc.thePlayer.motionZ -= (double) MathHelper.cos(direction) * speed;
    }

    void moveLeft(double speed) {
        float direction = getDirection();
        mc.thePlayer.motionZ += (double) MathHelper.sin(direction) * speed;
        mc.thePlayer.motionX += (double) MathHelper.cos(direction) * speed;
    }

    void moveRight(double speed) {
        float direction = getDirection();
        mc.thePlayer.motionZ -= (double) MathHelper.sin(direction) * speed;
        mc.thePlayer.motionX -= (double) MathHelper.cos(direction) * speed;
    }

    void handleForward(double speed) {
        if (!Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode())) {
            return;
        }
        this.moveForward(speed);
    }

    void handleBack(double speed) {
        if (!Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode())) {
            return;
        }
        this.moveBack(speed);
    }

    void handleLeft(double speed) {
        if (!Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode())) {
            return;
        }
        this.moveLeft(speed);
    }

    void handleRight(double speed) {
        if (!Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode())) {
            return;
        }
        this.moveRight(speed);
    }

    void handleJump() {
        if (mc.thePlayer.onGround && Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode())) {
            mc.thePlayer.jump();
        }
    }

    private float getDirection() {
        float var1 = mc.thePlayer.rotationYaw;
        if (mc.thePlayer.moveForward < 0.0f) {
            var1 += 180.0f;
        }
        float forward = 1.0f;
        if (mc.thePlayer.moveForward < 0.0f) {
            forward = -0.5f;
        } else if (mc.thePlayer.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (mc.thePlayer.moveStrafing > 0.0f) {
            var1 -= 90.0f * forward;
        }
        if (mc.thePlayer.moveStrafing < 0.0f) {
            var1 += 90.0f * forward;
        }
        return var1 *= (float) Math.PI / 180;
    }

}
