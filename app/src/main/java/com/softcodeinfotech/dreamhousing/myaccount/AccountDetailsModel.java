package com.softcodeinfotech.dreamhousing.myaccount;

public class AccountDetailsModel {

  /*  @SerializedName("id")
    @Expose
    private Integer propertyId;
    @SerializedName("details")
    @Expose
    private String propertyDetails;
    @SerializedName("mrp")
    @Expose
    private Integer propertyMrp;
    @SerializedName("Property_address")
    @Expose
    private String propertyAddress;
    @SerializedName("phone")
    @Expose
    private String propertyPhone;
    @SerializedName("image")
    @Expose
    private String propertyImage;
    @SerializedName("listing_date")
    @Expose
    private String propertyListingDate;
    @SerializedName("rating")
    @Expose
    private Integer propertyRating;
    @SerializedName("Property_type")
    @Expose
    private String propertyType;
    @SerializedName("category")
    @Expose
    private Integer propertyCategory;*/



    private String acc_details,acc_address,acc_phone,acc_image;
    private Double acc_mrp;
    private Integer acc_prop_id;

    public AccountDetailsModel(String acc_details, String acc_address, String acc_phone,
                               String acc_image, Double acc_mrp, Integer acc_prop_id) {
        this.acc_details = acc_details;
        this.acc_address = acc_address;
        this.acc_phone = acc_phone;
        this.acc_image = acc_image;
        this.acc_mrp = acc_mrp;
        this.acc_prop_id = acc_prop_id;
    }

    public String getAcc_details() {
        return acc_details;
    }

    public void setAcc_details(String acc_details) {
        this.acc_details = acc_details;
    }

    public String getAcc_address() {
        return acc_address;
    }

    public void setAcc_address(String acc_address) {
        this.acc_address = acc_address;
    }

    public String getAcc_phone() {
        return acc_phone;
    }

    public void setAcc_phone(String acc_phone) {
        this.acc_phone = acc_phone;
    }

    public String getAcc_image() {
        return acc_image;
    }

    public void setAcc_image(String acc_image) {
        this.acc_image = acc_image;
    }

    public Double getAcc_mrp() {
        return acc_mrp;
    }

    public void setAcc_mrp(Double acc_mrp) {
        this.acc_mrp = acc_mrp;
    }

    public Integer getAcc_id() {
        return acc_prop_id;
    }

    public void setAcc_id(Integer acc_id) {
        this.acc_prop_id = acc_prop_id;
    }
}
