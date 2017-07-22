package guoer.lf.ed.guoer.items;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import guoer.lf.ed.guoer.BuildConfig;
import guoer.lf.ed.guoer.FruitActivity;
import guoer.lf.ed.guoer.R;
import guoer.lf.ed.guoer.utils.NetworkUtils;

public class FruitAdpater extends RecyclerView.Adapter<FruitAdpater.ViewHolder> {

    private Context mContext;

    private List<Fruit> mFruitList;

    public FruitAdpater(List<Fruit> fruits) {
        mFruitList = fruits;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.items_item, parent, false);
        final ViewHolder holder;
        holder = new ViewHolder(view);

        holder.fruitName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.fruitName.getText().equals("Add")) {
                    AddData();
                    FruitAdpater.this.notifyDataSetChanged();
                    return;
                }
                if (NetworkUtils.isNetWorkAvailable(mContext)) {

                    int position = holder.getAdapterPosition();
                    Fruit fruit = mFruitList.get(position);
                    FruitActivity.actionIntent(mContext, fruit.getFruitName(), fruit.getFruitImageId());
                } else {
                    Toast.makeText(mContext, "Network unavailable! Please check network.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.fruitImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.fruitName.getText().equals("Add")) {
                    AddData();
                    FruitAdpater.this.notifyDataSetChanged();
                    return;
                }
                if (NetworkUtils.isNetWorkAvailable(mContext)) {

                    int position = holder.getAdapterPosition();
                    Fruit fruit = mFruitList.get(position);
                    FruitActivity.actionIntent(mContext, fruit.getFruitName(), fruit.getFruitImageId());
                } else {
                    Toast.makeText(mContext, "Network unavailable! Please check network.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Fruit fruit = mFruitList.get(position);
        holder.fruitName.setText(fruit.getFruitName());
//        holder.fruitImage.setImageResource(fruit.getFruitImageId());
        Glide.with(mContext).load(fruit.getFruitImageId()).into(holder.fruitImage);

    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView fruitName;
        ImageView fruitImage;

        View fruitView;

        ViewHolder(View itemView) {
            super(itemView);
            fruitView = itemView;
            cardView = (CardView) itemView;
            fruitName = (TextView) itemView.findViewById(R.id.item_name);
            fruitImage = (ImageView) itemView.findViewById(R.id.item_image);
        }
    }
    //Test data method
    private void AddData() {
        Fruit[] fruits = {
                new Fruit("apple", R.drawable.apple),
                new Fruit("orange", R.drawable.orange),
                new Fruit("lemon", R.drawable.lemon),
        };
//            mFruitList.clear();
//            Fruit addFruit = new Fruit("Add", android.R.drawable.ic_input_add);
//            mFruitList.add(addFruit);
//        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            mFruitList.add(fruits[index]);
//        }
    }
}
