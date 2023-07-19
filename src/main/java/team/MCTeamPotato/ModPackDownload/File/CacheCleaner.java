package team.MCTeamPotato.ModPackDownload.File;

import team.MCTeamPotato.ModPackDownload.Factory;

import java.io.File;

import static team.MCTeamPotato.ModPackDownload.Main.LOGGER;
import static team.MCTeamPotato.ModPackDownload.Util.LanguageUtil.languageMap;


public class CacheCleaner {

    public static void cleanCacheFolder(String folderPath) {
        File folder = new File(folderPath);
        if (folder.exists()) {
            deleteFolder(folder);
        }
    }

    private static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFolder(file);
                } else {
                    boolean deleted = file.delete();
                    if (deleted) {
                        LOGGER.info(languageMap.get("ModPackDownload.File.CacheCleaner.Delete.File") + file.getAbsolutePath());
                    } else {
                        LOGGER.error(languageMap.get("ModPackDownload.File.CacheCleaner.Delete.File.Failed") + file.getAbsolutePath());
                    }
                }
            }
        }
        boolean deleted = folder.delete();
        if (deleted) {
            //System.out.println("Deleted folder: " + folder.getAbsolutePath());
            LOGGER.info(languageMap.get("ModPackDownload.File.CacheCleaner.Delete.Folder") + folder.getAbsolutePath());
        } else {
            //System.out.println("Failed to delete folder: " + folder.getAbsolutePath());
            LOGGER.error(languageMap.get("ModPackDownload.File.CacheCleaner.Delete.Folder.Failed") + folder.getAbsolutePath());
        }
    }

    public static void Cleaner() {
        String cacheFolderPath = Factory.CachePath;
        cleanCacheFolder(cacheFolderPath);
    }
}





