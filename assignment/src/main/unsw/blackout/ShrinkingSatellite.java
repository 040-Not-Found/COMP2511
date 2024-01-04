package unsw.blackout;

import java.util.List;

import unsw.utils.Angle;

public class ShrinkingSatellite extends StorableSatellite {
    private final int linearVelocity;
    private double angularVelocity;
    private final int transferRange;
    private final int sendingSpeed;
    private final int receivingSpeed;
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
    public ShrinkingSatellite(String satelliteId, String type, double height, Angle position) {
        super(satelliteId, type, height, position);
        this.linearVelocity = 1000;
        this.angularVelocity = linearVelocity/height;
        this.transferRange = 200000;
        this.sendingSpeed = 10;
        this.receivingSpeed = 15;
        this.avaliableStorage = 150;
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
    public void setSendingBandwidth(int sendingFileSize) {
        if (sendingFileSize == 0) {
            this.sendingBandwidth = sendingSpeed;
        } else {
            this.sendingBandwidth = (int) Math.floor(sendingSpeed/sendingFileSize);
        }
    }

    @Override
    public int getReceivingBandwidth() {
        return receivingBandwidth;
    }
    
    @Override
    public void setReceivingBandwidth(int receivingFileSize) {
        if (receivingFileSize == 0) {
            this.receivingBandwidth = receivingSpeed;
        } else {
            this.receivingBandwidth = (int) Math.floor(receivingSpeed/receivingFileSize);
        }
    }
    
    @Override
    public boolean haveEnoughStorage(int fileSize, int numberOfFiles) {
        if (fileSize <= avaliableStorage) {
            return true;
        }
        return false;
    }

    @Override
    public void updateSendingFile(List<File> files, List<SendingFile> sendingFiles, List<String> communicableEntities, List<Device> devices, List<Satellite> satellites) {
        setSendingBandwidth(sendingFiles.size());
        try {
            for (SendingFile sendingFile : sendingFiles) {
                // remove file if it is disconnect
                if (!SearchHelper.isCommunicatable(sendingFile.getToId(), communicableEntities)) {
                    sendingFiles.remove(sendingFile);
                    continue;
                }
                sendingFile.setSendingStatus(sendingBandwidth);
                // remove file if it is commplete
                if (sendingFile.getSendingStatus() == sendingFile.getFileSize()) {
                    sendingFiles.remove(sendingFile);
                    continue;
                }
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void updateRecevingFile(List<File> files, List<ReceivingFile> receivingFiles, List<String> communicableEntities, List<Device> devices, List<Satellite> satellites) {
        setReceivingBandwidth(receivingFiles.size());
        try {
            for (ReceivingFile receivingFile : receivingFiles) {
                // remove file if it is disconnect
                if (!SearchHelper.isCommunicatable(receivingFile.getFromId(), communicableEntities)) {
                    receivingFiles.remove(receivingFile);
                    removeFile(SearchHelper.searchFile(devices, satellites, receivingFile.getFilename(), getSatelliteId()));
                    continue;
                }

                File file = SearchHelper.searchFile(devices, satellites, receivingFile.getFilename(), getSatelliteId());
                // receiving contents dependents on the bandwidth
                for (int i = 0; i < getReceivingBandwidth(); i++) {
                    file.addContent(receivingFile.getContent().charAt(0));
                    receivingFile.transferContent();
                    receivingFile.setReceivingStatus(1);
                    if (file.getContent().length() == file.getFileSize()) {
                        receivingFiles.remove(receivingFile);
                        file.setHasTransferCompleted(true);
                        if (file.getContent().contains("quantum")) {// compress "quantum file"
                            file.setFileSize((int) Math.ceil(file.getFileSize()*2/3));
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
    }


}
