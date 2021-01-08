package com.pirkovitaliysoft.testbankapplication.rvHandlers;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.pirkovitaliysoft.testbankapplication.R;
import com.pirkovitaliysoft.testbankapplication.retrofit.NbuEntity;

import java.util.List;

public class RVnbuAdapter extends RecyclerView.Adapter<RVnbuAdapter.NbuViewHolder>{
    private List<NbuEntity> entities;
    private int rowStyleColor;

    public RVnbuAdapter(int rowStyleColor){
        this.rowStyleColor = rowStyleColor;
    }

    public void setEntities(List<NbuEntity> entities){
        this.entities = entities;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NbuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nbu_item, parent, false);
        NbuViewHolder viewHolder = new NbuViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NbuViewHolder holder, int position) {
        if(position % 2 == 0)
            holder.getContainer().setBackgroundColor(rowStyleColor);
        else
            holder.getContainer().setBackgroundColor(Color.WHITE);

        NbuEntity current = entities.get(position);
        holder.getVaultTextView().setText(current.getCurrencyCodeL());
        holder.getAmountTextView().setText(String.valueOf(current.getAmount()));
        holder.getUnitsTextView().setText(String.valueOf(current.getUnits()));
    }

    @Override
    public int getItemCount() {
        if(entities == null)
            return 0;
        return entities.size();
    }

    public class NbuViewHolder extends RecyclerView.ViewHolder{
        private ConstraintLayout container;

        private TextView vaultTextView;
        private TextView amountTextView;
        private TextView unitsTextView;

        public NbuViewHolder(@NonNull View itemView) {
            super(itemView);

            container = (ConstraintLayout) itemView.findViewById(R.id.nbu_item_container);

            vaultTextView = (TextView) itemView.findViewById(R.id.vault_caption);
            amountTextView = (TextView) itemView.findViewById(R.id.amount_caption);
            unitsTextView = (TextView) itemView.findViewById(R.id.units_caption);
        }

        public ConstraintLayout getContainer() {
            return container;
        }

        public TextView getVaultTextView() {
            return vaultTextView;
        }

        public TextView getAmountTextView() {
            return amountTextView;
        }

        public TextView getUnitsTextView() {
            return unitsTextView;
        }
    }
}
