package ua.com.doko.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.RecViewHolder> implements Filterable {
    private Context context;
    private int idLayout;
    private List<App> apps;
    private List<App> originalApps;
    private ListFilter filter = new ListFilter();
    private ArrayList<App> filteredList;

    public AppAdapter(Context context, List<App> apps) {
        this.context = context;
        this.apps = apps;
        setIdLayout(idLayout);
        originalApps = new ArrayList<>(apps);
        filteredList = new ArrayList<>();
    }

    public void setIdLayout(int idLayout) {
        this.idLayout = idLayout;
    }

    @Override
    public RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, idLayout, null);
        return new RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecViewHolder holder, int position) {

        App data = apps.get(position);
        holder.imageView.setImageDrawable(data.getIcon());
        holder.textView.setText(data.getName());
        holder.buttonMenu.setTag(data);
        if (holder.buttonInfo != null) {
            holder.buttonInfo.setTag(data);
        }
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    class RecViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;
        ImageButton buttonMenu;
        ImageButton buttonInfo;

        public RecViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text_item_recycle);
            imageView = (ImageView) itemView.findViewById(R.id.image_item_recycle);
            buttonMenu = (ImageButton) itemView.findViewById(R.id.button_menu);
            buttonInfo = (ImageButton) itemView.findViewById(R.id.button_info);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    App app = apps.get(position);
                    String packageName = app.getAppInfo().packageName;
                    launchApp(packageName);
                }
            });
        }
    }

    public void launchApp(String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        context.startActivity(intent);
    }

    public void deleteApp(View view) {
        App app = (App) view.getTag();
        apps.remove(app);
        notifyDataSetChanged();
    }

    @Override
    public android.widget.Filter getFilter() {
        return filter;
    }

    private class ListFilter extends android.widget.Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            apps.clear();
            filteredList.clear();
            String str;
            String copyStr;
            String replaceStr;


            FilterResults filterResults = new FilterResults();

            if (constraint != null || constraint.length() != 0) {
                for (App app : originalApps) {
                    str = app.getName().toString();
                    copyStr = str.toLowerCase();
                    if (copyStr.contains(constraint.toString().toLowerCase())) {
                        replaceStr = changeRegister(constraint, str);
                        str = str.replaceAll(replaceStr, "<big><b>" + replaceStr + "</b></big>");
                        app.setName(Html.fromHtml(str));
                        filteredList.add(app);
                    }
                }
            }
            filterResults.values = filteredList;
            filterResults.count = filteredList.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            apps.addAll(((List<App>) results.values));
            notifyDataSetChanged();
        }
    }


    private String changeRegister(CharSequence constraint, String str) {
        String result = "";
        char[] originalStr = str.toCharArray();
        str = str.toLowerCase();
        char[] chars = str.toCharArray();
        char charConstraint;
        int c;
        if (constraint == null || constraint.length() == 0) {
            c = 0;

        } else c = str.indexOf(constraint.charAt(0));
        for (int i = 0; i < constraint.length(); i++) {
            charConstraint = Character.toLowerCase(constraint.charAt(i));
            for (int j = c; j < chars.length; j++) {
                if (charConstraint == chars[j] && Character.isUpperCase(originalStr[j])) {
                    charConstraint = Character.toUpperCase(charConstraint);
                }
                c = j;
            }
            result += charConstraint;
        }
        return result;
    }


}
