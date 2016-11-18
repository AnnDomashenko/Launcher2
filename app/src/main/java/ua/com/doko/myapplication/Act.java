package ua.com.doko.myapplication;

public class Act {
    String nameAct;
    int iconAct;

    public Act(int iconAct) {
        this.iconAct = iconAct;
    }

    public Act(String nameAct) {
        this.nameAct = nameAct;
    }

    public int getIconAct() {
        return iconAct;
    }

    public String getNameAct() {
        return nameAct;
    }
}
