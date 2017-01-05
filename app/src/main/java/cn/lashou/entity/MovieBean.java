package cn.lashou.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by luow on 2016/8/20.
 */

public class MovieBean implements Serializable {

    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable{

        private String filmName;
        private String grade;
        private String imageUrl;
        private String short_brief;

        public String getFilmName() {
            return filmName;
        }

        public void setFilmName(String filmName) {
            this.filmName = filmName;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getShort_brief() {
            return short_brief;
        }

        public void setShort_brief(String short_brief) {
            this.short_brief = short_brief;
        }
    }
}
