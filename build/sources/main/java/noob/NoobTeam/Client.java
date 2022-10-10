package noob.NoobTeam;


import net.minecraft.client.Minecraft;
import noob.NoobTeam.config.AutoSave;
import noob.NoobTeam.managers.CommandManager;
import noob.NoobTeam.managers.PacketManager;
import noob.NoobTeam.modules.Module;
import noob.NoobTeam.managers.ModuleManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import noob.NoobTeam.utils.impl.Helper;
import org.lwjgl.input.Keyboard;

public class Client {
    public static Client instance;
    public static boolean state = false;

    public static final String name="NoobTeam";
    public static final String version="0.1";

    public static String CLIENT_CONFIG = "default";

    public static ModuleManager moduleManager = new ModuleManager();

    public Client() {
        if (state) return;
        state = true;
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
        instance = this;
        CommandManager.init();
        PacketManager.INSTANCE.init();
        if(Minecraft.getMinecraft().getCurrentServerData() != null) {
            PacketManager.INSTANCE.onJoinServer(null);
        }

        new AutoSave().start();
    }

    public static boolean nullCheck() {
        return Helper.mc.thePlayer == null || Helper.mc.theWorld == null;
    }

    @SubscribeEvent
    public void keyInput(InputEvent.KeyInputEvent event) {
        for(Module m : ModuleManager.getModules()) {
            if(Keyboard.isKeyDown(m.key)) {
                m.toggle();
            }
        }
    }
}
