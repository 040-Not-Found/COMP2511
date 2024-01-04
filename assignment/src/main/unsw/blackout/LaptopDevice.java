package unsw.blackout;

import unsw.utils.Angle;

public class LaptopDevice extends Device{
    private final int transferRange;
    /**
     * Set extra attributes to the satelllite
     * @param deviceId
     * @param type
     * @param position (degrees)
     */
    public LaptopDevice(String deviceId, String type, Angle position) {
        super(deviceId, type, position);
        this.transferRange = 100000;
    }

    @Override
    public final int getTransferRange() {
        return transferRange;
    }
    
}
