package com.company;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Main {


    public static final String JAR_LOCATION = "/home/pawel/jars";
    public static final String BIN_LOCATION = "/usr/bin";
    private static final String LAUNCHER_FORMAT = "#!/bin/bash\n java -jar %s \"$@\"" ;

    public static void main(String[] args) throws IOException, InterruptedException {
        File pathToJar = new File(args[0]);
        String jarName = pathToJar.getName();
        File targetLocation = new File(JAR_LOCATION,jarName);
        copy(pathToJar,targetLocation);
        createLauncher(targetLocation);
    }

    private static void copy(File pathToJar, File targetLocation) throws IOException, InterruptedException {
        Files.copy(pathToJar.toPath(),targetLocation.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    private static void createLauncher(File targetLocation) throws IOException {
        String targetName = targetLocation.getName().replace(".jar","");
        String launcherScript = String.format(LAUNCHER_FORMAT,targetLocation.getAbsolutePath());
        File launcherPath = new File(BIN_LOCATION,targetName);
        Files.write(launcherPath.toPath(),launcherScript.getBytes());
        launcherPath.setExecutable(true,false);
    }
}
