package it.mikymaione.RationesCurare.UI.Adattatori;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import it.mikymaione.RationesCurare.R;

/**
 * Created by michele on 21/03/15.
 */
public class CustomArrayAdapter extends ArrayAdapter<String>
{
    private final Activity context;
    private final String[] testo;
    private final Integer[] imageId;

    public CustomArrayAdapter(Activity context, String[] testo, Integer[] imageId)
    {
        super(context, R.layout.elemento_menu, testo);

        this.context = context;
        this.testo = testo;
        this.imageId = imageId;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.elemento_menu, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);

        txtTitle.setText(testo[position]);
        imageView.setImageResource(imageId[position]);

        return rowView;
    }


}