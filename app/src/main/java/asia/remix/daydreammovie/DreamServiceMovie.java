package asia.remix.daydreammovie;

import android.service.dreams.DreamService;
import android.os.Bundle;
import android.widget.VideoView;
import android.media.MediaPlayer;
import android.os.Environment;
import java.io.File;
import android.util.Log;
import android.widget.Toast;

public class DreamServiceMovie extends DreamService{
	private static final String TAG = "DreamServiceMovie";
	private VideoView videoview;

	/**
	*	movie ready → start
	*/
	class MyVideoPreparedListener implements android.media.MediaPlayer.OnPreparedListener{
		@Override
		public void onPrepared( MediaPlayer mediaPlayer ){
			Log.d( TAG, "●MyVideoPreparedListener.onPrepared()" );
			videoview.start();
		}
	}

	/**
	*	movie finish → repeat
	*/
	class MyVideoEndListener implements android.media.MediaPlayer.OnCompletionListener{
		@Override
		public void onCompletion( MediaPlayer mediaPlayer ){
			Log.d( TAG, "●MyVideoEndListener.onCompletion()" );
			videoview.seekTo( 0 );
			videoview.start();
		}
	}

	@Override
	public void onAttachedToWindow(){
		super.onAttachedToWindow();
		setInteractive( false );
		setFullscreen( true );
		setContentView( R.layout.dream_service );
		Log.d( TAG, "●onAttachedToWindow()" );
	}

	@Override
	public void onDreamingStarted(){
		super.onDreamingStarted();
		Log.d( TAG, "●onDreamingStarted()" );

		videoview = (VideoView)findViewById( R.id.videoview );
		videoview.setOnPreparedListener( new MyVideoPreparedListener() );
		videoview.setOnCompletionListener( new MyVideoEndListener() );
		File file = new File( Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_MOVIES ), "sample.mp4" );
		if( file.exists() ){
			videoview.setVideoPath( file.toString() );
			videoview.start();
		}else{
			Toast.makeText( getApplicationContext(), R.string.msgFileNotFound, Toast.LENGTH_LONG ).show();
		}
	}
}
