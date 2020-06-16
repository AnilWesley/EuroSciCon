package com.applications.euroscicon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.applications.euroscicon.R;
import com.applications.euroscicon.models.ConferenceBanner;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class BannerAdapter extends SliderViewAdapter<BannerAdapter.SliderAdapterVH> {

    private Context context;
    private List<ConferenceBanner.ConferenceBean> dataBeans;

    public BannerAdapter(Context context, List<ConferenceBanner.ConferenceBean> dataBeans) {
        this.context = context;
        this.dataBeans = dataBeans;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {
        viewHolder.textViewDescription.setText("");

        Glide.with(viewHolder.itemView)
                .load(dataBeans.get(position).getSlider_url())
                .apply(new RequestOptions().placeholder(R.drawable.homepage).error(R.drawable.homepage))
                .into(viewHolder.imageViewBackground);



    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size

        return Math.min(dataBeans.size(), 3);


    }

    static class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        TextView textViewDescription;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView = itemView;
        }
    }
}
