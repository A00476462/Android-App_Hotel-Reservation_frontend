package com.example.hotelreservation;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.widget.RadioGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//import retrofit2.Call;

public class HotelGuestDetailsFragment extends Fragment {
    View view;
    Button reserveButton;
    Integer confirmationId;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.hotel_guest_details_fragment, container,false);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        TextView hotelRecapTextView = view.findViewById(R.id.hotel_recap_text_view);

        String hotelName = getArguments().getString("hotel name");
        String hotelPrice = getArguments().getString("hotel price");
        String hotelAvailability = getArguments().getString("hotel availability");
        String checkInDate = getArguments().getString("check in date");
        String checkOutDate = getArguments().getString("check out date");
        String numberOfGuests = getArguments().getString("number of guests");

        hotelRecapTextView.setText(
                "Hotel Name:       " + hotelName
                + "\nHotel Price:        $" + hotelPrice
                + "\nAvailability:        " + hotelAvailability
                + "\nCheck-in date:   " + checkInDate
                + "\nCheck-out date: " + checkOutDate
                + "\nGuest number:   " + numberOfGuests);

        //input Guest information: name, email, phone number and gender
        reserveButton = view.findViewById(R.id.reserve_button);
        reserveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //get hotel and guest info
                String hotelName = getArguments().getString("hotel name");
                String checkInDate = getArguments().getString("check in date");
                String checkOutDate = getArguments().getString("check out date");
                List<GuestData> guestsList = new ArrayList<>();

                // Check whether the name, phone number, and email are filled in and add them to guestsList (检查姓名、电话、邮件是否填写，并添加到 guestsList 中)
                if (!isAllEditTextField()) {
                    // If any fields are not filled in, no subsequent operations will be performed. (如果有字段未填写，则不执行后续操作)
                    return;
                }

                // Loop through each subcontainer (循环遍历每一个子container)
                ViewGroup container = (ViewGroup) view.findViewById(R.id.input_fields_container);
                for(int i = 0; i < container.getChildCount(); i++){
                    View guestInputView = container.getChildAt(i);
                    EditText guestNameEditText = guestInputView.findViewById(R.id.guest_name_edit_text);
                    EditText phoneNumberEditText = guestInputView.findViewById(R.id.phone_number_edit_text);
                    EditText emailEditText = guestInputView.findViewById(R.id.email_edit_text);
                    RadioGroup genderRadioGroup = guestInputView.findViewById(R.id.gender_radio_group);

                    String guestName = guestNameEditText.getText().toString();
                    String phoneNumber = phoneNumberEditText.getText().toString();
                    String email = emailEditText.getText().toString();
                    String gender = ((RadioButton) guestInputView.findViewById(genderRadioGroup.getCheckedRadioButtonId())).getText().toString();

                    // Create a GuestData object and add it to guestsList (创建 GuestData 对象并添加到 guestsList 中)
                    GuestData guestData = new GuestData(guestName,gender,phoneNumber,email);
                    guestsList.add(guestData);
                }
                //Log.d("hotelName222", "onClick: " +guestsList);
                //Loop through the guestList and confirm whether the variables have been stored in the variables (循环遍历guestList，确认变量是否都已经存储在变量里)
                for (GuestData guest : guestsList) {
                    String guestInfo = "Guest Name: " + guest.getName() + ", Gender: " + guest.getGender() +
                            ", Phone Number: " + guest.getPhoneNumber() + ", Email: " + guest.getEmail();
                    Log.d("guestsList", guestInfo);
                }

                //Use encapsulated methods to make POST requests (使用封装的方法进行POST请求)
                sendReservationData(hotelName,checkInDate,checkOutDate,guestsList);

            }
        });
        generateGuestInputFields(Integer.parseInt(numberOfGuests));
    }

    private void generateGuestInputFields(int numberOfGuests){
        ViewGroup container = (ViewGroup) view.findViewById(R.id.input_fields_container);

        //clear the view in container (清空容器中的视图)
        container.removeAllViews();

        // generate EditText and gender radio button for each guest(为每一个guest生成输入框和单选按钮)
        for(int i = 0; i < numberOfGuests; i++){
            View guestInputView = LayoutInflater.from(getContext()).inflate(R.layout.guest_input_item, container, false);

            EditText guestNameEditText = guestInputView.findViewById(R.id.guest_name_edit_text);
            EditText phoneNumberEditText = guestInputView.findViewById(R.id.phone_number_edit_text);
            EditText emailEditText = guestInputView.findViewById(R.id.email_edit_text);
            RadioGroup genderRadioGroup = guestInputView.findViewById(R.id.gender_radio_group);

            // add to container (添加到容器中)
            container.addView(guestInputView);
        }
    }

    private void sendReservationData(String hotelName, String checkInDate, String checkOutDate, List<GuestData> guestData){
        Log.d("sendReservationData", "sendReservationData: start to run sendReservationData method");

        // Date formate is: dd-MM-yyyy, so we need to transfer to YYYY-MM-DD formate
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date checkIn, checkOut;
        try {
            // parse the input data
            checkIn = inputFormat.parse(checkInDate);
            checkOut = inputFormat.parse(checkOutDate);

            // transfer to YYYY-MM-DD format
            checkInDate = outputFormat.format(checkIn);
            checkOutDate = outputFormat.format(checkOut);
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        ReservationData reservationData = new ReservationData(hotelName, checkInDate, checkOutDate, guestData);

        ApiInterface apiInterface = Api.getClient();
        retrofit2.Call <Map<String, Integer>> call = apiInterface.createReservation(reservationData);

        // Print Request Message
        Log.d("Print Request Message", call.request().body().toString());

        call.enqueue(new Callback<Map<String, Integer>>() {
            @Override
            public void onResponse(@NonNull Call<Map<String, Integer>> call, @NonNull Response<Map<String, Integer>> response) {
                //Log.d("aaaaaa", "onResponse: aaaaaa had run" + response.headers() + " " + response.raw());
                if (response.isSuccessful()){
                    Log.d("post reservation: ", "onResponse: post reservation successfully and response is successful!" + response.body());
                    confirmationId = response.body().get("confirmation_number");
                    Log.d("confirmationId: ", "onResponse: " + confirmationId);
                    //替换为ReserveFragment页面
                    ReservedFragment reservedFragment = new ReservedFragment();

                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_layout, reservedFragment);

                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                    Bundle bundle = new Bundle();
                    bundle.putInt("confirmation_number", confirmationId);
                    reservedFragment.setArguments(bundle);
                }else {
                    Log.d("post reservation: ", "onResponse: post reservation successfully but response is not successful!"+call.toString() + " " + response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Map<String, Integer>> call, @NonNull Throwable t) {
                Log.d("onFailure", "onResponse: failed in posting message: " + t.toString());
            }
        });
    }

    private boolean isAllEditTextField(){
        // The loop traverses the EditText in all subcontainers and checks whether they are empty. (循环遍历所有子container中的EditText，检查是否为空)
        ViewGroup container = (ViewGroup) view.findViewById(R.id.input_fields_container);
        boolean isAllFieldsValid = true; // 用于记录所有字段是否都有效，默认为 true
        for(int i = 0; i < container.getChildCount(); i++){
            View guestInputView = container.getChildAt(i);
            EditText guestNameEditText = guestInputView.findViewById(R.id.guest_name_edit_text);
            EditText emailEditText = guestInputView.findViewById(R.id.email_edit_text);
            EditText phoneNumberEditText = guestInputView.findViewById(R.id.phone_number_edit_text);
            RadioGroup genderRadioGroup = guestInputView.findViewById(R.id.gender_radio_group);

            if(TextUtils.isEmpty(guestNameEditText.getText().toString())){
                guestNameEditText.setError("Please input your name");
                isAllFieldsValid = false;
            }
            if(TextUtils.isEmpty(phoneNumberEditText.getText().toString())){
                phoneNumberEditText.setError("please input your phone number");
                isAllFieldsValid = false;
            }
            if(TextUtils.isEmpty(emailEditText.getText().toString())){
                emailEditText.setError("Please input your email");
                isAllFieldsValid = false;
            }
        }
        return isAllFieldsValid;
    }
}
