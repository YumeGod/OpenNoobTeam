package noob.NoobTeam.modules.Combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import noob.NoobTeam.modules.Category;
import noob.NoobTeam.modules.Module;
import noob.NoobTeam.settings.NumberSetting;
import org.lwjgl.input.Keyboard;

import java.util.List;
import java.util.Random;

import static noob.NoobTeam.modules.Combat.HitBox.getPlayersList;

public class Reach extends Module {

   private MovingObjectPosition moving;
   private Entity pointedEntity;

   private static float randReach = 3.0f;
   private double d0;


   private final NumberSetting MinReach = new NumberSetting("MinReach", 3.0, 1.0, 6.0, 0.1);
   private final NumberSetting MaxReach = new NumberSetting("MaxReach", 3.4, 1.0, 6.0, 0.1);

    public Reach() {
        super("Reach", Keyboard.KEY_J, Category.Combat, "Nothing.");
        this.addSettings(MinReach, MaxReach);
    }

    @Override
    public void enable() {
        super.enable();
    }

    @Override
    public void disable() {
        this.getMouseOver(0.07f);
        super.disable();
    }

    @SubscribeEvent
    public void onMouse(MouseEvent event) {
        try {
            if (this.moving != null && event.button == 0 && event.buttonstate) {
                mc.objectMouseOver = this.moving;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent e) {
        if (mc.theWorld != null) {
            double minReach = MinReach.getValue();
            double maxReach = MaxReach.getValue();
            int min = (int)(minReach *= 100.0f);
            int max = (int)(maxReach *= 100.0f);
            minReach /= 100.0f;
            maxReach /= 100.0f;
            Random r = new Random();
            int re = r.nextInt(max - min + 1) + min;
            double randomized = re;
            randReach = (float)(randomized /= 100.0);
            this.getMouseOver(1.0f);
        }
    }

    private void getMouseOver(float p_78473_1_) {
        if (mc.getRenderViewEntity() != null && mc.theWorld != null) {
            mc.pointedEntity = null;
            d0 = randReach;
            this.moving = mc.getRenderViewEntity().rayTrace(d0, p_78473_1_);
            double d1 = d0;
            Vec3 vec3 = mc.getRenderViewEntity().getPositionEyes(p_78473_1_);
            if (this.moving != null) {
                d1 = this.moving.hitVec.distanceTo(vec3);
            }
            Vec3 vec31 = mc.getRenderViewEntity().getLook(p_78473_1_);
            Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
            this.pointedEntity = null;
            Vec3 vec33 = null;
            float f1 = 1.0f;
            List list = mc.theWorld.getEntitiesWithinAABBExcludingEntity(mc.getRenderViewEntity(), mc.getRenderViewEntity().getEntityBoundingBox().addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand(f1, f1, f1));
            double d2 = d1;
            for (int i = 0; i < list.size(); ++i) {
                double d3;
                Entity entity = (Entity)list.get(i);
                if (!entity.canBeCollidedWith()) continue;
                float f2 = 0.13f;
                AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expand(f2, f2, f2);
                MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);
                if (axisalignedbb.isVecInside(vec3)) {
                    if (!(0.0 < d2) && d2 != 0.0) continue;
                    this.pointedEntity = entity;
                    vec33 = movingobjectposition == null ? vec3 : movingobjectposition.hitVec;
                    d2 = 0.0;
                    continue;
                }
                if (movingobjectposition == null || !((d3 = vec3.distanceTo(movingobjectposition.hitVec)) < d2) && d2 != 0.0) continue;
                if (entity == mc.getRenderViewEntity().ridingEntity && !entity.canRiderInteract()) {
                    if (d2 != 0.0) continue;
                    this.pointedEntity = entity;
                    vec33 = movingobjectposition.hitVec;
                    continue;
                }
                this.pointedEntity = entity;
                vec33 = movingobjectposition.hitVec;
                d2 = d3;
            }
            if (this.pointedEntity != null && (d2 < d1 || this.moving == null)) {
                this.moving = new MovingObjectPosition(this.pointedEntity, vec33);
                if (this.pointedEntity instanceof EntityLivingBase || this.pointedEntity instanceof EntityItemFrame) {
                    mc.pointedEntity = this.pointedEntity;
                }
            }
        }
    }
}
