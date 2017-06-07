package com.github.paweladamski.jinstall;

class UnixSpecific implements OsSpecific {

    @Override
    public String getLauncherScript(String jarPath) {
        return String.format("#!/bin/bash\n java -jar %s \"$@\"", jarPath);
    }

    @Override
    public String getLauncherName(String finalName) {
        return finalName;
    }
}