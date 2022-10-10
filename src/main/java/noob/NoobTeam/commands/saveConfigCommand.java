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

import java.util.*;
public class saveConfigCommand implements ICommand {

    Minecraft mc = Minecraft.getMinecraft();

    @Override
    public String getCommandName() {
        return "save";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/save <configName>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        StringBuilder values = new StringBuilder();
        for (Module m : ModuleManager.getModules()) {
            for (Setting s : m.settings) {
                if (s instanceof BooleanSetting) {
                    BooleanSetting bs = (BooleanSetting) s;
                    values.append(String.format("%s:%s:%s%s", m.getName(), bs.name, bs.isEnabled(), System.lineSeparator()));
                }
                if (s instanceof NumberSetting) {
                    NumberSetting ns = (NumberSetting) s;
                    values.append(String.format("%s:%s:%s%s", m.getName(), ns.name, ns.getValue(), System.lineSeparator()));
                }
                if (s instanceof ModeSetting) {
                    ModeSetting ms = (ModeSetting) s;
                    values.append(String.format("%s:%s:%s%s", m.getName(), ms.name, ms.getMode(), System.lineSeparator()));
                }
            }
        }

        String ConfigName;
        if (args.length > 1) {
            ConfigName = args[0];
        } else {
            ConfigName = Client.CLIENT_CONFIG;
        }

        ConfigManager.save(ConfigName +"-values.cfg", values.toString(), false);
        String enabled = "";
        ArrayList<Module> modules = new ArrayList<>();
        modules.addAll(ModuleManager.getModules());
        for (Module m : modules) {
            if (m != null && m.isEnabled()) {
                enabled = enabled + String.format("%s%s", m.getName(), System.lineSeparator());
            }
        }
        String content = "";
        Module m;
        for(Iterator var4 = ModuleManager.getModules().iterator(); var4.hasNext(); content = content + String.format("%s:%s%s", m.getName(), Keyboard.getKeyName(m.getKey()), System.lineSeparator())) {
            m = (Module)var4.next();
        }
        ConfigManager.save(ConfigName +"-binds.cfg", content, false);
        ConfigManager.save(ConfigName +"-modules.cfg", enabled, false);
        ConfigManager.save(ConfigName +"-client.cfg", Client.name, false);
        Helper.sendMessage("Save " + ConfigName + " Config Done!");
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
