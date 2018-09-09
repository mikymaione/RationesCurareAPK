package it.mikymaione.RationesCurare.UI.DataGrid;

import android.graphics.Color;

import it.mikymaione.RationesCurare.R;

class DataGridStyle
{

    public class DataGrid
    {
        public static final int BackgroundColor = Color.TRANSPARENT;
    }

    public class HeaderContainer
    {
        public static final int BackgroundColor = Color.LTGRAY;
        public static final int Background = R.drawable.gradient;
    }

    public class HeaderCell
    {
        public static final int Height = 40;
        public static final int Width = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
        public static final int LPadding = 5;
        public static final int RPadding = 5;
        public static final int TPadding = 3;
        public static final int BPadding = 3;
        public static final int BackgroundColor = Color.TRANSPARENT;
        public static final int OnClickBackgroundColor = Color.LTGRAY;
        public static final int Gravity = android.view.Gravity.CENTER_VERTICAL;
    }

    public class SpliterCell
    {
        public static final int BackgroundColor = Color.GRAY;
        public static final int OnClickBackgroundColor = Color.DKGRAY;
        public static final int LPadding = 1;
        public static final int RPadding = 1;
        public static final int TPadding = HeaderCell.TPadding + 9;
        public static final int BPadding = HeaderCell.BPadding;
    }

    public class HeaderSpliterCell
    {
        public static final int LPadding = 8;
        public static final int RPadding = 8;
    }

    public class DataContainer
    {
        public static final int BackgroundColor = Color.TRANSPARENT;
    }

    public class DataCell
    {
        public static final int Height = HeaderCell.Height;
        public static final int Gravity = HeaderCell.Gravity;
        public static final int LPadding = 5;
        public static final int RPadding = 5;
        public static final int TPadding = 2;
        public static final int BPadding = 2;
        public static final int ForegroundColor = Color.BLACK;
    }


}