package noob.NoobTeam.modules.Misc;

import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import noob.NoobTeam.modules.Category;
import noob.NoobTeam.modules.Module;
import noob.NoobTeam.settings.NumberSetting;
import noob.NoobTeam.utils.impl.Timer;
import org.lwjgl.input.Keyboard;

// TODO: Fix bugs
public class Fucker extends Module {

    private final NumberSetting reach = new NumberSetting("Reach", 4.2, 1.0, 6.0, 0.1);

    Timer timer = new Timer();

    public Fucker() {
        super("Fucker", Keyboard.KEY_NONE, Category.Misc, "");
        addSettings(reach);
    }

    private static int ticks;
    private BlockPos m;

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e) {
        if (++ticks % 20 == 0 && mc.thePlayer != null && mc.theWorld != null) {
            int ra;
            ticks = 0;
            for (int y = ra = (int)this.reach.getValue(); y >= -ra; --y) {
                block1: for (int x = -ra; x <= ra; ++x) {
                    for (int z = -ra; z <= ra; ++z) {
                        boolean bed;
                        BlockPos p = new BlockPos(mc.thePlayer.posX + (double)x, mc.thePlayer.posY + (double)y, mc.thePlayer.posZ + (double)z);
                        boolean bl = bed = mc.theWorld.getBlockState(p).getBlock() == Blocks.bed;
                        if (this.m == p) {
                            if (bed) continue;
                            this.m = null;
                            continue;
                        }
                        if (!bed) continue;
                        this.breakBlock(p);
                        this.m = p;
                        continue block1;
                    }
                }
            }
        }
    }

    private void breakBlock(BlockPos p) {
        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, p, EnumFacing.NORTH));
        mc.thePlayer.swingItem();
        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, p, EnumFacing.NORTH));
    }

}
