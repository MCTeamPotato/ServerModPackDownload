package team.MCTeamPotato.ModPackDownload.File.Modscanner;

import team.MCTeamPotato.ModPackDownload.Factory;
import team.MCTeamPotato.ModPackDownload.Util.ConfigUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static team.MCTeamPotato.ModPackDownload.Main.LOGGER;
import static team.MCTeamPotato.ModPackDownload.Util.LanguageUtil.languageMap;

public class ModFileChecker {
    public static void Main() {
        if (ConfigUtil.ForgeClientModCheck = false) {//如果是false就是不检测，适用于fabric
            // 将 Server/cache/mods 目录复制到 Server/mods 目录
            try (DirectoryStream<Path> mods = Files.newDirectoryStream(Paths.get(Factory.ModsCachePath), "*.jar")) {
                for (Path jarPath : mods) {
                    File targetFile = new File(Factory.ServerModsPath, jarPath.getFileName().toString());
                    Files.copy(jarPath, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    LOGGER.info(languageMap.get("ModPackDownload.File.Modscanner.ModFileChecker.Copied")
                            + jarPath.getFileName().toString()
                            + languageMap.get("ModPackDownload.File.Modscanner.ModFileChecker.To")
                            + Factory.ServerModsPath
                    );
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (ConfigUtil.ForgeClientModCheck = true) {//true是检查，适用于开启Forge的
            // 执行原来的分离操作
            try (DirectoryStream<Path> mods = Files.newDirectoryStream(Paths.get(Factory.ZipCachePath), "*.jar")) {
                for (Path jarPath : mods) {
                    processJarFile(jarPath, Factory.ServerModsPath, Factory.ClientModPath);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void processJarFile(Path jarPath, String serverModsFolderPath, String clientFolderPath) {
        try (JarFile jarFile = new JarFile(jarPath.toFile())) {
            JarEntry entry = jarFile.getJarEntry("META-INF/mods.toml");
            if (entry != null) {
                InputStream input = jarFile.getInputStream(entry);
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                String line;
                boolean isClientMod = false;

                while ((line = reader.readLine()) != null) {
                    if (line.trim().startsWith("side")) {
                        isClientMod = line.contains("CLIENT");
                        break;
                    }
                }

                reader.close();

                if (isClientMod) {
                    // 创建 Client 文件夹（如果不存在）
                    File clientFolder = new File(clientFolderPath);
                    if (!clientFolder.exists()) {
                        clientFolder.mkdirs();
                    }

                    // 复制 .jar 文件到 Client 文件夹
                    File targetFile = new File(clientFolder, jarPath.getFileName().toString());
                    Files.copy(jarPath, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    LOGGER.info(languageMap.get("ModPackDownload.File.Modscanner.ModFileChecker.ClientMod.Copied")
                            + jarPath.getFileName().toString()
                            + languageMap.get("ModPackDownload.File.Modscanner.ModFileChecker.ClientMod.To")
                            + clientFolder
                    );
                } else {
                    // 创建 Server/mods 文件夹（如果不存在）
                    File serverModsFolder = new File(serverModsFolderPath);
                    if (!serverModsFolder.exists()) {
                        serverModsFolder.mkdirs();
                    }

                    // 复制 .jar 文件到 Server/mods 文件夹
                    File targetFile = new File(serverModsFolder, jarPath.getFileName().toString());
                    Files.copy(jarPath, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
