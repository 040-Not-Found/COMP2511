package unsw.blackout;

import java.util.ArrayList;
import java.util.List;

import unsw.utils.Angle;

public abstract class StorableSatellite extends Satellite {
    private List<File> files;
    private List<SendingFile> sendingFiles;
    private List<ReceivingFile> receivingFiles;
    /**
     * Set extra attributes to the satelllite
     *  @param satelliteId 
     * @param type of the satellite
     * @param height (km)
     * @param position (degrees)
     */
    public StorableSatellite(String satelliteId, String type, double height, Angle position) {
        super(satelliteId, type, height, position);
        this.files = new ArrayList<>();
        this.sendingFiles = new ArrayList<>();
        this.receivingFiles = new ArrayList<>();
    }

    /**
     * @return the files in stored in the satellite
     */
    public List<File> getFiles() {
        return files;
    }
    
    /**
     * remove the file in the list
     * use for transfer incomplete files
     * @param file that want to remove
     */
    public void removeFile(File file) {
        files.remove(file);
    }
    
    /**
     * @return a list of receiving files
     */
    public List<ReceivingFile> getReceivingFiles() {
        return receivingFiles;
    }
    
    @Override
    public void updatePosition() {
        double newPosition = (this.getAngularVelocity()+this.getPosition().toRadians())%(2*Math.PI);
        super.setPosition(Angle.fromRadians(newPosition));
    }
    
    /**
     * update the transferring files
     * @param communicableEntities is a list of this satellite's communicableEntities
     * @param devices is a list of devices
     * @param satellites is a list of satellites
     */
    public void upadteTransferringFiles(List<String> communicableEntities, List<Device> devices, List<Satellite> satellites) {
        this.updateSendingFile(files, sendingFiles, communicableEntities, devices, satellites);
        this.updateRecevingFile(files, receivingFiles, communicableEntities, devices, satellites);
    }
    
    /**
     * satellite sends a file
     * add the file to the list of SendingFiles
     * @param file wants to send
     * @param fromId (this id)
     * @param toId 
     */
    public void addSendingFile(File file, String fromId, String toId) {
        sendingFiles.add(new SendingFile(file.getFilename(), file.getContent(), fromId, toId));
        setSendingBandwidth(sendingFiles.size());
    }
    
    /**
     * receive a file from a device or satellite
     * add it to the receiving file list
     * and safe storage for this file
     * @param file wants to receive
     * @param fromId 
     * @param toId (this id)
     */
    public void addReceivingFile(File file, String fromId, String toId) {
        if (!this.haveEnoughStorage(file.getFileSize(),files.size())) {
            return;
        }
        this.receivingFiles.add(new ReceivingFile(file.getFilename(), file.getContent(), fromId, toId));
        this.setAvaliableStorage(file.getFileSize());
        files.add(new File(file.getFilename(), ""));
        files.get(files.size()-1).setFileSize(file.getFileSize());
        setReceivingBandwidth(receivingFiles.size());
    }
    
    /**
     * @return the satellite's linearVelocity
     */
    public abstract int getLinearVelocity();
    
    /**
     * set the setAngularVelocity
     * @param angularVelocity we want to set
     */
    public abstract void setAngularVelocity(double angularVelocity);

    /**
     * @return angularVelocity
     */
    public abstract double getAngularVelocity();
    
    /**
     * decrease the avaliable storage by minus the use
     * of the storage
     * @param use of storage of the files
     */
    public abstract void setAvaliableStorage(int use);
    
    /**
     * @return the current avaliable storage
     */
    public abstract int getAvaliableStorage();
    
    /**
     * @return the transfer range for the satellite
     */
    public abstract int getTransferRange();

    /**
     * @return sendingSpeed of the satellite
     */
    public abstract int getSendingSpeed();
    
    /**
     * @return receiving speed of the satllite
     */
    public abstract int getReceivingSpeed();
    
    /**
     * divide the bandwidth to the sendingFiles
     * @param fileSize of the list of sending files
     */
    public abstract void setSendingBandwidth(int fileSize);
    
    /**
     * @return sendingBandwidth
     */
    public abstract int getSendingBandwidth();
    
    /**
     * divide the bandwidth to the receivingFiles
     * @param fileSize of the list of receiving files
     */
    public abstract void setReceivingBandwidth(int fileSize);
    
    /**
     * @return receivingBandwidth
     */
    public abstract int getReceivingBandwidth();

    /**
     * check if the satellite have the enough storage to
     * receiving the file
     * @param fileSize
     * @param numberOfFiles as StandardSatellite can receive up to 3 files
     * @return true if the satellite have enough storage
     */
    public abstract boolean haveEnoughStorage(int fileSize, int numberOfFiles);
    
    /**
     * remove the sendingFiles that is complete or disconnect from the toId
     * and recalculate the bandwidth 
     * @param sendingFiles is a list contains sendingFiles
     */
    public abstract void updateSendingFile(List<File> files, List<SendingFile> sendingFiles, List<String> communicableEntities, List<Device> devices, List<Satellite> satellites);
    
    /**
     * remove the receivingFiles that is complete or disconnect from fromId
     * and recalculate the bandwidth 
     * @param files a list of satellites exist file
     * @param receivingFiles a list of receiving files
     * @param communicableEntities check if is still connecting
     * @param devices is a list of devices
     * @param satellites is a list of satellites
     */
    public abstract void updateRecevingFile(List<File> files, List<ReceivingFile> receivingFiles, List<String> communicableEntities, List<Device> devices, List<Satellite> satellites);
    
}
