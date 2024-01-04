# COMP2511 ASSIGNMENT Design

By: Celine Lin z5311209

## Device

As there are three types of devices, so these types of devices will be extends the abstract class device. Each device has there different range, so that the subclasses has the attribute: **transferRange**, and an abstract method that gets the **transferRange**.

## Satellite

As there are three types of satellites, and two of them can store files, therefore, there are two subclasses extends the abstract class satellite: one is the RelaySatellite that cannot store files, the other is an abstract class StorableSatellite, which have subclasses StandardSatellite and ShrinkingSatellite. The StorableClass can help to decrease repetitions that follows the DRY principle, as both StandardSatellite and ShrinkingSatellite can transfer and store files.

## File

The File is created by the Device, therefore, it has compossion relationship with Device. As part of the Satellite can transfer and store the files, so that they hava a aggregation relationship. 

There's a abstract class TransferringFile extends the class File, and two classses: SendfingFile and ReceivingFile, extends the TransferringFile. The TransferringFile helps to avoid DRY, and the SendfingFile and ReceivingFile helps to send/receive files. This is by the attribute of sending/receiving status that helps to transfer the correct conent and tell if the file has transfer completed.



## BlackController

This class can create Device and Satellite, so that this have compossion relationship with Device and Satellite.

