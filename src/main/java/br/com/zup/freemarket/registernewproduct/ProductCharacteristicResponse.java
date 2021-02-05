package br.com.zup.freemarket.registernewproduct;


public class ProductCharacteristicResponse {

    private String name;
    private String description;

    public ProductCharacteristicResponse(ProductCharacteristic characteristic) {
        this.name = characteristic.getName();
        this.description = characteristic.getDescription();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
