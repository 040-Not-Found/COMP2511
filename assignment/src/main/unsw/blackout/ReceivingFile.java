package unsw.blackout;

public class ReceivingFile extends TransferringFile{
    private int receivingStatus;
    /**
     * Set extra attributes to the satelllite
     * @param filename
     * @param content of the file
     * @param fromId
     * @param toId
     */
    public ReceivingFile(String filename, String content, String fromId, String toId){
        super(filename, content, fromId, toId);
        this.receivingStatus = 0;
    }

    /**
     * @return receiving status of the file
     */
    public int getReceivingStatus() {
        return receivingStatus;
    }

    /**
     * when the file updated, increase the receiving status
     * @param receivingStatus is the content that has receivied
     */
    public void setReceivingStatus(int receivingStatus) {
        this.receivingStatus += receivingStatus;
    }

}
