package noob.NoobTeam.modules;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import noob.NoobTeam.settings.Setting;
import noob.NoobTeam.utils.impl.Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Module {
    public static final Minecraft mc = Minecraft.getMinecraft();
    public boolean state = false;
    public int key;
    public String name;
    public Category category;
    public String description;
    public boolean hide;

    public String suffix = "";

    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    public List<Setting> settings = new ArrayList<>();

    public Module(String name, int key, Category category, String description) {
        this.name = name;
        this.key = key;
        this.category = category;
        this.description = description;
    }

    /**
     * 添加功能设置
     * 修改可以看SettingCommand
     * @param settings 功能的Value
     */
    public void addSettings(Setting... settings) {
        this.settings.addAll(Arrays.asList(settings));
    }

    /**
     * 获取功能后调用这个方法即可实现打开或关闭
     */
    public void toggle() {
        this.setState(!this.state);
    }

    /**
     * 手动设置功能开关
     * @param state 状态
     */
    public void setState(boolean state) {
        if (this.state == state) {
            return;
        }
        this.state = state;
        if (state) {
            Helper.sendMessage(this.getName() + " Enabled!");
            MinecraftForge.EVENT_BUS.register(this);
            FMLCommonHandler.instance().bus().register(this);
            enable();
        } else {
            Helper.sendMessage(this.getName() + " Disabled!");
            MinecraftForge.EVENT_BUS.unregister(this);
            FMLCommonHandler.instance().bus().unregister(this);
            disable();
        }
    }

    public void enable() {

    }

    public void disable() {

    }

    public String getName() {
        return name;
    }

    public int getKey() {
        return key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return state;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
