package main.model;

// 予約記録
public class Reservation {
    public String id;
    public String owner;
    public String ckin;
    public String ckout;
    public String num;

    public Reservation() {}

    // 2引数:id,ownerのみ(削除専用)
    public Reservation(String id, String owner) {
        this(id,owner,"","","");
    }

    // 3引数:id=0,num=1(デフォルト値)
    public Reservation(String owner, String ckin, String ckout) {
        this("0",owner,ckin,ckout,"1");
    }

    // 4引数:id=0
    public Reservation(String owner, String ckin, String ckout, String num) {
        this("0",owner,ckin,ckout,num);
    }

    // 引数省略なし
    public Reservation(String id, String owner, String ckin, String ckout, String num) {
        this.id = id;
        this.owner = owner;
        this.ckin  = ckin;
        this.ckout = ckout;
        this.num = num;
    }

    public String toString() {
        return String.format("(%s,%s,%s,%s,%s)",id,owner,ckin,ckout,num); // 暫定
    }
}
