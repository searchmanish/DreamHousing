package com.softcodeinfotech.dreamhousing.home;

public class PropertyDetailsModelFresh {


   /* property_id	int
    property_details	text
    property_mrp	float
    Property_address	text
    property_image	text
    property_phone	text
    property_listing_date	timestamp
    property_rating	float
    Property_type	text
    property_category	int
*/

   private String property_id,property_details,property_address,property_phone,property_image;
   private Double property_mrp;

    public PropertyDetailsModelFresh(String property_id,String property_details, String property_address, String property_phone, String property_image, Double property_mrp) {
        this.property_id = property_id;
        this.property_details = property_details;
        this.property_address = property_address;
        this.property_phone = property_phone;
        this.property_image = property_image;
        this.property_mrp = property_mrp;
    }

    public String getProperty_id() {
        return property_id;
    }

    public void setProperty_id(String property_id) {
        this.property_id = property_id;
    }

    public String getProperty_details() {
        return property_details;
    }

    public void setProperty_details(String property_details) {
        this.property_details = property_details;
    }

    public String getProperty_address() {
        return property_address;
    }

    public void setProperty_address(String property_address) {
        this.property_address = property_address;
    }

    public String getProperty_phone() {
        return property_phone;
    }

    public void setProperty_phone(String property_phone) {
        this.property_phone = property_phone;
    }

    public String getProperty_image() {
        return property_image;
    }

    public void setProperty_image(String property_image) {
        this.property_image = property_image;
    }

    public Double getProperty_mrp() {
        return property_mrp;
    }

    public void setProperty_mrp(Double property_mrp) {
        this.property_mrp = property_mrp;
    }
}
