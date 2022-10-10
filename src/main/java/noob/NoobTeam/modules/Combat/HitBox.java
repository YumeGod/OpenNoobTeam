package noob.NoobTeam.modules.Combat;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import noob.NoobTeam.modules.Category;
import noob.NoobTeam.modules.Module;
import noob.NoobTeam.settings.NumberSetting;
import noob.NoobTeam.utils.impl.EntitySize;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class HitBox extends Module {
    private final NumberSetting heights = new NumberSetting("Height", 2.0, 2.0, 5.0,1.0);
    private final NumberSetting Widths = new NumberSetting("Width", 1.0, 1.0, 5.0,1.0);
    public HitBox() {
        super("HitBox",  Keyboard.KEY_NONE, Category.Combat,"Change hitbox");
        this.addSettings(this.heights, this.Widths);
    }
    public static void setEntityBoundingBoxSize(Entity entity, float width, float height) {
        EntitySize size = getEntitySize(entity);
        entity.width = size.width;
        entity.height = size.height;
        double d0 = (double) (width) / 2.0D;
        entity.setEntityBoundingBox(new AxisAlignedBB(entity.posX - d0, entity.posY, entity.posZ - d0, entity.posX + d0,
                entity.posY + (double) height, entity.posZ + d0));
    }

    public static void setEntityBoundingBoxSize(Entity entity) {
        EntitySize size = getEntitySize(entity);
        entity.width = size.width;
        entity.height = size.height;
        double d0 = (double) (entity.width) / 2.0D;
        entity.setEntityBoundingBox(new AxisAlignedBB(entity.posX - d0, entity.posY, entity.posZ - d0, entity.posX + d0,
                entity.posY + (double) entity.height, entity.posZ + d0));
    }

    public static EntitySize getEntitySize(Entity entity) {
        return new EntitySize(0.6F, 1.8F);
    }

    public static ArrayList<EntityPlayer> getPlayersList() {
        return (ArrayList<EntityPlayer>) mc.theWorld.playerEntities;
    }

    public boolean check(EntityLivingBase entity) {
        if (entity instanceof EntityPlayerSP) {
            return false;
        }
        if (entity == mc.thePlayer) {
            return false;
        }
        return !entity.isDead;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (mc.thePlayer == null || mc.theWorld == null) {
            return;
        }
        setSuffix("Size: " + Widths.getValue());
        for (EntityPlayer player : getPlayersList()) {
            if (!check(player)) continue;
            float width = (float) this.Widths.getValue();
            float height = (float) this.heights.getValue();
            setEntityBoundingBoxSize(player, width, height);
        }
    }

    @Override
    public void disable() {
        for (EntityPlayer player : getPlayersList())
            setEntityBoundingBoxSize(player);
    }

}