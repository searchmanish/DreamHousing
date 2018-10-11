package com.softcodeinfotech.dreamhousing.webServices;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.softcodeinfotech.dreamhousing.BuildConfig;
import com.softcodeinfotech.dreamhousing.beanResponse.AddProperty;
import com.softcodeinfotech.dreamhousing.beanResponse.DeleteProperties;
import com.softcodeinfotech.dreamhousing.beanResponse.ForgotPassword;
import com.softcodeinfotech.dreamhousing.beanResponse.GetbannerModel;
import com.softcodeinfotech.dreamhousing.beanResponse.MyAccountItemsDetails;
import com.softcodeinfotech.dreamhousing.beanResponse.NewPassword;
import com.softcodeinfotech.dreamhousing.beanResponse.NewUserRegistration;
import com.softcodeinfotech.dreamhousing.beanResponse.PropertyDetails;
import com.softcodeinfotech.dreamhousing.beanResponse.PropertyDetailsHot;
import com.softcodeinfotech.dreamhousing.beanResponse.PropertyDetailsOwner;
import com.softcodeinfotech.dreamhousing.beanResponse.PropertySell;
import com.softcodeinfotech.dreamhousing.beanResponse.Registration;
import com.softcodeinfotech.dreamhousing.beanResponse.userSignin;
import com.softcodeinfotech.dreamhousing.utility.Constant;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServiceWrapper {

    private ServiceInterface mServiceInterface;

    public ServiceWrapper(Interceptor mInterceptorheader) {
        mServiceInterface = getRetrofit(mInterceptorheader).create(ServiceInterface.class);
    }

    public Retrofit getRetrofit(Interceptor mInterceptorheader) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient mOkHttpClient = null;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(Constant.API_CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(Constant.API_READ_TIMEOUT, TimeUnit.SECONDS);

//        if (BuildConfig.DEBUG)
//            builder.addInterceptor(loggingInterceptor);

        if (BuildConfig.DEBUG) {
//            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }


        mOkHttpClient = builder.build();
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(mOkHttpClient)
                .build();
        return retrofit;
    }

    public Call<NewUserRegistration> newUserRegistrationCall(String fullname, String email, String phone, String username, String password) {
        return mServiceInterface.NewUserRegistrationCall(convertPlainString(fullname), convertPlainString(email), convertPlainString(phone), convertPlainString(username),
                convertPlainString(password));

    }


    //  user signin
    public Call<userSignin> UserSigninCall(String phone, String password) {
        return mServiceInterface.UserSigninCall(convertPlainString(phone), convertPlainString(password));

    }

    //Social signin

    public Call<Registration> socialRegister(String fullname, String email, String uid, String type) {
        return mServiceInterface.register(convertPlainString(fullname),
                convertPlainString(email), convertPlainString(uid), convertPlainString(type));
    }

    //  forgot password
    public Call<ForgotPassword> UserForgotPassword(String phone) {
        return mServiceInterface.UserForgotPassword(convertPlainString(phone));
    }

    //  user new password
    public Call<NewPassword> UserNewPassword(String userid, String password) {
        return mServiceInterface.UserNewPassword(convertPlainString(userid), convertPlainString(password));
    }

    ///  new propertySell details
    public Call<PropertySell> getNewProductRes(String securcode) {
        return mServiceInterface.getNewPorduct(convertPlainString(securcode));
    }
  //propertyDetailsFresh
    //Updated as it works for product sell layout
  public Call<PropertyDetails> PorductDetailsFreshRes(String securcode) {
      return mServiceInterface.getPorductDetailsFresh(convertPlainString(securcode));
  }

    //propertyDetailsOwner
    //Updated as it works for product purchase layout
    public Call<PropertyDetailsOwner> PorductDetailsOwner(String securcode) {
        return mServiceInterface.getPorductDetailsOwner(convertPlainString(securcode));
    }

    //propertyDetailsHot
    ////Updated as it works for product rent layout
    public Call<PropertyDetailsHot> PorductDetailsHot(String securcode) {
        return mServiceInterface.getPorductDetailsHot(convertPlainString(securcode));
    }

  // Add new Property

    public  Call<AddProperty> addNewProperty(String mUser_id, String mMrp, String mAddress, String mPhone, String mProperty_type,
                                             String mDetails){
        return mServiceInterface.addNewProductDetails(convertPlainString(mUser_id),convertPlainString(mMrp),convertPlainString(mAddress),
                convertPlainString(mPhone),convertPlainString(mProperty_type),convertPlainString(mDetails)


        );
    }

    // get banner image
    public Call<GetbannerModel> getbannerModelCall(String securcode){
        return mServiceInterface.getbannerimagecall(convertPlainString(securcode) );
    }

    //get user AccountItemsDetails

    public  Call<MyAccountItemsDetails> getUserAccountItemsdetails(String securecode,String user_id)
    {
        return mServiceInterface.getAccountItems(convertPlainString(securecode),convertPlainString(user_id));
    }

    //delete PropertiesFrom user account

    public Call<DeleteProperties> deletePropertiesCall(String securecode,String property_id,String path)
    {
        return mServiceInterface.deleteUserAddedItems(convertPlainString(securecode),convertPlainString(property_id),
                convertPlainString(path));
    }



    // convert aa param into plain text
    public RequestBody convertPlainString(String data) {
        RequestBody plainString = RequestBody.create(MediaType.parse("text/plain"), data);
        return plainString;
    }
}


