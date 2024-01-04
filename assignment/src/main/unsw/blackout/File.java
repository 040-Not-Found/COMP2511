package unsw.blackout;

public class File {
    private String filename;
    private String content;
    private int fileSize;
    private boolean hasTransferCompleted;
    /**
     * @param filename
     * @param content of the file
     */
    public File(String filename, String content) {
        this.filename = filename;
        this.content = content;
        this.hasTransferCompleted = false;
        this.fileSize = content.length();
    }

    /**
     * @return filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @return content of the file
     */
    public String getContent() {
        return content;
    }

    /**
     * set file content
     * use in transferring files
     * @param content of the file
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * add file content
     * @param char that wants to add into the content
     */
    public void addContent(char c) {
        setContent(this.content += c);
    }

    /**
     * remove the first char of the content
     * this is use in transferring files
     */
    public void transferContent() {
        String s = getContent();
        setContent(s.substring(1));
    }
    
    /**
     * set the file size
     * This is use in transferring files, as we would
     * like to save the storage for the transferring files
     * @param fileSize that we wants to set with
     */
    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * @return file size (not the length of the content if is a transferring file)
     */
    public int getFileSize() {
        return fileSize;
    }

    /**
     * @param completed is a boolean to set if the file has transfer completed
     */
    public void setHasTransferCompleted(boolean completed) {
        this.hasTransferCompleted = completed;
    }

    /**
     * @return true if the file has transfer complete
     */
    public boolean hasTransferCompleted() {
        return hasTransferCompleted;
    }
    
}
