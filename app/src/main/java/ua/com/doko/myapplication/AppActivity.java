package ua.com.doko.myapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

public class AppActivity extends AppCompatActivity {

    private AppAdapter adapter;
    private RecyclerView recyclerView;
    private Filter filter;
    private List<ResolveInfo> info;
    private List<Act> actions;
    private PackageManager pm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        recyclerView = (RecyclerView) findViewById(R.id.list);
        findViewById(R.id.button_list).setOnClickListener(onClickListener);
        findViewById(R.id.button_grid).setOnClickListener(onClickListener);
        EditText editText = (EditText) findViewById(R.id.edit);
        editText.addTextChangedListener(textWatcher);
        pm = getPackageManager();
        info = getListResolveInfo(pm);
        List<App> packages = getApps(info);
        adapter = new AppAdapter(this, packages);
        adapter.setIdLayout(R.layout.item_grid);
        filter = adapter.getFilter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(getGrid());

        actions = new ArrayList<>();
        actions.add(new Act(android.R.drawable.ic_menu_info_details));
        actions.add(new Act(getResources().getString(R.string.delete)));
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        View actView;

        @Override
        public void onClick(View view) {
            view.setEnabled(false);
            if (actView != null)
                actView.setEnabled(true);
            actView = view;
            switch (view.getId()) {
                case R.id.button_list:
                    adapter.setIdLayout(R.layout.item_list);
                    recyclerView.setLayoutManager(getList());
                    actions.remove(0);
                    break;
                case R.id.button_grid:
                    adapter.setIdLayout(R.layout.item_grid);
                    recyclerView.setLayoutManager(getGrid());
                    actions.add(0, new Act(android.R.drawable.ic_menu_info_details));
                    break;
            }
        }
    };

    private List<ResolveInfo> getListResolveInfo(PackageManager pm) {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        return pm.queryIntentActivities(intent, 0);
    }

    private List<App> getApps(List<ResolveInfo> appsResolveInfo) {
        App app;
        List<App> apps = new ArrayList<>();
        for (int i = 0; i < appsResolveInfo.size(); i++) {
            app = new App(appsResolveInfo.get(i), pm);
            apps.add(app);
        }
        return apps;
    }

    private GridLayoutManager getGrid() {
        return new GridLayoutManager(this, 4);
    }

    private LinearLayoutManager getList() {
        return new LinearLayoutManager(this);
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            filter.filter(s);
        }
    };

    public void showInfoApp(View view) {
        App app = (App) view.getTag();
        String name = app.getAppInfo().packageName;
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", name, null));
        startActivity(intent);
    }


    public ListPopupWindow createMenu() {

        ListPopupWindow popup = new ListPopupWindow(this);
        popup.setHeight(ListPopupWindow.WRAP_CONTENT);
        popup.setWidth((int) getResources().getDimension(R.dimen.width_item));
        return popup;
    }

    public void showMenu(final View v) {
        final ListPopupWindow popup = createMenu();
        popup.setAdapter(new ActAdapter(this, R.layout.popup_menu, actions));
        popup.setAnchorView(v);
        popup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (actions.get(i).getIconAct() != 0) {
                    showInfoApp(v);
                } else adapter.deleteApp(v);
                popup.dismiss();
            }
        });
        popup.show();
    }
}





