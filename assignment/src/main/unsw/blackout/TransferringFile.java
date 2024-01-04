package unsw.blackout;

public abstract class TransferringFile extends File{

    private String fromId;
    private String toId;
    /**
     * Set extra attributes to the satelllite
     * @param filename
     * @param content of the file
     * @param fromId
     * @param toId
     */
    public TransferringFile(String filename, String content, String fromId, String toId) {
        super(filename, content);
        this.fromId = fromId;
        this.toId = toId;
    }
    
    /**
     * @return id who send the file
     */
    public String getFromId() {
        return fromId;
    }

    /**
     * @return if who receive the file
     */
    public String getToId() {
        return toId;
    }

    
}
