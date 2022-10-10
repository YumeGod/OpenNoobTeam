package noob.NoobTeam.config;

import noob.NoobTeam.Client;
import noob.NoobTeam.managers.ConfigManager;
import noob.NoobTeam.managers.ModuleManager;
import noob.NoobTeam.modules.Module;
import noob.NoobTeam.settings.BooleanSetting;
import noob.NoobTeam.settings.NumberSetting;
import noob.NoobTeam.settings.Setting;
import noob.NoobTeam.utils.impl.Helper;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Iterator;

public class AutoSave extends Thread {

    @Override
    public void run() {
        if (ModuleManager.getModule("AutoSaveConfig").isEnabled()) {
            String values = "";
            for (Module m : ModuleManager.getModules()) {
                for (Setting s : m.settings) {
                    if (s instanceof BooleanSetting) {
                        BooleanSetting bs = (BooleanSetting) s;
                        values = values + String.format("%s:%s:%s%s", m.getName(), bs.name, bs.isEnabled(), System.lineSeparator());
                    }
                    if (s instanceof NumberSetting) {
                        NumberSetting ms = (NumberSetting) s;
                        values = values + String.format("%s:%s:%s%s", m.getName(), ms.name, ms.getValue(), System.lineSeparator());
                    }
                }
            }
            ConfigManager.save(Client.CLIENT_CONFIG +"-values.cfg", values, false);
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
            ConfigManager.save(Client.CLIENT_CONFIG +"-binds.cfg", content, false);
            ConfigManager.save(Client.CLIENT_CONFIG +"-modules.cfg", enabled, false);
            ConfigManager.save(Client.CLIENT_CONFIG +"-client.cfg", Client.name, false);
            Helper.sendMessage("15min AutoSaveConfig Work!");
            try {
                sleep(900000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
