package co.pie.pie.platform.impl;

import co.pie.pie.cli.api.CommandLineApi;
import co.pie.pie.platform.api.TargetPlatformCli;
import co.pie.pie.utils.Utils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class MacTargetPlatformCli implements TargetPlatformCli {
  private final String targetPlatforms;
  private final String dockerfilePath;
  private final String imageTag;
  private final boolean pushImage;

  public MacTargetPlatformCli(
      String targetPlatforms, String dockerfilePath, String imageTag, boolean pushImage) {
    this.targetPlatforms = targetPlatforms;
    this.dockerfilePath = dockerfilePath;
    this.imageTag = imageTag;
    this.pushImage = pushImage;
  }

  @Override
  public void buildImage() {
    try {
      StringBuilder sb = new StringBuilder("docker buildx build");
      if (StringUtils.isNotBlank(targetPlatforms)) {
        sb.append(" --platform=").append(targetPlatforms);
      }
      sb.append(" -t ")
          .append(imageTag)
          .append(" ")
          .append(Utils.pushOrKeepLocally(pushImage))
          .append(" .");

      CommandLineApi.executeCommand(dockerfilePath, "sh", "-c", sb.toString());

      if (!pushImage) {
        registerImageLocally();
      }
    } catch (IOException | InterruptedException e) {
      throw new IllegalStateException("Build via buildx failed", e);
    }
  }

  @Override
  public void registerImageLocally() {
    try {
      CommandLineApi.executeCommand(
          dockerfilePath, "sh", "-c", String.format("cat out.tar | docker import - %s", imageTag));
    } catch (IOException | InterruptedException e) {
      throw new IllegalStateException("Image import to local registry failed", e);
    }
  }
}
