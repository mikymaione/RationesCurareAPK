package it.mikymaione.RationesCurare.UI.DataGrid;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

import it.mikymaione.RationesCurare.Globals.GB;

public class Item extends LinearLayout
{

    private TextView[] artTextView;
    private Context mContext;
    private Spliter2 mTxtContent;
    private DataGrid.MemberCollection mc;
    private DataTable.DataRow Valore = null;

    private int MyColor;
    public int IDD = -1;

    public Item(Context context, DataGrid.MemberCollection mc, DataTable.DataRow data, int IDD_)
    {
        super(context);

        IDD = IDD_;
        mContext = context;
        Valore = data;
        this.mc = mc;

        int intCellSpliter = 0;
        int tot = mc.COLUMN_STYLE.size();

        artTextView = new TextView[data.getColumnSize()];

        setOrientation(HORIZONTAL);

        for (int i = 0; i < tot; i++)
        {
            artTextView[i] = new TextView(mContext);
            artTextView[i].setTextColor(Color.BLACK);
            artTextView[i].setPadding(DataGridStyle.DataCell.LPadding, DataGridStyle.DataCell.TPadding, DataGridStyle.DataCell.RPadding, DataGridStyle.DataCell.BPadding);
            artTextView[i].setText(data.get_for_grid(mc.COLUMN_STYLE.get(i).getFieldName()));
            artTextView[i].setWidth(mc.COLUMN_STYLE.get(i).getWitdh());
            artTextView[i].setGravity(DataGridStyle.DataCell.Gravity);
            artTextView[i].setSingleLine(true);

            mc.ITEM_VIEW.get(i).add(artTextView[i]);

            addView(artTextView[i], new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, DataGridStyle.DataCell.Height));

            if (intCellSpliter < mc.COLUMN_STYLE.size())
            {
                mTxtContent = new Spliter2(getContext(), mc, i);
                mc.SPLITER_VIEW.get(i).add(mTxtContent);
                addView(mTxtContent, new LinearLayout.LayoutParams(DataGridStyle.HeaderCell.Width, DataGridStyle.DataCell.Height));
                intCellSpliter++;
            }
        }
    }

    public void HighlightRow(boolean selected)
    {
        /*
        for (TextView e : artTextView)
        {
            e.setTypeface(null, selected ? Typeface.BOLD : Typeface.NORMAL);
        }
        */

        setBackgroundColor(selected ? GB.cHoloBlueLight : MyColor);
    }

    private void Colora(boolean selected, boolean pressed)
    {
        boolean spair = (Valore.getID() % 2 == 0);

        Colora(spair, selected, pressed);
    }

    private void Colora(boolean spair, boolean selected, boolean pressed)
    {
        int colore;

        if (pressed)
        {
            colore = GB.cHoloBlueDark;
        }
        else
        {
            if (selected)
            {
                colore = GB.cHoloBlueLight;
            }
            else
            {
                if (spair)
                {
                    colore = GB.cWhite;
                }
                else
                {
                    colore = GB.cDarkerGray;
                }

                MyColor = colore;
            }
        }

        setBackgroundColor(colore);
    }

    protected void Colora(boolean spair)
    {
        Colora(spair, isSelected(), isPressed());
    }

    public void populate(DataTable.DataRow data)
    {
        for (int i = 0; i < mc.COLUMN_STYLE.size(); i++)
        {
            artTextView[i].setWidth(mc.COLUMN_STYLE.get(i).getWitdh());
            artTextView[i].setText(data.get_for_grid(mc.COLUMN_STYLE.get(i).getFieldName()));
        }
    }

    public DataTable.DataRow getValore()
    {
        return Valore;
    }


}