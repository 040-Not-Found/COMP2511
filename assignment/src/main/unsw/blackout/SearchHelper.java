package unsw.blackout;

import java.util.List;

import unsw.utils.Angle;
import unsw.utils.MathsHelper;

public final class SearchHelper {

    /**
     * search for the device using the given id
     * @param devices is a list of devices
     * @param id of the device we wants to search
     * @return Device with the match device id
     */
    public static Device searchDevice(List<Device> devices, String id) {
        for (Device device : devices) {
            if (device.getDevicedId().equals(id)) {
                return device;
            }
        }
        return null;
    }

    /**
     * search for the satetllite using the given id
     * @param satellites is a list of satellites
     * @param id of the satellite we wants to search
     * @return Satellite with the match satellite id
     */
    public static Satellite searchSatellite(List<Satellite> satellites, String id) {
        for (Satellite satellite : satellites) {
            if (satellite.getSatelliteId().equals(id)) {
                return satellite;
            }
        }
        return null;
    }

    /**
     * search for the storable satetllite using the given id
     * @param satellites is a list of satellites
     * @param id of the satellite we wants to search
     * @return StorableSatellite with the match satellite id
     */
    public static StorableSatellite searchStorableSatellite(List<Satellite> satellites, String id) {
        for (Satellite satellite : satellites) {
            if (satellite.getSatelliteId().equals(id)) {
                return (StorableSatellite) satellite;
            }
        }
        return null;
    }

    /**
     * search the file in the file list of the fromId without knowings the 
     * from Id
     * @param devices a list of device
     * @param satellites a list of satellites
     * @param fileName
     * @param fromId
     * @return File with the corresponding filename
     */
    public static File searchFile(List<Device> devices, List<Satellite> satellites, String fileName, String fromId) {
        // case 1: if the file stores by a device
        try {
            Device device = searchDevice(devices, fromId);
            for (File file : device.getFiles()) {
                if (file.getFilename().equals(fileName)) {
                    return file;
                }
            }
        } catch (Exception e) {
        }

        // case 2: if the file stores by a satorable satellite
        try {
            StorableSatellite satellite = searchStorableSatellite(satellites, fromId);
            for (File file : satellite.getFiles()) {
                if (file.getFilename().equals(fileName)) {
                    return file;
                }
            }
        } catch (Exception e) {
        }
        
        return null;
    }

    /**
     * search file in the file list of the fromId
     * @param files is a list of files
     * @param fileName
     * @return File with the corresponing filename
     */
    public static File searchFile(List<File> files, String fileName) {
        for (File file : files) {
            if (file.getFilename().equals(fileName)) {
                return file;
            }
        }
        return null;
    }

    /**
     * check if entity1(id1) and entity2(id2) can communicate to each other
     * @param id1 
     * @param id2CommunicateIds is a list of entity that id2 can communicate with
     * @return true if entity1 cna communicate to entity2
     */
    public static boolean isCommunicatable(String id1, List<String> id2CommunicateIds) {
        for (String connectedId : id2CommunicateIds) {
            if (connectedId.equals(id1)) {
                return true;
            }
        }
        return false;
    }

    /**
     * As StandardSatellite only supports Handheld and Laptop devices
     * check if the satellite supports the given device
     * @param device
     * @return true if the satellite supports the given device
     */
    public static boolean isSupportsDevice(Device device, Satellite satellite) {
        if (satellite instanceof Satellite && !(device instanceof HandheldDevice || device instanceof LaptopDevice)) {
            return false;
        }
        return true;
    }

    /**
     * find the maximum transferring range
     * @param transferRange1 of one entity
     * @param transferRange2 of another entity
     * @return the smaller range
     */
    public static int maxRange(int transferRange1, int transferRange2) {
        return Math.min(transferRange1, transferRange2);
    }

    /**
     * check if the entity can connect to the other satellite
     * if so, add it to the connectedIds' list
     * @param satellite that is check if is connectable
     */
    public static void tryToConnectSatellite(Satellite satellite, Angle position, int transferRange, List<String> connectedIds) {
        if (MathsHelper.isVisible(satellite.getHeight(), satellite.getPosition(), position) && 
           (MathsHelper.getDistance(satellite.getHeight(), satellite.getPosition(), position) <= maxRange(satellite.getTransferRange(), transferRange))) {
            connectedIds.add(satellite.getSatelliteId());
        }
    }

    /**
     * check if satellite can connect to the device
     * if so, add it to the connectedIds' list
     * @param device that is check if is connectable
     */
    public static void tryToConnectDevice(Device device, double height, Angle position, int transferRange, List<String> connectedIds) {
        if (MathsHelper.isVisible(height, position, device.getPosition()) && 
           (MathsHelper.getDistance(height, position, device.getPosition()) < maxRange(device.getTransferRange(), transferRange))) {
            connectedIds.add(device.getDevicedId());
        }
    }
}
