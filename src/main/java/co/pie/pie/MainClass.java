package co.pie.pie;

import co.pie.pie.platform.api.TargetPlatformCli;
import co.pie.pie.platform.api.TargetPlatformCliFactory;

public class MainClass {
  /**
   * Prerequisites: 1) docker login should've been done manually in the forefield 2) ...
   *
   * @param args args[0] => platform(s) you want to build the image(s) for; if missing the current
   *     platform will be used as the target one for the image args[1] => your dockerfile location
   *     on the filesystem args[2] => operation mode; local/pushToRemote args[3] => tag
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      throw new IllegalArgumentException("no arguments provided");
    }

    TargetPlatformCliFactory targetPlatformCliFactory = new TargetPlatformCliFactory();
    TargetPlatformCli targetPlatformCli =
        targetPlatformCliFactory.create(args[0], args[1], args[3], Boolean.parseBoolean(args[2]));
    targetPlatformCli.buildImage();
  }
}
