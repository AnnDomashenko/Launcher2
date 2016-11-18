package ua.com.doko.myapplication;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spanned;

public class App {

    private Drawable icon;
    private Spanned name;
    private ActivityInfo appInfo;

    public App(ResolveInfo resolveInfo, PackageManager pm) {
        icon = resolveInfo.loadIcon(pm);
        name = Html.fromHtml(resolveInfo.loadLabel(pm).toString());
        appInfo = resolveInfo.activityInfo;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public Spanned getName() {
        return name;
    }

    public void setName(Spanned name) {
        this.name = name;
    }

    public ActivityInfo getAppInfo() {
        return appInfo;
    }
}
