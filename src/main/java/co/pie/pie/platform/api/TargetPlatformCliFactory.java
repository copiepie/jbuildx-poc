package co.pie.pie.platform.api;

import co.pie.pie.platform.impl.MacTargetPlatformCli;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class TargetPlatformCliFactory {

    public TargetPlatformCli create(String targetPlatforms,
                             String dockerfilePath,
                             String imageTag,
                             boolean pushImage) {
        OS os = getMatchingOS(System.getProperty("os.name"));
        return create(os, targetPlatforms, dockerfilePath, imageTag, pushImage);
    }

    public TargetPlatformCli create(OS os, String targetPlatforms,
                             String dockerfilePath,
                             String imageTag,
                             boolean pushImage) {
        switch (os) {
            case MAC_OS_X:
                return new MacTargetPlatformCli(targetPlatforms, dockerfilePath, imageTag, pushImage);
            case UNIX:
            case LINUX:
            case WINDOWS:
            default:
                throw new IllegalArgumentException(String.format("OS %s is not supported", os));
        }
    }

    private OS getMatchingOS(String osName) {
        if (StringUtils.containsIgnoreCase(osName, "win")) {
            return OS.WINDOWS;
        }
        if (StringUtils.containsIgnoreCase(osName, "linux")) {
            return OS.LINUX;
        }
        if (StringUtils.containsIgnoreCase(osName, "unix")) {
            return OS.UNIX;
        }
        if (StringUtils.containsIgnoreCase(osName, "mac")) {
            return OS.MAC_OS_X;
        }
        throw new IllegalArgumentException(String.format("OS with name % is not supported", osName));
    }
}
