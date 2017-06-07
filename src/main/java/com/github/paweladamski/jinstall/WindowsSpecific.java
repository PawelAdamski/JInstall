package com.github.paweladamski.jinstall;

public class WindowsSpecific implements OsSpecific {
    @Override
    public String getLauncherScript(String jarName) {
        return null;
    }

    @Override
    public String getLauncherName(String finalName) {
        return finalName + ".bat";
    }
}
