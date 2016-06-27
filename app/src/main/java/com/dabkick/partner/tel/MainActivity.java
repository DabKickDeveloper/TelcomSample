package com.dabkick.partner.tel;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dabkick.sdk.Dabkick;
import com.dabkick.sdk.Global.GlobalHandler;
import com.dabkick.sdk.Global.HorizontalListView;
import com.dabkick.sdk.Global.PreferenceHandler;
import com.dabkick.sdk.Global.VideoManager;
import com.dabkick.sdk.Livesession.LSManager.YouTubeVideoDetail;
import com.dabkick.sdk.Livesession.VideoHorizontalAdapter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //your own listview
    public ListView listView;

    //Dabkick agent init to make use of lib
    VideoManager videoManager = VideoManager.getInstance();
    //local array list to get the results
    ArrayList VideosList;
    public static VideoVerticalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);

        Dabkick.receivedNotification(this);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);


        listView = (ListView)this.findViewById(R.id.verticalListview);

        final ArrayList<ArrayList> list = new ArrayList();
        for (int i=0; i<4; i++)
        {
            list.add(new ArrayList());
        }

        adapter = new VideoVerticalAdapter(this, list);
        listView.setAdapter(adapter);

        //make a call to this agent to get the search results and pass the results to your adapter to show it in list view
        videoManager.setOnSearchFinishedLoading("Game of Thrones", new VideoManager.OnSearchFinishedLoadingListener() {
            @Override
            public void onSearchFinishedLoading(boolean b) {
                GlobalHandler.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        //call this method with the search term to get the results
                        VideosList = videoManager.getSearchResultByTerm("Game of Thrones");
                        list.get(0).addAll(VideosList);
                        adapter.notifyDataSetChanged();


                    }
                });
            }
        });
        //Condition check to load the searched videos if not throw alert(logic done in DabKickVideoManagerAgent)
        videoManager.searchVideo("Game of Thrones");

        videoManager.setOnSearchFinishedLoading("Fantastic Four", new VideoManager.OnSearchFinishedLoadingListener() {
            @Override
            public void onSearchFinishedLoading(boolean b) {
                GlobalHandler.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        //call this method with the search term to get the results
                        VideosList = videoManager.getSearchResultByTerm("Fantastic Four");
                        list.get(1).addAll(VideosList);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
        //Condition check to load the searched videos if not throw alert(logic done in DabKickVideoManagerAgent)
        videoManager.searchVideo("Fantastic Four");

        videoManager.setOnSearchFinishedLoading("HBO Trailers", new VideoManager.OnSearchFinishedLoadingListener() {
            @Override
            public void onSearchFinishedLoading(boolean b) {
                GlobalHandler.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        //call this method with the search term to get the results
                        VideosList = videoManager.getSearchResultByTerm("HBO Trailers");
                        list.get(2).addAll(VideosList);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
        //Condition check to load the searched videos if not throw alert(logic done in DabKickVideoManagerAgent)
        videoManager.searchVideo("HBO Trailers");

        videoManager.setOnSearchFinishedLoading("HBO Boxing",new VideoManager.OnSearchFinishedLoadingListener() {
            @Override
            public void onSearchFinishedLoading(boolean b) {
                GlobalHandler.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        //call this method with the search term to get the results
                        VideosList = videoManager.getSearchResultByTerm("HBO Boxing");
                        list.get(3).addAll(VideosList);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
        //Condition check to load the searched videos if not throw alert(logic done in DabKickVideoManagerAgent)
        videoManager.searchVideo("HBO Boxing");

    }

}
