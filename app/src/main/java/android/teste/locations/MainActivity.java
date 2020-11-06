package android.teste.locations;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String menu [] = new String [] {"Minha casa na cidade natal", "Minha casa em Viçosa", "Meu departamento", "Fechar aplicação"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu);
        setListAdapter(arrayAdapter);
    }
}