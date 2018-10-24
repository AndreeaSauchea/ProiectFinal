package proiectfinal;

public class Service {

    private String servicesName;
    private double servicePrice;
    private int serviceDuration;

    public Service(String servicesName, double servicePrice) {
        this.servicesName = servicesName;
        this.servicePrice = servicePrice;
    }

    public String getServicesName() {
        return servicesName;
    }

    public void setServicesName(String servicesName) {
        this.servicesName = servicesName;
    }

    public double getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(double servicePrice) {
        this.servicePrice = servicePrice;
    }

    public int getServiceDuration() {
        return serviceDuration;
    }

    public void setServiceDuration(int serviceDuration) {
        this.serviceDuration = serviceDuration;
    }
}
