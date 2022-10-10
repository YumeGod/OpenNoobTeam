package noob.NoobTeam.managers;

import noob.NoobTeam.modules.Combat.*;
import noob.NoobTeam.modules.Misc.*;
import noob.NoobTeam.modules.Module;
import noob.NoobTeam.modules.movement.*;
import noob.NoobTeam.modules.player.FastPlace;
import noob.NoobTeam.modules.player.NoFall;
import noob.NoobTeam.modules.player.NoJumpDelay;
import noob.NoobTeam.modules.player.SpeedMine;
import noob.NoobTeam.modules.render.ClickGui;
import noob.NoobTeam.modules.render.FullBright;
import noob.NoobTeam.modules.render.Hud;

import java.util.ArrayList;

public class ModuleManager {
    static ArrayList<Module> list = new ArrayList<Module>();

    public static ArrayList<Module> getModules() {
        return list;
    }

    public ModuleManager() {

    }

    public static Module getModule(String name) {
        for (Module m : list) {
            if (m.getName().equalsIgnoreCase(name))
                return m;
        }
        return null;
    }

    static {
        // Combat
        list.add(new KillAura());
        list.add(new Reach());
        list.add(new DelayRemover());
        list.add(new HitBox());
        list.add(new AutoClicker());
        list.add(new Velocity());
        list.add(new Wtap());
        list.add(new SuperKB());
        list.add(new Criticals());

        // Movement
        list.add(new Sprint());
        list.add(new InventoryMove());
        list.add(new Speed());
        list.add(new Strafe());
        list.add(new KeepSprint());
        list.add(new NoSlowDown());

        // player
        list.add(new NoJumpDelay());
        list.add(new NoFall());
        list.add(new SpeedMine());
        list.add(new FastPlace());

        // render
        list.add(new Hud());
        list.add(new FullBright());
        list.add(new ClickGui());

        //misc
        list.add(new NoCommand());
        list.add(new AutoSaveConfig());
        list.add(new AutoL());
        list.add(new Fucker());
        // list.add(new TestModule());
    }
}
