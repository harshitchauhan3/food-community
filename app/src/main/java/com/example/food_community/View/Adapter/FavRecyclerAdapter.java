package com.example.food_community.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.food_community.Models.Meals;
import com.example.food_community.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavRecyclerAdapter extends RecyclerView.Adapter<FavRecyclerAdapter.favViewHolder> {

    private List<Meals.Meal> meals;
    private Context context;
    private static ClickListener clickListener;

    public FavRecyclerAdapter(Context context, List<com.example.food_community.Models.Meals.Meal> meals) {
        this.meals = meals;
        this.context = context;
    }

    @NotNull
    @Override
    public FavRecyclerAdapter.favViewHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fav_meal,
                viewGroup, false);
        return new favViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull FavRecyclerAdapter.favViewHolder viewHolder, int i) {

        String strMealThumb = meals.get(i).getStrMealThumb();
        Picasso.get().load(strMealThumb).placeholder(R.drawable.shadow_bottom_to_top).into(viewHolder.favMealThumb);

        String strMealName = meals.get(i).getStrMeal();
        viewHolder.favMealName.setText(strMealName);
    }


    @Override
    public int getItemCount() {
        return meals.size();
    }

    static class favViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.favMealThumb)
        ImageView favMealThumb;
        @BindView(R.id.favMealName)
        TextView favMealName;
        favViewHolder(@NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getBindingAdapterPosition());
        }
    }


    public void setOnItemClickListener(ClickListener clickListener) {
        FavRecyclerAdapter.clickListener = clickListener;
    }


    public interface ClickListener {
        void onClick(View view, int position);
    }
}


