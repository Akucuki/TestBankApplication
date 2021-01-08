package com.pirkovitaliysoft.testbankapplication.rvHandlers;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.pirkovitaliysoft.testbankapplication.R;
import com.pirkovitaliysoft.testbankapplication.retrofit.PbEntity;

import java.util.List;

public class RVpbAdapter extends RecyclerView.Adapter<RVpbAdapter.PBviewHolder>{
    private List<PbEntity> entities;
    private int rowStyleColor;

    private OnItemClickListener listener;

    public RVpbAdapter(int rowHighlightColor){
        this.rowStyleColor = rowHighlightColor;
    }

    public void setEntities(List<PbEntity> entities) {
        this.entities = entities;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public PBviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pb_item, parent, false);
        PBviewHolder viewHolder = new PBviewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PBviewHolder holder, int position) {
        PbEntity entity = entities.get(position);
        if(position % 2 == 0)
            holder.getContainer().setBackgroundColor(rowStyleColor);
        else
            holder.getContainer().setBackgroundColor(Color.WHITE);

        holder.getVaultTextView().setText(entity.getCurrency() == null ? entity.getBaseCurrency() : entity.getCurrency());
        holder.getPurchaseTextView().setText(String.valueOf(entity.getPurchaseRate() == 0.0 ? entity.getPurchaseRateNB() : entity.getPurchaseRate()));
        holder.getSellingTextView().setText(String.valueOf(entity.getSaleRate() == 0.0 ? entity.getSaleRateNB() : entity.getSaleRate()));
    }

    @Override
    public int getItemCount() {
        if(entities == null)
            return 0;
        return entities.size();
    }

    public class PBviewHolder extends RecyclerView.ViewHolder{
        private ConstraintLayout container;
        private TextView vaultTextView, purchaseTextView, sellingTextView;

        public PBviewHolder(@NonNull View itemView) {
            super(itemView);

            container = (ConstraintLayout) itemView.findViewById(R.id.pb_item_container);
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            vaultTextView = (TextView) itemView.findViewById(R.id.vault_caption);
            purchaseTextView = (TextView) itemView.findViewById(R.id.purchase_caption);
            sellingTextView = (TextView) itemView.findViewById(R.id.selling_caption);
        }

        public ConstraintLayout getContainer() {
            return container;
        }

        public TextView getVaultTextView() {
            return vaultTextView;
        }

        public TextView getPurchaseTextView() {
            return purchaseTextView;
        }

        public TextView getSellingTextView() {
            return sellingTextView;
        }
    }
}
