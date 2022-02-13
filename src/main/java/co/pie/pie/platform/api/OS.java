package co.pie.pie.platform.api;

public enum OS {
    WINDOWS("wmic os get osarchitecture"),
    LINUX("uname -m"),
    UNIX("uname -m"),
    MAC_OS_X("uname -m");

    private final String architectureCmd;

    OS(String architectureCmd) {
        this.architectureCmd = architectureCmd;
    }

    public String getArchitectureCmd() {
        return architectureCmd;
    }
}
