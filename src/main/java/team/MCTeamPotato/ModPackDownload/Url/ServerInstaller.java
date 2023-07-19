package team.MCTeamPotato.ModPackDownload.Url;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.MCTeamPotato.ModPackDownload.Factory;
import team.MCTeamPotato.ModPackDownload.Util.ConfigUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static team.MCTeamPotato.ModPackDownload.Main.LOGGER;
import static team.MCTeamPotato.ModPackDownload.Main.NAME;
import static team.MCTeamPotato.ModPackDownload.Util.LanguageUtil.languageMap;

public class ServerInstaller {
    private static final Logger LOGFG = LoggerFactory.getLogger(NAME + " - " + languageMap.get("ModPackDownload.Url.ServerInstaller.Server.Installer"));

    public static void ServerInstall() {
        String forgeInstallerUrl = ConfigUtil.ServerInstallerUrl;

        // 检查 forgeInstallerUrl 是否为空
        if (forgeInstallerUrl.isEmpty()) {
            LOGGER.info(languageMap.get("ModPackDownload.Url.ServerInstaller.SkipDownload"));
        } else {
            // 获取文件名
            String fileName = getFileNameFromUrl(forgeInstallerUrl);

            // 下载安装程序
            try {
                downloadFile(forgeInstallerUrl, Factory.ServerPath + fileName);
                LOGGER.info(languageMap.get("ModPackDownload.Url.ServerInstaller.Downloaded"));
            } catch (IOException e) {
                LOGGER.error(languageMap.get("ModPackDownload.Url.ServerInstaller.Downloaded.Failed") + e.getMessage());
                return;
            }

            // 执行服务端安装命令
            try {
                runServerInstallCommand(fileName, Factory.ServerPath);
                LOGGER.info(languageMap.get("ModPackDownload.Url.ServerInstaller.Installation"));
            } catch (IOException e) {
                LOGGER.error(languageMap.get("ModPackDownload.Url.ServerInstaller.Installation.Failed") + e.getMessage());
            }
        }
    }

    private static String getFileNameFromUrl(String fileUrl) {
        // 从 URL 中提取文件名
        String[] segments = fileUrl.split("/");
        return segments[segments.length - 1];
    }

    private static void downloadFile(String fileUrl, String file) throws IOException {
        URL url = new URL(fileUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int fileSize = connection.getContentLength();
        LOGGER.info(languageMap.get("ModPackDownload.Url.ServerInstaller.Download") + fileSize / (1024 * 1024) + "MB");

        try (InputStream in = connection.getInputStream()) {
            Files.copy(in, Paths.get(file), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private static void runServerInstallCommand(String installerPath, String serverFolderPath) throws IOException {
        List<String> command = new ArrayList<>();
        command.add("java");
        command.add("-jar");
        command.add(installerPath);
        command.addAll(ConfigUtil.Parameters);

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.directory(new File(serverFolderPath));
        try {
            Process process = processBuilder.start();
// 读取进程的输出
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "GBK"));
            String line;
            while ((line = reader.readLine()) != null) {
                LOGFG.info(line);
            }

            // 读取进程的错误输出
            InputStream errorStream = process.getErrorStream();
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                LOGFG.error(errorLine);
            }

            // 等待进程执行完成
            int exitCode = process.waitFor();
            LOGFG.info(languageMap.get("ModPackDownload.Url.ServerInstaller.ExitCode") + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
