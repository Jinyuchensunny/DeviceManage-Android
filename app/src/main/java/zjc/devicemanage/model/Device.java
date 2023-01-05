package zjc.devicemanage.model;

public class Device {
    private String DeviceID;
    private String DeviceClassID;
    private String DeviceName;
    private String DevicePrice;

    public String getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(String deviceID) {
        DeviceID = deviceID;
    }

    public String getDeviceClassID() {
        return DeviceClassID;
    }

    public void setDeviceClassID(String deviceClassID) {
        DeviceClassID = deviceClassID;
    }

    public String getDeviceName() {
        return DeviceName;
    }

    public void setDeviceName(String deviceName) {
        DeviceName = deviceName;
    }

    public String getDevicePrice() {
        return DevicePrice;
    }

    public void setDevicePrice(String devicePrice) {
        DevicePrice = devicePrice;
    }
}
