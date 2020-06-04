package cn.edu.scujcc.music;

import java.io.Serializable;
import java.util.Objects;

public class Music implements Serializable {
    private String songtitle;
    private String qingxidu;
    private String image;
    private String url;
    private String id;
    private String singer;
    private String star;

    @Override
    public String toString() {
        return "Music{" +
                "songtitle='" + songtitle + '\'' +
                ", qingxidu='" + qingxidu + '\'' +
                ", image='" + image + '\'' +
                ", url='" + url + '\'' +
                ", id='" + id + '\'' +
                ", singer='" + singer + '\'' +
                ", star='" + star + '\'' +
                '}';
    }

    public String getSongtitle() {
        return songtitle;
    }

    public String getQingxidu() {
        return qingxidu;
    }

    public String getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }

    public String getSinger() {
        return singer;
    }

    public String getStar() {
        return star;
    }

    public void setSongtitle(String songtitle) {
        this.songtitle = songtitle;
    }

    public void setQingxidu(String qingxidu) {
        this.qingxidu = qingxidu;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public void setStar(String star) {
        this.star = star;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Music music = (Music) o;
        return Objects.equals(songtitle, music.songtitle) &&
                Objects.equals(qingxidu, music.qingxidu) &&
                Objects.equals(singer, music.singer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(songtitle, qingxidu, singer);
    }
}