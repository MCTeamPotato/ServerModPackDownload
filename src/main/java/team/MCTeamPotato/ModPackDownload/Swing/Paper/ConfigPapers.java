package team.MCTeamPotato.ModPackDownload.Swing.Paper;

import com.moandjiezana.toml.TomlWriter;
import team.MCTeamPotato.ModPackDownload.File.ConfigIntroGenerator;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static team.MCTeamPotato.ModPackDownload.Util.LanguageUtil.languageMap;


public class ConfigPapers {
    public static JPanel ConfigPaper = new JPanel(null); // 使用 null 布局
    public static void Main() {
        //名称设置
        JFrame frame = new JFrame("ServerModPackDownload");

        // 创建输入框和设置配置按钮
        JLabel MPULabel = new JLabel(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Text.ModPackUrl"));
        JTextField Text = new JTextField(20);

        JLabel ServerCoreUrlLabel = new JLabel(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Text.ServerCoreUrl"));
        JTextField ServerCoreUrlText = new JTextField(20);

        JLabel ModPackNameLabel = new JLabel(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Text.ModPackName"));
        JTextField ModPackNameText = new JTextField(20);

        JLabel CFAPIKEYLabel = new JLabel(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Text.CurseForgeAPIKey"));
        JTextField CFAPIKEYText = new JTextField(20);

        JLabel ThreadLabel = new JLabel(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Text.Thread"));
        JTextField ThreadText = new JTextField(20);

        JLabel ServerJVMParameterLabel = new JLabel(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Text.ServerJVMParameter"));
        JTextField ServerJVMParameterText = new JTextField(20);

        JLabel ParametersLabel = new JLabel(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Text.Parameters"));
        JTextField ParametersText = new JTextField(20);

        JLabel ServerCoreNameLabel = new JLabel(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Text.ServerCoreName"));
        JTextField ServerCoreNameText = new JTextField(20);

        JLabel ForgeClientModCheckLabel = new JLabel(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Text.ForgeClientModCheck"));
        JCheckBox ForgeClientModCheck = new JCheckBox();

        JButton GenButton = new JButton(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Text.GenConfig"));
        JButton GenTutorialFileButton = new JButton(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Text.GenTutorialFile"));

        // 设置组件的位置和大小
        int x = 10;
        int y = 10;
        int width = 200;
        int height = 25;

        //生成配置
        MPULabel.setBounds(x, y, width, height);
        Text.setBounds(x + width + 10, y, width, height);

        ServerCoreUrlLabel.setBounds(x, y + height + 10, width, height);
        ServerCoreUrlText.setBounds(x + width + 10, y + height + 10, width, height);

        ModPackNameLabel.setBounds(x, y + 2 * (height + 10), width, height);
        ModPackNameText.setBounds(x + width + 10, y + 2 * (height + 10), width, height);

        CFAPIKEYLabel.setBounds(x, y + 3 * (height + 10), width, height);
        CFAPIKEYText.setBounds(x + width + 10, y + 3 * (height + 10), width, height);

        ThreadLabel.setBounds(x, y + 4 * (height + 10), width, height);
        ThreadText.setBounds(x + width + 10, y + 4 * (height + 10), width, height);

        ServerJVMParameterLabel.setBounds(x, y + 5 * (height + 10), width, height);
        ServerJVMParameterText.setBounds(x + width + 10, y + 5 * (height + 10), width, height);

        ParametersLabel.setBounds(x, y + 6 * (height + 10), width, height);
        ParametersText.setBounds(x + width + 10, y + 6 * (height + 10), width, height);

        ServerCoreNameLabel.setBounds(x, y + 7 * (height + 10), width, height);
        ServerCoreNameText.setBounds(x + width + 10, y + 7 * (height + 10), width, height);

        ForgeClientModCheckLabel.setBounds(x, y + 8 * (height + 10), width, height);
        ForgeClientModCheck.setBounds(x + width + 10, y + 8 * (height + 10), width, height);

        GenButton.setBounds(x, y + 9 * (height + 10), width, height);

        GenTutorialFileButton.setBounds(x + 220, y + 9 * (height + 10), width, height);
        //生成配置完成
        //将组件添加到ConfigPaper
        ConfigPaper.add(MPULabel);
        ConfigPaper.add(Text);
        ConfigPaper.add(ServerCoreUrlLabel);
        ConfigPaper.add(ServerCoreUrlText);
        ConfigPaper.add(ModPackNameLabel);
        ConfigPaper.add(ModPackNameText);
        ConfigPaper.add(CFAPIKEYLabel);
        ConfigPaper.add(CFAPIKEYText);
        ConfigPaper.add(ThreadLabel);
        ConfigPaper.add(ThreadText);
        ConfigPaper.add(ServerJVMParameterLabel);
        ConfigPaper.add(ServerJVMParameterText);
        ConfigPaper.add(ParametersLabel);
        ConfigPaper.add(ParametersText);
        ConfigPaper.add(ServerCoreNameLabel);
        ConfigPaper.add(ServerCoreNameText);
        ConfigPaper.add(ForgeClientModCheckLabel);
        ConfigPaper.add(ForgeClientModCheck);
        ConfigPaper.add(GenButton);
        ConfigPaper.add(GenTutorialFileButton);
        GenTutorialFileButton.addActionListener(es -> {
            ConfigIntroGenerator.generateConfigIntro(languageMap);
            JOptionPane.showMessageDialog(frame, languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Text.GenTutorialFile.Tip"));
        });

        //生成配置的点击事件处理
        GenButton.addActionListener(e -> {
            //获取用户输入的配置参数
            String Parameters = ParametersText.getText();
            String modPackUrl = Text.getText();
            String serverCoreUrl = ServerCoreUrlText.getText();
            String modPackName = ModPackNameText.getText();
            String curseForgeAPIKey = CFAPIKEYText.getText();
            int thread = Integer.parseInt(ThreadText.getText());
            String serverJVMParameter = ServerJVMParameterText.getText();
            String serverCoreName = ServerCoreNameText.getText();
            boolean forgeClientModCheck = ForgeClientModCheck.isSelected();

            // 创建 TOML 对象并设置配置内容
            Map<String, Object> toml = new HashMap<>();
            toml.put("Parameters", Parameters);
            toml.put("ModPackName", modPackName);
            toml.put("ForgeClientModCheck", forgeClientModCheck);
            toml.put("CurseForgeAPI_Key", curseForgeAPIKey);
            toml.put("ServerInstallerUrl", serverCoreUrl);
            toml.put("ServerJVMParameter", serverJVMParameter);
            toml.put("Thread", thread);
            toml.put("ServerCoreName", serverCoreName);
            toml.put("ModPackUrl", modPackUrl);
            toml.put("NetWorkTestList", new String[]{"github.com", "www.curseforge.com"});

            // 获取用户选择的文件保存路径
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new File("SMD.toml")); // 设置默认文件名
            int result = fileChooser.showSaveDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                    TomlWriter tomlWriter = new TomlWriter.Builder()
                            .indentValuesBy(2) // 设置缩进为 2 个空格
                            .build();
                    tomlWriter.write(toml, writer);

                    JOptionPane.showMessageDialog(frame, "Config file saved successfully.");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error occurred while saving config file.");
                }
            }
        });
    }
}
