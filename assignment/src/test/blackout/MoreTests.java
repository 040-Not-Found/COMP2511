package blackout;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import unsw.blackout.BlackoutController;
import unsw.blackout.FileTransferException;
import unsw.utils.Angle;

import static blackout.TestHelpers.assertListAreEqualIgnoringOrder;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static unsw.utils.MathsHelper.RADIUS_OF_JUPITER;

import java.util.Arrays;


@TestInstance(value = Lifecycle.PER_CLASS)
public class MoreTests {

    @Test
    public void testOutOfRange() {
        BlackoutController controller = new BlackoutController();

        // create a satellite and a device
        controller.createSatellite("Satellite1", "StandardSatellite", 10000 + RADIUS_OF_JUPITER, Angle.fromDegrees(171));
        controller.createDevice("DeviceB", "LaptopDevice", Angle.fromDegrees(140));

        // create and send message
        String msg = "This is for testing OutOfRange";
        controller.addFileToDevice("DeviceB", "FileAlpha", msg);
        assertDoesNotThrow(() -> controller.sendFile("FileAlpha", "DeviceB", "Satellite1"));
        controller.simulate(5);
        
        // sending message incomplete by out of range
        assertEquals(null, controller.getInfo("Satellite1").getFiles().get("FileAlpha"));
    }

    @Test
    public void testOutOfStorage() {
        BlackoutController controller = new BlackoutController();

        // create a satellite and a device
        controller.createSatellite("Satellite1", "StandardSatellite", 20000 + RADIUS_OF_JUPITER, Angle.fromDegrees(135));
        controller.createDevice("DeviceB", "HandheldDevice", Angle.fromDegrees(140));

        // create 4 files
        String msg = "1";
        controller.addFileToDevice("DeviceB", "FileAlpha1", msg);
        controller.addFileToDevice("DeviceB", "FileAlpha2", msg);
        controller.addFileToDevice("DeviceB", "FileAlpha3", msg);
        controller.addFileToDevice("DeviceB", "FileAlpha4", msg);

        // send 3 files
        assertDoesNotThrow(() -> controller.sendFile("FileAlpha1", "DeviceB", "Satellite1"));
        controller.simulate();
        assertDoesNotThrow(() -> controller.sendFile("FileAlpha2", "DeviceB", "Satellite1"));
        controller.simulate();
        assertDoesNotThrow(() -> controller.sendFile("FileAlpha3", "DeviceB", "Satellite1"));
        controller.simulate();

        // standard satellite can not held the 4th file as it is out of storage
        assertThrows(FileTransferException.VirtualFileNoStorageSpaceException.class, () -> controller.sendFile("FileAlpha4", "DeviceB", "Satellite1"));
    }

    @Test
    public void testOutOfBandwidth() {
        BlackoutController controller = new BlackoutController();

        // create a satellite and a device
        controller.createSatellite("Satellite1", "StandardSatellite", 20000 + RADIUS_OF_JUPITER, Angle.fromDegrees(140));
        controller.createDevice("DeviceB", "LaptopDevice", Angle.fromDegrees(140));

        // create and send 2files
        String msg = "file";
        controller.addFileToDevice("DeviceB", "FileAlpha1", msg);
        controller.addFileToDevice("DeviceB", "FileAlpha2", msg);
        assertDoesNotThrow(() -> controller.sendFile("FileAlpha1", "DeviceB", "Satellite1"));

        // the second file cannot be send as there's no bandwidth
        assertThrows(FileTransferException.VirtualFileNoBandwidthException.class, () -> controller.sendFile("FileAlpha2", "DeviceB", "Satellite1"));
    }

    @Test
    public void testSendImcompleteFile() {
        BlackoutController controller = new BlackoutController();

        // create 2 satellites and a device
        controller.createSatellite("Satellite1", "StandardSatellite", 20000 + RADIUS_OF_JUPITER, Angle.fromDegrees(140));
        controller.createSatellite("Satellite2", "StandardSatellite", 10000 + RADIUS_OF_JUPITER, Angle.fromDegrees(140));
        controller.createDevice("DeviceB", "LaptopDevice", Angle.fromDegrees(140));
        
        // create and send a file
        String msg = "file";
        controller.addFileToDevice("DeviceB", "FileAlpha", msg);
        assertDoesNotThrow(() -> controller.sendFile("FileAlpha", "DeviceB", "Satellite1"));
        controller.simulate();
        
        assertThrows(FileTransferException.VirtualFileNotFoundException.class, () -> controller.sendFile("FileAlpha2", "Satellite1", "Satellite2"));
    }

    @Test
    public void testCommunicationThroughRelaySatellite() {
        BlackoutController controller = new BlackoutController();

        // create a satellites and a device
        controller.createDevice("Device", "HandheldDevice", Angle.fromDegrees(70));
        controller.createSatellite("Satellite", "StandardSatellite", 13505 + RADIUS_OF_JUPITER, Angle.fromDegrees(19));
        
        // no connection between device and satellite
        assertEquals(Arrays.asList(), controller.communicableEntitiesInRange("Device"));

        // create a relay satellite
        controller.createSatellite("Relay", "RelaySatellite", 16863 + RADIUS_OF_JUPITER, Angle.fromDegrees(51));
        assertListAreEqualIgnoringOrder(Arrays.asList("Relay", "Satellite"), controller.communicableEntitiesInRange("Device"));
    }

    @Test
    public void testCommunicateEntitiesWithoutThemselves() {
        BlackoutController controller = new BlackoutController();

        // create a satellite and a device
        controller.createDevice("Device", "HandheldDevice", Angle.fromDegrees(70));
        controller.createSatellite("Satellite", "StandardSatellite", 13505 + RADIUS_OF_JUPITER, Angle.fromDegrees(70));
        
        assertListAreEqualIgnoringOrder(Arrays.asList("Satellite"), controller.communicableEntitiesInRange("Device"));
    }

    @Test
    public void testCommunicateEntitiesRemoveRelay() {
        BlackoutController controller = new BlackoutController();

        // create 2 satellites and a device
        // standard satellite communicate to device through the relay satellite
        controller.createDevice("Device", "HandheldDevice", Angle.fromDegrees(70));
        controller.createSatellite("Satellite", "StandardSatellite", 13505 + RADIUS_OF_JUPITER, Angle.fromDegrees(19));
        controller.createSatellite("Relay", "RelaySatellite", 16863 + RADIUS_OF_JUPITER, Angle.fromDegrees(51));
        assertListAreEqualIgnoringOrder(Arrays.asList("Relay", "Satellite"), controller.communicableEntitiesInRange("Device"));
        
        // remove RelaySatellite
        controller.removeSatellite("Relay");
        assertListAreEqualIgnoringOrder(Arrays.asList(), controller.communicableEntitiesInRange("Device"));
    
    }
}
