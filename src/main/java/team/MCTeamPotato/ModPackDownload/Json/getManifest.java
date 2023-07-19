package team.MCTeamPotato.ModPackDownload.Json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import team.MCTeamPotato.ModPackDownload.Factory;
import team.MCTeamPotato.ModPackDownload.Url.CurseForgeMod;
import team.MCTeamPotato.ModPackDownload.Util.ConfigUtil;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class getManifest {
    public static void ManifestAndDownload() {
        String filePath = Factory.CachePath + File.separator + "manifest.json"; // 整合包内的 manifest.json

        try {
            List<ModFileInfo> modFileInfos = parseModFileInfos(filePath);
            List<Integer> modIdList = new ArrayList<>();
            List<Integer> fileIdList = new ArrayList<>();

            for (int i = 0; i < modFileInfos.size(); i++) {
                ModFileInfo modFileInfo = modFileInfos.get(i);

                if (modFileInfo.isRequired()) {
                    modIdList.add(modFileInfo.getProjectID());
                    fileIdList.add(modFileInfo.getFileID());
                }

                // 当 modIdList 或 fileIdList 中的元素数量达到 10 时，将其转换为 int[] 数组并传入 CurseForgeApiExample.main() 方法
                if (modIdList.size() == ConfigUtil.Thread || i == modFileInfos.size() - 1) {
                    int[] modIds = modIdList.stream().mapToInt(Integer::intValue).toArray();
                    int[] fileIds = fileIdList.stream().mapToInt(Integer::intValue).toArray();

                    CurseForgeMod.Download(modIds, fileIds);

                    // 清空列表，准备下一组 modId 和 fileId
                    modIdList.clear();
                    fileIdList.clear();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<ModFileInfo> parseModFileInfos(String filePath) throws IOException {
        List<ModFileInfo> modFileInfos = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(new File(filePath));
        ArrayNode filesNode = (ArrayNode) root.get("files");

        for (JsonNode fileNode : filesNode) {
            int projectID = fileNode.get("projectID").asInt();
            int fileID = fileNode.get("fileID").asInt();
            boolean required = fileNode.get("required").asBoolean();

            if (!required) {
                continue;
            }

            ModFileInfo modFileInfo = new ModFileInfo(projectID, fileID, required);
            modFileInfos.add(modFileInfo);
        }

        return modFileInfos;
    }

    static class ModFileInfo {
        private final int projectID;
        private final int fileID;
        private final boolean required;

        public ModFileInfo(int projectID, int fileID, boolean required) {
            this.projectID = projectID;
            this.fileID = fileID;
            this.required = required;
        }

        public int getProjectID() {
            return projectID;
        }

        public int getFileID() {
            return fileID;
        }

        public boolean isRequired() {
            return required;
        }
    }
}
