/*
 * Copyright (C) 2013 M. Ritscher
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.mikymaione.RationesCurare.UI.CalendarControl;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import it.mikymaione.RationesCurare.R;

/**
 * @author M. Ritscher
 * 11/11/13.
 */
public class EventMenuFragment extends Fragment
{

    EventSelectedListener mCallback;
    private ListView mEventList;
    private List<DateEvent> mEvents;
    private ArrayAdapter<DateEvent> mEventAdapter;

    public EventMenuFragment()
    {

    }

    /**
     * Called to do initial creation of a fragment.  This is called after
     * {@link android.app.Fragment#onAttach(android.app.Activity)}  and before
     * {@link #onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)}.
     * <p/>
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (mEvents == null)
        {
            mEvents = new ArrayList<DateEvent>();
        }

        mEventAdapter = new ArrayAdapter<DateEvent>(getActivity(), android.R.layout.simple_list_item_1, mEvents);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View layout = inflater.inflate(R.layout.event_menu, container, false);

        // Drawable d = getResources().getDrawable(R.relevant_drawable);
        //d.setAlpha(50);
        //layout.setBackground(d);

//        TypedArray attributesArray = getActivity().obtainStyledAttributes(null, R.styleable.PopupWindow,
//                android.R.attr.popupWindowStyle, 0);

//        final Drawable background = attributesArray.getDrawable(R.styleable.PopupWindow_android_popupBackground);
//        layout.setBackgroundDrawable( background );

//        TypedArray attributesArray = getActivity().obtainStyledAttributes(null, R.styleable.MenuView);
//                // android.R.attr.popupMenuStyle, 0 );
//        final int color = attributesArray.getColor(R.styleable.MenuView_android_itemBackground, 3 );
//
//        attributesArray.recycle();

        mEventList = (ListView) layout.findViewById(R.id.event_list);
//        mEventList.setBackgroundColor(color);

        mEventList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                final DateEvent event = mEventAdapter.getItem(position);
                mCallback.onEventSelected(event);
            }
        });

        mEventList.setAdapter(mEventAdapter);

        return layout;
    }

    /**
     * Sets the listener to be called when an event was selected
     *
     * @param listener
     */
    void setSelectionListener(EventSelectedListener listener)
    {
        mCallback = listener;
    }

    /**
     * Adds the events to the listview to be displayed as a popup menu
     *
     * @param events
     */
    void setEventMenuEntries(List<DateEvent> events)
    {
        mEvents = events;
        if (mEventAdapter != null)
        {
            mEventAdapter.clear();
            mEventAdapter.addAll(events);
            mEventAdapter.notifyDataSetChanged();
        }
    }

    public interface EventSelectedListener
    {
        public void onEventSelected(DateEvent event);
    }


}