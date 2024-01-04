package unsw.blackout;

import java.util.ArrayList;
import java.util.List;
import unsw.utils.Angle;

public abstract class Satellite {
    private String satelliteId;
    public String type;
    private double height;
    private Angle position;
    private List<String> connectedIds;
    /**
     * Set extra attributes to the satelllite
     *  @param satelliteId 
     * @param type of the satellite
     * @param height (km)
     * @param position (degrees)
     */
    public Satellite(String satelliteId, String type, double height, Angle position) {
        this.satelliteId = satelliteId;
        this.type = type;
        this.height = height;
        this.position = position;
        this.connectedIds = new ArrayList<>();
    }

    /**
     * @return satellite's id
     */
    public String getSatelliteId() {
        return satelliteId;
    }

    /**
     * @return satellite's height (km)
     */
    public double getHeight() {
        return height;
    }

    /**
     * @return satellite's type
     */
    public String getType() {
        return type;
    }

    /**
     * @return satellite's position (degrees)
     */
    public Angle getPosition() {
        return position;
    }

    /**
     * @param satellite's new position (degrees)
     */
    public void setPosition(Angle position) {
        this.position = position;
    }

    /**
     * update the position of the satellite
     */
    public abstract void updatePosition();

    /**
     * @return linearVelocity of the satellite (km/min)
     */
    public abstract int getLinearVelocity();

    /**
     * @return transferRange of the satellite (km)
     */
    public abstract int getTransferRange();

    /**
     * setter for angularVelocity
     * @param angularVelocity of the satellite
     */
    public abstract void setAngularVelocity(double angularVelocity);

    /**
     * getter for angularVelocity
     * @return angularVelocity of the satellite (rad/min)
     */
    public abstract double getAngularVelocity();

    /**
     * update the connections between devices and other satellites
     * by removing the previous connects, and reset the connections
     * @param devices is a list of all devices
     * @param satellites is a list of all satellites
     */
    public void updateConnection(List<Device> devices, List<Satellite> satellites) {
        connectedIds.clear();
        for (Device device : devices) {
            if (!SearchHelper.isSupportsDevice(device, this)) {
                continue;
            }
            SearchHelper.tryToConnectDevice(device, getHeight(), getPosition(), getTransferRange(), connectedIds);
        }
        for (Satellite satellite : satellites) {
            if (satellite.equals(this)) {
                continue;
            }            
            SearchHelper.tryToConnectSatellite(satellite, getPosition(), getTransferRange(), getConnectedIds());
        }
    }
    
    /**
     * @return a list of strings contains the connected ids (can be both satellite and device ids)
     */
    public List<String> getConnectedIds() {
        return connectedIds;
    }

}
