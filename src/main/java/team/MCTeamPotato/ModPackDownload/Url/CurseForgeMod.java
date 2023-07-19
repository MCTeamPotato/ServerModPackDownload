package team.MCTeamPotato.ModPackDownload.Url;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.io.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import team.MCTeamPotato.ModPackDownload.Factory;
import team.MCTeamPotato.ModPackDownload.Util.ConfigUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static team.MCTeamPotato.ModPackDownload.Main.LOGGER;
import static team.MCTeamPotato.ModPackDownload.Util.LanguageUtil.languageMap;


public class CurseForgeMod{
    private static final String API_URL = "https://api.curseforge.com";
    private static final String API_KEY = ConfigUtil.CurseForgeAPIKey;


    public static void Download(int[] modIds,int[] fileIds) {

        try {
            List<String> downloadUrls = getDownloadUrls(modIds, fileIds);
            LOGGER.info(languageMap.get("ModPackDownload.Url.CurseForgeMod.Download.Url") + downloadUrls);
            downloadFiles(downloadUrls);
            LOGGER.info(languageMap.get("ModPackDownload.Url.CurseForgeMod.Download.Complete"));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static List<String> getDownloadUrls(int[] modIds, int[] fileIds) throws IOException {
        ExecutorService threadPool = Executors.newFixedThreadPool(ConfigUtil.Thread);
        List<Future<String>> futures = new ArrayList<>();

        for (int i = 0; i < modIds.length; i++) {
            int modId = modIds[i];
            int fileId = fileIds[i];

            Callable<String> task = () -> getDownloadUrl(modId, fileId);
            Future<String> future = threadPool.submit(task);
            futures.add(future);
        }

        List<String> downloadUrls = new ArrayList<>();
        for (Future<String> future : futures) {
            try {
                String downloadUrl = future.get();
                downloadUrls.add(downloadUrl);
            } catch (InterruptedException | ExecutionException e) {
                // Handle exception
            }
        }

        threadPool.shutdown();
        return downloadUrls;
    }

    private static String getDownloadUrl(int modId, int fileId) throws IOException {
        String apiUrl = API_URL + "/v1/mods/" + modId + "/files/" + fileId + "/download-url";
        URL url = new URL(apiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        // 添加 API 密钥到头部
        con.setRequestProperty("x-api-key", API_KEY);

        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response.toString());
                return jsonNode.get("data").asText();
            }
        } else {
            throw new IOException(languageMap.get("ModPackDownload.Url.CurseForgeMod.Download.Error.getUrl") + responseCode);
        }
    }

    private static void downloadFiles(List<String> downloadUrls) throws IOException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(ConfigUtil.Thread);
        List<Future<Void>> futures = new ArrayList<>();

        for (String downloadUrl : downloadUrls) {
            Callable<Void> task = () -> {
                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException();
                }
                downloadFile(downloadUrl);
                return null;
            };
            Future<Void> future = threadPool.submit(task);
            futures.add(future);
        }

        for (Future<Void> future : futures) {
            try {
                future.get();
            } catch (ExecutionException e) {
                // Handle exception
            }
        }

        threadPool.shutdown();
    }


    private static void downloadFile(String downloadUrl) throws IOException {
        String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/") + 1);
        File folder = new File(Factory.ModsCachePath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        URL url = new URL(downloadUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (InputStream in = con.getInputStream();
                 FileOutputStream out = new FileOutputStream(new File(folder, fileName))) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
        } else {
            throw new IOException(languageMap.get("ModPackDownload.Url.CurseForgeMod.Download.Error.getFile") + responseCode);
        }
    }
}
