package dark2phoenix.mods.rescuechest.lib;

import dark2phoenix.mods.rescuechest.core.types.Inventory;

public class Constants {

    
    /**
     * Name of TileEntityRescueChest
     */
    public static final String TILE_ENTITY_RESCUE_CHEST_NAME = "TileEntityRescueChest";
    
    
    /**
     * Name of the primary NBTTagCompound that holds Rescue Chest Data
     */
    public static final String NBT_RESCUE_CHEST_TAG_NAME = "RescueChest";
    
    /**
     * Armor slot values used to determine which pieces go in which slot
     */
    public enum ArmorSlots {
        HELMET(0), CHEST_PLATE(1), GREAVES(2), BOOTS(3);
        private int value;

        private ArmorSlots(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }
    
    /**
     *  Inventory Types to determine which inventory is being processes
     *
     */
    public enum InventoryType {
        ARMOR("Armor", 0), MAIN("Main", 4 ), HOTBAR("Hotbar", 31);
        private Inventory inventory;
        
        private InventoryType(String newName, int newStartingOffset ) {
            this.inventory = new Inventory(newName, newStartingOffset);
        }   
        
        public int getStartingOffset() {
            return this.inventory.getStartingOffset();
        }
        
        public String getName() {
            return this.inventory.getName();
        }
    }
    
    public enum UpgradeCoinType {
        WOOD(1), EARTH(4), METAL(8), DIMENSION(16);
        
        private int upgradeValue;
        
        private UpgradeCoinType(int startingUpgradeValue) {
            this.upgradeValue = startingUpgradeValue;
        }
        
        public int getUpgradeValue() {
            return this.upgradeValue;
        }
        
    }
    
    

}
