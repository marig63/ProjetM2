package guillaume.marisa.projetm2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FirstActivity extends Activity {

    EditText userName;
    String oldName;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        userName = (EditText) findViewById(R.id.editUserName);

        userName.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_GO){
                    checkName();
                }
                return false;
            }
        });


        sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        String key = getResources().getString(R.string.user_name_key);
        oldName = sharedPref.getString(getString(R.string.user_name), key);

        if(oldName != null){
            if(!oldName.matches("")){
                userName.setText(oldName, TextView.BufferType.EDITABLE);
            }
        }



    }

    public void onClickFirstActivity(View v){
       checkName();
    }

    public void checkName(){
        String name = userName.getText().toString();
        if(name != null && !name.matches("")){
            System.out.println(" -----------------------999999999999999999999999999999999999"+ name);



            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(getString(R.string.user_name), name);
            editor.commit();




            Intent intent = new Intent(FirstActivity.this, MainActivity.class);
            Bundle b = new Bundle();
            b.putString("name", name); //Your id
            intent.putExtras(b); //Put your id to your next Intent


            startActivity(intent);
            finish();
        }
        else{
            //Toast
            Toast.makeText(this,"Nom vide",Toast.LENGTH_LONG).show();
        }
    }

}
