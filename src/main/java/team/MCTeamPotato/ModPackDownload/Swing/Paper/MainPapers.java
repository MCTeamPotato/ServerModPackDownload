package team.MCTeamPotato.ModPackDownload.Swing.Paper;

import team.MCTeamPotato.ModPackDownload.Swing.ConfigLoader;
import team.MCTeamPotato.ModPackDownload.Swing.MainSwing;
import team.MCTeamPotato.ModPackDownload.Swing.SwingFactory;
import team.MCTeamPotato.ModPackDownload.Util.ConfigUtil;
import team.MCTeamPotato.ModPackDownload.Util.LanguageUtil;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import static team.MCTeamPotato.ModPackDownload.Swing.MainSwing.*;
import static team.MCTeamPotato.ModPackDownload.Util.LanguageUtil.languageMap;

public class MainPapers {
    // 静态方法用于更新主面板文本内容
    public static void updateLanguageText() {
        stepLabel.setText(getStepText());
        ProgressText.setText(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.main.ProgressText") + Progress + "%");
        // 如果有其他需要更新的文本组件，也可以在这里更新
    }

    public static JPanel MainPaper = new JPanel(null);
    public static JLabel stepLabel = new JLabel(getStepText()); // 步骤文本标签
    public static JProgressBar progressBar = new JProgressBar();
    public static JFrame frame = new JFrame("ServerModPackDownload");
    public static JLabel ProgressText = new JLabel(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.main.ProgressText") + "0%");//进度
    public static void Main() {
        //主菜单
        progressBar.setBounds(120, 50, 400, 30);
        progressBar.setMinimum(0);
        progressBar.setMaximum(6);
        progressBar.setValue(0);
        //创建配置表
        int TextX = 140;
        int TextY = 93;
        int TextWidth = 400;
        int TextHeight = 25;


        JLabel ModPackName = new JLabel(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.main.Text.ModPackName") + ConfigUtil.ModPackName);//模组包名称
        ModPackName.setBounds(TextX, TextY, TextWidth, TextHeight);
        JLabel ModPackUrl = new JLabel(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.main.Text.ModPackUrl") + ConfigUtil.ModPackUrl);//模组包URL
        ModPackUrl.setBounds(TextX, TextY + 20, TextWidth, TextHeight);
        JLabel Parameters = new JLabel(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.main.Text.Parameters") + ConfigUtil.Parameters);//Server核心安装参数
        Parameters.setBounds(TextX, TextY + 40, TextWidth, TextHeight);
        JLabel ServerJVMParameter = new JLabel(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.main.Text.ServerJVMParameter") + ConfigUtil.ServerJVMParameter);//Server核心安装参数
        ServerJVMParameter.setBounds(TextX, TextY + 60, TextWidth, TextHeight);
        JLabel Threads = new JLabel(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.main.Text.Thread") + ConfigUtil.Thread);
        Threads.setBounds(TextX, TextY + 80, TextWidth, TextHeight);
        JLabel ServerCoreName = new JLabel(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.main.Text.ServerCoreName") + ConfigUtil.ServerCoreName);
        ServerCoreName.setBounds(TextX, TextY + 100, TextWidth, TextHeight);
        JLabel ForgeClientModCheck2 = new JLabel(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.main.Text.ServerCoreName2") + ConfigUtil.ForgeClientModCheck);
        ForgeClientModCheck2.setBounds(TextX, TextY + 120, TextWidth, TextHeight);

        stepLabel.setBounds(30, 65, 100, 25);
        // 创建进度文本标签
        ProgressText.setBounds(30, 50, 100, 25);
        // 将按钮和组件添加到 MainPaper 页面
        MainPaper.add(ModPackName);
        MainPaper.add(ModPackUrl);
        MainPaper.add(Parameters);
        MainPaper.add(ServerJVMParameter);
        MainPaper.add(Threads);
        MainPaper.add(ServerCoreName);
        MainPaper.add(ForgeClientModCheck2);
        MainPaper.add(stepLabel);
        MainPaper.add(progressBar);
        MainPaper.add(ProgressText);
        MainPapers.ButtonEvent();

    }
    public static void updateProgressBar(int value) {
        SwingUtilities.invokeLater(() -> {
            progressBar.setValue(value);
            int progressPercentage = (int)(MainSwing.Progress * 16.67D);
            String formattedProgress = String.valueOf(progressPercentage);
            ProgressText.setText(LanguageUtil.languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.main.ProgressText") + formattedProgress + "%");
        });
    }
public static void ButtonEvent() {
    //创建文件选择器
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.main.SetFile"));

    // 添加文件过滤器，只显示后缀为".toml"的文件
    FileNameExtensionFilter filter = new FileNameExtensionFilter(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.main.SetFile.Toml"), "toml");
    fileChooser.setFileFilter(filter);

    // 创建按钮用于触发文件选择对话框
    JButton fileChooserButton = new JButton(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.main.SetFile"));
    fileChooserButton.setBounds(10, 10, 100, 25); // 设置按钮的位置和大小

    // 创建文件名称标签
    JLabel fileNameLabel = new JLabel(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.main.SetFile.Toml.Name"));
    fileNameLabel.setBounds(120, 10, 160, 25);
    // 创建用于显示文件路径的标签
    JLabel filePathLabel = new JLabel();
    filePathLabel.setBounds(180, 10, 300, 25);

    fileChooserButton.addActionListener(e -> {
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            filePathLabel.setText(selectedFile.getAbsolutePath());
            // 处理选择的配置文件
            // 在这里添加你的处理逻辑
            String filePath = selectedFile.getAbsolutePath();
            filePathLabel.setText(filePath);

            // 加载配置文件
            ConfigLoader.loadConfig(filePath);
        }
    });
    JButton startButton = new JButton(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.main.Run"));
    startButton.setBounds(10, 100, 100, 30);


    JButton stopButton = new JButton(languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.main.Stop"));
    stopButton.setBounds(10, 140, 100, 30);


    stopButton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
        int choice = JOptionPane.showConfirmDialog(frame, languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.main.ConfirmStop"));
        if (choice == JOptionPane.YES_OPTION) {
            SwingFactory.setStopRequested(true);
            System.exit(0);
        }
    }));

    startButton.addActionListener(new ActionListener() {
        private volatile Thread thread; // 声明一个线程变量

        @Override
        public void actionPerformed(ActionEvent e) {
            startButton.setEnabled(false); // 禁用开始按钮
            thread = new Thread(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted() && !SwingFactory.isStopRequested()) {
                        if (Thread.currentThread().isInterrupted()) {
                            // 在循环中检查中断状态，如果收到中断请求则退出循环
                            break;
                        }

                        SwingFactory.step();

                        // 执行一次任务后休眠一段时间
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException ex) {
                    // 处理线程中断异常
                    Thread.currentThread().interrupt();
                } catch (IOException ex) {
                    // 处理可能的异常
                    ex.printStackTrace();
                } finally {
                    // 线程执行完成后恢复开始按钮状态
                    startButton.setEnabled(true);
                }
            });
            // 启动线程
            thread.start();
        }
    });
    // 创建进度条
    // 在按钮的点击事件处理中更新进度条
    startButton.addActionListener(e -> {
        // 检查是否导入了配置文件
        if (ConfigUtil.ModPackName == null || ConfigUtil.ServerInstallerUrl == null) {
            JOptionPane.showMessageDialog(frame, languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.main.ConfigNotImported"));
            return;
        }

        // 创建并启动线程
        Thread thread = new Thread(() -> {
            // 模拟执行任务，假设有 6 个子任务
            for (int i = 1; i <= 6; i++) {
                // 更新进度条
                MainSwing.Progress = i;
                stepLabel.setText(getStepText()); // 更新步骤文本的内容

                // 让线程休眠一段时间，以模拟任务执行的时间
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    // 处理线程中断异常
                    ex.printStackTrace();
                }
            }

            // 任务完成后的逻辑;
        });

        thread.start();
    });
    MainPaper.add(startButton);
    MainPaper.add(stopButton);
    MainPaper.add(filePathLabel);
    MainPaper.add(fileChooserButton);
    MainPaper.add(fileNameLabel);
}



    public static String getStepText() {
        if (MainSwing.Step == 1) {
            return languageMap.get("ModPackDownload.Swing.SwingFactory.Step.1");
        } else if (MainSwing.Step == 2) {
            return languageMap.get("ModPackDownload.Swing.SwingFactory.Step.2");
        } else if (MainSwing.Step == 3) {
            return languageMap.get("ModPackDownload.Swing.SwingFactory.Step.3");
        } else if (MainSwing.Step == 4) {
            return languageMap.get("ModPackDownload.Swing.SwingFactory.Step.4");
        } else if (MainSwing.Step == 5) {
            return languageMap.get("ModPackDownload.Swing.SwingFactory.Step.5");
        } else if (MainSwing.Step == 6) {
            return languageMap.get("ModPackDownload.Swing.SwingFactory.Step.6");
        }
        return languageMap.get("ModPackDownload.Swing.SwingFactory.Step.0");
    }
}
