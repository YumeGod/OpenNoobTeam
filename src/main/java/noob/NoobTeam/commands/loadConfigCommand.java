package noob.NoobTeam.commands;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.BlockPos;
import net.minecraft.world.WorldSettings;
import noob.NoobTeam.Client;
import noob.NoobTeam.managers.ConfigManager;
import noob.NoobTeam.managers.ModuleManager;
import noob.NoobTeam.modules.Module;
import noob.NoobTeam.settings.BooleanSetting;
import noob.NoobTeam.settings.ModeSetting;
import noob.NoobTeam.settings.NumberSetting;
import noob.NoobTeam.settings.Setting;
import noob.NoobTeam.utils.impl.Helper;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.*;

public class loadConfigCommand implements ICommand {

    public static String ConfigName;


    Minecraft mc = Minecraft.getMinecraft();

    @Override
    public String getCommandName() {
        return "load";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/load <ConfigName>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length > 1) {
            ConfigName = args[0];
        } else {
            ConfigName = Client.CLIENT_CONFIG;
        }

        List<String> binds = ConfigManager.read(ConfigName +"-binds.cfg");
        for (String v : binds) {
            String name = v.split(":")[0];
            String bind = v.split(":")[1];
            Module m = ModuleManager.getModule(name);
            if (m == null) continue;
            m.setKey(Keyboard.getKeyIndex(bind.toUpperCase()));
        }
        List<String> enabled = ConfigManager.read(ConfigName +"-modules.cfg");
        for (String v : enabled) {
            Module m = ModuleManager.getModule(v);
            if (m == null) continue;
            m.setState(true);
        }
        List<String> vals = ConfigManager.read(ConfigName +"-values.cfg");
        for (String v : vals) {
            String name = v.split(":")[0];
            String values = v.split(":")[1];
            Module m = ModuleManager.getModule(name);
            if (m == null) continue;
            for (Setting s : m.settings) {
                if (!s.name.equalsIgnoreCase(values)) continue;
                if (s instanceof BooleanSetting) {
                    ((BooleanSetting) s).setEnabled(Boolean.parseBoolean(v.split(":")[2]));
                    continue;
                }
                if (s instanceof NumberSetting) {
                    ((NumberSetting) s).setValue(Double.parseDouble(v.split(":")[2]));
                    continue;
                }
                if (s instanceof ModeSetting) {
                    ((ModeSetting) s).setMode(v.split(":")[2]);
                }
            }
        }
        List<String> cfg = ConfigManager.read(ConfigName +"-client.cfg");
        if (cfg.size() != 0) {
            Helper.sendMessage( ConfigName + " Config Loaded.");
        } else {
            Helper.sendMessage("Failed to load config");
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return sender.getCommandSenderEntity() instanceof EntityPlayer;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        List<String> list = new ArrayList<String>();
        try {
            Ordering<NetworkPlayerInfo> field_175252_a = Ordering.from(new Comparator<NetworkPlayerInfo>()
            {
                private void PlayerComparator()
                {
                }

                public int compare(NetworkPlayerInfo p_compare_1_, NetworkPlayerInfo p_compare_2_)
                {
                    ScorePlayerTeam scoreplayerteam = p_compare_1_.getPlayerTeam();
                    ScorePlayerTeam scoreplayerteam1 = p_compare_2_.getPlayerTeam();
                    return ComparisonChain.start().compareTrueFirst(p_compare_1_.getGameType() != WorldSettings.GameType.SPECTATOR, p_compare_2_.getGameType() != WorldSettings.GameType.SPECTATOR).compare(scoreplayerteam != null ? scoreplayerteam.getRegisteredName() : "", scoreplayerteam1 != null ? scoreplayerteam1.getRegisteredName() : "").compare(p_compare_1_.getGameProfile().getName(), p_compare_2_.getGameProfile().getName()).result();
                }
            });
            String last_s = "";
            for (NetworkPlayerInfo networkPlayerInfoIn : field_175252_a.sortedCopy(mc.thePlayer.sendQueue.getPlayerInfoMap())) {
                String s = networkPlayerInfoIn.getDisplayName() != null && false ? networkPlayerInfoIn.getDisplayName().getFormattedText() : networkPlayerInfoIn.getGameProfile().getName();
                if (!s.equals(last_s))
                    list.add(s);
                last_s = s;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }



        String[] astring = new String[list.size()];

        for (int i = 0; i < list.size(); ++i)
        {
            astring[i] = list.get(i);
        }

        return CommandBase.getListOfStringsMatchingLastWord(args, astring);
    }

    @Override
    public int compareTo(ICommand arg0) {
        return this.getCommandName().compareTo(arg0.getCommandName());
    }

    @Override
    public List<String> getCommandAliases() {
        return Collections.emptyList();
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }
}
