package dem.corp.physicsapp;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class FormulaAdapter extends RecyclerView.Adapter<FormulaAdapter.ViewHolder>{

    ArrayList<Formula> list;

    public FormulaAdapter(ArrayList<Formula> list){
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.getName().setText(list.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(holder.itemView.getContext());
                bottomSheetDialog.setContentView(R.layout.formula_bottom_sheet);

                ImageButton close = bottomSheetDialog.findViewById(R.id.create_formula_dialog_close_btn);
                TextView formula_name = bottomSheetDialog.findViewById(R.id.formula_name);
                TextView formula_txt = bottomSheetDialog.findViewById(R.id.formula_txt);
                formula_name.setText(list.get(position).getName());
                formula_txt.setText(list.get(position).getFormula());
                bottomSheetDialog.show();
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;

        public ViewHolder(View view) {
            super(view);
            this.name = view.findViewById(R.id.formula_txt);
        }

        public TextView getName() {
            return name;
        }
    }
}
