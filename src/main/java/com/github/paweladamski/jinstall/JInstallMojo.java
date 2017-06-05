package com.github.paweladamski.jinstall;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@org.apache.maven.plugins.annotations.Mojo(name = "install")
public class JInstallMojo extends AbstractMojo {
    @Parameter(defaultValue = "${project.build.finalName}")
    private File finalName = new File("");

    private static final String LAUNCHER_FORMAT = "#!/bin/bash\n java -jar %s \"$@\"";

    private String getBinLocation() {
        return System.getenv("JAR_PATH");
    }

    private void copy(File pathToJar, File targetLocation) throws IOException {
        Files.copy(pathToJar.toPath(), targetLocation.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    private void createLauncher(File jarLocation) throws IOException {
        String launcherName = jarLocation.getName()
                .replace(".jar", "")
                .replace("-jar-with-dependencies", "");
        String launcherScript = String.format(LAUNCHER_FORMAT, jarLocation.getAbsolutePath());
        File launcherPath = new File(getBinLocation(), launcherName);
        Files.write(launcherPath.toPath(), launcherScript.getBytes());
        if (!launcherPath.setExecutable(true, false)) {
            getLog().error("Can't make file executable.");
        }
    }

    public void execute() throws MojoExecutionException {
        checkSystemVariables();
        File originalJarLocation = new File("target/" + finalName.getName() + "-jar-with-dependencies.jar");
        String jarName = originalJarLocation.getName();
        File targetJarLocation = new File(getBinLocation(), jarName);
        try {
            copy(originalJarLocation, targetJarLocation);
            createLauncher(targetJarLocation);
        } catch (Exception e) {
            getLog().error(e);
            throw new MojoExecutionException("Can't install plugin", e);

        }
    }

    private void checkSystemVariables() throws MojoExecutionException {
        String jarPath = System.getenv("JAR_PATH");
        String path = System.getenv("PATH");
        if (jarPath == null) {
            throw new MojoExecutionException("Variable \"JAR_PATH\" is not set up");
        }
        if (!path.contains(jarPath)) {
            throw new MojoExecutionException("\"PATH\" variable doesn't contain \"JAR_PATH\"");
        }
    }
}