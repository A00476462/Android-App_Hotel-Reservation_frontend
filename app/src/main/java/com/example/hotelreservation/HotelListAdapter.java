package com.example.hotelreservation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import androidx.annotation.NonNull;

public class HotelListAdapter extends RecyclerView.Adapter<HotelListAdapter.ViewHolder> {
    private List<HotelListData> hotelListData;
    private LayoutInflater layoutInflater;
    private ItemClickListener clickListener;

    HotelListAdapter(Context context, List<HotelListData> hotelListData){
        this.layoutInflater = LayoutInflater.from(context);
        this.hotelListData = hotelListData;
    }

    @NonNull
    @Override
    public HotelListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.hotel_list_layout, parent,false);
        return new ViewHolder(view);
    }

    //The Adapter will fetch hotel information from the data source based on the list item's position (Adapter将会根据列表项的位置，从数据源获取对应的酒店信息)
    @Override
    public void onBindViewHolder(@NonNull HotelListAdapter.ViewHolder holder, int position) {
        String hotelName = hotelListData.get(position).getHotel_name();
        String hotelPrice = hotelListData.get(position).getPrice();
        String hotelAvailability = hotelListData.get(position).getAvailability();

        holder.hotelName.setText(hotelName);
        holder.hotelAvailability.setText(hotelAvailability);
        holder.hotelPrice.setText(hotelPrice);
    }

    //Returns the quantity in the data source, telling the recyclerView how much data to display (返回数据源中的数量，告诉recyclerView有多少数据要显示)
    @Override
    public int getItemCount() {
        if (hotelListData != null){
            return hotelListData.size();
        }else {
            return 0;
        }
    }
    public void setClickListener(ItemClickListener itemClickListener){
        this.clickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView hotelName, hotelPrice, hotelAvailability;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hotelName = itemView.findViewById(R.id.hotel_name_text_view);
            hotelPrice = itemView.findViewById(R.id.price_text_view);
            hotelAvailability = itemView.findViewById(R.id.availability_text_view);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.onClick(view, getAbsoluteAdapterPosition());
            }
        }
    }
}
