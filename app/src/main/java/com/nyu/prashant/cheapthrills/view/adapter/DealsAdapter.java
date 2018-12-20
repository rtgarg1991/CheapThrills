package com.nyu.prashant.cheapthrills.view.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nyu.prashant.cheapthrills.R;
import com.nyu.prashant.cheapthrills.model.Deal;
import com.nyu.prashant.cheapthrills.model.DealList;
import com.nyu.prashant.cheapthrills.model.MainDeal;
import com.nyu.prashant.cheapthrills.view.activity.DealActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DealsAdapter extends RecyclerView.Adapter<DealsAdapter.ViewHolder> {
    List<MainDeal> deals;

    public void setData(DealList dealList) {
        if (this.deals == null) {
            this.deals = new ArrayList<>();
        }
        this.deals.clear();

        if (dealList.getDeals() != null) {
            for (Deal deal : dealList.getDeals()) {
                if (deal.getDeal() != null) {
                    this.deals.add(deal.getDeal());
                }
            }
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                viewGroup.getContext());
        View v = inflater.inflate(R.layout.row_offer, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        MainDeal mainDeal = this.deals.get(i);

        if (mainDeal.getDiscountPercentage() != null) {
            viewHolder.discount.setText(String.format("%.2f", mainDeal.getDiscountPercentage() * 100) + " %");
        } else {
            viewHolder.discount.setText("N/A");
        }


        if (mainDeal.getTitle() != null) {
            viewHolder.title.setText(mainDeal.getTitle());
        } else {
            viewHolder.title.setText("N/A");
        }
        if (mainDeal.getDescription() != null) {
            viewHolder.description.setText(mainDeal.getDescription());
        } else {
            viewHolder.description.setText("N/A");
        }


        if (!TextUtils.isEmpty(mainDeal.getImageUrl())) {
            viewHolder.imageView.setVisibility(View.VISIBLE);
            Picasso.get().load(mainDeal.getImageUrl()).into(viewHolder.imageView);
        } else {
            viewHolder.imageView.setVisibility(View.GONE);
        }

        viewHolder.setDealId(mainDeal.getId());

    }

    @Override
    public int getItemCount() {
        if (this.deals == null) {
            return 0;
        }
        return this.deals.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView title;
        private TextView discount;
        private TextView description;
        private ImageView imageView;
        private View layout;
        private Long dealId;

        ViewHolder(View v) {
            super(v);
            layout = v;
            title = v.findViewById(R.id.title);
            description = v.findViewById(R.id.description);
            discount = v.findViewById(R.id.discount);
            imageView = v.findViewById(R.id.image);


            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dealId != null) {
                        Intent intent = new Intent(layout.getContext(), DealActivity.class);
                        intent.putExtra("deal_id", dealId);
                        layout.getContext().startActivity(intent);
                    }
                }
            });
        }

        void setDealId(Long dealId) {
            this.dealId = dealId;
        }
    }
}
