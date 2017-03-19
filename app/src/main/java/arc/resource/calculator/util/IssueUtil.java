package arc.resource.calculator.util;

import android.os.AsyncTask;

/**
 * Created by jared on 3/17/2017.
 */

class IssueUtil {
    private static final String TAG = IssueUtil.class.getSimpleName();

    private static class IssueTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground( Void... params ) {
//            try {
//                Log.d( TAG, "issueTask" );
//
//                String bitbucket = "https://api.bitbucket.org/1.0/repositories";
//                String accountName = "StoneDevs";
//                String repoSlug = "CraftingCalcARK";
//
//                String title = "New User Bug Report";
//                String content = "Input taken from dialog.";
//
//                String status = "new";
//                String priority = "trivial";
//
//                String kind = "bug";
//
//                JSONObject issue = new JSONObject();
//                issue.put( "status", status );
//                issue.put( "priority", priority );
//                issue.put( "title", title );
//
//                JSONObject metadata = new JSONObject();
//                metadata.put( "kind", kind );
//
//                issue.put( "metadata", metadata );
//                issue.put( "content", content );
//
//                Uri uri = Uri.parse( bitbucket ).buildUpon()
//                        .appendPath( accountName )
//                        .appendPath( repoSlug )
//                        .appendPath( "issues" ).build();
//
//                URL url = new URL( uri.toString() );
//
//                HttpURLConnection http = new HttpURLConnection( url ) {
//                    @Override
//                    public void disconnect() {
//                        Log.d( TAG, "connected" );
//                    }
//
//                    @Override
//                    public boolean usingProxy() {
//                        return false;
//                    }
//
//                    @Override
//                    public void connect() throws IOException {
//                        Log.d( TAG, getResponseCode() + ": " + getResponseMessage() );
//                    }
//                };
//
////                HttpURLConnection httpURLConnection = ( HttpURLConnection ) url.openConnection();
////                http.setDoOutput( true );
//                http.setRequestMethod( "POST" ); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
//                http.setRequestProperty( "Content-Type", "application/json" ); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
//                http.connect();
//
//                DataOutputStream wr = new DataOutputStream( http.getOutputStream() );
//                wr.writeBytes( issue.toString() );
//                wr.flush();
//                wr.close();
//            } catch ( Exception e ) {
//                Log.e( TAG, "bitbucket issue creation", e );
//            }

            return null;
        }
    }
}
