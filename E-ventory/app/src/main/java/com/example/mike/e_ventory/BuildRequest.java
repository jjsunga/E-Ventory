package com.example.mike.e_ventory;

import okhttp3.HttpUrl;


public class BuildRequest {

    private String request;

    public String getRequest(){
        return request;
    }

    //platform - the platform the request is coming from
    //apiKey - key needed to manipulate the chosen platform
    //shopName - name of the shop the edits will work on
    //username - user of the shop under specified platform
    //password - password of the shop under specified platform
    //updateGetOrAdd - determines how to build the url in case of updating, adding, or getting data from shop
    //listingId - determines the listing to perform update on

    public BuildRequest(){
        request = "";
    }

    public void setRequest(String platform, String shopName, String updateGetOrAdd, String listingId) {

        if(platform == "etsy") {
            if(updateGetOrAdd == "get") {
                //https://openapi.etsy.com/v2/shops/PaperPlusCloth/listings/active.json?api_key=al4lqgpsr6yu691hfvvx2tl8&limit=1&fields=title,price
                //https://openapi.etsy.com/v2/shops/PaperPlusCloth/listings/active.json?api_key=al4lqgpsr6yu691hfvvx2tl8&limit=1&fields=title,price,currency_code,url,quantity,description
                HttpUrl.Builder urlBuilder = HttpUrl.parse("https://openapi.etsy.com/v2/shops/"+shopName+"/listings/active.json").newBuilder();
                //HttpUrl.Builder urlBuilder = HttpUrl.parse("https://openapi.etsy.com/v2/shops/PaperPlusCloth/listings/active.json").newBuilder();
                urlBuilder.addQueryParameter("api_key", "al4lqgpsr6yu691hfvvx2tl8");
                urlBuilder.addQueryParameter("limit", "9999");
                urlBuilder.addQueryParameter("fields", "title,price,currency_code,url,quantity,description,listing_id,state");
                request = urlBuilder.build().toString();
            }else if(updateGetOrAdd == "update"){

            }else if(updateGetOrAdd == "add"){

            }
        }else if(platform == "ebay"){
            if(updateGetOrAdd == "get") {

                String ebay_app_id = "JadrianS-Eventory-PRD-745f30466-998f0b09";

                request ="http://svcs.ebay.com/services/search/FindingService/v1?"+
                "OPERATION-NAME=findItemsIneBayStores&"+
                        "SERVICE-VERSION=1.0.0&"+
                        "SECURITY-APPNAME="+ebay_app_id+"&"+
                        "RESPONSE-DATA-FORMAT=JSON&"+
                        "callback=_cb_findItemsIneBayStores&"+
                        "REST-PAYLOAD&"+
                        "storeName="+shopName+"&"+
                        "outputSelector=UnitPriceInfo";

            }else if(updateGetOrAdd == "update"){

            }else if(updateGetOrAdd == "add"){

            }
        }
    }



}
