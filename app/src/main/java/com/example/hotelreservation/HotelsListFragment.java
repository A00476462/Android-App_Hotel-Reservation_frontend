package com.example.hotelreservation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HotelsListFragment extends Fragment implements ItemClickListener {
    View view;
    TextView headingTextView, checkInOutDateTextView, guestNumberTextView;
    ProgressBar progressBar;
    List<HotelListData> userListResponseData;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.hotel_list_fragment, container, false);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        headingTextView = view.findViewById(R.id.hotel_list);
        progressBar = view.findViewById(R.id.progress_bar);
        checkInOutDateTextView = view.findViewById(R.id.hotel_list_check_in_out_date);
        guestNumberTextView = view.findViewById(R.id.hotel_list_guest_number_text_view);

        String checkInDate = getArguments().getString("check in date");
        String checkOutDate = getArguments().getString("check out date");
        String numberOfGuests = getArguments().getString("number of guests");

        headingTextView.setText("Welcome user🙂");
        checkInOutDateTextView.setText("Guest number: " + numberOfGuests);
        guestNumberTextView.setText("Checkin:   " + checkInDate + "\nCheckout: " + checkOutDate);

        getHotelsListData();
    }
    private void getHotelsListData(){
        progressBar.setVisibility(View.VISIBLE);

        ApiInterface apiInterface = Api.getClient(); //ApiInterface类型的对象
        Call<List<HotelListData>> call = apiInterface.getHotelsLists();

        call.enqueue(new Callback<List<HotelListData>>() {
            @Override
            public void onResponse(Call<List<HotelListData>> call, Response<List<HotelListData>> response) {
                progressBar.setVisibility(View.GONE);
                Log.e("HotelListFragmentSize", String.valueOf(response.body().size()));

                if (response.isSuccessful()) {
                    userListResponseData = response.body();
                    Log.e("tttttAvailability", response.body().get(0).getAvailability());
                    setupRecyclerView();
                } else {
                    Toast.makeText(getActivity(), "Failed to fetch hotel list", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<HotelListData>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Failed to fetch hotel list2: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("onFailureTAG", "Failed to fetch hotel list2: " + t.getMessage());
            }
        });
        //Log.d("@@@行：", "getHotelsListData已执行完毕");
    }

    private void setupRecyclerView(){
        progressBar.setVisibility(View.GONE);
        RecyclerView recyclerView = view.findViewById(R.id.hotel_list_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        HotelListAdapter hotelListAdapter = new HotelListAdapter(getActivity(), userListResponseData);
        recyclerView.setAdapter(hotelListAdapter);

        hotelListAdapter.setClickListener(this);
    }
    @Override
    public void onClick(View view, int position) {
        HotelListData hotelListData = userListResponseData.get(position);

        String hotelName = hotelListData.getHotel_name();
        String price = hotelListData.getPrice();
        String availability = hotelListData.getAvailability();
        //String checkInDate = hotelListData.getAvailability();
        String checkInDate = getArguments().getString("check in date");
        String checkOutDate = getArguments().getString("check out date");
        String numberOfGuests = getArguments().getString("number of guests");

        Bundle bundle = new Bundle();
        bundle.putString("hotel name", hotelName);
        bundle.putString("hotel price", price);
        bundle.putString("hotel availability", availability);
        bundle.putString("check in date", checkInDate);
        bundle.putString("check out date", checkOutDate);
        bundle.putString("number of guests", numberOfGuests);

        HotelGuestDetailsFragment hotelGuestDetailsFragment = new HotelGuestDetailsFragment();
        hotelGuestDetailsFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.remove(HotelsListFragment.this);
        fragmentTransaction.replace(R.id.main_layout, hotelGuestDetailsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }
}
