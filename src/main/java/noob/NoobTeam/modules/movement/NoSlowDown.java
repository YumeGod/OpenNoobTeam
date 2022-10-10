package noob.NoobTeam.modules.movement;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.MovementInput;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import noob.NoobTeam.events.PacketEvent;
import noob.NoobTeam.modules.Category;
import noob.NoobTeam.modules.Module;
import noob.NoobTeam.settings.BooleanSetting;
import noob.NoobTeam.utils.impl.NoSlowInput;
import org.lwjgl.input.Keyboard;

public class NoSlowDown extends Module {
    MovementInput origmi;

    public static BooleanSetting shift = new BooleanSetting("InShift", false);

    public NoSlowDown() {
        super("NoSlow", Keyboard.KEY_NONE, Category.Movement, "idk");
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent e) {
        if (mc.thePlayer == null) return;
        if (mc.gameSettings.keyBindSneak.isPressed())
            return;
        if (!(mc.thePlayer.movementInput instanceof NoSlowInput)) {
            origmi = mc.thePlayer.movementInput;
            mc.thePlayer.movementInput = new NoSlowInput(mc.gameSettings);
        }
        if ((mc.thePlayer.onGround && !mc.gameSettings.keyBindJump.isKeyDown()) || !mc.gameSettings.keyBindSneak.isKeyDown()) {
            NoSlowInput move = (NoSlowInput) mc.thePlayer.movementInput;
            move.setNSD(true);
        }
    }

    @Override
    public void disable() {
        mc.thePlayer.movementInput = origmi;
    }

    @SubscribeEvent
    public void Pre(PacketEvent e) {
        if (mc.gameSettings.keyBindSneak.isPressed())
            mc.thePlayer.movementInput = origmi;
        if (isUsingFood()) {
            if (mc.thePlayer.getItemInUseDuration() >= 1) {
                mc.getNetHandler().getNetworkManager().sendPacket(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
            }
        }
    }

    public boolean isUsingFood() {
        if (mc.thePlayer.getItemInUse() == null)
            return false;
        Item usingItem = mc.thePlayer.getItemInUse().getItem();
        return mc.thePlayer.isUsingItem() && (usingItem instanceof ItemFood || usingItem instanceof ItemBucketMilk || usingItem instanceof ItemPotion);
    }
}
