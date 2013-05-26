package dark2phoenix.mods.rescuechest.configuration;


public class ConfigurationSettings {

    /*
     * Version check related settings
     */
    public static boolean DISPLAY_VERSION_RESULT;
    public static final String DISPLAY_VERSION_RESULT_CONFIGNAME = "version_check.display_results";
    public static final boolean DISPLAY_VERSION_RESULT_DEFAULT = true;

    public static String LAST_DISCOVERED_VERSION;
    public static final String LAST_DISCOVERED_VERSION_CONFIGNAME = "version_check.last_discovered_version";
    public static final String LAST_DISCOVERED_VERSION_DEFAULT = "";

    public static String LAST_DISCOVERED_VERSION_TYPE;
    public static final String LAST_DISCOVERED_VERSION_TYPE_CONFIGNAME = "version_check.last_discovered_version_type";
    public static final String LAST_DISCOVERED_VERSION_TYPE_DEFAULT = "";

    
    /*
     * NLS Settings  
     */
    public static String DEFAULT_LANGUAGE;
    public static final String DEFAULT_LANGUAGE_CONFIGNAME = "DefaultLanguage";
    public static final String DEFAULT_LANGUAGE_DEFAULT = "en_US";

    
    
    /*
     * Audio config settings
     */
    public static boolean ENABLE_SOUNDS;
    public static final String ENABLE_SOUNDS_CONFIGNAME = "sounds.enabled";
    public static final boolean ENABLE_SOUNDS_DEFAULT = true;

    /*
     * Graphic config settings
     */
    // Whether or not particle fx are enabled
    public static boolean ENABLE_PARTICLE_FX;
    public static final String ENABLE_PARTICLE_FX_CONFIGNAME = "particle_fx.enabled";
    public static final boolean ENABLE_PARTICLE_FX_DEFAULT = true;
}
