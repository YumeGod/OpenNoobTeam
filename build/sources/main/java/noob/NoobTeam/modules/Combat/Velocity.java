package noob.NoobTeam.modules.Combat;

import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import noob.NoobTeam.Client;
import noob.NoobTeam.events.PacketEvent;
import noob.NoobTeam.modules.Category;
import noob.NoobTeam.modules.Module;
import noob.NoobTeam.settings.BooleanSetting;
import noob.NoobTeam.settings.NumberSetting;
import noob.NoobTeam.utils.impl.ReflectionUtil;
import org.lwjgl.input.Keyboard;

public class Velocity extends Module {
    private final NumberSetting horizontal = new NumberSetting("Horizontal", 0.0, 0.0, 100.0,1.0);
    private final NumberSetting vertical = new NumberSetting("Vertical", 0.0, 0.0, 100.0,1.0);
    private final NumberSetting chance = new NumberSetting("Chance", 100.0, 0.0, 100.0,1.0);
    private final BooleanSetting Targeting = new BooleanSetting("On Targeting", false);
    private final BooleanSetting NoSword = new BooleanSetting("No Sword", false);
    public Velocity() {
        super("Velocity", Keyboard.KEY_NONE, Category.Combat,"Reduces your knockback");
        this.addSettings(this.horizontal,this.vertical,this.chance,this.Targeting,this.NoSword);
    }
    @SubscribeEvent
    public void onPacket(PacketEvent e) {
        if(Client.nullCheck())
            return;

        if (e.getPacket() instanceof S12PacketEntityVelocity) {
            if (this.Targeting.isEnabled() && (mc.objectMouseOver == null || mc.objectMouseOver.entityHit == null)) {
                return;
            }

            if (this.NoSword.isEnabled() && Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode())) {
                return;
            }
            
            S12PacketEntityVelocity packet = (S12PacketEntityVelocity) e.getPacket();
            
            if(packet.getEntityID() != mc.thePlayer.getEntityId())
            	return;
            
            double motionX = packet.getMotionX(),
            	   motionY = packet.getMotionY(),
            	   motionZ = packet.getMotionZ();
            
            if (this.chance.getValue() != 100.0D) {
                double ch = Math.random();
                if (ch >= this.chance.getValue() / 100.0D) {
                    return;
                }
            }
            

            if (this.horizontal.getValue() != 100.0D) {
                motionX *= this.horizontal.getValue() / 100.0D;
                motionZ *= this.horizontal.getValue() / 100.0D;
            }

            if (this.vertical.getValue() != 100.0D) {
                motionY *= this.vertical.getValue() / 100.0D;
            }
            ReflectionUtil.setFieldValue(packet, (int)motionX, "motionX", "field_149415_b");
            ReflectionUtil.setFieldValue(packet, (int)motionY, "motionY", "field_149416_c");
            ReflectionUtil.setFieldValue(packet, (int)motionZ, "motionZ", "field_149414_d");
        }
        
        if (e.getPacket() instanceof S27PacketExplosion) {
            if (this.Targeting.isEnabled() && (mc.objectMouseOver == null || mc.objectMouseOver.entityHit == null)) {
                return;
            }

            if (this.NoSword.isEnabled() && Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode())) {
                return;
            }
            
            S27PacketExplosion packet = (S27PacketExplosion) e.getPacket();
            
            float motionX = packet.func_149149_c(),
            	  motionY = packet.func_149144_d(),
            	  motionZ = packet.func_149147_e();
            
            if (this.chance.getValue() != 100.0D) {
                double ch = Math.random();
                if (ch >= this.chance.getValue() / 100.0D) {
                    return;
                }
            }
            

            if (this.horizontal.getValue() != 100.0D) {
                motionX *= this.horizontal.getValue() / 100.0D;
                motionZ *= this.horizontal.getValue() / 100.0D;
            }

            if (this.vertical.getValue() != 100.0D) {
                motionY *= this.vertical.getValue() / 100.0D;
            }
            ReflectionUtil.setFieldValue(packet, motionX, "field_149152_f");
            ReflectionUtil.setFieldValue(packet, motionY, "field_149153_g");
            ReflectionUtil.setFieldValue(packet, motionZ, "field_149159_h");
        }

    }

}
