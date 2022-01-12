package dem.corp.physicsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recView;
    private FloatingActionButton create_formula;
    private ArrayList<Formula> list=new ArrayList<>();
    private ImageButton close;
    private EditText formula_name;
    private EditText formula_txt;
    private Button create;
    private BottomSheetDialog bottomSheetDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recView = findViewById(R.id.recView);
        create_formula=findViewById(R.id.create_formula);
        loadData();

        create_formula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog();
            }
        });

    }

    private void showBottomSheetDialog() {

        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.create_formula);

        close = bottomSheetDialog.findViewById(R.id.create_formula_dialog_close_btn);
        formula_name = bottomSheetDialog.findViewById(R.id.name_formula_txt);
        formula_txt = bottomSheetDialog.findViewById(R.id.formula_txt);
        create = bottomSheetDialog.findViewById(R.id.create_formula_btn);
        bottomSheetDialog.show();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = formula_name.getText().toString();
                String formula = formula_txt.getText().toString();
                list.add(0, new Formula(name, formula));
                saveData();
                bottomSheetDialog.dismiss();
                setRecyclerView();
            }
        });
    }

    private void setRecyclerView(){
        FormulaAdapter adapter = new FormulaAdapter(list);
        recView.setLayoutManager(new LinearLayoutManager(this));
        recView.setAdapter(adapter);
    }

    private void fillRecView(){
        list.add(new Formula("Вычисление количества теплоты", "Q=c*m*(t-tн), Дж"));
        list.add(new Formula("Удельная теплоемкость вещества", "C=Q/(m*(t-t0), Дж/(кг*град)"));
        list.add(new Formula("Теплоемкость вещества", "C=m*c, Дж/(кг*град)"));
        list.add(new Formula("Закон Кулона", "F=k*(q1*q2)/r^2, k=9*10^9, Н"));
        list.add(new Formula("Сила тока", "I=q/t, A"));
        list.add(new Formula("Напряжение", "U=A/q, B"));
        list.add(new Formula("Сопротивление проводника", "R=p*l/S, Ом"));
        list.add(new Formula("Закон Ома", "I=U/R, A"));
        list.add(new Formula("Мощность Тока", "P=A/t=U*I, Вт"));
        list.add(new Formula("Работа тока", "A=U*q=U*I*t, Дж"));
        list.add(new Formula("Формула скорости движ тела", "V=S/t, м/с"));
        list.add(new Formula("Второй закон Ньютона", "a=F/m, м/с^2"));
        list.add(new Formula("Гравитационная постоянная", "G=6,67*10^(-11), Н*м^2/кг^2"));
        list.add(new Formula("Расчет центростремительного ускорения", "a=v^2/r, м/с^2"));
        setRecyclerView();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("formules", null);
        Type type = new TypeToken<ArrayList<Formula>>() {}.getType();
        list = gson.fromJson(json, type);
        if (list == null) {
            list=new ArrayList<>();
            fillRecView();
        }
        setRecyclerView();
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("formules", json);
        editor.apply();
    }

}