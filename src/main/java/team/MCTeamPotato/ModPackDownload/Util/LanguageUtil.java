package team.MCTeamPotato.ModPackDownload.Util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static team.MCTeamPotato.ModPackDownload.Main.LOGGER;

public class LanguageUtil {
    public static final String LANG_FOLDER_PATH = "assets/lang/";
    public static final String CONFIG_FOLDER_PATH = "assets/config/";
    public static final String LANG_FILE_NAME = "languages.json";
    public static String DEFAULT_LANG = "en_us";
    public static String langFilePath;
    public static List<String> supportedLanguages = new ArrayList<>();

    public static Map<String, String> languageMap;
    // Getter method to access the languageMap


    public static void loadLanguageFile(String filePath) {
        try (InputStream inputStream = LanguageUtil.class.getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream != null) {
                ObjectMapper objectMapper = new ObjectMapper(new JsonFactory());
                languageMap = objectMapper.readValue(inputStream, new TypeReference<>() {});
            } else {
                LOGGER.error("Language file not found: " + filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadLanguage() {
        loadSupportedLanguages();
        Locale defaultLocale = Locale.getDefault();
        String systemLanguage = defaultLocale.getLanguage();

        for (String language : supportedLanguages) {
            if (systemLanguage.equals(language.substring(0, 2))) {
                langFilePath = LANG_FOLDER_PATH + language + ".json";
                break;
            }
        }

        if (langFilePath == null) {
            langFilePath = LANG_FOLDER_PATH + DEFAULT_LANG + ".json";
        }

        loadLanguageFile(langFilePath);
    }

    private static void loadSupportedLanguages() {
        String configFilePath = CONFIG_FOLDER_PATH + LANG_FILE_NAME;
        try (InputStream inputStream = LanguageUtil.class.getClassLoader().getResourceAsStream(configFilePath)) {
            if (inputStream != null) {
                ObjectMapper objectMapper = new ObjectMapper(new JsonFactory());
                Map<String, List<String>> configMap = objectMapper.readValue(inputStream, new TypeReference<>() {});
                List<String> languagesList = configMap.get("LanguagesList");
                supportedLanguages.addAll(languagesList);
            } else {
                LOGGER.error("Language config file not found: " + configFilePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
