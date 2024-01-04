package unsw.blackout;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import unsw.response.models.EntityInfoResponse;
import unsw.response.models.FileInfoResponse;
import unsw.utils.Angle;

public class BlackoutController {
    private List<Device> devices = new ArrayList<Device>();
    private List<Satellite> satellites = new ArrayList<Satellite>();

    /**
     * create a Device and add it to the devices list
     * set the connects between the satellites
     * @param deviceId
     * @param type the of device
     * @param position (degrees)
     */
    public void createDevice(String deviceId, String type, Angle position) {
        if (getInfo(deviceId) != null) { // Id already exist
            return;
        }

        // creating device
        if (type.equals("HandheldDevice")) {
            devices.add(new HandheldDevice(deviceId, type, position));
        } else if (type.equals("LaptopDevice")) {
            devices.add(new LaptopDevice(deviceId, type, position));
        } else if (type.equals("DesktopDevice")) {
            devices.add(new DesktopDevice(deviceId, type, position));
        }
        
        // setting connections
        Device device = SearchHelper.searchDevice(devices, deviceId);
        device.updateConnection(devices, satellites);
        for (Satellite satellite : satellites) {
            SearchHelper.tryToConnectDevice(device, satellite.getHeight(), satellite.getPosition(), satellite.getTransferRange(), satellite.getConnectedIds());
        }
    }

    /**
     * Remove the Device, with the given device id, from the devices list
     * update the connections
     * @param deviceId
     */
    public void removeDevice(String deviceId) {
        devices.remove(SearchHelper.searchDevice(devices, deviceId));
        for (Satellite satellite : satellites) {
            satellite.updateConnection(devices, satellites);
        }
        for (Device device : devices) {
            if (device.getDevicedId().equals(deviceId)) continue;
            device.updateConnection(devices, satellites);
        }
    }

    /**
     * Create a satellite and add it to the satellite list
     * set the connections with devices and other satellites
     * @param satelliteId 
     * @param type of the satellite
     * @param height of the satellite
     * @param position (degrees)
     */
    public void createSatellite(String satelliteId, String type, double height, Angle position) {
        if (getInfo(satelliteId) != null) { // Id already exist
            return;
        }

        // creating satellite
        if (type.equals("StandardSatellite")) {
            satellites.add(new StandardSatellite(satelliteId, type, height, position));                
        } else if (type.equals("ShrinkingSatellite")) {
            satellites.add(new ShrinkingSatellite(satelliteId, type, height, position));
        } else if (type.equals("RelaySatellite")) {
            satellites.add(new RelaySatellite(satelliteId, type, height, position));
        }

        // setting connections
        Satellite satellite = SearchHelper.searchSatellite(satellites, satelliteId);
        satellite.updateConnection(devices, satellites);
        for (Device device : devices) {
            SearchHelper.tryToConnectSatellite(satellite, device.getPosition(), device.getTransferRange(), device.getConnectedIds());
        }
        for (Satellite s : satellites) {
            if (s.equals(satellite)) continue;
            SearchHelper.tryToConnectSatellite(satellite, s.getPosition(), s.getTransferRange(), s.getConnectedIds());
        }
    }

    /**
     * Remove satellite, with the given id, from the satellites list
     * update the connections
     * @param satelliteId we want to remove
     */
    public void removeSatellite(String satelliteId) {
        satellites.remove(SearchHelper.searchSatellite(satellites, satelliteId));
        for (Satellite satellite : satellites) {
            satellite.updateConnection(devices, satellites);
        }
        for (Device device : devices) {
            device.updateConnection(devices, satellites);
        }
    }

    /**
     * List out the devices in the list
     * @return a list of devices
     */
    public List<String> listDeviceIds() {
        List<String> deviceIdList = new ArrayList<>();
        for (Device device : devices) {
            deviceIdList.add(device.getDevicedId());
        }
        return deviceIdList;
    }

    /**
     * List out the satellites in the list
     * @return a list of satellites
     */
    public List<String> listSatelliteIds() {
        List<String> satelliteIdList = new ArrayList<>();
        for (Satellite satellite : satellites) {
            satelliteIdList.add(satellite.getSatelliteId());
        }
        return satelliteIdList;
    }

    /**
     * Create a file by a device
     * @param deviceId 
     * @param filename 
     * @param content of the file
     */
    public void addFileToDevice(String deviceId, String filename, String content) {
        for (Device device : devices) {
            if (device.getDevicedId().equals(deviceId)) {
                device.addFile(filename, content);
                SearchHelper.searchFile(device.getFiles(), filename).setHasTransferCompleted(true);
            }
        }
    }

    /**
     * Infomations about a device or satellite
     * @param id, can be a deviceId or SatelliteId
     * @return (EntityInfoResponse) about the given id's information
     */
    public EntityInfoResponse getInfo(String id) {
        try {// case 1: id is a device
            Device device = SearchHelper.searchDevice(devices,id);
            Map<String, FileInfoResponse> map = new HashMap<>();
            for (File file : device.getFiles()) {
                map.put(file.getFilename(), new FileInfoResponse(file.getFilename(),file.getContent(),file.getFileSize(),file.hasTransferCompleted()));
            }
            return new EntityInfoResponse(id, device.getPosition(), device.getHeight(), device.getType(), map);
        } catch (Exception e) {
        }

        try {// case 2: id is a satellite
            Satellite satellite = SearchHelper.searchSatellite(satellites, id);
            if (satellite instanceof RelaySatellite) {
                return new EntityInfoResponse(id, satellite.getPosition(), satellite.getHeight(), satellite.getType());
            } else {
                StorableSatellite s = SearchHelper.searchStorableSatellite(satellites, id);
                Map<String, FileInfoResponse> map = new HashMap<>();
                for (File file : s.getFiles()) {
                    map.put(file.getFilename(), new FileInfoResponse(file.getFilename(),file.getContent(),file.getFileSize(),file.hasTransferCompleted()));
                }
                return new EntityInfoResponse(id, satellite.getPosition(), satellite.getHeight(), satellite.getType(), map);
            }
        } catch (Exception e) {
        }

        return null;
    }

    /**
     * Simulation of every minute
     * We need to transfer the files that are been transferring
     * and update the satellite's position, and reset the connection between the satellites and device, 
     * and the connection between satellites and satellites
     */
    public void simulate() {
        // update transferring files
        for (Satellite satellite : satellites) {
            if (satellite instanceof StandardSatellite || satellite instanceof ShrinkingSatellite) {
                StorableSatellite s = SearchHelper.searchStorableSatellite(satellites, satellite.getSatelliteId());
                s.upadteTransferringFiles(communicableEntitiesInRange(satellite.getSatelliteId()), devices, satellites);
            }
        }
        for (Device device : devices) {
            device.updateRecevingFile(communicableEntitiesInRange(device.getDevicedId()), devices, satellites);
        }

        // update satellite's positions and reset connections
        for (Satellite satellite : satellites) {
            satellite.updatePosition();
            satellite.updateConnection(devices, satellites);
        }
        for (Device device : devices) {
            device.updateConnection(devices, satellites);
        }
    }

    /**
     * Simulate for the specified number of minutes.
     * You shouldn't need to modify this function.
     */
    public void simulate(int numberOfMinutes) {
        for (int i = 0; i < numberOfMinutes; i++) {
            simulate();
        }
    }

    /**
     * As relay Satellite can increase the communicate range, not only the connected ines,
     * so that this method is to find the communicable entities for the given id
     * @param id, can be a deviceId or satelliteId
     * @return a list of ids that can be communicate from the given id 
     */
    public List<String> communicableEntitiesInRange(String id) {
        List<String> communicableEntities = communicableEntitiesInRangeHelper(id);
        communicableEntities.remove(id);
        return communicableEntities;
    }

    /**
     * helper function for communicableEntitiesInRange
     * @param id
     * @return a list of ids that can be communicate from the given id 
     */
    public List<String> communicableEntitiesInRangeHelper(String id) {
        List<String> communicableEntities = new ArrayList<>();
        try {// case 1: id is a device
            Device device = SearchHelper.searchDevice(devices,id);
            for (String connectedid : device.getConnectedIds()) {
                if (SearchHelper.searchSatellite(satellites,connectedid) instanceof RelaySatellite) {
                    List<String> connectedByRelaySatellite = communicableEntitiesInRangeHelper(connectedid);

                    // avoid repetition
                    communicableEntities.removeAll(connectedByRelaySatellite);
                    communicableEntities.addAll(connectedByRelaySatellite);
                }
                communicableEntities.add(connectedid);
            }
            return communicableEntities;
        } catch (Exception e) {
        }

        try {// case 2: id is a satellite
            Satellite satellite = SearchHelper.searchSatellite(satellites,id);
            for (String connectedid : satellite.getConnectedIds()) {
                if (SearchHelper.searchSatellite(satellites,connectedid) instanceof RelaySatellite) {
                    List<String> connectedByRelaySatellite = communicableEntitiesInRangeHelper(connectedid);

                    // avoid repetition
                    communicableEntities.removeAll(connectedByRelaySatellite);
                    communicableEntities.addAll(connectedByRelaySatellite);
                }
                communicableEntities.add(connectedid);
            }
            return communicableEntities;
        } catch (Exception e) {
        }

        return communicableEntities;
    }
    
    /**
     * Send file from the "fromId" to "toId"
     * Throw exceptions if NonExistentFile, FileAlreadyExists, NoBandwidthException
     * and NoStorageSpaceException.
     * @param fileName
     * @param fromId can be device or satellite
     * @param toId can be device or satellite
     * @throws FileTransferException
     */
    public void sendFile(String fileName, String fromId, String toId) throws FileTransferException {
        if (SearchHelper.searchFile(devices, satellites, fileName, fromId) == null) {
            throw new FileTransferException.VirtualFileNotFoundException("NonExistentFile");
        }
        
        if (!SearchHelper.searchFile(devices, satellites, fileName, fromId).hasTransferCompleted()) {
            throw new FileTransferException.VirtualFileNotFoundException("NonExistentFile");
        }

        if (SearchHelper.searchFile(devices, satellites, fileName, toId) != null) {
            throw new FileTransferException.VirtualFileAlreadyExistsException("FileAlreadyExists");
        }
 
        // case 1: satellite send file to device
        StorableSatellite satellite = SearchHelper.searchStorableSatellite(satellites, fromId);
        Device device = SearchHelper.searchDevice(devices,toId);
        if (satellite != null && device != null) {
            if (!SearchHelper.isSupportsDevice(device, satellite)) {
                return;
            }
            if (SearchHelper.isCommunicatable(fromId, communicableEntitiesInRange(toId))) {
                if (satellite.getSendingBandwidth() < 1) {
                    throw new FileTransferException.VirtualFileNoBandwidthException("NoBandwidthException");
                }
                device.addReceivingFile(SearchHelper.searchFile(devices, satellites, fileName, fromId), fromId, toId);
                satellite.addSendingFile(SearchHelper.searchFile(devices, satellites, fileName, fromId), fromId, toId);
            }
            return;
        }

        // case 2: device send file to satellite
        device = SearchHelper.searchDevice(devices,fromId);
        satellite = SearchHelper.searchStorableSatellite(satellites, toId);
        if (satellite != null && device != null) {
            if (!SearchHelper.isSupportsDevice(device, satellite)) {
                return;
            }
            
            if (SearchHelper.isCommunicatable(fromId, communicableEntitiesInRange(toId))) {
                File file = SearchHelper.searchFile(devices, satellites, fileName, fromId);
                if (!satellite.haveEnoughStorage(file.getFileSize(), satellite.getFiles().size())) {
                    throw new FileTransferException.VirtualFileNoStorageSpaceException("NoStorageSpaceException");
                }
                if (satellite.getReceivingBandwidth() < 1) {
                    throw new FileTransferException.VirtualFileNoBandwidthException("NoBandwidthException");
                }
                satellite.addReceivingFile(file, fromId, toId);
            }
            return;
        }
        // case 3: satellite send file to satellite
        StorableSatellite fromSatellite = SearchHelper.searchStorableSatellite(satellites, fromId);
        StorableSatellite toSatellite = SearchHelper.searchStorableSatellite(satellites, toId);
        if (SearchHelper.isCommunicatable(fromId, communicableEntitiesInRange(toId))) {
            File file = SearchHelper.searchFile(devices, satellites, fileName, fromId);
            if (!toSatellite.haveEnoughStorage(file.getFileSize(), satellite.getFiles().size())) {
                throw new FileTransferException.VirtualFileNoStorageSpaceException("NoStorageSpaceException");
            }
            if (fromSatellite.getSendingBandwidth() < 1) {
                throw new FileTransferException.VirtualFileNoBandwidthException("NoBandwidthException");
            }
            if (toSatellite.getReceivingBandwidth() < 1) {
                throw new FileTransferException.VirtualFileNoBandwidthException("NoBandwidthException");
            }
            fromSatellite.addSendingFile(file, fromId, toId);
            toSatellite.addReceivingFile(file, fromId, toId);
        }
    }

    public static void main(String[] args) {
    }
}
