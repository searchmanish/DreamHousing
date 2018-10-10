package com.softcodeinfotech.dreamhousing.myaccount;

public class AccountDetailsModel {

  /*  @SerializedName("property_id")
    @Expose
    private Integer propertyId;
    @SerializedName("property_details")
    @Expose
    private String propertyDetails;
    @SerializedName("property_mrp")
    @Expose
    private Integer propertyMrp;
    @SerializedName("Property_address")
    @Expose
    private String propertyAddress;
    @SerializedName("property_phone")
    @Expose
    private String propertyPhone;
    @SerializedName("property_image")
    @Expose
    private String propertyImage;
    @SerializedName("property_listing_date")
    @Expose
    private String propertyListingDate;
    @SerializedName("property_rating")
    @Expose
    private Integer propertyRating;
    @SerializedName("Property_type")
    @Expose
    private String propertyType;
    @SerializedName("property_category")
    @Expose
    private Integer propertyCategory;*/



    private String property_details,property_address,property_phone,property_image;
    private Double property_mrp;
    private Integer property_id;

    public AccountDetailsModel(Integer property_id,String property_details, String property_address,
                               String property_phone, String property_image, Double property_mrp) {
        this.property_id = property_id;
        this.property_details = property_details;
        this.property_address = property_address;
        this.property_phone = property_phone;
        this.property_image = property_image;
        this.property_mrp = property_mrp;
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

    public Integer getProperty_id() {
        return property_id;
    }

    public void setProperty_id(Integer property_id) {
        this.property_id = property_id;
    }



}
