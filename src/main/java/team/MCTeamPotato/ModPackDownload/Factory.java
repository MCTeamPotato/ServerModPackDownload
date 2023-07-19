package team.MCTeamPotato.ModPackDownload;


import team.MCTeamPotato.ModPackDownload.File.CacheCleaner;
import team.MCTeamPotato.ModPackDownload.File.FileCopier;

import team.MCTeamPotato.ModPackDownload.File.Modscanner.ModFileChecker;
import team.MCTeamPotato.ModPackDownload.File.ParameterFileGenerator;
import team.MCTeamPotato.ModPackDownload.Json.getManifest;
import team.MCTeamPotato.ModPackDownload.Url.NetworkTester;
import team.MCTeamPotato.ModPackDownload.Url.ServerInstaller;
import team.MCTeamPotato.ModPackDownload.Util.ConfigUtil;
import team.MCTeamPotato.ModPackDownload.Util.FileUtil;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static team.MCTeamPotato.ModPackDownload.Main.LOGGER;
import static team.MCTeamPotato.ModPackDownload.Util.LanguageUtil.*;

public class Factory {
    public static String CachePath = "Server" + File.separator + "cache";
    public static String ServerPath = "Server" + File.separator;
    public static String ModsCachePath = "Server" + File.separator + "cache" + File.separator + "mods" + File.separator;
    public static String ZipCachePath = "Server" + File.separator + "cache" + File.separator + "overrides" + File.separator;
    public static String ServerModsPath = "Server" + File.separator + "mods" + File.separator;
    public static String ClientModPath = "Server" + File.separator + "Client";
    public static void step() throws IOException {
        NetworkTester.NetworkTest();
        LOGGER.info(languageMap.get("ModPackDownload.Factory.Step.1"));
        CreatePath();
        LOGGER.info(languageMap.get("ModPackDownload.Factory.GetConfig"));
        LOGGER.info(languageMap.get("ModPackDownload.Factory.ModPack.Name") + ConfigUtil.ModPackName);
        ConfigUtil.CFAPICheck();//检查那混蛋一般的APIKEY
        FileUtil.downloadAndUnzip(ConfigUtil.ModPackUrl,"Server"); // 解压缩
        LOGGER.info(languageMap.get("ModPackDownload.Factory.Download.Completed"));
        LOGGER.info(languageMap.get("ModPackDownload.Factory.GetManifestAndDownload"));
        getManifest.ManifestAndDownload();
        LOGGER.info(languageMap.get("ModPackDownload.Factory.Step.2"));
        LOGGER.info(languageMap.get("ModPackDownload.Factory.FileCopier"));
        FileCopier.File();//文件复制和处理
        LOGGER.info(languageMap.get("ModPackDownload.Factory.Step.3"));
        LOGGER.info(languageMap.get("ModPackDownload.Factory.ModScanner"));
        ModFileChecker.Main();//将客户端模组放到Client文件夹
        LOGGER.info(languageMap.get("ModPackDownload.Factory.Step.4"));
        LOGGER.info(languageMap.get("ModPackDownload.Factory.CacheCleaner"));
        CacheCleaner.Cleaner();//删除缓存文件
        LOGGER.info(languageMap.get("ModPackDownload.Factory.Step.5"));
        LOGGER.info(languageMap.get("ModPackDownload.Factory.ServerInstaller"));
        ServerInstaller.ServerInstall();//安装核心
        LOGGER.info(languageMap.get("ModPackDownload.Factory.Step.6"));
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


