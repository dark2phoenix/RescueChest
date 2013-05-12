package dark2phoenix.mods.rescuechest;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.client.registry.ClientRegistry;
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
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import dark2phoenix.mods.rescuechest.block.BlockRescueChest;
import dark2phoenix.mods.rescuechest.client.renderer.TileEntityRescueChestRenderer;
import dark2phoenix.mods.rescuechest.core.Localization;
import dark2phoenix.mods.rescuechest.core.Version;
import dark2phoenix.mods.rescuechest.core.handlers.PlayerLivingDeathEventHandler;
import dark2phoenix.mods.rescuechest.core.proxy.CommonProxy;
import dark2phoenix.mods.rescuechest.gui.GuiHandler;
import dark2phoenix.mods.rescuechest.item.ItemHotBar;
import dark2phoenix.mods.rescuechest.lib.Reference;
import dark2phoenix.mods.rescuechest.tileentity.TileEntityRescueChest;

@Mod(modid = "RescueChest", name = "Rescue Chest", version = "0.0.1")
@NetworkMod(clientSideRequired = true, serverSideRequired = true)
public class RescueChest {

	public static final Logger logger = Logger.getLogger("RescueChest");

	public static final String modid = Reference.MOD_ID;
	
	String sourceClass = this.getClass().getName();
	
	private int rescueChestBlockId;
	public int hotbarItemId;
	
	public static Block rescueChestBlock;
	
    public static Item hotbarItem;

	/**
	 * Instance of the mod used by Forge
	 */
	@Instance("RescueChest")
	public static RescueChest instance;

	
	public static Configuration rescueChestConfig;
	
	/**
	 * Forge Proxy Information
	 */
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		String sourceMethod = "preInit";
		logger.entering(sourceClass, sourceMethod, event);
		Version.init(event.getVersionProperties());
		event.getModMetadata().version = Version.fullVersionString();
		Configuration rescueChestConfig = new Configuration(event.getSuggestedConfigurationFile());
		try {
		    rescueChestConfig.load();
			rescueChestBlockId = rescueChestConfig.getBlock("RescueChest", 501).getInt(501);
			hotbarItemId = rescueChestConfig.getItem("HotbarItem", 29775).getInt(29775);
			logger.logp(Level.FINE, sourceClass, sourceMethod, "Rescue Chest Block ID is " + rescueChestBlockId);

	       Property defaultLanguageProp = rescueChestConfig.get("general", "DefaultLanguage", "en_US");
	        
	        Localization.addLocalization( "/lang/rescuechest/", defaultLanguageProp.getString() );
			
			
		}
		catch (Exception e) {
			FMLLog.log(Level.SEVERE, e, "Rescue Chest could not load configuration");
		}
		finally {
		    rescueChestConfig.save();
		}
		logger.exiting(sourceClass, sourceMethod);
	}


    @Init
	public void load(FMLInitializationEvent event) {

		// Setup our chest block
		
		rescueChestBlock = new BlockRescueChest(rescueChestBlockId, BlockRescueChest.material );

		GameRegistry.registerBlock(rescueChestBlock, "rescueChest");
		GameRegistry.registerTileEntity(TileEntityRescueChest.class, "rescueChest");

		proxy.registerRenderInformation();
		proxy.registerTileEntitySpecialRenderer();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRescueChest.class, new TileEntityRescueChestRenderer());

		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());

		// Define our ore dictionary resources so other variations of these elements will work
		OreDictionary.registerOre("plankWood", new ItemStack(Block.planks));
		OreDictionary.registerOre("ingotGold", new ItemStack(Item.ingotGold));

		// Register custom events
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new PlayerLivingDeathEventHandler());

    
    }

	public RescueChest() {
		super();

		logger.setParent(FMLLog.getLogger());
		logger.setLevel(Level.FINEST);
		logger.setUseParentHandlers(true);
	
	}

    @SuppressWarnings("unchecked")
	@PostInit
	public void postInit(FMLPostInitializationEvent event) {
	    hotbarItem = new ItemHotBar(hotbarItemId).setUnlocalizedName("hotbarItem");
        LanguageRegistry.addName(hotbarItem, "Hotbar Item");

        
        // Define our ore dictionary resources so other variations of these elements will work
        // OreDictionary.registerOre("plankWood", new ItemStack(Block.planks));
        // OreDictionary.registerOre("ingotGold", new ItemStack(Item.ingotGold));
        //        
        // Define our ore dictionary resources so other variations of these elements will work

        List<String> oreNameList = Arrays.asList( OreDictionary.getOreNames() );
        
        if ( ! oreNameList.contains("plankWood") ) {
            OreDictionary.registerOre("plankWood", new ItemStack(Block.planks));
        }
        if ( ! oreNameList.contains("ingotGold") ) {
            OreDictionary.registerOre("ingotGold", new ItemStack(Item.ingotGold));
        }
        
        // Recipes to add
        CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(new ItemStack(rescueChestBlock), true, new Object[] { "PPP", "PGP", "PPP", Character.valueOf('P'), "plankWood", Character.valueOf('G'), "ingotGold" }));
        LanguageRegistry.addName(rescueChestBlock, "Rescue Chest");
	}
}
