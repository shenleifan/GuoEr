package guoer.lf.ed.guoer.items;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import guoer.lf.ed.guoer.R;

public class FruitAdpater extends RecyclerView.Adapter<FruitAdpater.ViewHolder>{

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

        return new ViewHolder(view);
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

        ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            fruitName = (TextView) itemView.findViewById(R.id.item_name);
            fruitImage = (ImageView) itemView.findViewById(R.id.item_image);
        }
    }
}
