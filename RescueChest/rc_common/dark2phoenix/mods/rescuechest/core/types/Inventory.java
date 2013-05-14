package dark2phoenix.mods.rescuechest.core.types;

public class Inventory {

    private String name;

    private int startingOffset;

    
    public Inventory( String inName, int inStartingOffset ) {
        this.name = inName;
        this.startingOffset = inStartingOffset;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * @return the id
     */
    public int getStartingOffset() {
        return startingOffset;
    }

    /**
     * @param startingOffset
     *            the id to set
     */
    public void setStartingOffset(int newStartingOffset) {
        this.startingOffset = newStartingOffset;
    }

}
