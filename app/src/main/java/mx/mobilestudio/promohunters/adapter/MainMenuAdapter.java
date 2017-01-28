package mx.mobilestudio.promohunters.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import mx.mobilestudio.promohunters.R;
import mx.mobilestudio.promohunters.model.ListMenuElement;

/**
 * Created by mobile on 11/26/16.
 */

public class MainMenuAdapter extends BaseAdapter {

    public Context context;
    private ListMenuElement[] listMenuElements;
    private LayoutInflater layoutInflater;

    public MainMenuAdapter(ListMenuElement[] listMenuElements, Context context) {
        this.listMenuElements = listMenuElements;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        return listMenuElements.length;
    }

    @Override
    public ListMenuElement getItem(int i) {
        return listMenuElements[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ListMenuElement listMenuElement = getItem(i);

        view = layoutInflater.inflate(R.layout.list_view_item, viewGroup, false);

        TextView textName = (TextView) view.findViewById(R.id.title_menu);
        ImageView imageViewIcon = (ImageView) view.findViewById(R.id.imageViewIcon);

        textName.setText(listMenuElement.name);
        imageViewIcon.setImageResource(listMenuElement.icon);

        return view;
    }
}
