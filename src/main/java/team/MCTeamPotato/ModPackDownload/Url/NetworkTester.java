package team.MCTeamPotato.ModPackDownload.Url;

import team.MCTeamPotato.ModPackDownload.Util.ConfigUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.Arrays;

import static team.MCTeamPotato.ModPackDownload.Main.LOGGER;
import static team.MCTeamPotato.ModPackDownload.Util.LanguageUtil.languageMap;

public class NetworkTester {

    public static void NetworkTest() {
        LOGGER.info(languageMap.get("ModPackDownload.Url.NetWorkTester.NetworkTest"));
        String[] websites = ConfigUtil.NetWorkTestList.toArray(new String[0]);
        LOGGER.info(languageMap.get("ModPackDownload.Url.NetWorkTester.NetworkTest.Web.TestList") + Arrays.toString(websites));

        for (String website : websites) {
            boolean isReachable = isWebsiteReachable(website);
            if (!isReachable) {
                LOGGER.error(languageMap.get("ModPackDownload.Url.NetWorkTester.NetworkTest.Web.Error") + website);
                int userInput = getUserInput();
                if (userInput == -1) {
                    System.exit(0); // 退出程序
                }
            }
        }
        LOGGER.info(languageMap.get("ModPackDownload.Url.NetWorkTester.NetworkTest.Done"));
    }

    public static boolean isWebsiteReachable(String website) {
        try {
            InetAddress address = InetAddress.getByName(website);
            return address.isReachable(5000); // 设置超时时间为5秒
        } catch (IOException e) {
            return false;
        }
    }

    public static int getUserInput() {
        LOGGER.warn(languageMap.get("ModPackDownload.Url.NetWorkTester.getUserInput.Info"));
        try {
            boolean validInput = false;
            int userInput = 0;

            while (!validInput) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String input = reader.readLine().trim();
                if (input.equalsIgnoreCase("true")) {
                    validInput = true;
                    userInput = 1;
                } else if (input.equalsIgnoreCase("false")) {
                    validInput = true;
                    userInput = -1; // 使用-1表示退出程序
                } else {
                    LOGGER.warn(languageMap.get("ModPackDownload.Url.NetWorkTester.getUserInput.Invalid"));
                }
            }

            return userInput;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
