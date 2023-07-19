package team.MCTeamPotato.ModPackDownload.Util;

import team.MCTeamPotato.ModPackDownload.Factory;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static team.MCTeamPotato.ModPackDownload.Main.LOGGER;
import static team.MCTeamPotato.ModPackDownload.Util.LanguageUtil.languageMap;

public class FileUtil {
    //臭搞获取文件地址或者下载整合包地址的玩意
    public static void downloadAndUnzip(String zipUrl, String destinationFolder) throws IOException {
        if (zipUrl.startsWith("http://") || zipUrl.startsWith("https://")) {
            URL url = new URL(zipUrl);
            try (InputStream in = url.openStream()) {
                Path destinationPath = Path.of(Factory.ServerPath, getFileNameFromUrl(zipUrl));
                Files.copy(in, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                unzipFile(destinationPath.toString(), destinationFolder);
            }
        } else {
            unzipFile(Path.of(Factory.ServerPath, zipUrl).toString(), destinationFolder);
        }
    }


    private static void unzipFile(String filePath, String destinationFolder) throws IOException {
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(filePath))) {
            byte[] buffer = new byte[1024];
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                Path destinationFolderPath = Path.of(destinationFolder, "cache");
                Files.createDirectories(destinationFolderPath);

                Path destinationPath = Path.of(destinationFolder, "cache", zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    Files.createDirectories(destinationPath);
                } else {
                    Files.createDirectories(destinationPath.getParent());
                    try (OutputStream out = new FileOutputStream(destinationPath.toFile())) {
                        int bytesRead;
                        while ((bytesRead = zipInputStream.read(buffer)) != -1) {
                            out.write(buffer, 0, bytesRead);
                        }
                    }
                }
                zipInputStream.closeEntry();
            }
        }
        LOGGER.info(languageMap.get("ModPackDownload.Util.FileUtil.Completed"));
    }

    private static String getFileNameFromUrl(String url) {
        int lastSlashIndex = url.lastIndexOf('/');
        if (lastSlashIndex != -1 && lastSlashIndex < url.length() - 1) {
            return url.substring(lastSlashIndex + 1);
        }
        return null;
    }
}
