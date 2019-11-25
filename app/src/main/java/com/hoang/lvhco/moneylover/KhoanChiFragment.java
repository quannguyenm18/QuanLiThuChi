package com.hoang.lvhco.moneylover;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.hoang.lvhco.moneylover.adapter.AdapterKhoanChi;
import com.hoang.lvhco.moneylover.dao.KhoanChiDao;
import com.hoang.lvhco.moneylover.model.KhoanChi;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class KhoanChiFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private EditText edNgayChi, edTenChi, edSoTienChi;
    private ImageView imgLich;
    static private DatePickerDialog.OnDateSetListener onDateSetListener;

    private RecyclerView recyclerView;
    private AdapterKhoanChi adapterKhoanChi;
    private KhoanChiDao khoanChiDao;
    private List<KhoanChi> khoanChiList = new ArrayList<>();
    private String strTenChi, strNgayChi, strTienChi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_khoan_chi_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        onDateSetListener = this;
        recyclerView = view.findViewById(R.id.recyclerView);
        khoanChiDao = new KhoanChiDao(getContext());
        try {
            khoanChiList = khoanChiDao.getAllKhoanChi();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        adapterKhoanChi = new AdapterKhoanChi(getContext(), (ArrayList<KhoanChi>) khoanChiList);
        recyclerView.setAdapter(adapterKhoanChi);

        FloatingActionButton fab = view.findViewById(R.id.fabThemKhoanChi);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                View view1 = getLayoutInflater().inflate(R.layout.dialog_them, null);
                dialog.setView(view1);
                dialog.setCancelable(false);
                edNgayChi = view1.findViewById(R.id.edTenThem);
                edSoTienChi = view1.findViewById(R.id.edSoTienThem);
                edTenChi = view1.findViewById(R.id.edTenThem);
                edNgayChi = view1.findViewById(R.id.edNgayThem);
                imgLich = view1.findViewById(R.id.imgLich);

                edSoTienChi.addTextChangedListener(onTextChangedListener());

                SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
                String currentDateTime = sdf1.format(new Date());
                edNgayChi.setText(currentDateTime);
                imgLich.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datePicker(v);
                    }
                });

                dialog.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        khoanChiDao = new KhoanChiDao(getContext());
                        if (checkChi() > 0) {
                            Toast.makeText(getContext(), "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();

                            try {
                                KhoanChi khoanChi = new KhoanChi(1,edTenChi.getText().toString(),
                                        Double.parseDouble(edSoTienChi.getText().toString().replaceAll(",", "")),
                                        sdf.parse(edNgayChi.getText().toString()));

                                if (khoanChiDao.insertKhoanChi(khoanChi) > 0) {
//                                    Toast.makeText(getContext(), "Thêm Thành công", Toast.LENGTH_SHORT).show();
//                                    khoanChiList.clear();
                                    try {
                                        khoanChiList = khoanChiDao.getAllKhoanChi();
                                        Toast.makeText(getContext(), "Thêm Thành công", Toast.LENGTH_SHORT).show();
                                        onResume();
                                        // khởi tạo lại database
                                        //

                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } catch (ParseException e) {
                                Log.d("Error:", e.toString());
                            }
                        }

                    }
                });

                dialog.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

                AlertDialog dialog1 = dialog.create();
                dialog1.setTitle("Thêm khoản chi");
                dialog1.show();
            }


        });
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
    }

    private TextWatcher onTextChangedListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edSoTienChi.removeTextChangedListener(this);

                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to EditText
                    edSoTienChi.setText(formattedString);
                    edSoTienChi.setSelection(edSoTienChi.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                edSoTienChi.addTextChangedListener(this);
            }
        };
    }



    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
        setDate(cal);
    }


    private void setDate(final Calendar calendar) {
        edNgayChi.setText(sdf.format(calendar.getTime()));


    }

    public void datePicker(View view) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getFragmentManager(), "date");
    }

    public static class DatePickerFragment extends android.support.v4.app.DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(),
                    onDateSetListener, year, month, day);
        }
    }

    public void onResume() {
        super.onResume();

        try {
            khoanChiList = khoanChiDao.getAllKhoanChi();
            adapterKhoanChi.changeDataset(khoanChiDao.getAllKhoanChi());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public int checkChi() {
        int check1 = 1;
        strTenChi = edTenChi.getText().toString();
        strTienChi = edSoTienChi.getText().toString();
        strNgayChi = edNgayChi.getText().toString();

        if (strNgayChi.isEmpty() || strTienChi.isEmpty() || strTenChi.isEmpty()) {
            Toast.makeText(getContext(), "nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check1 = -1;
        }
        return check1;
    }
}
