package com.hoang.lvhco.moneylover;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.hoang.lvhco.moneylover.adapter.AdapterKhoanThu;
import com.hoang.lvhco.moneylover.dao.KhoanThuDao;
import com.hoang.lvhco.moneylover.model.KhoanThu;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class KhoanThuFramgent extends Fragment implements DatePickerDialog.OnDateSetListener {
    static private DatePickerDialog.OnDateSetListener onDateSetListener1;
    private EditText edTenThu, edNgayThu, edSoTienThu;
    private ImageView imgNgayThu;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private RecyclerView recyclerView;
    private AdapterKhoanThu adapterKhoanThu;
    private KhoanThuDao khoanThuDao;
    private List<KhoanThu> khoanThuList = new ArrayList<>();
    private String strTen, strTien, strNgay;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_khoan_thu_framgent, container, false);
        return view;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        onDateSetListener1 = this;
        recyclerView = view.findViewById(R.id.recyclerViewThu);
        khoanThuDao = new KhoanThuDao(getContext());
        try {
            khoanThuList = khoanThuDao.getAllKhoanThu();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        adapterKhoanThu = new AdapterKhoanThu(getContext(), (ArrayList<KhoanThu>) khoanThuList);
        recyclerView.setAdapter(adapterKhoanThu);


        FloatingActionButton fab = view.findViewById(R.id.fabThemKhoanThu);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                View view = getLayoutInflater().inflate(R.layout.dialog_them_thu, null);
                dialog.setView(view);
                dialog.setCancelable(false);
                edTenThu = view.findViewById(R.id.edTenThemThu);
                edSoTienThu = view.findViewById(R.id.edSoTienThemThu);
                edNgayThu = view.findViewById(R.id.edNgayThemThu);
                imgNgayThu = view.findViewById(R.id.imgLichThu);

                edSoTienThu.addTextChangedListener(onTextChangedListener());
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
                String currentDateTime = sdf1.format(new Date());
                edNgayThu.setText(currentDateTime);
                imgNgayThu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datePicker(v);
                    }
                });

                dialog.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        khoanThuDao = new KhoanThuDao(getContext());
                        if (checkThu() < 0) {
                            Toast.makeText(getContext(), "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            try {

                                KhoanThu khoanThu = new KhoanThu(1,edTenThu.getText().toString(),
                                        Double.parseDouble(edSoTienThu.getText().toString().replaceAll(",", "")),
                                        sdf.parse(edNgayThu.getText().toString()));

                                if (khoanThuDao.insertKhoanThu(khoanThu) > 0) {
                                    Toast.makeText(getContext(), "Thêm Thành công", Toast.LENGTH_SHORT).show();
                                    onResume();
                                }
                            } catch (ParseException e) {
                                Log.d("Error: ", e.toString());
                            }
                        }

                    }
                });

                dialog.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog.setCancelable(true);
                    }
                });

                AlertDialog dialog1 = dialog.create();
                dialog1.setTitle("Thêm khoản thu");
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
                    edSoTienThu.removeTextChangedListener(this);

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
                        edSoTienThu.setText(formattedString);
                        edSoTienThu.setSelection(edSoTienThu.getText().length());
                    } catch (NumberFormatException nfe) {
                        nfe.printStackTrace();
                    }

                    edSoTienThu.addTextChangedListener(this);
                }
            };
        }


    public void onResume() {
        super.onResume();
        khoanThuList.clear();
        try {
            khoanThuList = khoanThuDao.getAllKhoanThu();
            adapterKhoanThu.changeDataset(khoanThuDao.getAllKhoanThu());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
        setDate(cal);
    }

    private void setDate(final Calendar calendar) {
        edNgayThu.setText(sdf.format(calendar.getTime()));
    }

    public void datePicker(View view) {
        KhoanThuFramgent.DatePickerFragment fragment1 = new DatePickerFragment();
        fragment1.show(getFragmentManager(), "date");
    }

    public static class DatePickerFragment extends android.support.v4.app.DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(),
                    onDateSetListener1, year, month, day);
        }
    }

    public int checkThu() {
        int check = 1;
        strTien = edSoTienThu.getText().toString();
        strTen = edTenThu.getText().toString();
        strNgay = edSoTienThu.getText().toString();
        if (strNgay.isEmpty() || strTien.isEmpty() || strTen.isEmpty()) {
            Toast.makeText(getContext(), "nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }
}
