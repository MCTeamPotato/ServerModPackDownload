package team.MCTeamPotato.ModPackDownload.Util;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import team.MCTeamPotato.ModPackDownload.File.ConfigIntroGenerator;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static team.MCTeamPotato.ModPackDownload.Main.LOGGER;
import static team.MCTeamPotato.ModPackDownload.Util.LanguageUtil.languageMap;

public class ConfigUtil {

    public static String ModPackName;
    public static String ServerInstallerUrl;
    public static Boolean ForgeClientModCheck;
    public static int Thread = 10;
    public static String ModPackUrl;

    public static String CurseForgeAPIKey;
    public static List<String> Parameters;
    public static List<String> NetWorkTestList;
    public static String ServerJVMParameter;
    public static String ServerCoreName;
    public static final String TOML = "SMD.toml";

    public static void TOML() {
        File configFile = new File(TOML);
        if (configFile.exists()) {
            try (FileReader reader = new FileReader(configFile)) {
                Toml toml = new Toml().read(reader);
                ModPackName = toml.getString("ModPackName");
                ModPackUrl = toml.getString("ModPackUrl");
                CurseForgeAPIKey = toml.getString("CurseForgeAPI_Key");
                ServerInstallerUrl = toml.getString("ServerInstallerUrl");
                Parameters = toml.getList("Parameters");
                NetWorkTestList = toml.getList("NetWorkTestList");
                ServerJVMParameter = toml.getString("ServerJVMParameter");
                ServerCoreName = toml.getString("ServerCoreName");
                ForgeClientModCheck = toml.getBoolean("ForgeClientModCheck");
                if (CurseForgeAPIKey == null) {
                    CurseForgeAPIKey = ""; // 如果未找到配置值，则设置为空字符串
                }

                Thread = toml.getLong("Thread").intValue();

            } catch (IOException e) {
                e.printStackTrace();
                LOGGER.error(languageMap.get("ModPackDownload.Util.ConfigUtil.Config.Load.Error") + e.getMessage());
            }
        } else {
            try  {
                LOGGER.info(languageMap.get("ModPackDownload.Util.ConfigUtil.Config.Gen"));
                configFile.createNewFile();
                Map<String, Object> configMap = new HashMap<>();
                ConfigIntroGenerator.generateConfigIntro(languageMap);
                configMap.put("ModPackName", "Your Mod Pack Name");
                configMap.put("CurseForgeAPI_Key", "");
                configMap.put("ServerInstallerUrl", "Server Installer Url");
                configMap.put("Thread", 8);
                configMap.put("Parameters", Arrays.asList("server", "-mcversion", "1.19.4", "-loader", "0.14.21", "-dir", "/"));
                configMap.put("NetWorkTestList", Arrays.asList("github.com", "www.curseforge.com"));
                configMap.put("ServerJVMParameter", "");
                configMap.put("ServerCoreName","");
                configMap.put("ModPackUrl","");
                configMap.put("ForgeClientModCheck",false);

                try (FileWriter fileWriter = new FileWriter(configFile)) {
                    TomlWriter writer = new TomlWriter.Builder()
                            .indentValuesBy(2) // 设置缩进为 2 个空格
                            .build();
                    writer.write(configMap, fileWriter);
                }
            } catch (IOException e) {
                e.printStackTrace();
                LOGGER.error(languageMap.get("ModPackDownload.Util.ConfigUtil.Config.Gen.Error") + e.getMessage());
            }

        }
    }

    public static void CFAPICheck() {
        if (CurseForgeAPIKey.length() == 0) {
            LOGGER.warn(languageMap.get("ModPackDownload.Util.ConfigUtil.APIKey.OFF"));//是空的
            System.exit(0);
        } else {
            LOGGER.info(languageMap.get("ModPackDownload.Util.ConfigUtil.APIKey.ON"));//有效的
        }
    }
}
