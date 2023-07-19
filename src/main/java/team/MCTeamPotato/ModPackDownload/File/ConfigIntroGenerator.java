package team.MCTeamPotato.ModPackDownload.File;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;


public class ConfigIntroGenerator {

    public static void generateConfigIntro(Map<String, String> languageMap) {
        StringBuilder introText = new StringBuilder();

        introText.append(languageMap.get("ModPackDownload.Util.ConfigUtil.Config.Gen.ModPackName")).append("\n");
        introText.append(languageMap.get("ModPackDownload.Util.ConfigUtil.Config.Gen.CurseForgeAPI_Key")).append("\n");
        introText.append(languageMap.get("ModPackDownload.Util.ConfigUtil.Config.Gen.ServerInstallerUrl")).append("\n");
        introText.append(languageMap.get("ModPackDownload.Util.ConfigUtil.Config.Gen.ForgeClientModCheck")).append("\n");
        introText.append(languageMap.get("ModPackDownload.Util.ConfigUtil.Config.Gen.Thread")).append("\n");
        introText.append(languageMap.get("ModPackDownload.Util.ConfigUtil.Config.Gen.Parameters")).append("\n");
        introText.append(languageMap.get("ModPackDownload.Util.ConfigUtil.Config.Gen.ServerJVMParameter")).append("\n");

        try (FileWriter writer = new FileWriter("config_intro.txt")) {
            writer.write(introText.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}