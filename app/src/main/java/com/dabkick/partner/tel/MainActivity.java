package com.dabkick.partner.tel;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("yuan", "on new intent");

        Bundle b = intent.getExtras();
        if(b!=null) {
            Log.e("yuan", "received with notification");
           /* requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
            setProgressBarIndeterminateVisibility(true);*/
            String sessionID = b.getString("sessionID");
            Log.e("yuan", "sessionID:"+sessionID);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);

        Dabkick.receivedNotification(this);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        Button openPhotos = (Button)findViewById(R.id.photos_btn);

        openPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dabkick.openPhotos(MainActivity.this);

            }
        });


        /*listView = (ListView)this.findViewById(R.id.verticalListview);

        final ArrayList<ArrayList> list = new ArrayList();
        for (int i=0; i<4; i++)
        {
            list.add(new ArrayList());
        }

        final String[] searchTerms = new String[]{"Game of Thrones episode","Fantastic Four","HBO Trailers","HBO Boxing"};
        adapter = new VideoVerticalAdapter(this, list, searchTerms);
        listView.setAdapter(adapter);

        //make a call to this agent to get the search results and pass the results to your adapter to show it in list view
        videoManager.setOnSearchFinishedLoading(searchTerms[0], new VideoManager.OnSearchFinishedLoadingListener() {
            @Override
            public void onSearchFinishedLoading(boolean b) {
                GlobalHandler.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        //call this method with the search term to get the results
                        VideosList = videoManager.getSearchResultByTerm(searchTerms[0]);
                        list.get(0).addAll(VideosList);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
        //Condition check to load the searched videos if not throw alert(logic done in DabKickVideoManagerAgent)
        videoManager.searchVideo(searchTerms[0]);

        videoManager.setOnSearchFinishedLoading(searchTerms[1], new VideoManager.OnSearchFinishedLoadingListener() {
            @Override
            public void onSearchFinishedLoading(boolean b) {
                GlobalHandler.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        //call this method with the search term to get the results
                        VideosList = videoManager.getSearchResultByTerm(searchTerms[1]);
                        list.get(1).addAll(VideosList);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
        //Condition check to load the searched videos if not throw alert(logic done in DabKickVideoManagerAgent)
        videoManager.searchVideo(searchTerms[1]);

        videoManager.setOnSearchFinishedLoading(searchTerms[2], new VideoManager.OnSearchFinishedLoadingListener() {
            @Override
            public void onSearchFinishedLoading(boolean b) {
                GlobalHandler.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        //call this method with the search term to get the results
                        VideosList = videoManager.getSearchResultByTerm(searchTerms[2]);
                        list.get(2).addAll(VideosList);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
        //Condition check to load the searched videos if not throw alert(logic done in DabKickVideoManagerAgent)
        videoManager.searchVideo(searchTerms[2]);

        videoManager.setOnSearchFinishedLoading(searchTerms[3],new VideoManager.OnSearchFinishedLoadingListener() {
            @Override
            public void onSearchFinishedLoading(boolean b) {
                GlobalHandler.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        //call this method with the search term to get the results
                        VideosList = videoManager.getSearchResultByTerm(searchTerms[3]);
                        list.get(3).addAll(VideosList);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
        //Condition check to load the searched videos if not throw alert(logic done in DabKickVideoManagerAgent)
        videoManager.searchVideo(searchTerms[3]);
*/
    }

}
