package noob.NoobTeam.managers;

import net.minecraft.command.ICommand;
import net.minecraftforge.client.ClientCommandHandler;
import noob.NoobTeam.commands.*;

import java.util.ArrayList;

public class CommandManager {

    public static ArrayList<ICommand> commands = new ArrayList<>();

    public static void init() {
        for (ICommand command : commands) {
            ClientCommandHandler.instance.registerCommand(command);
        }
    }

    static {
        commands.add(new bindCommand());
        commands.add(new settingCommand());
        commands.add(new saveConfigCommand());
        commands.add(new loadConfigCommand());
        commands.add(new ToggleCommand());
        commands.add(new hideCommand());
        commands.add(new timerCommand());
        commands.add(new SearchCommand());
    }

}
