package baghcal.com.bagchal;

import android.app.Activity;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.view.View.OnClickListener;
        import android.content.Intent;

public class MainActivity extends Activity {
    Button ComAIbutton;
    Button PPbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnButton();

    }

    public void addListenerOnButton() {

        ComAIbutton = (Button) findViewById(R.id.ComAIButton);
        ComAIbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,ChooseDifficulty.class);
                startActivity(intent);
//                BaghChalState.GoatsLeft = 8;
//                setContentView(R.layout.choose_difficulty);
            }
        });


        PPbutton = (Button) findViewById(R.id.PPButton);
        PPbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.baghchal_view);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}