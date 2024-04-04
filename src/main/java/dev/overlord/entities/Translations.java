package dev.overlord.entities;

public class Translations {
    private String bn;
    private String de;
    private String es;
    private String fr;
    private String hi;
    private String tl;

    public Translations() {
    }

    public Translations(String bn, String de, String es, String fr, String hi, String tl) {
        this.bn = bn;
        this.de = de;
        this.es = es;
        this.fr = fr;
        this.hi = hi;
        this.tl = tl;
    }

    public String getBn() {
        return bn;
    }

    public void setBn(String bn) {
        this.bn = bn;
    }

    public String getDe() {
        return de;
    }

    public void setDe(String de) {
        this.de = de;
    }

    public String getEs() {
        return es;
    }

    public void setEs(String es) {
        this.es = es;
    }

    public String getFr() {
        return fr;
    }

    public void setFr(String fr) {
        this.fr = fr;
    }

    public String getHi() {
        return hi;
    }

    public void setHi(String hi) {
        this.hi = hi;
    }

    public String getTl() {
        return tl;
    }

    public void setTl(String tl) {
        this.tl = tl;
    }

    @Override
    public String toString() {
        return "Translations{" +
                "bn='" + bn + '\'' +
                ", de='" + de + '\'' +
                ", es='" + es + '\'' +
                ", fr='" + fr + '\'' +
                ", hi='" + hi + '\'' +
                ", tl='" + tl + '\'' +
                '}';
    }
}
