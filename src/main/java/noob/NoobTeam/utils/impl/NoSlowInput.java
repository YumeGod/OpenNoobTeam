package noob.NoobTeam.utils.impl;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MovementInput;
import noob.NoobTeam.modules.movement.NoSlowDown;

public class NoSlowInput extends MovementInput {
    private final GameSettings gameSettings;
    boolean NoSlowBoolean = true;

    public NoSlowInput(GameSettings par1GameSettings) {
        this.gameSettings = par1GameSettings;
    }

    public void updatePlayerMoveState() {
        super.moveStrafe = 0.0F;
        super.moveForward = 0.0F;
        if (this.gameSettings.keyBindForward.isKeyDown()) {
            ++super.moveForward;
        }

        if (this.gameSettings.keyBindBack.isKeyDown()) {
            --super.moveForward;
        }

        if(this.gameSettings.keyBindLeft.isKeyDown()) {
            ++super.moveStrafe;
        }

        if(this.gameSettings.keyBindRight.isKeyDown()) {
            --super.moveStrafe;
        }

        super.jump = this.gameSettings.keyBindJump.isKeyDown();
        super.sneak = this.gameSettings.keyBindSneak.isKeyDown();

        if (this.NoSlowBoolean) {
            if (sneak) {
                if (!NoSlowDown.shift.isEnabled()) {
                    super.moveStrafe *= 0.2F;
                    super.moveForward *= 0.2F;
                }
            } else {
                super.moveStrafe *= 5.0F;
                super.moveForward *= 5.0F;
            }
        }
    }

    public void setNSD(boolean a) {
        this.NoSlowBoolean = a;
    }
}