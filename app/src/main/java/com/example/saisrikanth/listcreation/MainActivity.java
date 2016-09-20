package com.example.saisrikanth.listcreation;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ListView songView;
    String[] song; //Song Name
    String[] artist; //Artiest Name
    Integer[] imgId={
                     R.drawable.ss01,R.drawable.ss02,R.drawable.ss03,R.drawable.ss04,R.drawable.ss05,R.drawable.ss06
                    };// images in drawable. Each one is associated with each list item
    String[] songUrl; //URL of Song
    String[] songWiki; // Wikipedia link of Song
    String[] artistWiki; //Wikipedia link of Artist
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        song=getResources().getStringArray(R.array.title);
        artist=getResources().getStringArray(R.array.artist);
        songUrl=getResources().getStringArray(R.array.song);
        songWiki=getResources().getStringArray(R.array.songPage);
        artistWiki=getResources().getStringArray(R.array.artistPage);
        MyListAdapter adapter=new MyListAdapter(this,song,artist,imgId);
        songView=(ListView)findViewById(R.id.listView);
        registerForContextMenu(songView);   //Registering for the Context Menu
        songView.setLongClickable(true);   //Long Click is Enabled
        songView.setAdapter(adapter);
        songView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(songUrl[position]));
                startActivity(i);
            }
        });
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.contextualmenu, menu); //Inflating the Context Menu
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Intent i = new Intent(Intent.ACTION_VIEW);
        switch(item.getItemId()) {
            case R.id.id_youtube:   //Gets Youtube links based on position
                i.setData(Uri.parse(songUrl[info.position]));
                startActivity(i);
                return true;
            case R.id.id_wiki:      //Gets Wikipedia links of songs based on position
                i.setData(Uri.parse(songWiki[info.position]));
                startActivity(i);
                return true;
            case R.id.id_wiki2:     //Gets Wikipedia links of Artists based on position
                i.setData(Uri.parse(artistWiki[info.position]));
                startActivity(i);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private class MyListAdapter extends ArrayAdapter<String>{
        private final Activity context;
        private final String[] song;
        private final String[] artist;
        private final Integer[] imgId;
                //List Adapter for loading the details into List
        public MyListAdapter(Activity context, String[] song, String[] artist, Integer[] imgId) {
            super(context,R.layout.item_list,artist);
            this.context = context;
            this.artist=artist;
            this.song = song;
            this.imgId = imgId;
        }

        @Override
        public View getView(int position, View convertView,ViewGroup parent){
    // Gets the List from item_list
            View itemView=convertView;
            if (itemView==null)
            {
                itemView=getLayoutInflater().inflate(R.layout.item_list,parent,false);
            }
            ImageView imageView=(ImageView)itemView.findViewById(R.id.item_Icon);
            imageView.setImageResource(imgId[position]);

            TextView titleText=(TextView) itemView.findViewById(R.id.item_Title);
            titleText.setText(song[position]);

            TextView artistText=(TextView) itemView.findViewById(R.id.item_Artist);
            artistText.setText(artist[position]);
            return itemView;
        }
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
