package noob.NoobTeam.modules.player;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import noob.NoobTeam.events.PacketEvent;
import noob.NoobTeam.modules.Category;
import noob.NoobTeam.modules.Module;
import noob.NoobTeam.settings.ModeSetting;
import noob.NoobTeam.utils.impl.ReflectionUtil;
import org.lwjgl.input.Keyboard;

public class SpeedMine extends Module {
    public SpeedMine() {
        super("SpeedMine", Keyboard.KEY_NONE, Category.Player, "Speeds up block breaking");
    }

    private final ModeSetting mode = new ModeSetting("Mode", "Remix", "NewPacket", "NewPacket2", "Potion", "Novovline", "Remix", "Autumn");

    private boolean bzs = false;
    private double bzx = 0.0;
    private BlockPos pos;
    private EnumFacing face;

    float curBlockDamageMP;

    @SubscribeEvent
    public void onPacket(PacketEvent event) {
        if (mode.is("Remix")) {
            if(event.getPacket() instanceof C07PacketPlayerDigging && mc.playerController != null) {
                if(((C07PacketPlayerDigging) event.getPacket()).getStatus() == C07PacketPlayerDigging.Action.START_DESTROY_BLOCK) {
                    bzs = true;
                    pos = ((C07PacketPlayerDigging) event.getPacket()).getPosition();
                    face = ((C07PacketPlayerDigging) event.getPacket()).getFacing();
                    bzx = 0.0;
                } else if(((C07PacketPlayerDigging) event.getPacket()).getStatus() == C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK || ((C07PacketPlayerDigging) event.getPacket()).getStatus() == C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK) {
                    bzs = false;
                    pos = null;
                    face = null;
                }
            }
        }
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent event) {
        ReflectionUtil.setFieldValue(mc.playerController, 0, "blockHitDelay", "field_78781_i");
        curBlockDamageMP = (float) ReflectionUtil.getFieldValue(mc.playerController, "curBlockDamageMP", "field_78770_f");
        switch (mode.getMode().toLowerCase()) {
            case "newpacket":
                if(curBlockDamageMP == 0.1) {
                    // curBlockDamageMP += 0.1;
                    ReflectionUtil.setFieldValue(mc.playerController, curBlockDamageMP += 0.1, "curBlockDamageMP", "field_78781_i");
                }
                if(curBlockDamageMP == 0.4) {
                    ReflectionUtil.setFieldValue(mc.playerController, curBlockDamageMP += 0.1, "curBlockDamageMP", "field_78781_i");
                }
                if(curBlockDamageMP == 0.7) {
                    ReflectionUtil.setFieldValue(mc.playerController, curBlockDamageMP += 0.1, "curBlockDamageMP", "field_78781_i");
                }
                break;
            case "newpacket2":
                if(curBlockDamageMP == 0.2) {
                    ReflectionUtil.setFieldValue(mc.playerController, curBlockDamageMP += 0.1, "curBlockDamageMP", "field_78781_i");
                }
                if(curBlockDamageMP == 0.4) {
                    ReflectionUtil.setFieldValue(mc.playerController, curBlockDamageMP += 0.1, "curBlockDamageMP", "field_78781_i");
                }
                if(curBlockDamageMP == 0.6) {
                    ReflectionUtil.setFieldValue(mc.playerController, curBlockDamageMP += 0.1, "curBlockDamageMP", "field_78781_i");
                }
                if(curBlockDamageMP == 0.8) {
                    ReflectionUtil.setFieldValue(mc.playerController, curBlockDamageMP += 0.2, "curBlockDamageMP", "field_78781_i");
                }
                break;
            case "potion":
                mc.thePlayer.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 10, 3));
                break;
            case "novoline":
                if(mc.theWorld != null) {
                    if(curBlockDamageMP > (mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemTool ? 0.6:0.675)) {
                        ReflectionUtil.setFieldValue(mc.playerController, 1.0, "curBlockDamageMP", "field_78781_i");
                    }
                }
                break;
            case "remix":
                if(mc.playerController.extendedReach()) {
                    ReflectionUtil.setFieldValue(mc.playerController, 0, "blockHitDelay", "field_78781_i");
                } else if(bzs) {
                    Block block = mc.theWorld.getBlockState(pos).getBlock();
                    bzx += (block.getPlayerRelativeBlockHardness(mc.thePlayer, mc.theWorld, pos) * 1.4);
                    if (bzx >= 1.0) {
                        mc.theWorld.setBlockState(pos, Blocks.air.getDefaultState(), 11);
                        mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, face));
                        bzx = 0.0;
                        bzs = false;
                    }
                }
                break;
        }
    }

    @Override
    public void disable() {
        if (mode.is("Potion")) {
            mc.thePlayer.removePotionEffect(Potion.digSpeed.getId());
        }
    }
}
