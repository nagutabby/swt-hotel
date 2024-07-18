package main.models;

// 予約記録
public class Reservation {
    public String id;
    public String name;
    public String startDate;
    public String endDate;
    public String numberRooms;

    public Reservation() {
    }

    // 2引数: id, nameのみ (削除専用)
    public Reservation(String id, String name) {
        this(id, name, "", "", "");
    }

    // 3引数: id = 0, num = 1 (デフォルト値)
    public Reservation(String name, String startDate, String endDate) {
        this("0", name, startDate, endDate, "1");
    }

    // 4引数: id = 0
    public Reservation(String name, String startDate, String endDate, String numberRooms) {
        this("0", name, startDate, endDate, numberRooms);
    }

    // 引数省略なし
    public Reservation(String id, String name, String startDate, String endDate, String numberRooms) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberRooms = numberRooms;
    }

    public String toString() {
        return String.format("(%s,%s,%s,%s,%s)", id, name, startDate, endDate, numberRooms);
    }
}
