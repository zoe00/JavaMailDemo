package javamail.demo.activities;

import javamail.demo.R;
import javamail.demo.util.GmailSender;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.main);    
		if (Integer.valueOf(android.os.Build.VERSION.SDK) >= 9) {
			try {
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
				StrictMode.setThreadPolicy(policy);
			} catch (Exception e) {
			}
		}
		Button go=(Button) findViewById(R.id.button1);
		go.setOnClickListener(this);
	}

	private class SendMailTask extends AsyncTask<Void, Void, Void>{
		
		private ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			try{
				dialog = new ProgressDialog(MainActivity.this);
				dialog.setCancelable(false);
				dialog.setMessage("Loading, please wait...");
				dialog.show();
			}catch(Exception e){
			}
		}

		@Override
		protected Void doInBackground(Void... params) {	
			GmailSender sender=new GmailSender("abc_email@gmail.com", "your_password_here");
			try {
				String recepient_emails_csv="";
				sender.sendMail("Subject", "Message Body", "", recepient_emails_csv);
				byte[] attachmentByteArray;
				attachmentByteArray="String".getBytes();
				// With Attachment
				sender.sendMailWithAttachment("Subject", "Message Body", "Sender's Email", "Comma seperated recipient email addresses", attachmentByteArray);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;

		}
		@Override
		protected void onPostExecute(Void result) {
			try{
				dialog.dismiss();
			}catch (Exception e) {}
			Toast.makeText(MainActivity.this, "Mail sent.", Toast.LENGTH_SHORT).show();
		}				 
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.button1){
			new SendMailTask().execute();
		}
	}
}
