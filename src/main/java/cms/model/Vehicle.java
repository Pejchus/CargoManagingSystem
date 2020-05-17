package cms.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue("VEHICLE")
public class Vehicle {

  private long shipmentid;
  private boolean availability;
  @Id
  private String licenseplate;
  private String driver;

  public String getDriver() {
    return driver;
  }

  public void setDriver(String driver) {
    this.driver = driver;
  }

  public boolean isAvailability() {
    return availability;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  private int id;




  public long getShipmentid() {
    return shipmentid;
  }

  public void setShipmentid(long shipmentid) {
    this.shipmentid = shipmentid;
  }


  public boolean getAvailability() {
    return availability;
  }

  public void setAvailability(boolean availability) {
    this.availability = availability;
  }


  public String getLicenseplate() {
    return licenseplate;
  }

  public void setLicenseplate(String licenseplate) {
    this.licenseplate = licenseplate;
  }

}
