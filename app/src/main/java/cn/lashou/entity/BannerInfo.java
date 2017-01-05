package cn.lashou.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by luow on 2016/8/20.
 */

public class BannerInfo implements Serializable{

    /**
     * banner : [{"imageUrl":"http://s1.lashouimg.com/wirelessimg/1481702074_zhuanti.jpg"},{"imageUrl":"http://s1.lashouimg.com/wirelessimg/1481267783_zhuanti.jpg"},{"imageUrl":"http://s1.lashouimg.com/wirelessimg/1480389217_zhuanti.jpg"}]
     * msg : success
     * other : [{"bigTitle":"电影进行中","imageUrl":"http://s1.lashouimg.com/wirelessimg/1480577746_zhuanti.png","smallTitle":"特惠多多"},{"bigTitle":"暖暖冬至","imageUrl":"http://s1.lashouimg.com/wirelessimg/1481078406_zhuanti.png","smallTitle":"我们在一起"},{"bigTitle":"边玩边赚钱","imageUrl":"http://s1.lashouimg.com/wirelessimg/1481875919_zhuanti.png","smallTitle":"麻将馆代理招募"},{"bigTitle":"难忘今宵","imageUrl":"http://s1.lashouimg.com/wirelessimg/1480388476_zhuanti.png","smallTitle":"享人生温馨"},{"bigTitle":"圣诞梦幻购","imageUrl":"http://s1.lashouimg.com/wirelessimg/1481266569_zhuanti.png","smallTitle":"享温暖享优惠"},{"bigTitle":"冬季养生","imageUrl":"http://s1.lashouimg.com/wirelessimg/1480386462_zhuanti.png","smallTitle":"活力再生"},{"bigTitle":"尽情体验","imageUrl":"http://s1.lashouimg.com/wirelessimg/1480386701_zhuanti.png","smallTitle":"速度与激情"}]
     */

    private List<BannerBean> banner;
    private List<OtherBean> other;

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public List<OtherBean> getOther() {
        return other;
    }

    public void setOther(List<OtherBean> other) {
        this.other = other;
    }

    public static class BannerBean{
        /**
         * imageUrl : http://s1.lashouimg.com/wirelessimg/1481702074_zhuanti.jpg
         */

        private String imageUrl;

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }

    public static class OtherBean {
        /**
         * bigTitle : 电影进行中
         * imageUrl : http://s1.lashouimg.com/wirelessimg/1480577746_zhuanti.png
         * smallTitle : 特惠多多
         */

        private String bigTitle;
        private String imageUrl;
        private String smallTitle;

        public String getBigTitle() {
            return bigTitle;
        }

        public void setBigTitle(String bigTitle) {
            this.bigTitle = bigTitle;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getSmallTitle() {
            return smallTitle;
        }

        public void setSmallTitle(String smallTitle) {
            this.smallTitle = smallTitle;
        }
    }
}
