package team.MCTeamPotato.ModPackDownload.Swing;

import javax.swing.*;
import java.io.*;

import com.formdev.flatlaf.FlatDarkLaf;
import team.MCTeamPotato.ModPackDownload.Swing.Paper.AboutPapers;
import team.MCTeamPotato.ModPackDownload.Swing.Paper.ConfigPapers;
import team.MCTeamPotato.ModPackDownload.Swing.Paper.MainPapers;
import team.MCTeamPotato.ModPackDownload.Swing.Paper.SettingPapers;

import static team.MCTeamPotato.ModPackDownload.Swing.Paper.AboutPapers.AboutPaper;
import static team.MCTeamPotato.ModPackDownload.Swing.Paper.ConfigPapers.ConfigPaper;
import static team.MCTeamPotato.ModPackDownload.Swing.Paper.MainPapers.MainPaper;
import static team.MCTeamPotato.ModPackDownload.Swing.Paper.SettingPapers.SettingPaper;
import static team.MCTeamPotato.ModPackDownload.Util.LanguageUtil.languageMap;

public class MainSwing {
    public static int Step = 0;
    public static int Progress = 0;

    public static void createAndShowGUI() throws UnsupportedEncodingException {
        try {
            // 设置FlatLaf的主题为FlatIntelliJLaf（或者FlatDarkLaf、FlatLightLaf）
            UIManager.setLookAndFeel(new FlatDarkLaf());
            // 创建工具栏
            JTabbedPane tabbedPane = new JTabbedPane();
            //设置名称
            JFrame frame = new JFrame("ServerModPackDownload");

            // 其他添加组件和创建GUI的代码
            tabbedPane.addTab(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.main"), MainPaper);
            tabbedPane.addTab(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.GenConfig"), ConfigPaper);
            tabbedPane.addTab(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.Setting"), SettingPaper);
            tabbedPane.addTab(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.About"), AboutPaper);
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            frame.add(tabbedPane);

            // 创建并添加工具栏等其他组件
            AboutPapers.Main();
            ConfigPapers.Main();
            MainPapers.Main(); // 添加此行来初始化主面板
            //SettingPapers.Main();


            frame.setSize(600, 500);
            frame.setVisible(true);
        } catch (Exception e) {
            // 处理异常
        }
    }


}

