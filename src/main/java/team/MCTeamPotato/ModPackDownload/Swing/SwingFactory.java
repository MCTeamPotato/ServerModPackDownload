package team.MCTeamPotato.ModPackDownload.Swing;

import team.MCTeamPotato.ModPackDownload.File.CacheCleaner;
import team.MCTeamPotato.ModPackDownload.File.FileCopier;
import team.MCTeamPotato.ModPackDownload.File.Modscanner.ModFileChecker;
import team.MCTeamPotato.ModPackDownload.File.ParameterFileGenerator;
import team.MCTeamPotato.ModPackDownload.Json.getManifest;
import team.MCTeamPotato.ModPackDownload.Swing.Paper.MainPapers;
import team.MCTeamPotato.ModPackDownload.Url.ServerInstaller;
import team.MCTeamPotato.ModPackDownload.Util.ConfigUtil;
import team.MCTeamPotato.ModPackDownload.Util.FileUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicBoolean;

import static team.MCTeamPotato.ModPackDownload.Main.LOGGER;
import static team.MCTeamPotato.ModPackDownload.Util.LanguageUtil.*;


public class SwingFactory {
    private static final AtomicBoolean stopRequested = new AtomicBoolean(false);

    public static void setStopRequested(boolean value) {
        stopRequested.set(value);
    }

    public static boolean isStopRequested() {
        return stopRequested.get();
    }

    public static void step() throws IOException {
            LOGGER.info(languageMap.get("ModPackDownload.Factory.Step.1"));
            MainSwing.Step = 1;
            MainSwing.Progress = 1;
            MainPapers.updateProgressBar(MainSwing.Progress);
            CreatePath();
            LOGGER.info(languageMap.get("ModPackDownload.Factory.GetConfig"));
            LOGGER.info(languageMap.get("ModPackDownload.Factory.ModPack.Name") + ConfigUtil.ModPackName);
            ConfigUtil.CFAPICheck();//检查那混蛋一般的APIKEY
            FileUtil.downloadAndUnzip(ConfigUtil.ModPackUrl, "Server"); // 解压缩
            LOGGER.info(languageMap.get("ModPackDownload.Factory.Download.Completed"));
            LOGGER.info(languageMap.get("ModPackDownload.Factory.GetManifestAndDownload"));
            getManifest.ManifestAndDownload();
            LOGGER.info(languageMap.get("ModPackDownload.Factory.Step.2"));
            MainSwing.Step = 2;
            MainSwing.Progress = 2;
            MainPapers.updateProgressBar(MainSwing.Progress);
            LOGGER.info(languageMap.get("ModPackDownload.Factory.FileCopier"));
            FileCopier.File();//文件复制和处理
            LOGGER.info(languageMap.get("ModPackDownload.Factory.Step.3"));
            MainSwing.Step = 3;
            MainSwing.Progress = 3;
            MainPapers.updateProgressBar(MainSwing.Progress);
            LOGGER.info(languageMap.get("ModPackDownload.Factory.ModScanner"));
            ModFileChecker.Main();//将客户端模组放到Client文件夹
            LOGGER.info(languageMap.get("ModPackDownload.Factory.Step.4"));
            MainSwing.Step = 4;
            MainSwing.Progress = 4;
            MainPapers.updateProgressBar(MainSwing.Progress);
            LOGGER.info(languageMap.get("ModPackDownload.Factory.CacheCleaner"));
            CacheCleaner.Cleaner();//删除缓存文件
            LOGGER.info(languageMap.get("ModPackDownload.Factory.Step.5"));
            MainSwing.Step = 5;
            MainSwing.Progress = 5;
            MainPapers.updateProgressBar(MainSwing.Progress);
            LOGGER.info(languageMap.get("ModPackDownload.Factory.ServerInstaller"));
            ServerInstaller.ServerInstall();//安装核心
            LOGGER.info(languageMap.get("ModPackDownload.Factory.Step.6"));
            MainSwing.Step = 6;
            MainSwing.Progress = 6;
            MainPapers.updateProgressBar(MainSwing.Progress);
            LOGGER.info(languageMap.get("ModPackDownload.Factory.ParameterFileGenerator"));
            ParameterFileGenerator.generateParameterFile();

}
    public static void CreatePath() {
        String serverFolderPath = "Server"; // 要创建的服务器文件夹名称

        Path path = Paths.get(serverFolderPath);
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
                LOGGER.info(languageMap.get("ModPackDownload.Factory.CreatePath") + path);
            } catch (Exception e) {
                LOGGER.error(languageMap.get("ModPackDownload.Factory.CreatePath.Failed") + e.getMessage());
            }
        }

    }
}
