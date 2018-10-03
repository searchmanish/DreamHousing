package com.softcodeinfotech.dreamhousing.home;

//price
//	specification
//	address
//	phone
//	image_url
public class PropertySell_Model {

    private String price,specification,address,phone,image_url;

    public PropertySell_Model(String price, String specification, String address, String phone, String image_url) {
        this.price = price;
        this.specification = specification;
        this.address = address;
        this.phone = phone;
        this.image_url = image_url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
