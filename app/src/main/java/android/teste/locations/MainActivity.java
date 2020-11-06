package android.teste.locations;

import android.app.ListActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String menu [] = new String [] {"Minha casa na cidade natal", "Minha casa em Viçosa", "Meu departamento", "Fechar aplicação"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu);
        setListAdapter(arrayAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){
        if(position != l.getAdapter().getCount()-1){//Caso não seja a última opção, "Fechar aplicação"
            String aux = l.getItemAtPosition(position).toString();

            Toast t = Toast.makeText(getBaseContext(), aux,Toast.LENGTH_SHORT);
            TextView tv = (TextView) t.getView().findViewById(android.R.id.message);
            tv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            tv.setTextColor(getResources().getColor(R.color.colorBackground));
            t.show();
        } else {
            finish();
        }
    }
}