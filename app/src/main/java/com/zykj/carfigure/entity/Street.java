package com.zykj.carfigure.entity;

import java.io.Serializable;
import java.util.List;

//街道
public class Street implements Serializable {

    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":1,"name":"民族广场","countryId":"中国","stateId":"广西壮族自治区","city":"南宁","district":"青秀区","street":"麻村园湖路东一里","price":5,"parkImg":"images\\park\\k\\park1.jpg","longitude":108.342768,"latitude":22.819409,"emptypark":0,"parknumber":0,"scopen":0,"parkinterval":1.0260671765698925},{"id":8,"name":"星光大道","countryId":"中国","stateId":"广西壮族自治区","city":"南宁","district":"江南区","street":"236号","price":5,"parkImg":"images\\park\\k\\park1.jpg","longitude":108.363763,"latitude":22.819409,"emptypark":0,"parknumber":0,"scopen":0,"parkinterval":1.1281608605953617}]
     * code : 200
     */

    private int status;
    private String message;
    private int code;
    private List<StreetDetail> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<StreetDetail> getData() {
        return data;
    }

    public void setData(List<StreetDetail> data) {
        this.data = data;
    }

    public static class StreetDetail {
        /**
         * id : 1
         * name : 民族广场
         * countryId : 中国
         * stateId : 广西壮族自治区
         * city : 南宁
         * district : 青秀区
         * street : 麻村园湖路东一里
         * price : 5
         * parkImg : images\park\k\park1.jpg
         * longitude : 108.342768
         * latitude : 22.819409
         * emptypark : 0
         * parknumber : 0
         * scopen : 0
         * parkinterval : 1.0260671765698925
         */
        /**街道编号**/
        private int id;
        /**路段地址**/
        private String addressName;
        /**国家地址**/
        private String countryId;
        /**省地址**/
        private String stateId;
        /**城市**/
        private String city;
        /**区域**/
        private String district;
        /**街道地址**/
        private String streetName;
        /**价格（按小时)**/
        private int price;
        /**停车场图片**/
        private String parkImg;
        /**经度 **/
        private double longitude;
        /**纬度**/
        private double  latitude;
        /**空车位**/
        private  int emptyPark;
        /*车位总数*/
        private  int totalParkNumber;
        /**记录距离**/
        private double  parkinterval;
        /**车位类型**/
        private int type;
        /**街道的长度*/
        private double streetLength;
        private int list_id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAddressName() {
            return addressName;
        }

        public void setAddressName(String addressName) {
            this.addressName = addressName;
        }

        public String getCountryId() {
            return countryId;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }

        public String getStateId() {
            return stateId;
        }

        public void setStateId(String stateId) {
            this.stateId = stateId;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getStreetName() {
            return streetName;
        }

        public void setStreetName(String streetName) {
            this.streetName = streetName;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getParkImg() {
            return parkImg;
        }

        public void setParkImg(String parkImg) {
            this.parkImg = parkImg;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public int getEmptyPark() {
            return emptyPark;
        }

        public void setEmptyPark(int emptyPark) {
            this.emptyPark = emptyPark;
        }

        public int getTotalParkNumber() {
            return totalParkNumber;
        }

        public void setTotalParkNumber(int totalParkNumber) {
            this.totalParkNumber = totalParkNumber;
        }

        public double getParkinterval() {
            return parkinterval;
        }

        public void setParkinterval(double parkinterval) {
            this.parkinterval = parkinterval;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public double getStreetLength() {
            return streetLength;
        }

        public void setStreetLength(double streetLength) {
            this.streetLength = streetLength;
        }

        public int getList_id() {
            return list_id;
        }

        public void setList_id(int list_id) {
            this.list_id = list_id;
        }
    }
}
