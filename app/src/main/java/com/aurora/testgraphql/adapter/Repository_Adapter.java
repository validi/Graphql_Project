package com.aurora.testgraphql.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.aurora.testgraphql.R;
import com.aurora.testgraphql.databinding.RowRepositoryBinding;
import com.example.GetUserRepositoryQuery;

import java.util.ArrayList;
import java.util.List;


public class Repository_Adapter extends RecyclerView.Adapter<Repository_Adapter.ViewHolder> {
    List<GetUserRepositoryQuery.Node> requestItems = new ArrayList<>();
    //  private Context context;
    Clik_Item clik_item;
    String state;

    public Repository_Adapter(Clik_Item clik_item) {
        //  this.context = context;
        // this.requestItems = requestItems;
        this.clik_item = clik_item;

    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RowRepositoryBinding binding;

        public ViewHolder(RowRepositoryBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;


        }

        @Override
        public void onClick(View v) {


        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RowRepositoryBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.row_repository, parent, false);

        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        GetUserRepositoryQuery.Node item = requestItems.get(position);


        holder.binding.txtName.setText(item.name());
        holder.binding.txtIsPublic.setText(item.isPrivate() ? "Private" : "Public");
        holder.binding.txtUpdateAt.setText(item.updatedAt().toString().substring(0,10));
        holder.binding.txtDescription.setText(item.description());
        holder.binding.imgArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewGroup.LayoutParams layoutParams = holder.binding.layDescription.getLayoutParams();

                if(layoutParams.height==LinearLayout.LayoutParams.WRAP_CONTENT)
                {
                    layoutParams.height= 0;
                    holder.binding.imgArrow.setRotation(0);
                }else {
                    layoutParams.height= LinearLayout.LayoutParams.WRAP_CONTENT;
                    holder.binding.imgArrow.setRotation(180);
                }


                holder.binding.layDescription.setLayoutParams(layoutParams);

            }
        });

        holder.binding.lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clik_item.Onrecived(item);
            }
        });

    }

    public void setItems(List<GetUserRepositoryQuery.Node> items) {
        this.requestItems = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return requestItems.size();

    }

    public interface Clik_Item {
        void Onrecived(GetUserRepositoryQuery.Node infoResult);
    }


}
