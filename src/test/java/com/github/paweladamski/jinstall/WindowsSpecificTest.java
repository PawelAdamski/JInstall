package com.github.paweladamski.jinstall;

import org.hamcrest.Matchers;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class WindowsSpecificTest  {

    private WindowsSpecific windowsSpecific = new WindowsSpecific();

    @Test
    public void testLauncherScript() {
        String script = windowsSpecific.getLauncherScript("jecho.jar");
        assertThat(script, Matchers.equalTo("@echo off\n java -jar jecho.jar %*"));
    }
}
