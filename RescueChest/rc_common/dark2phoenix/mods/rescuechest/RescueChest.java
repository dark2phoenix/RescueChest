package dark2phoenix.mods.rescuechest;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import dark2phoenix.mods.rescuechest.block.ModBlocks;
import dark2phoenix.mods.rescuechest.configuration.ConfigurationHandler;
import dark2phoenix.mods.rescuechest.configuration.ConfigurationSettings;
import dark2phoenix.mods.rescuechest.core.Localization;
import dark2phoenix.mods.rescuechest.core.Version;
import dark2phoenix.mods.rescuechest.core.handlers.IconRegistryHandler;
import dark2phoenix.mods.rescuechest.core.handlers.PlayerLivingDeathEventHandler;
import dark2phoenix.mods.rescuechest.core.proxy.CommonProxy;
import dark2phoenix.mods.rescuechest.gui.GuiHandler;
import dark2phoenix.mods.rescuechest.item.ModItems;
import dark2phoenix.mods.rescuechest.lib.Reference;
import dark2phoenix.mods.rescuechest.network.PacketHandler;

@Mod(modid = "RescueChest", name = "Rescue Chest")
@NetworkMod(channels = { "RescueChest" }, clientSideRequired = true, serverSideRequired = true, packetHandler = PacketHandler.class)
public class RescueChest {

    /** Common Mod Logger */
    public static Logger        logger              = Logger.getLogger("RescueChest");

    /** this class's name */
    String                      sourceClass         = this.getClass().getName();

    /** Forge Proxy Information */
    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy   proxy;

    /**
     * Instance of the mod used by Forge
     */
    @Instance("RescueChest")
    public static RescueChest   instance;

    private static int          rescueChestRenderID = RenderingRegistry.getNextAvailableRenderId();

    /**
     * @return the dimChestRenderID
     */
    public static int getRescueChestRenderID() {
        return rescueChestRenderID;
    }

    /**
     * @param dimChestRenderID
     *            the dimChestRenderID to set
     */
    public static void setDimChestRenderID(int dimChestRenderID) {
        RescueChest.rescueChestRenderID = dimChestRenderID;
    }

    @PreInit
    public void preInit(FMLPreInitializationEvent event) {
        String sourceMethod = "preInit";
        logger.entering(sourceClass, sourceMethod);
        logger.logp(Level.INFO, sourceClass, sourceMethod, String.format("LogLevel is currently %s", logger.getLevel().getName()));

        logger.entering(sourceClass, sourceMethod, event);
        Version.init(event.getVersionProperties());
        event.getModMetadata().version = Version.fullVersionString();
        try {
            // Initialize the configuration
            ConfigurationHandler.init(event.getSuggestedConfigurationFile());
        } catch (Exception e) {
            FMLLog.log(Level.SEVERE, e, "Rescue Chest could not load configuration");
        } 
//        Property defaultLanguageProp = rescueChestConfig.get("general", "DefaultLanguage", "en_US");
        Localization.addLocalization("/lang/rescuechest/", ConfigurationSettings.DEFAULT_LANGUAGE);

        // Setup Blocks
        ModBlocks.init();

        // Setup Items
        ModItems.init();
        
        // Register the Sound Handler (Client only)
        proxy.registerSoundHandler();
       
        
        logger.exiting(sourceClass, sourceMethod);
    }

    @Init
    public void load(FMLInitializationEvent event) {

 
        // Register Tile Entities
        proxy.initTileEntities();
        
        //Register Renderers
        proxy.registerTileEntitySpecialRenderer();
        proxy.registerRenderInformation();


        // Setup GUI's
        NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());

        // Register Custom Event Handlers 
        MinecraftForge.EVENT_BUS.register(new PlayerLivingDeathEventHandler());
        
        MinecraftForge.EVENT_BUS.register(new IconRegistryHandler());
        
        MinecraftForge.EVENT_BUS.register(this);
    }

    public RescueChest() {
        super();

        logger.setParent(FMLLog.getLogger());
        logger.setLevel(Level.FINEST);
        logger.setUseParentHandlers(true);
    }

    @PostInit
    public void postInit(FMLPostInitializationEvent event) {
        List<String> oreNameList = Arrays.asList(OreDictionary.getOreNames());

        // Define our ore dictionary resources so other variations of these
        // elements will work, but only if they have not already been registered

        if (!oreNameList.contains("plankWood")) {
            OreDictionary.registerOre("plankWood", new ItemStack(Block.planks));
        }
        if (!oreNameList.contains("ingotGold")) {
            OreDictionary.registerOre("ingotGold", new ItemStack(Item.ingotGold));
        }
        if (!oreNameList.contains("woodLog")) {
            OreDictionary.registerOre("woodLog", new ItemStack(Block.wood));
        }


        // Recipes to add
    }
}
