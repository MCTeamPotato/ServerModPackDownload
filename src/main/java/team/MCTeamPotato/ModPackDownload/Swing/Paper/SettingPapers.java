package team.MCTeamPotato.ModPackDownload.Swing.Paper;

import team.MCTeamPotato.ModPackDownload.Swing.MainSwing;
import team.MCTeamPotato.ModPackDownload.Util.LanguageUtil;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import static team.MCTeamPotato.ModPackDownload.Main.LOGGER;
import static team.MCTeamPotato.ModPackDownload.Swing.Paper.AboutPapers.AboutPaper;
import static team.MCTeamPotato.ModPackDownload.Swing.Paper.ConfigPapers.ConfigPaper;
import static team.MCTeamPotato.ModPackDownload.Swing.Paper.MainPapers.MainPaper;
import static team.MCTeamPotato.ModPackDownload.Util.LanguageUtil.languageMap;

public class SettingPapers {

    public static JPanel SettingPaper = new JPanel(null);//使用null布局
    public static void Main() {
        JFrame frame = new JFrame("ServerModPackDownload");
        //语言设置
        //添加Text
        JLabel LangLabel = new JLabel(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.Setting.Languages"));
        LangLabel.setBounds(10, 10, 500, 40);
        //添加列表
        //创建语言列表的数据列表
        DefaultListModel<String> languageListModel = new DefaultListModel<>();
        languageListModel.addElement(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.Setting.Languages.English"));
        languageListModel.addElement(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.Setting.Languages.Chinese"));
        languageListModel.addElement(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.Setting.Languages.Russia"));
        languageListModel.addElement(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.Setting.Languages.Chinese.TW"));
        //创建 JList 组件并设置数据模型
        JList<String> languageList = new JList<>(languageListModel);

        // 设置选择模式为单选
        languageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        LanguageUtil.supportedLanguages = new ArrayList<>();
        LanguageUtil.supportedLanguages.add("en_us");
        LanguageUtil.supportedLanguages.add("zh_cn");
        LanguageUtil.supportedLanguages.add("ru_ru");
        LanguageUtil.supportedLanguages.add("zh_tw");

        // 创建滚动面板，并将 JList 添加到滚动面板中
        JScrollPane scrollPane = new JScrollPane(languageList);

        // 创建按钮并添加动作监听器
        JButton selectButton = new JButton(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.Setting.Select" ));
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取选择的语言
                String selectedLanguage = languageList.getSelectedValue();
                if (selectedLanguage != null) {
                    // 根据选择的语言执行相应的操作
                    if (selectedLanguage.equals(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.Setting.Languages.English"))) {
                        LanguageUtil.loadLanguageFile("assets/lang/en_us.json");

                        LOGGER.info(languageMap.get("ModPackDownload.Url.ServerInstaller.Downloaded"));
                        // 更新组件文字
                        SettingPapers.ReloadUI();
                        LangLabel.setText(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.Setting.Languages"));
                        // 执行英语选择的操作
                        JOptionPane.showMessageDialog(frame, languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.Setting.Languages.English.Selected"));

                    } else if (selectedLanguage.equals(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.Setting.Languages.Chinese"))) {

                        SettingPapers.ReloadUI();
                        // 更新组件文字
                        LangLabel.setText(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.Setting.Languages"));
                        // 执行中文选择的操作
                        JOptionPane.showMessageDialog(frame, languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.Setting.Languages.Chinese.Selected"));
                    } else if (selectedLanguage.equals(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.Setting.Languages.Russia"))) {

                        SettingPapers.ReloadUI();
                        LangLabel.setText(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.Setting.Languages"));
                        //俄语
                        JOptionPane.showMessageDialog(frame,languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.Setting.Languages.Russia.Selected"));
                    } else if (selectedLanguage.equals(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.Setting.Languages.Chinese.TW"))) {


                        SettingPapers.ReloadUI();
                        LangLabel.setText(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.Setting.Languages"));
                        JOptionPane.showMessageDialog(frame,languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.Setting.Languages.Chinese.TW.Selected"));

                    }
                }
            }
        });
        selectButton.setBounds(240, 10, 80, 40);//选择
        scrollPane.setBounds(10,10,220,40);//列表

        // 团队地址
        //JLabel addressLabel = new JLabel(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.About.Github"));//团队地址
        //addressLabel.setBounds(10, 35, 500, 25);

        //将组件添加到AboutPaper
        //将滚动面板添加到面板中
        SettingPaper.add(selectButton);
        SettingPaper.add(scrollPane, BorderLayout.CENTER);
        SettingPaper.add(LangLabel);
        SettingPaper.add(Box.createVerticalStrut(10));

        //SettingPaper.add(licenseLabel);
    }
    public static void ReloadUI() {
        LanguageUtil.loadLanguage();

        // 设置其他组件的文本，以此类推，根据需要更新其他组件的文本
        // 重新布局和绘制所有组件
        MainPaper.invalidate();
        MainPaper.repaint();
        MainPaper.updateUI();

        ConfigPaper.invalidate();
        ConfigPaper.repaint();
        ConfigPaper.updateUI();


        AboutPaper.invalidate();
        AboutPaper.repaint();
        AboutPaper.updateUI();

        SettingPaper.invalidate();
        SettingPaper.repaint();
        SettingPaper.updateUI();
        System.out.println("Reload UI!");
    }
}
