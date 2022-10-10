package noob.NoobTeam.modules.player;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import noob.NoobTeam.Client;
import noob.NoobTeam.modules.Category;
import noob.NoobTeam.modules.Module;
import org.lwjgl.input.Keyboard;

import java.lang.reflect.Field;

// skid自己客户端不算skid！
public class NoJumpDelay extends Module {

    private final Field jumpTicksFiled;
    private final Field nextStepDistanceFiled;

    public NoJumpDelay() {
        super("NoJumpDelay", Keyboard.KEY_NONE, Category.Player, "disable jump delay");

        this.jumpTicksFiled = ReflectionHelper.findField(EntityLivingBase.class, "field_70773_bE", "jumpTicks");
        this.nextStepDistanceFiled = ReflectionHelper.findField(Entity.class, "field_70150_b", "nextStepDistance");
        if (this.jumpTicksFiled != null)
            this.jumpTicksFiled.setAccessible(true);
        if (this.nextStepDistanceFiled != null)
            this.nextStepDistanceFiled.setAccessible(true);
    }

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent e){
        if (Client.nullCheck() && this.jumpTicksFiled == null && this.nextStepDistanceFiled == null) {
            if (!mc.inGameHasFocus || mc.thePlayer.capabilities.isCreativeMode) {
                return;
            }
        }

        try {
            jumpTicksFiled.set(mc.thePlayer, 0);
            nextStepDistanceFiled.set(mc.thePlayer, 0);
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
            this.disable();
        }
    }
}
