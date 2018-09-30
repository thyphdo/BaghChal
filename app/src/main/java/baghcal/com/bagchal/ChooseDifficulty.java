package baghcal.com.bagchal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.content.Context;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.ViewGroup;


/**
 * Created by ThyDo on 12/5/16.
 */

public class ChooseDifficulty extends Activity {
    Button easyButton, mediumButton, hardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_difficulty);
        addListenerOnButton();

    }

    public void addListenerOnButton() {

        easyButton = (Button) findViewById(R.id.easyButton);
        easyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                BaghChalState.GoatsLeft = 20;
                BaghChalState.GoatsLeftStore = 20;
                setContentView(R.layout.baghchalai_view);
//                Intent intent= new Intent(ChooseDifficulty.this,BaghChalAIView.class);
//                startActivity(intent);
            }
        });

        mediumButton = (Button) findViewById(R.id.mediumButton);
        mediumButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                BaghChalState.GoatsLeft = 18;
                BaghChalState.GoatsLeftStore = 18;

                setContentView(R.layout.baghchalai_view);
            }
        });

        hardButton = (Button) findViewById(R.id.hardButton);
        hardButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                BaghChalState.GoatsLeft = 16;
                BaghChalState.GoatsLeftStore = 16;

                setContentView(R.layout.baghchalai_view);
            }
        });

    }

}