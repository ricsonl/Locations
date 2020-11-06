package android.teste.locations;

import android.app.ListActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
        super.onListItemClick(l, v, position, id);

        if(position != l.getAdapter().getCount()-1){// caso NAO seja a última opção, "Fechar aplicação"

            String aux = l.getItemAtPosition(position).toString();// nome do item clicado
            Bundle params = new Bundle();
            params.putString("toastMsg", aux);// mensagem que irá aparecer no toast

            switch (position){
                case 0:
                    params.putString("local", "ita");
                    break;
                case 1:
                    params.putString("local", "vic");
                    break;
                case 2:
                    params.putString("local", "dpi");
                    break;
            }
            Intent it = new Intent(getBaseContext(), MapActivity.class);
            it.putExtras(params);
            startActivity(it);
        } else {
            finishAffinity();
        }
    }
}