package com.github.paweladamski.jinstall;

interface OsSpecific {
    String getLauncherScript(String jarName);

    String getLauncherName(String finalName);

    static OsSpecific getInstance() {
        return isWindows() ? new WindowsSpecific() : new UnixSpecific();
    }

    static boolean isWindows() {
        String osName = System.getProperty("os.name");
        return osName.startsWith("Windows");
    }
}
