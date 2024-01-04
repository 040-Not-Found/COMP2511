package unsw.blackout;

public class SendingFile extends TransferringFile{
    private int sendingStatus;
    /**
     * Set extra attributes to the satelllite
     * @param filename
     * @param content of the file
     * @param fromId
     * @param toId
     */
    public SendingFile(String filename, String content, String fromId, String toId){
        super(filename, content, fromId, toId);
        this.sendingStatus = 0;
    }

    /**
     * @return sending status of the file
     */
    public int getSendingStatus() {
        return sendingStatus;
    }

    /**
     * when the file updated, increase the sending status
     * @param sendingStatus is the content that has sent
     */
    public void setSendingStatus(int sendingStatus) {
        this.sendingStatus += sendingStatus;
    }

}

