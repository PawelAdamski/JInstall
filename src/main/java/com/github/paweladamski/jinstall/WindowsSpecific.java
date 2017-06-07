package com.github.paweladamski.jinstall;

class WindowsSpecific implements OsSpecific {

    @Override
    public String getLauncherScript(String jarName) {
        return String.format("@echo off\n java -jar %s %*", jarName);
    }

    @Override
    public String getLauncherName(String finalName) {
        return finalName + ".bat";
    }
}
