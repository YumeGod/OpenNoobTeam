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
import noob.NoobTeam.managers.ModuleManager;
import noob.NoobTeam.modules.Module;
import noob.NoobTeam.settings.NumberSetting;
import noob.NoobTeam.settings.Setting;
import noob.NoobTeam.utils.impl.Helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class settingCommand implements ICommand {

    Minecraft mc = Minecraft.getMinecraft();

    @Override
    public String getCommandName() {
        return "set";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/set <module> <valueName> <value>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length >= 3 && !ModuleManager.getModule("NoCommand").isEnabled()) {
            String name = args[0];
            String valueName = args[1];
            String value = args[2];
            Module m = ModuleManager.getModule(name);
            if(m == null){
                Helper.sendMessage("Module not found. please use correct module name.");
            } else {
                /**
                 * 遍历功能的设置，判断相等
                 * 其实这里应该还有一个instanceof判断
                 */
                for (Setting setting : m.settings) {
                    if (setting.name.equalsIgnoreCase(valueName)) {
                        NumberSetting numberSetting = (NumberSetting) setting;
                        numberSetting.setValue(Double.parseDouble(value));
                    }
                }
            }
        } else {
            Helper.sendMessage("Pls use /set <module> <valueName> <value> to change a module Value");
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
