package dark2phoenix.mods.rescuechest.lib;

import dark2phoenix.mods.rescuechest.core.types.Inventory;

public class Constants {

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
        ARMOR(0, "Armor"), MAIN(4, "Main");
        private Inventory inventory;
        
        private InventoryType(int newStartingOffset, String newName ) {
            this.inventory = new Inventory(newStartingOffset, newName);
        }   
        
        public int getStartingOffset() {
            return this.inventory.getId();
        }
        
        public String getName() {
            return this.inventory.getName();
        }
    }

}
