package com.github.paweladamski.jinstall;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@org.apache.maven.plugins.annotations.Mojo( name = "sayhi")
public class JInstallMojo extends AbstractMojo
{
    @Parameter(defaultValue = "${project.build.finalName}")
    File finalName = new File("");

    public static final String JAR_LOCATION = "/home/pawel/jars";
    private static final String LAUNCHER_FORMAT = "#!/bin/bash\n java -jar %s \"$@\"" ;

    public String getBinLocation() {
        return System.getenv("JAR_PATH");
    }


    private  void copy(File pathToJar, File targetLocation) throws IOException, InterruptedException {
        Files.copy(pathToJar.toPath(), targetLocation.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    private  void createLauncher(File targetLocation) throws IOException {
        String targetName = targetLocation.getName().replace(".jar","");
        String launcherScript = String.format(LAUNCHER_FORMAT,targetLocation.getAbsolutePath());
        File launcherPath = new File(getBinLocation(),targetName);
        Files.write(launcherPath.toPath(),launcherScript.getBytes());
        if (!launcherPath.setExecutable(true,false) ) {
            getLog().error("Can't make file executable.");
        }
    }


    public void execute() throws MojoExecutionException
    {
        File pathToJar = new File("target/"+ finalName.getName()+"-jar-with-dependencies.jar") ;

        getLog().error(pathToJar.toString());
        String jarName = pathToJar.getName();
        File targetLocation = new File(JAR_LOCATION,jarName);

        try {
            copy(pathToJar,targetLocation);
            createLauncher(targetLocation);
        } catch (Exception e) {
            getLog().error(e);
            throw new MojoExecutionException("Can't install plugin",e);

        }
    }
}