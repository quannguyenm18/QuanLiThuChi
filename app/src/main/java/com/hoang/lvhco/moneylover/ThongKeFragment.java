package com.hoang.lvhco.moneylover;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hoang.lvhco.moneylover.dao.KhoanChiDao;
import com.hoang.lvhco.moneylover.dao.KhoanThuDao;
import com.hoang.lvhco.moneylover.model.KhoanChi;
import com.hoang.lvhco.moneylover.model.KhoanThu;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class ThongKeFragment extends Fragment {
    private TextView DoanhThuTK,ChiTieuTK,CanDoiTK;
    private KhoanThuDao khoanThuDao;
    private KhoanChiDao khoanChiDao;
    private TextView TatCa,Thang,Nam;
    private TextView tvThang,tvNam;
    private Spinner spinnerThang, spinnerNam;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_thong_ke_fragment,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinnerThang = view.findViewById(R.id.spThang);
        spinnerNam = view.findViewById(R.id.spNam);

        khoanThuDao = new KhoanThuDao(getActivity());
        DoanhThuTK = view.findViewById(R.id.DoanhThuTK);

        ChiTieuTK = view.findViewById(R.id.ChiTieuTK);
        CanDoiTK = view.findViewById(R.id.CanDoiTK);

        TatCa = view.findViewById(R.id.TatCa);
        Thang = view.findViewById(R.id.Thang);
        Nam = view.findViewById(R.id.Nam);

        tvThang = view.findViewById(R.id.tvThang);
        tvNam = view.findViewById(R.id.tvNam);

        tvThang.setVisibility(View.GONE);
        tvNam.setVisibility(View.GONE);
        spinnerThang.setVisibility(View.GONE);
        spinnerNam.setVisibility(View.GONE);

        khoanThuDao = new KhoanThuDao(getActivity());

        khoanChiDao = new KhoanChiDao(getActivity());

        ChiTieuTK.setText(formatVnCurrence(String.valueOf(khoanChiDao.getTongKhoanChi()))+"VNĐ");
        DoanhThuTK.setText(formatVnCurrence(String.valueOf(khoanThuDao.getTongKhoanThu()))+"VNĐ");
        CanDoiTK.setText(formatVnCurrence(String.valueOf(khoanThuDao.getTongKhoanThu()-khoanChiDao.getTongKhoanChi()))+"VNĐ");


        TatCa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                tvThang.setVisibility(View.GONE);
                tvNam.setVisibility(View.GONE);
                spinnerThang.setVisibility(View.GONE);
                spinnerNam.setVisibility(View.GONE);

                khoanThuDao = new KhoanThuDao(getActivity());

                ChiTieuTK.setText(formatVnCurrence(String.valueOf(khoanChiDao.getTongKhoanChi()))+"VNĐ");
                DoanhThuTK.setText(formatVnCurrence(String.valueOf(khoanThuDao.getTongKhoanThu()))+"VNĐ");
                CanDoiTK.setText(formatVnCurrence(String.valueOf(khoanThuDao.getTongKhoanThu()-khoanChiDao.getTongKhoanChi()))+"VNĐ");
            }
        });


        Thang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvThang.setVisibility(View.VISIBLE);
                tvNam.setVisibility(View.INVISIBLE);
                spinnerThang.setVisibility(View.VISIBLE);
                spinnerNam.setVisibility(View.INVISIBLE);


                khoanThuDao = new KhoanThuDao(getActivity());
                DoanhThuTK.setText("0 VNĐ");
                khoanChiDao = new KhoanChiDao(getActivity());
                ChiTieuTK.setText("0 VNĐ");
                CanDoiTK.setText("0 VNĐ");

                final List<String> list = new ArrayList<>();
                list.add("12");
                list.add("11");
                list.add("10");
                list.add("09");
                list.add("08");
                list.add("07");
                list.add("06");
                list.add("05");
                list.add("04");
                list.add("03");
                list.add("02");
                list.add("01");
                final String[] selectMonth = {""};
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line,list);

                arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                spinnerThang.setAdapter(arrayAdapter);
                spinnerThang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        try {
                            double sumchi = 0;
                            double sumthu = 0;
                            KhoanChiDao chiDao  = new KhoanChiDao(getContext());
                            KhoanThuDao thuDao  = new KhoanThuDao(getContext());
                            List<KhoanChi> dschi = chiDao.getKhoanChi(spinnerThang.getItemAtPosition(position).toString()+"-");
                            List<KhoanThu> dsthu = thuDao.getKhoanThu(spinnerThang.getItemAtPosition(position).toString()+"-");
                            for (int i = 0; i<dschi.size(); i++){
                                sumchi = sumchi+dschi.get(i).getSoTienKhoanChi();
                                Log.e("test", String.valueOf(sumchi));
                            }
                            for (int i = 0; i<dsthu.size(); i++){
                                sumthu = sumthu+dsthu.get(i).getSoTienKhoanThu();
                                Log.e("test", String.valueOf(sumthu));
                            }
                            String chi = String.valueOf(sumchi);
                            String thu = String.valueOf(sumthu);
                            ChiTieuTK.setText(formatVnCurrence(String.valueOf(chi))+"VNĐ");
                            DoanhThuTK.setText(formatVnCurrence(String.valueOf(thu))+"VNĐ");
                            CanDoiTK.setText(formatVnCurrence(String.valueOf(sumthu-sumchi))+"VNĐ");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }
        });

        Nam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvThang.setVisibility(View.INVISIBLE);
                spinnerThang.setVisibility(View.INVISIBLE);
                tvNam.setVisibility(View.VISIBLE);
                spinnerNam.setVisibility(View.VISIBLE);
                List<String> list1 = new ArrayList<>();
                list1.add("2018");
                list1.add("2019");
                list1.add("2020");
                list1.add("2021");
                list1.add("2022");
                ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line,list1);

                arrayAdapter2.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                spinnerNam.setAdapter(arrayAdapter2);
                spinnerNam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                        try {
                            double sumchi = 0;
                            double sumthu = 0;
                            KhoanChiDao chiDao  = new KhoanChiDao(getContext());
                            KhoanThuDao thuDao  = new KhoanThuDao(getContext());
                            List<KhoanChi> dschi = chiDao.getKhoanChi("-"+spinnerNam.getItemAtPosition(position).toString());
                            List<KhoanThu> dsthu = thuDao.getKhoanThu("-"+spinnerNam.getItemAtPosition(position).toString());
                            for (int i = 0; i<dschi.size(); i++){
                                sumchi = sumchi+dschi.get(i).getSoTienKhoanChi();
                                Log.e("test", String.valueOf(sumchi));
                            }
                            for (int i = 0; i<dsthu.size(); i++){
                                sumthu = sumthu+dsthu.get(i).getSoTienKhoanThu();
                                Log.e("test", String.valueOf(sumthu));

                            }
                            String chi = String.valueOf(sumchi);
                            String thu = String.valueOf(sumthu);
                            ChiTieuTK.setText(formatVnCurrence(String.valueOf(chi))+"VNĐ");
                            DoanhThuTK.setText(formatVnCurrence(String.valueOf(thu))+"VNĐ");
                            CanDoiTK.setText(formatVnCurrence(String.valueOf(sumthu-sumchi))+"VNĐ");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });
    }
    public static String formatVnCurrence(String price) {

        NumberFormat format =
                new DecimalFormat("#,##0.00");// #,##0.00 ¤ (¤:// Currency symbol)
        format.setCurrency(Currency.getInstance(Locale.US));//Or default locale

        price = (!TextUtils.isEmpty(price)) ? price : "0";
        price = price.trim();
        price = format.format(Double.parseDouble(price));
        price = price.replaceAll(",", "\\.");

        if (price.endsWith(".00")) {
            int centsIndex = price.lastIndexOf(".00");
            if (centsIndex != -1) {
                price = price.substring(0, centsIndex);
            }
        }
        price = String.format("%s ", price);
        return price;
    }
}
