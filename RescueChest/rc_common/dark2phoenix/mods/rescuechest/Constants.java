package dark2phoenix.mods.rescuechest;

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
};  


}
