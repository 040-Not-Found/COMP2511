package unsw.blackout;

import java.util.ArrayList;
import java.util.List;
import static unsw.utils.MathsHelper.RADIUS_OF_JUPITER;
import unsw.utils.Angle;

public abstract class Device{
    private String devicedId;
    private Angle position;
    private final double height;
    private String type;
    private List<File> files;
    private List<ReceivingFile> receivingFiles;
    private List<String> connectedIds;
    /**
     * Set extra attributes to the satelllite
     * @param deviceId
     * @param type
     * @param position (degrees)
     */
    public Device(String deviceId, String type, Angle position) {
        this.devicedId = deviceId;
        this.type = type;
        this.position = position;
        this.height = RADIUS_OF_JUPITER;
        this.files = new ArrayList<>();
        this.connectedIds = new ArrayList<>();
        this.receivingFiles = new ArrayList<>();
    }

    /**
     * @return id of the device
     */
    public String getDevicedId() {
        return devicedId;
    }

    /**
     * @return position, in degrees, of the device
     */
    public Angle getPosition() {
        return position;
    }

    /**
     * @return height of the device
     */
    public final double getHeight() {
        return height;
    }

    /**
     * @return type of the device
     */
    public String getType() {
        return type;
    }

    /**
     * @return maximum transferring range of the device
     */
    public abstract int getTransferRange();

    /**
     * add a file to the device
     * if file exist, return
     * @param filename
     * @param content of the file
     */
    public void addFile(String filename, String content) {
        if (SearchHelper.searchFile(files, filename) != null) {
            return;
        }
        files.add(new File(filename, content));
    }

    /**
     * @return a list of file contains by the device
     */
    public List<File> getFiles() {
        return files;
    }

    /**
     * @return a list of receiving file
     */
    public List<ReceivingFile> getReceivingFiles() {
        return receivingFiles;
    }

    /**
     * @return a list of satellite ids that this
     * connecting to this device
     */
    public List<String> getConnectedIds() {
        return connectedIds;
    }

    /**
     * update the connections with the device
     * @param devices a list of devices
     * @param satellites a list of satellites
     */
    public void updateConnection(List<Device> devices, List<Satellite> satellites) {
        connectedIds.clear();
        for (Satellite satellite : satellites) {
            if (!SearchHelper.isSupportsDevice(this, satellite)) {
                continue;
            }
            SearchHelper.tryToConnectSatellite(satellite, getPosition(), getTransferRange(), connectedIds);
        }
    }

    /**
     * add satellite to the connectedIds list
     * @param id of the satellite
     */
    public void connectSatellite(String id) {
        connectedIds.add(id);
    }

    /**
     * add the file to the receiving file list
     * @param file
     * @param fromId
     * @param toId
     */
    public void addReceivingFile(File file, String fromId, String toId) {
        receivingFiles.add(new ReceivingFile(file.getFilename(), file.getContent(), fromId, toId));
        files.add(new File(file.getFilename(), ""));
        files.get(files.size()-1).setFileSize(file.getContent().length());
    }

    /**
     * update the status of the receiving file
     * @param communicableEntities is a list of communicableEntities
     * @param devices is a list of devices
     * @param satellites is a list of satellites
     */
    public void updateRecevingFile(List<String> communicableEntities, List<Device> devices, List<Satellite> satellites) {
        try{
            for (ReceivingFile receivingFile : receivingFiles) {
                // remove file if it is disconnect
                if (!SearchHelper.isCommunicatable(receivingFile.getFromId(), communicableEntities)) {
                    receivingFiles.remove(receivingFile);
                    files.remove(SearchHelper.searchFile(devices, satellites, receivingFile.getFilename(), getDevicedId()));
                    continue;
                }

                File file = SearchHelper.searchFile(devices, satellites, receivingFile.getFilename(), getDevicedId());
                StorableSatellite satellite = SearchHelper.searchStorableSatellite(satellites, receivingFile.getFromId());
                // adding content to the file
                for (int i = 0; i < satellite.getSendingBandwidth(); i++) {
                    file.addContent(receivingFile.getContent().charAt(0));
                    receivingFile.transferContent();
                    receivingFile.setReceivingStatus(1);
                    if (receivingFile.getReceivingStatus() == file.getFileSize()) {
                        receivingFiles.remove(receivingFile);
                        file.setHasTransferCompleted(true);
                    }
                }
            }
        } catch (Exception e) {
        }
    }

}
