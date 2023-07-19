package team.MCTeamPotato.ModPackDownload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import team.MCTeamPotato.ModPackDownload.Swing.MainSwing;
import team.MCTeamPotato.ModPackDownload.Util.ConfigUtil;

import javax.swing.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


import static team.MCTeamPotato.ModPackDownload.Util.LanguageUtil.*;


public class Main {

    public static String NAME = "ModPackDownload";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

    public static void main(String[] args) throws IOException {

        loadLanguage();
        ConfigUtil.TOML();
        if (languageMap != null) {
            LOGGER.info("===========================================");
            LOGGER.info(languageMap.get("ModPackDownload.Main.Name"));
            LOGGER.info(languageMap.get("ModPackDownload.Main.Author"));
            LOGGER.info(languageMap.get("ModPackDownload.Main.Version"));
            LOGGER.info("===========================================");

            String smdValue = System.getProperty("SMD");

            // 检查启动参数是否为 "auto"，如果是，设置为 true
            boolean smdAuto = "auto".equalsIgnoreCase(smdValue);

            // 使用 smdAuto 的值在 if 语句中执行逻辑
            if (smdAuto) {
                // 在这里处理 SMD=auto 的逻辑
                LOGGER.info(languageMap.get("ModPackDownload.Main.JVM.AUTO"));
                Factory.step();//执行 类Factory-step()
            } else {
                // 处理其他情况
                //MainSwing.createAndShowGUI();//芝士GUI
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            MainSwing.createAndShowGUI();
                        } catch (UnsupportedEncodingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                LOGGER.info(languageMap.get("ModPackDownload.Main.JVM.NOT"));
            }
        } else {
            LOGGER.error("Unable to obtain language file!");
        }
    }
}

