package dark2phoenix.mods.rescuechest.item;

import dark2phoenix.mods.rescuechest.lib.Constants.UpgradeCoinType;

public abstract class ItemChestUpgradeCoin extends ItemBase {

    public ItemChestUpgradeCoin(int id) {
        super(id);
    }
    
    protected UpgradeCoinType coinType;

    public int getUpgradeValue() {
        return coinType.getUpgradeValue();
    }
    
}
