package team.MCTeamPotato.ModPackDownload.Swing.Paper;

import javax.swing.*;

import static team.MCTeamPotato.ModPackDownload.Util.LanguageUtil.languageMap;

public class AboutPapers {
    public static JPanel AboutPaper = new JPanel(null); // 使用 null 布局
    public static void Main() {
        int TextX = 10;

        //介绍About
        JLabel teamLabel = new JLabel(languageMap.get("ModPackDownload.Main.Author"));
        teamLabel.setBounds(TextX, 10, 500, 25);

        // 团队地址
        JLabel addressLabel = new JLabel(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.About.Github"));//团队地址
        addressLabel.setBounds(TextX, 35, 500, 25);

        JLabel projectLabel = new JLabel(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.About.Name"));//项目名称
        projectLabel.setBounds(TextX, 60, 200, 25);

        JLabel licenseLabel = new JLabel(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.About.LICENSE"));//协议
        licenseLabel.setBounds(TextX, 85, 200, 25);
        //将组件添加到AboutPaper
        AboutPaper.add(teamLabel);
        AboutPaper.add(addressLabel);
        AboutPaper.add(Box.createVerticalStrut(10));
        AboutPaper.add(projectLabel);
        AboutPaper.add(licenseLabel);
    }
}
