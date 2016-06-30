package com.dabkick.partner.tel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dabkick.sdk.Global.HorizontalListView;
import com.dabkick.sdk.Livesession.LSManager.YouTubeVideoDetail;
import com.dabkick.sdk.Livesession.VideoHorizontalAdapter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by developer3 on 6/27/16.
 */
public class VideoVerticalAdapter extends BaseAdapter {

    Activity activity;
    ArrayList<ArrayList> data;
    String[] titles;

    public VideoVerticalAdapter(Activity activity,ArrayList<ArrayList> list, String[] titles) {
        this.activity = activity;
        this.data = list;
        this.titles = titles;

        this.titles = Arrays.copyOf(titles, titles.length);
        this.titles[0] = "Game of Thrones";
        this.titles[2] = "Trailers";
        this.titles[3] = "Boxing";
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public ArrayList getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder = null;
        if (view == null)
        {
            LayoutInflater inflater = activity.getLayoutInflater();
            view = inflater.inflate(R.layout.list_videos_horizontal, viewGroup, false);
            viewHolder = ViewHolder.create(view);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)view.getTag();
        }

        if (viewHolder.adapter == null)
            viewHolder.adapter = new VideoHorizontalAdapter(activity, R.layout.video_view_item, data.get(i), false);

        viewHolder.listView.setAdapter(viewHolder.adapter);
        viewHolder.title.setText(titles[i]);

        final int rowIndex = i;
        viewHolder.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                YouTubeVideoDetail detail = (YouTubeVideoDetail) data.get(rowIndex).get(i);
                Intent intent = new Intent(activity, PlayDabKickVideoActivity.class);
                intent.putExtra(PlayDabKickVideoActivity.EXTRA_VIDEO_ID, detail.videoID);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(intent);
            }
        });

        return view;
    }

    public static class ViewHolder {
        VideoHorizontalAdapter adapter;
        HorizontalListView listView;
        TextView title;

        public ViewHolder(HorizontalListView listView, TextView title) {
            this.listView = listView;
            this.title = title;
        }

        public static ViewHolder create(View rootLayout) {
            HorizontalListView listView = (HorizontalListView)rootLayout.findViewById(R.id.videoHorizontalListview);
            TextView title = (TextView)rootLayout.findViewById(R.id.title);
            return new ViewHolder(listView,title);
        }
    }


}
