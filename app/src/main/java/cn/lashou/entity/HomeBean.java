package cn.lashou.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by luow on 2016/8/20.
 */

public class HomeBean implements Serializable {

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable{
        private List<GoodlistBean> goodlist;

        public List<GoodlistBean> getGoodlist() {
            return goodlist;
        }

        public void setGoodlist(List<GoodlistBean> goodlist) {
            this.goodlist = goodlist;
        }

        public static class GoodlistBean implements Serializable{

            private String price;
            private String product;
            private String short_title;
            private String title;
            private String value;
            private int bought;
            private String distance;
            private String goods_id;
            private String goods_type;
            private List<ImagesBean> images;

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getProduct() {
                return product;
            }

            public void setProduct(String product) {
                this.product = product;
            }

            public String getShort_title() {
                return short_title;
            }

            public void setShort_title(String short_title) {
                this.short_title = short_title;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public int getBought() {
                return bought;
            }

            public void setBought(int bought) {
                this.bought = bought;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_type() {
                return goods_type;
            }

            public void setGoods_type(String goods_type) {
                this.goods_type = goods_type;
            }

            public List<ImagesBean> getImages() {
                return images;
            }

            public void setImages(List<ImagesBean> images) {
                this.images = images;
            }

            public static class ImagesBean {

                private String image;
                private int width;

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }
            }
        }
    }
}
