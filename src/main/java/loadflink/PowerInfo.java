package loadflink;


import sun.util.calendar.BaseCalendar;


/**
 * Created by Administrator on 2020/7/3.
 */
public class PowerInfo {
    public long   CLDBS;
    public String SJSJ;
    public Double ZYGGL;
    public Double AZXYG;
    public Double BZXYG;
    public Double CZXYG;
    public Double ZWGGL;
    public Double AZXWG;
    public Double BZXWG;
    public Double CZXWG;
    public Double SZGL;
    public Double ASZGL;
    public Double BSZGL ;
    public Double CSZGL;
    public Double ZGLYS;
    public Double AGLYS;
    public Double BGLYS;
    public Double CGLYS;

    public PowerInfo(long CLDBS, String SJSJ, Double ZYGGL, Double AZXYG, Double BZXYG, Double CZXYG, Double ZWGGL, Double AZXWG, Double BZXWG, Double CZXWG, Double SZGL, Double ASZGL, Double BSZGL, Double CSZGL, Double ZGLYS, Double AGLYS, Double BGLYS, Double CGLYS) {
        this.CLDBS = CLDBS;
        this.SJSJ = SJSJ;
        this.ZYGGL = ZYGGL;
        this.AZXYG = AZXYG;
        this.BZXYG = BZXYG;
        this.CZXYG = CZXYG;
        this.ZWGGL = ZWGGL;
        this.AZXWG = AZXWG;
        this.BZXWG = BZXWG;
        this.CZXWG = CZXWG;
        this.SZGL = SZGL;
        this.ASZGL = ASZGL;
        this.BSZGL = BSZGL;
        this.CSZGL = CSZGL;
        this.ZGLYS = ZGLYS;
        this.AGLYS = AGLYS;
        this.BGLYS = BGLYS;
        this.CGLYS = CGLYS;
    }

    @Override
    public String toString() {
        return "PowerInfo{" +
                "CLDBS=" + CLDBS +
                ", SJSJ='" + SJSJ + '\'' +
                ", ZYGGL=" + ZYGGL +
                ", AZXYG=" + AZXYG +
                ", BZXYG=" + BZXYG +
                ", CZXYG=" + CZXYG +
                ", ZWGGL=" + ZWGGL +
                ", AZXWG=" + AZXWG +
                ", BZXWG=" + BZXWG +
                ", CZXWG=" + CZXWG +
                ", SZGL=" + SZGL +
                ", ASZGL=" + ASZGL +
                ", BSZGL=" + BSZGL +
                ", CSZGL=" + CSZGL +
                ", ZGLYS=" + ZGLYS +
                ", AGLYS=" + AGLYS +
                ", BGLYS=" + BGLYS +
                ", CGLYS=" + CGLYS +
                '}';
    }

    public PowerInfo() {
    }


    public static PowerInfo of(long CLDBS, String SJSJ, Double ZYGGL, Double AZXYG, Double BZXYG, Double CZXYG, Double ZWGGL, Double AZXWG, Double BZXWG, Double CZXWG, Double SZGL, Double ASZGL, Double BSZGL, Double CSZGL, Double ZGLYS, Double AGLYS, Double BGLYS, Double CGLYS){
        return new PowerInfo( CLDBS,  SJSJ,  ZYGGL,  AZXYG,  BZXYG,  CZXYG,  ZWGGL,  AZXWG,  BZXWG,  CZXWG,  SZGL,  ASZGL,  BSZGL,  CSZGL,  ZGLYS,  AGLYS,  BGLYS,  CGLYS);
    }
}

