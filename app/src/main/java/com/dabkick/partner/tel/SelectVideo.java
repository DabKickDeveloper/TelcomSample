package com.dabkick.partner.tel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dabkick.sdk.Adapter.VideoHorizontalAdapter;
import com.dabkick.sdk.DabKick_Agent;
import com.dabkick.sdk.Dabkick;
import com.dabkick.sdk.Global.GlobalData;
import com.dabkick.sdk.Global.GlobalHandler;
import com.dabkick.sdk.Global.VideoManager;
import com.dabkick.sdk.Horizontal.HorizontalListView;
import com.dabkick.sdk.Livesession.YouTubeVideoDetail;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;

public class SelectVideo extends AppCompatActivity {

    //your own listview
    public HorizontalListView hListView;
    //your own adapter
    public VideoHorizontalAdapter mVideoHorizontalAdapter;
    //Dabkick agent init to make use of lib
    VideoManager videoManager = VideoManager.getInstance();
    //local array list to get the results
    ArrayList VideosList;
    private Button goToLs;

    //Dabkickvideodetail
    YouTubeVideoDetail mYoutubeVideoDetailSingleItem;

    TextView statusMsg,userInfo;
    Button watchWithFriends;

    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_video);

        init();

        statusMsg.setMovementMethod(new ScrollingMovementMethod());

        Observable.interval(1, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                GlobalHandler.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        statusMsg.setText(statusMsg.getText().toString() + Dabkick.getStatusMsg());
                    }
                });
            }
        });



        goToLs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DabKick_Agent.goToLiveSession(SelectVideo.this);
                Dabkick.watchWithFriends(SelectVideo.this);
            }
        });

//        watchWithFriends.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                DabKickRegisterAgent.watchWithFriends(SelectVideo.this);
//
//            }
//        });
        mProgressBar.setVisibility(View.VISIBLE);

        hListView = (HorizontalListView) findViewById(R.id.testvideosListView);

        //make a call to this agent to get the search results and pass the results to your adapter to show it in list view
        videoManager.setOnSearchFinishedLoading(new VideoManager.OnSearchFinishedLoadingListener() {
            @Override
            public void onSearchFinishedLoading(boolean b) {
                GlobalHandler.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressBar.setVisibility(View.GONE);
                        //call this method with the search term to get the results
                        VideosList = videoManager.getSearchResultByTerm("telcom");
                        mVideoHorizontalAdapter = new VideoHorizontalAdapter(SelectVideo.this, R.layout.video_view_item, VideosList, false);
                        hListView.setAdapter(mVideoHorizontalAdapter);
                        mVideoHorizontalAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
        //Condition check to load the searched videos if not throw alert(logic done in DabKickVideoManagerAgent)
        videoManager.searchVideo("telcom");

        hListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Call this method to get the selected video
                mYoutubeVideoDetailSingleItem = (YouTubeVideoDetail)mVideoHorizontalAdapter.getItem(position);
                //PlayDabKickVideoActivity is an dabkick library class, use this to get a lib built-in player.
               // Intent intent = new Intent(SelectVideo.this, PlayDabKickVideoActivity.class);
                //intent.putExtra(PlayDabKickVideoActivity.EXTRA_VIDEO_ID, mYoutubeVideoDetailSingleItem.videoID);
               // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //startActivity(intent);
            }
        });
    }

    void init(){

        statusMsg = (TextView)findViewById(R.id.statusMsg);
      //  userInfo = (TextView)findViewById(R.id.userInfo) ;
//        watchWithFriends = (Button)findViewById(R.id.wwf) ;
        mProgressBar = (ProgressBar)findViewById(R.id.progress_bar);
        goToLs = (Button) findViewById(R.id.go_to_ls_btn);

    }
}
