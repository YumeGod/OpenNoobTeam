package noob.NoobTeam.modules.Combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import noob.NoobTeam.events.PacketEvent;
import noob.NoobTeam.modules.Category;
import noob.NoobTeam.modules.Module;
import noob.NoobTeam.settings.BooleanSetting;
import noob.NoobTeam.settings.NumberSetting;
import noob.NoobTeam.utils.impl.PlayerUtil;
import noob.NoobTeam.utils.impl.Timer;
import org.lwjgl.input.Keyboard;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class KillAura extends Module {

    public Timer timer = new Timer();
    private final NumberSetting range = new NumberSetting("Range", 3.4, 1, 6, 0.1);
    private final BooleanSetting noSwing = new BooleanSetting("noSwing", false);
    private final NumberSetting aps = new NumberSetting("APS", 10,1,20,1);

    private final BooleanSetting LockView = new BooleanSetting("LockView", false);
    private final BooleanSetting autoblock = new BooleanSetting("AutoBlock", true);

    public static EntityLivingBase target;
    public static Boolean attacking;

    private static List<Entity> targets;

    public KillAura() {
        super("Killaura", Keyboard.KEY_NONE, Category.Combat, "Nothing.");
        this.addSettings(range, noSwing, aps, autoblock);
    }

    @Override
    public void enable() {
        super.enable();
    }

    @Override
    public void disable() {
        attacking = false;
        super.disable();
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent e) {
        targets = mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());

        targets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < range.getValue() && entity != mc.thePlayer && !entity.isDead).collect(Collectors.toList());
        targets.sort(Comparator.comparingDouble(entity -> entity.getDistanceToEntity(mc.thePlayer)));

        if (!targets.isEmpty()) {
            target = (EntityLivingBase) targets.get(0);

            //Rotations
            if (LockView.isEnabled()) {
                mc.thePlayer.rotationYaw = (getRotationsToEnt(target)[0]);
                mc.thePlayer.rotationPitch = (getRotationsToEnt(target)[1]);
            }

            //autoblock
            if (autoblock.isEnabled() && mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) {
                // ab不会写 ((
            }

            if (timer.hasTimeElapsed((long) (1000 / aps.getValue()), true)){
                if (noSwing.isEnabled()) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                }else {
                    mc.thePlayer.swingItem();
                }
                attacking = true;
                mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
            }
        } else {
            attacking = false;
        }
    }

    @SubscribeEvent
    public void antiBotsEvent(PacketEvent e) {
        if (target != null) {
            if (e.getPacket() instanceof S18PacketEntityTeleport) {
                S18PacketEntityTeleport s18 = (S18PacketEntityTeleport) e.getPacket();

                if (target.getEntityId() == s18.getEntityId()) {
                    if (s18.getX() == mc.thePlayer.posX && s18.getY() == mc.thePlayer.posY && s18.getZ() == mc.thePlayer.posZ) {
                        targets.remove(target);
                    }
                }
            }
        }
    }

    private float[] getRotationsToEnt(Entity ent) {
        final double differenceX = ent.posX - mc.thePlayer.posX;
        final double differenceY = (ent.posY + ent.height) - (mc.thePlayer.posY + mc.thePlayer.height) - 0.5;
        final double differenceZ = ent.posZ - mc.thePlayer.posZ;
        final float rotationYaw = (float) (Math.atan2(differenceZ, differenceX) * 180.0D / Math.PI) - 90.0f;
        final float rotationPitch = (float) (Math.atan2(differenceY, mc.thePlayer.getDistanceToEntity(ent)) * 180.0D
                / Math.PI);
        final float finishedYaw = mc.thePlayer.rotationYaw
                + MathHelper.wrapAngleTo180_float(rotationYaw - mc.thePlayer.rotationYaw);
        final float finishedPitch = mc.thePlayer.rotationPitch
                + MathHelper.wrapAngleTo180_float(rotationPitch - mc.thePlayer.rotationPitch);
        return new float[]{finishedYaw, -MathHelper.clamp_float(finishedPitch, -90, 90)};
    }
}
