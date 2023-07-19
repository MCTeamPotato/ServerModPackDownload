package team.MCTeamPotato.ModPackDownload.File;

import team.MCTeamPotato.ModPackDownload.Factory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static team.MCTeamPotato.ModPackDownload.Main.LOGGER;
import static team.MCTeamPotato.ModPackDownload.Util.LanguageUtil.*;



public class FileCopier {
    public static void File() {
        String sourceFolderPath = Factory.CachePath + File.separator + "overrides";
        String destinationFolderPath = Factory.ServerPath;

        try {
            copyFolder(new File(sourceFolderPath), new File(destinationFolderPath));
            //System.out.println("Files copied successfully.");
            LOGGER.info(languageMap.get("ModPackDownload.File.FileCopier.Copied"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyFolder(File sourceFolder, File destinationFolder) throws IOException {
        if (sourceFolder.isDirectory()) {
            // 如果目标文件夹不存在，则创建它
            if (!destinationFolder.exists()) {
                destinationFolder.mkdirs();
            }

            // 获取源文件夹中的所有文件和子文件夹
            File[] files = sourceFolder.listFiles();
            if (files != null) {
                for (File file : files) {
                    File newFile = new File(destinationFolder, file.getName());
                    if (file.isDirectory()) {
                        // 递归复制子文件夹
                        copyFolder(file, newFile);
                    } else {
                        // 复制文件
                        Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            }
        }
    }
}

