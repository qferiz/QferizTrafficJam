package com.qferiz.trafficjam.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.qferiz.trafficjam.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRequestInfo extends Fragment implements
        DatePickerDialog.OnDateSetListener {

    private View mView;
    private CardView mCardCity, mCardCondition, mCardDate;
    private CheckBox mChkCity, mChkCondition, mChkDate;
    private TextView mTxtShowDate;
    private Spinner mSpinnerKondisi;
    private EditText mNamaKota;
    private static final String SEARCH_CITY = "search_city";
    private static final String SEARCH_CONDITION = "search_condition";
    private static final String SEARCH_DATE = "search_date";
    private static final String SEARCH_CITY_TEXT = "search_city_text";
    private static final String SEARCH_DATE_TEXT = "search_date_text";
    private static final String SEARCH_KONDISI = "search_kondisi";
    private boolean isCheckedCity, isCheckedCondition, isCheckedDate = false;
    private String strCity, strDate;
    private int mKondisi;

    public FragmentRequestInfo() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* if (savedInstanceState != null) {
            savedInstanceState.putBoolean(SEARCH_CITY, isCheckedCity);
            savedInstanceState.putBoolean(SEARCH_CONDITION, isCheckedCondition);
            savedInstanceState.putBoolean(SEARCH_DATE, isCheckedDate);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_request_info, container, false);

        mCardCity = (CardView) mView.findViewById(R.id.cardViewCity);
        mCardCondition = (CardView) mView.findViewById(R.id.cardViewCondition);
        mCardDate = (CardView) mView.findViewById(R.id.cardViewDate);

        mTxtShowDate = (TextView) mView.findViewById(R.id.txtDate);
        mNamaKota = (EditText) mView.findViewById(R.id.edNamaKotaCari);

        // Show Material DateTime Picker
        mCardDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mCalendar = Calendar.getInstance();
                DatePickerDialog mDatePicker = DatePickerDialog.newInstance(
                        FragmentRequestInfo.this,
                        mCalendar.get(Calendar.YEAR),
                        mCalendar.get(Calendar.MONTH),
                        mCalendar.get(Calendar.DAY_OF_MONTH)
                );
                mDatePicker.vibrate(true);
                mDatePicker.show(getActivity().getFragmentManager(), "Datepickerdialog");
            }
        });

        mSpinnerKondisi = (Spinner) mView.findViewById(R.id.spinnerKondisiCari);
        ArrayAdapter<CharSequence> mAdapterKondisi = ArrayAdapter.createFromResource(
                getActivity(), R.array.object_kondisi, android.R.layout.simple_spinner_item);
        mAdapterKondisi.setDropDownViewResource(R.layout.my_spinner_item);
        mSpinnerKondisi.setAdapter(mAdapterKondisi);

        mSpinnerKondisi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//                L.T(getActivity(), "Item Number Kondisi : " + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // set All CardView = GONE
        mCardCity.setVisibility(View.GONE);
        mCardCondition.setVisibility(View.GONE);
        mCardDate.setVisibility(View.GONE);

        mChkCity = (CheckBox) mView.findViewById(R.id.chkCityShow);
        mChkCondition = (CheckBox) mView.findViewById(R.id.chkConditionShow);
        mChkDate = (CheckBox) mView.findViewById(R.id.chkDateShow);

        mChkCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mChkCity.isChecked()) {
                    mCardCity.setVisibility(View.VISIBLE);
                    mNamaKota.setText("");
//                    isCheckedCity = true;
                } else {
                    mCardCity.setVisibility(View.GONE);
//                    isCheckedCity = false;
                }
            }
        });

        mChkCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mChkCondition.isChecked()) {
                    mCardCondition.setVisibility(View.VISIBLE);
                    mSpinnerKondisi.setSelection(0);
//                    isCheckedCondition = true;
                } else {
                    mCardCondition.setVisibility(View.GONE);
//                    isCheckedCondition = false;
                }
            }
        });

        mChkDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mChkDate.isChecked()) {
                    mCardDate.setVisibility(View.VISIBLE);
                    mTxtShowDate.setText(getResources().getString(R.string.txtDate));
//                    isCheckedDate = true;
                } else {
                    mCardDate.setVisibility(View.GONE);
//                    isCheckedDate = false;
                }
            }
        });

        return mView;
    }


    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        // Format date = 2015/07/23
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(year, month, day);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String formatDate = sdf.format(mCalendar.getTime());
        mTxtShowDate.setText(formatDate);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(SEARCH_CITY, mChkCity.isChecked());
        outState.putBoolean(SEARCH_CONDITION, mChkCondition.isChecked());
        outState.putBoolean(SEARCH_DATE, mChkDate.isChecked());
        outState.putString(SEARCH_CITY_TEXT, mNamaKota.getText().toString());
        outState.putString(SEARCH_DATE_TEXT, mTxtShowDate.getText().toString());
        outState.putInt(SEARCH_KONDISI, mSpinnerKondisi.getSelectedItemPosition());
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            isCheckedCity = savedInstanceState.getBoolean(SEARCH_CITY);
            isCheckedCondition = savedInstanceState.getBoolean(SEARCH_CONDITION);
            isCheckedDate = savedInstanceState.getBoolean(SEARCH_DATE);
            strCity = savedInstanceState.getString(SEARCH_CITY_TEXT);
            strDate = savedInstanceState.getString(SEARCH_DATE_TEXT);
            mKondisi = savedInstanceState.getInt(SEARCH_KONDISI);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        mChkCity.setChecked(isCheckedCity);
        mChkCondition.setChecked(isCheckedCondition);
        mChkDate.setChecked(isCheckedDate);
        mNamaKota.setText(strCity);
        mTxtShowDate.setText(strDate);
        mSpinnerKondisi.setSelection(mKondisi);

        checkedSearch();
    }

    private void checkedSearch() {
        if (mChkCity.isChecked()) {
            mCardCity.setVisibility(View.VISIBLE);
//            mNamaKota.setText("");
        } else {
            mCardCity.setVisibility(View.GONE);
        }

        if (mChkCondition.isChecked()) {
            mCardCondition.setVisibility(View.VISIBLE);
        } else {
            mCardCondition.setVisibility(View.GONE);
        }

        if (mChkDate.isChecked()) {
            mCardDate.setVisibility(View.VISIBLE);
//            mTxtShowDate.setText(getResources().getString(R.string.txtDate));
        } else {
            mCardDate.setVisibility(View.GONE);
        }
    }
}
