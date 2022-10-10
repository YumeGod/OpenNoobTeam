package noob.NoobTeam;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid=Client.name, name=Client.name, version=Client.version, acceptedMinecraftVersions="[1.8.9]")
public class ForgeMod {

    @SideOnly(Side.CLIENT)
    @Mod.EventHandler
    public void init(FMLPreInitializationEvent event) {
        new Client();
    }
    @SideOnly(Side.SERVER)
    @Mod.EventHandler
    public void Mod(FMLInitializationEvent event) {}

}
