package dark2phoenix.mods.rescuechest.core.types;

public class Inventory {

    private String name;

    private int startingOffset;

    
    public Inventory( int inStartingOffset, String inName ) {
        this.startingOffset = inStartingOffset;
        this.name = inName;
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
    public int getId() {
        return startingOffset;
    }

    /**
     * @param startingOffset
     *            the id to set
     */
    public void setId(int newStartingOffset) {
        this.startingOffset = newStartingOffset;
    }

}
