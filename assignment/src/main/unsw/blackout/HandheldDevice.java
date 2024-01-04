package unsw.blackout;

import unsw.utils.Angle;

public class HandheldDevice extends Device{
    private final int transferRange;
    /**
     * Set extra attributes to the satelllite
     * @param deviceId
     * @param type
     * @param position (degrees)
     */
    public HandheldDevice(String deviceId, String type, Angle position) {
        super(deviceId, type, position);
        this.transferRange = 50000;
    }

    @Override
    public final int getTransferRange() {
        return this.transferRange;
    }
    
}
