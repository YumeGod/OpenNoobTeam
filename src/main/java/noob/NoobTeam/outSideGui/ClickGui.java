package noob.NoobTeam.outSideGui;

import noob.NoobTeam.managers.ModuleManager;
import noob.NoobTeam.modules.Module;
import noob.NoobTeam.other.OnTest;

import javax.swing.*;
import java.util.ArrayList;

public class ClickGui extends JFrame {

    ArrayList<Module> mods = ModuleManager.getModules();
    Module gui = ModuleManager.getModule("ClickGui");

    @OnTest
    public void init() {
        this.setTitle("NoobTeam OutSide ClickGui"); // 设置标题
        this.setVisible(true); // 设置可见
        this.setBounds(500, 500, 1000, 550);
        this.setUndecorated(true); // 去掉边框



        // 关闭事件
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gui.setState(false);
    }

}
