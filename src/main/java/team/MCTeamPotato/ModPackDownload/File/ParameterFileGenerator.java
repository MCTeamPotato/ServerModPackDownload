package team.MCTeamPotato.ModPackDownload.File;

import team.MCTeamPotato.ModPackDownload.Factory;
import team.MCTeamPotato.ModPackDownload.Util.ConfigUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ParameterFileGenerator {

    public static void generateParameterFile() {
        String jvmInfo = ConfigUtil.ServerJVMParameter;

        String os = System.getProperty("os.name").toLowerCase();

        String fileName = "";
        String fileContent = "";

        if (os.contains("win")) {
            fileName = "start.bat";
            fileContent = "@echo off" + System.lineSeparator()
                    + "java " + jvmInfo + " -jar " + ConfigUtil.ServerCoreName;
        } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
            fileName = "start.sh";
            fileContent = "#!/bin/bash" + System.lineSeparator()
                    + "java " + jvmInfo + " -jar " + ConfigUtil.ServerCoreName;
        }

        if (!fileName.isEmpty()) {
            String filePath = Factory.ServerPath + File.separator + fileName;
            File parameterFile = new File(filePath);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(parameterFile))) {
                writer.write(fileContent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
