package dark2phoenix.mods.rescuechest;

import java.util.Properties;

public class Version {
    private static String major;
    private static String minor;
    private static String rev;
    private static String build;
    private static String mcversion;

    static void init(Properties properties)
    {
        if (properties != null)
        {
            major = properties.getProperty("RescueChest.build.major.number");
            minor = properties.getProperty("RescueChest.build.minor.number");
            rev = properties.getProperty("RescueChest.build.revision.number");
            build = properties.getProperty("RescueChest.build.number");
            mcversion = properties.getProperty("RescueChest.build.mcversion");
        }
    }

    public static String fullVersionString()
    {
        return String.format("%s.%s.%s build %s", major, minor, rev, build);
    }
}