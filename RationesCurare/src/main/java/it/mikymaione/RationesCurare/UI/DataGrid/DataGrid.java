package it.mikymaione.RationesCurare.UI.DataGrid;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.util.ArrayList;
import java.util.Collections;

public class DataGrid extends LinearLayout
{

    public CircularFifoQueue<Item> idSelectedItem = new CircularFifoQueue<Item>(9);
    private Context mContext;
    private LinearLayout mListHeader;
    private HorizontalScroller mHsv;
    private MemberCollection mc;
    private TextView mTvNoData;
    private String strNoDataText;
    private Display display;

    public DataGrid(Context context, AttributeSet attrs) throws Exception
    {
        super(context, attrs);

        setOrientation(VERTICAL);
        setBackgroundColor(DataGridStyle.DataGrid.BackgroundColor);

        display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        mContext = context;

        mc = new MemberCollection();
        mc.DATAGRID_WINDOW = this;
        mc.HEADER_VIEW = new ArrayList<TextView>();
        mc.ITEM_VIEW = new ArrayList<ArrayList<TextView>>();
        mc.COLUMN_STYLE = new ArrayList<ColumnStyle>();
    }

    public void DeleteRow(Object toFind)
    {
        try
        {
            DataTable.DataRow r = mc.DATA_SOURCE.select_by_column_index(0, toFind);

            if (r != null)
            {
                mc.DATA_SOURCE.remove(r);
            }
        }
        catch (Exception e)
        {
            //not finded
        }
    }

    private void initHeader()
    {
        Header HeaderView;

        for (int i = 0; i < mc.COLUMN_STYLE.size(); i++)
        {
            mc.COLUMN_STYLE.get(i).setIndex(i);

            HeaderView = new Header(getContext(), mc);
            HeaderView.setTextColor(DataGridStyle.HeaderContainer.TextColor);
            HeaderView.setText((mc.COLUMN_STYLE.get(i)).getColumnName());
            HeaderView.setTag(mc.COLUMN_STYLE.get(i));
            HeaderView.setWidth(mc.COLUMN_STYLE.get(i).getWitdh());

            mc.HEADER_VIEW.add(HeaderView);
            mListHeader.addView(HeaderView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, DataGridStyle.HeaderCell.Height));
        }
    }

    public void initListView()
    {
        mc.DATAGRID_ADAPTER = new DataGridAdapter(getContext(), mc);

        mc.LIST_VIEW.setDividerHeight(2);
        mc.LIST_VIEW.setBackgroundColor(DataGridStyle.DataContainer.BackgroundColor);
        mc.LIST_VIEW.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mc.LIST_VIEW.setAdapter(mc.DATAGRID_ADAPTER);
    }

    public void create()
    {
        mListHeader = new LinearLayout(mContext);
        mListHeader.setOrientation(LinearLayout.HORIZONTAL);
        mListHeader.setBackgroundColor(DataGridStyle.HeaderContainer.BackgroundColor);
        //mListHeader.setBackgroundDrawable(getResources().getDrawable(DataGridStyle.HeaderContainer.Background));

        mc.LIST_VIEW = new ListView(mContext);

        mc.DATAGRID_VIEW = new LinearLayout(mContext);
        mc.DATAGRID_VIEW.setOrientation(VERTICAL);
        mc.DATAGRID_VIEW.setPadding(2, 0, 3, 0);

        mTvNoData = new TextView(mContext);
        mTvNoData.setText(strNoDataText == null ? "No data available" : strNoDataText);
        mTvNoData.setGravity(Gravity.LEFT);
        mTvNoData.setTextColor(DataGridStyle.DataCell.ForegroundColor);

        //
        //	Trick to position the text to center of the screen
        //
        mTvNoData.setPadding((display.getWidth() / 2) - (150 / 2), 10, 0, 10);
        mTvNoData.setVisibility(View.GONE);

        mHsv = new HorizontalScroller(mContext, mc);

        ArrayList<TextView> ary;
        for (int i = 0; i < mc.COLUMN_STYLE.size(); i++)
        {
            ary = new ArrayList<TextView>();
            mc.ITEM_VIEW.add(ary);
        }

        mc.DATAGRID_VIEW.addView(mListHeader);
        mc.DATAGRID_VIEW.addView(mc.LIST_VIEW);
        mc.DATAGRID_VIEW.setScrollBarStyle(SCROLLBARS_INSIDE_OVERLAY);
        mc.DATAGRID_VIEW.addView(mTvNoData);

        mHsv.setScrollBarStyle(SCROLLBARS_INSIDE_OVERLAY);
        mHsv.addView(mc.DATAGRID_VIEW);
        addView(mHsv);

        initHeader();
        initListView();
    }

    public void refresh()
    {
        if (mListHeader == null)
        {
            create();
        }

        mc.DATAGRID_ADAPTER.notifyDataSetChanged();
        mTvNoData.setVisibility(View.GONE);

        if (mc.DATA_SOURCE.getRowSize() == 0)
        {
            mTvNoData.setVisibility(View.VISIBLE);
        }
    }

    public void addColumnStyle(ColumnStyle columnStyle)
    {
        mc.COLUMN_STYLE.add(columnStyle);
    }

    public void addColumnStyle(String DisplayName, String strFieldName, int intDisplaySize)
    {
        mc.COLUMN_STYLE.add(new ColumnStyle(DisplayName, strFieldName, intDisplaySize));
    }

    public void addColumnStyles(ColumnStyle[] columnStyle)
    {
        Collections.addAll(mc.COLUMN_STYLE, columnStyle);
    }

    public void removeColumnStyle(int index)
    {
        mc.COLUMN_STYLE.remove(index);
    }

    public void removeColumnStyles()
    {
        mc.COLUMN_STYLE.clear();
    }

    public ColumnStyle[] getColumnStyles()
    {
        return mc.COLUMN_STYLE.toArray(new ColumnStyle[mc.COLUMN_STYLE.size()]);
    }

    public DataTable getDataSource()
    {
        return mc.DATA_SOURCE;
    }

    public void setDataSource(DataTable data)
    {
        mc.DATA_SOURCE = data;
    }

    public void setLvOnClickListener(OnClickListener listener)
    {
        if (mc.LIST_VIEW == null)
        {
            create();
        }
        mc.LIST_VIEW.setOnClickListener(listener);
    }

    public void setLvOnItemClickListener(OnItemClickListener listener)
    {
        if (mc.LIST_VIEW == null)
        {
            create();
        }
        mc.LIST_VIEW.setOnItemClickListener(listener);
    }

    public void setLvOnItemLongClickListener(OnItemLongClickListener listener)
    {
        if (mc.LIST_VIEW == null)
        {
            create();
        }
        mc.LIST_VIEW.setOnItemLongClickListener(listener);
    }

    public void setLvOnItemSelectedListener(OnItemSelectedListener listener)
    {
        if (mc.LIST_VIEW == null)
        {
            create();
        }
        mc.LIST_VIEW.setOnItemSelectedListener(listener);
    }

    public void setLvOnCreateContextMenuListener(OnCreateContextMenuListener listener)
    {
        if (mc.LIST_VIEW == null)
        {
            create();
        }
        mc.LIST_VIEW.setOnCreateContextMenuListener(listener);
    }

    public void setLvOnFocusChangeListener(OnFocusChangeListener listener)
    {
        if (mc.LIST_VIEW == null)
        {
            create();
        }
        mc.LIST_VIEW.setOnFocusChangeListener(listener);
    }

    public void setLvOnLongClickListener(OnLongClickListener listener)
    {
        if (mc.LIST_VIEW == null)
        {
            create();
        }
        mc.LIST_VIEW.setOnLongClickListener(listener);
    }

    public void setLvOnScrollListener(OnScrollListener listener)
    {
        if (mc.LIST_VIEW == null)
        {
            create();
        }
        mc.LIST_VIEW.setOnScrollListener(listener);
    }

    public void setLvClickable(boolean isClickable)
    {
        if (mc.LIST_VIEW == null)
        {
            create();
        }
        mc.LIST_VIEW.setClickable(isClickable);
    }

    public void setNoDataText(String strText)
    {
        strNoDataText = strText;
    }

    public static class ColumnStyle
    {
        private String strColumnName;
        private String strFieldName;
        private int intWidth;
        private int intIndex;

        public ColumnStyle(String FieldName_and_DisplayName, int width)
        {
            this(FieldName_and_DisplayName, FieldName_and_DisplayName, width);
        }

        public ColumnStyle(String FieldName, String DisplayName, int width)
        {
            strColumnName = DisplayName;
            strFieldName = FieldName;
            intWidth = width;
        }

        public int getIndex()
        {
            return intIndex;
        }

        public void setIndex(int index)
        {
            intIndex = index;
        }

        public String getColumnName()
        {
            return strColumnName;
        }

        public String getFieldName()
        {
            return strFieldName;
        }

        public int getWitdh()
        {
            return intWidth;
        }

        public void setWidth(int width)
        {
            intWidth = width;
        }

    }

    class MemberCollection
    {
        public boolean IS_SPLITER_CLICKED = false;
        public MotionEvent DOWNSTART;
        public LinearLayout DATAGRID_VIEW;
        public DataGrid DATAGRID_WINDOW;
        public ArrayList<TextView> HEADER_VIEW;
        public ArrayList<ArrayList<TextView>> ITEM_VIEW;
        public ArrayList<ColumnStyle> COLUMN_STYLE;
        public DataGridAdapter DATAGRID_ADAPTER;
        public ListView LIST_VIEW;
        public DataTable DATA_SOURCE;
    }


}