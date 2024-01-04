package unsw.blackout;

import java.util.List;

import unsw.utils.Angle;

public class StandardSatellite extends StorableSatellite{
    private final int linearVelocity;
    private double angularVelocity;
    private final int transferRange;
    private final int sendingSpeed;
    private final int receivingSpeed;
    private final int maxFile;
    private int avaliableStorage;
    private int sendingBandwidth;
    private int receivingBandwidth;
    /**
     * Set extra attributes to the satelllite
     *  @param satelliteId 
     * @param type of the satellite
     * @param height (km)
     * @param position (degrees)
     */
    public StandardSatellite(String satelliteId, String type, double height, Angle position) {
        super(satelliteId, type, height, position);
        this.linearVelocity = 2500;
        this.angularVelocity = linearVelocity/height;
        this.transferRange = 150000;
        this.sendingSpeed = 1;
        this.receivingSpeed = 1;
        this.maxFile = 3;
        this.avaliableStorage = 80;
        this.sendingBandwidth = sendingSpeed;
        this.receivingBandwidth = receivingSpeed;
    }
    
    @Override
    public final int getLinearVelocity() {
        return linearVelocity;
    }
    
    @Override
    public void setAngularVelocity(double angularVelocity) {
        this.angularVelocity = angularVelocity;
    }
    
    @Override
    public double getAngularVelocity() {
       return angularVelocity;
    }
    
    @Override
    public void setAvaliableStorage(int use) {
        this.avaliableStorage -= use;
    }

    @Override
    public int getAvaliableStorage() {
        return avaliableStorage;
    }
    
    @Override
    public final int getTransferRange() {
        return transferRange;
    }

    @Override
    public int getSendingSpeed() {
        return sendingSpeed;
    }

    @Override
    public int getReceivingSpeed() {
        return receivingSpeed;
    }

    @Override
    public int getSendingBandwidth() {
        return sendingBandwidth;
    }

    @Override
    public void setSendingBandwidth(int fileSize) {
        this.sendingBandwidth -= fileSize;
    }

    @Override
    public int getReceivingBandwidth() {
        return receivingBandwidth;
    }

    @Override
    public void setReceivingBandwidth(int fileSize) {
        this.receivingBandwidth -= fileSize;
    }

    /**
     * getter function for the maxFile can store in this satellite
     * @return
     */
    public int getMaxFile() {
        return maxFile;
    }
    
    @Override
    public boolean haveEnoughStorage(int fileSize, int numberOfFiles) {
        if (numberOfFiles < maxFile && fileSize <= avaliableStorage) {
            return true;
        }
        return false;
    }

    @Override
    public void updateSendingFile(List<File> files, List<SendingFile> sendingFiles, List<String> communicableEntities, List<Device> devices, List<Satellite> satellites) {
        sendingBandwidth = 1;
        try {
            for (SendingFile sendingFile : sendingFiles) {
                // remove file if it is disconnect
                // and check the next sending file
                if (!SearchHelper.isCommunicatable(sendingFile.getToId(), communicableEntities)) {
                    sendingFiles.remove(sendingFile);
                    continue;
                }

                sendingFile.setSendingStatus(1);
                // if send complete, then this is done as StandardSatellite can only
                // send one file at a time
                if (sendingFile.getSendingStatus() == sendingFile.getFileSize()) {
                    sendingFiles.remove(sendingFile);
                    return;
                }
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void updateRecevingFile(List<File> files, List<ReceivingFile> receivingFiles, List<String> communicableEntities, List<Device> devices, List<Satellite> satellites) {
        receivingBandwidth = 1;
        try {
            for (ReceivingFile receivingFile : receivingFiles) {
                // remove file if it is disconnect
                if (!SearchHelper.isCommunicatable(receivingFile.getFromId(), communicableEntities)) {
                    receivingFiles.remove(receivingFile);
                    removeFile(SearchHelper.searchFile(devices, satellites, receivingFile.getFilename(), getSatelliteId()));
                    continue;
                }
                // receiving content
                File file = SearchHelper.searchFile(devices, satellites, receivingFile.getFilename(), getSatelliteId());
                String content = receivingFile.getContent();
                file.addContent(content.charAt(0));
                receivingFile.transferContent();
                receivingFile.setReceivingStatus(1);
                if (receivingFile.getReceivingStatus() == file.getFileSize()) {
                    receivingFiles.remove(receivingFile);
                    file.setHasTransferCompleted(true);
                    return;
                }
            }
           
        } catch (Exception e) {
        }
    }

}
