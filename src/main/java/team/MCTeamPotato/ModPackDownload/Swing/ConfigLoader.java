package team.MCTeamPotato.ModPackDownload.Swing;

import com.moandjiezana.toml.Toml;
import team.MCTeamPotato.ModPackDownload.Util.ConfigUtil;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static team.MCTeamPotato.ModPackDownload.Util.LanguageUtil.languageMap;

public class ConfigLoader {
    public static void loadConfig(String filePath) {
        //设置名称
        JFrame frame = new JFrame("ServerModPackDownload");
        File configFile = new File(filePath);
        if (configFile.exists()) {
            try (FileReader reader = new FileReader(configFile)) {
                Toml toml = new Toml().read(reader);

                // 更新配置值
                ConfigUtil.ModPackName = toml.getString("ModPackName");
                ConfigUtil.ModPackUrl = toml.getString("ModPackUrl");
                ConfigUtil.CurseForgeAPIKey = toml.getString("CurseForgeAPI_Key");
                ConfigUtil.ServerInstallerUrl = toml.getString("ServerInstallerUrl");
                ConfigUtil.Parameters = toml.getList("Parameters");
                ConfigUtil.NetWorkTestList = toml.getList("NetWorkTestList");
                ConfigUtil.ServerJVMParameter = toml.getString("ServerJVMParameter");
                ConfigUtil.ServerCoreName = toml.getString("ServerCoreName");
                ConfigUtil.ForgeClientModCheck = toml.getBoolean("ForgeClientModCheck");
                ConfigUtil.Thread = toml.getLong("Thread").intValue();

                // 处理配置值
                if (ConfigUtil.ModPackName == null || ConfigUtil.ModPackName.isEmpty()) {
                    // 配置值不存在或为空
                    JOptionPane.showMessageDialog(frame, languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.main.Config.Error.ModPackName.null"));
                }
                // 其他配置值的处理...

            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame,languageMap.get("ModPackDownload.Swing.Main.createAndShowGUI.Paper.main.Config.Error.load") + e);
            }
        } else {
            JOptionPane.showMessageDialog(frame, languageMap.get( "ModPackDownload.Swing.Main.createAndShowGUI.Paper.main.Config.Error.Not"));
        }
    }
}
