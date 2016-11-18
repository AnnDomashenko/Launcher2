package ua.com.doko.myapplication;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ActAdapter extends ArrayAdapter<Act> {
    private Context context;
    private int resource;
    private List<Act> actions;


    public ActAdapter(Context context, int resource, List<Act> actions) {
        super(context, resource, actions);
        this.actions = actions;
        this.context = context;
        this.resource = resource;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Act act = actions.get(position);
        convertView = android.view.View.inflate(context, resource, null);
        viewHolder = new ViewHolder(convertView);
        viewHolder.action.setTag(act);

        if (act.getIconAct() != 0) {
            viewHolder.action.setCompoundDrawablesWithIntrinsicBounds(act.getIconAct(), 0, 0, 0);
        }
        if (act.getNameAct() != null) {
            viewHolder.action.setText(act.getNameAct());
        }
        return convertView;
    }

    private class ViewHolder {
        private TextView action;

        public ViewHolder(View view) {
            action = ((TextView) view.findViewById(R.id.text_popup));
        }
    }

}
