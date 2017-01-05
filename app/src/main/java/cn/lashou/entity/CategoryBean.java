package cn.lashou.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by luow on 2016/8/29.
 */

public class CategoryBean implements Serializable{

    /**
     * data : [{"categoryName":"美食","categoryNount":"2120","symptomName":[{"itemName":"全部","symptomNount":2120},{"itemName":"自助餐","symptomNount":86},{"itemName":"火锅","symptomNount":108},{"itemName":"烧烤","symptomNount":45},{"itemName":"烤鱼","symptomNount":51},{"itemName":"干锅/香锅","symptomNount":26},{"itemName":"西餐","symptomNount":68},{"itemName":"海鲜","symptomNount":124},{"itemName":"北京菜","symptomNount":6},{"itemName":"川菜","symptomNount":110},{"itemName":"粤菜","symptomNount":7},{"itemName":"台湾菜","symptomNount":3},{"itemName":"福建菜","symptomNount":3},{"itemName":"湘菜","symptomNount":12}]},{"categoryName":"休闲娱乐","categoryNount":422,"symptomName":[{"itemName":"全部","symptomNount":422},{"itemName":"KTV","symptomNount":45},{"itemName":"养生休闲","symptomNount":192},{"itemName":"温泉洗浴","symptomNount":9},{"itemName":"运动健身","symptomNount":32},{"itemName":"桌游电玩","symptomNount":94},{"itemName":"演出赛事","symptomNount":1},{"itemName":"其他娱乐","symptomNount":17}]},{"categoryName":"生活服务","categoryNount":865,"symptomName":[{"itemName":"全部","symptomNount":865},{"itemName":"健康护理","symptomNount":13},{"itemName":"汽车服务","symptomNount":79},{"itemName":"母婴亲子","symptomNount":7},{"itemName":"洗条护理","symptomNount":32},{"itemName":"婚庆服务","symptomNount":94},{"itemName":"宠物护理","symptomNount":1},{"itemName":"缴费充值","symptomNount":3},{"itemName":"配镜","symptomNount":61},{"itemName":"照片冲洗","symptomNount":8},{"itemName":"其他生活","symptomNount":3}]},{"categoryName":"摄影写真","categoryNount":635,"symptomName":[{"itemName":"全部","symptomNount":635},{"itemName":"品质婚纱","symptomNount":13},{"itemName":"热卖婚纱","symptomNount":79},{"itemName":"孕妇写真","symptomNount":7},{"itemName":"成人写真","symptomNount":32},{"itemName":"全家福摄影","symptomNount":94},{"itemName":"儿童写真","symptomNount":1},{"itemName":"证件照","symptomNount":3},{"itemName":"照片冲印","symptomNount":61},{"itemName":"结婚婚庆","symptomNount":8},{"itemName":"其他","symptomNount":3}]},{"categoryName":"酒店","categoryNount":542,"symptomName":[{"itemName":"全部","symptomNount":542},{"itemName":"经济型酒店","symptomNount":352},{"itemName":"三星/舒适","symptomNount":79},{"itemName":"四星/高档","symptomNount":7},{"itemName":"五星/豪华","symptomNount":32},{"itemName":"特色酒店","symptomNount":18},{"itemName":"小时房","symptomNount":112}]},{"categoryName":"购物","categoryNount":631,"symptomName":[{"itemName":"全部","symptomNount":631},{"itemName":"服饰鞋包","symptomNount":108},{"itemName":"家居家纺","symptomNount":143},{"itemName":"运动户外","symptomNount":15},{"itemName":"食品茶酒","symptomNount":32},{"itemName":"数码家电","symptomNount":102},{"itemName":"母婴玩具","symptomNount":42},{"itemName":"美妆个护","symptomNount":53},{"itemName":"奢饰品","symptomNount":0}]}]
     * msg : 成功
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * categoryName : 美食
         * categoryNount : 2120
         * symptomName : [{"itemName":"全部","symptomNount":2120},{"itemName":"自助餐","symptomNount":86},{"itemName":"火锅","symptomNount":108},{"itemName":"烧烤","symptomNount":45},{"itemName":"烤鱼","symptomNount":51},{"itemName":"干锅/香锅","symptomNount":26},{"itemName":"西餐","symptomNount":68},{"itemName":"海鲜","symptomNount":124},{"itemName":"北京菜","symptomNount":6},{"itemName":"川菜","symptomNount":110},{"itemName":"粤菜","symptomNount":7},{"itemName":"台湾菜","symptomNount":3},{"itemName":"福建菜","symptomNount":3},{"itemName":"湘菜","symptomNount":12}]
         */

        private String categoryName;
        private String categoryNount;
        private List<SymptomNameBean> symptomName;

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCategoryNount() {
            return categoryNount;
        }

        public void setCategoryNount(String categoryNount) {
            this.categoryNount = categoryNount;
        }

        public List<SymptomNameBean> getSymptomName() {
            return symptomName;
        }

        public void setSymptomName(List<SymptomNameBean> symptomName) {
            this.symptomName = symptomName;
        }

        public static class SymptomNameBean implements Serializable{
            /**
             * itemName : 全部
             * symptomNount : 2120
             */

            private String itemName;
            private int symptomNount;

            public String getItemName() {
                return itemName;
            }

            public void setItemName(String itemName) {
                this.itemName = itemName;
            }

            public int getSymptomNount() {
                return symptomNount;
            }

            public void setSymptomNount(int symptomNount) {
                this.symptomNount = symptomNount;
            }
        }
    }
}
