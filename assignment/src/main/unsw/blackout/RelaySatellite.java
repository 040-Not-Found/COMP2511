package unsw.blackout;

import unsw.utils.Angle;

public class RelaySatellite extends Satellite {

    private final int linearVelocity;
    private double angularVelocity;
    private final int transferRange;
    /**
     * Set extra attributes to the satelllite
     *  @param satelliteId 
     * @param type of the satellite
     * @param height (km)
     * @param position (degrees)
     */
    public RelaySatellite(String satelliteId, String type, double height, Angle position) {
        super(satelliteId, type, height, position);
        this.linearVelocity = 1500;
        this.transferRange = 300000;
        this.angularVelocity = linearVelocity/height;
    }

    /**
     * @return linear velocity (km/min)
     */
    public final int getLinearVelocity() {
        return linearVelocity;
    }

    @Override
    public void updatePosition() {
        if ((this.getPosition().toDegrees() > 190 && this.getPosition().toDegrees() < 345 && this.getAngularVelocity() > 0)||
            (this.getPosition().toDegrees() < 140 && this.getPosition().toDegrees() > 345 && this.getAngularVelocity() < 0)){
            this.setAngularVelocity(-1*this.getAngularVelocity());
        }

        double newPosition = (this.getAngularVelocity()+this.getPosition().toRadians())%(2*Math.PI);
        this.setPosition(Angle.fromRadians(newPosition));
    }

    @Override
    public final int getTransferRange() {
        return transferRange;
    }

    @Override
    public void setAngularVelocity(double angularVelocity) {
        this.angularVelocity = angularVelocity;
    }
    
    @Override
    public double getAngularVelocity() {
       return angularVelocity;
    }

}
