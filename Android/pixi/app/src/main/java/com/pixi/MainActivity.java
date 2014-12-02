package com.pixi;


import android.app.Activity;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
//import android.util.Log;

import com.couchbase.lite.*;
import com.couchbase.lite.android.AndroidContext;
import com.couchbase.lite.util.Log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;




public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String TAG = "HelloWorld";


        Manager manager;
        try{
            manager = new Manager(new AndroidContext(this), Manager.DEFAULT_OPTIONS);
            Log.d(TAG, "Manager created");
        } catch (IOException e){
            Log.e(TAG, "Error:  Unable to create manager object");
            return;
        }
        //Name for database
        String databaseName = "postcards";
        if(!Manager.isValidDatabaseName(databaseName)){
            Log.e(TAG, "Error:  Invalid Database Name");
            return;
        }
        //Create a new database
        Database database;
        try{
            database = manager.getDatabase(databaseName);
            Log.d(TAG, "Database created");
        } catch (CouchbaseLiteException e) {
            Log.e (TAG, "Error: Unable to create or get database");
            return;
        }
        //Get current date and time
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Calendar calendar = GregorianCalendar.getInstance();
        String currentTimeString = dateFormatter.format(calendar.getTime());

        // create an object that contains data for a document
        Map<String, Object> docContent = new HashMap<String, Object>();
        docContent.put("message", "Hello Couchbase Lite");
        docContent.put("creationDate", currentTimeString);

        // display the data for the new document
        Log.d(TAG, "docContent=" + String.valueOf(docContent));

        // create an empty document
        Document document = database.createDocument();

        // add content to document and write the document to the database
        try {
            document.putProperties(docContent);
            Log.d (TAG, "Document written to database named: " + databaseName + " with ID: " + document.getId());
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Error:  Cannot write document to database", e);
        }

        // save the ID of the new document
        String docID = document.getId();

        // retrieve the document from the database
        Document retrievedDocument = database.getDocument(docID);
        // display the retrieved document
        Log.d(TAG, "retrievedDocument: " + String.valueOf(retrievedDocument.getProperties()));
        Log.d(TAG, "retrievedDocument ID: " + retrievedDocument.getId());
        Log.d(TAG, "retrievedDocument Revision: " + retrievedDocument.getCurrentRevisionId());
        Log.d(TAG, "Document Revision: " + document.getCurrentRevisionId());

        // update the document
        Map<String, Object> updatedProperties = new HashMap<String, Object>();
        updatedProperties.putAll(retrievedDocument.getProperties());
        updatedProperties.put ("message", "We're having a heat wave!");
        updatedProperties.put ("temperature", "95");
        try {
            retrievedDocument.putProperties(updatedProperties);
            Log.d(TAG, "updated retrievedDocument: " + String.valueOf(retrievedDocument.getProperties()));
        } catch (CouchbaseLiteException e) {
            Log.e (TAG, "Cannot update document", e);
        }

        // delete the document
        try {
            String retrievedDocID = retrievedDocument.getId();
            retrievedDocument.delete();
            Log.d (TAG, "Deleted document: " + retrievedDocID + " , Deletion Status: " + retrievedDocument.isDeleted());
            Log.d(TAG, "DocumentID: " + document.getId());
        } catch (CouchbaseLiteException e) {
            Log.e (TAG, "Cannot delete document", e);
        }

        Log.d(TAG, "Beginning of: Pixi App");
        Log.d(TAG, "End of:  Pixi App");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
