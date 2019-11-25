package com.hoang.lvhco.moneylover.adapter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hoang.lvhco.moneylover.R;
import com.hoang.lvhco.moneylover.dao.KhoanChiDao;
import com.hoang.lvhco.moneylover.model.KhoanChi;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class AdapterKhoanChi extends RecyclerView.Adapter<AdapterKhoanChi.ViewHolder> {
    static private DatePickerDialog.OnDateSetListener onDateSetListener1;
    private Context context;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private List<KhoanChi> khoanChiList;
    private KhoanChiDao khoanChiDao;

    public AdapterKhoanChi(Context context,List<KhoanChi> khoanChiList){
        this.context = context;
        this.khoanChiList = khoanChiList;
        khoanChiDao = new KhoanChiDao(context);
    }
    @NonNull
    @Override
    public AdapterKhoanChi.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_khoan_chi,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterKhoanChi.ViewHolder viewHolder, final int i) {
        final KhoanChi khoanChi = khoanChiList.get(i);
        viewHolder.tvTenKhoanChi.setText(khoanChiList.get(i).getTenKhoanChi());
//        viewHolder.tvSoTienChi.setText(khoanChiList.get(i).getSoTienKhoanChi()+" VNĐ");
        viewHolder.tvSoTienChi.setText(formatVnCurrence(String.valueOf(khoanChiList.get(i).getSoTienKhoanChi()))+"VNĐ");

        viewHolder.tvNgayChi.setText(sdf.format(khoanChiList.get(i).getNgayChi()));
        viewHolder.imgKhoanChi.setImageResource(R.drawable.chitieu1);
        viewHolder.xoaKhoanChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                khoanChiDao = new KhoanChiDao(context);
                khoanChiDao.deleteKhoanChi(khoanChiList.get(i).getId());
                khoanChiList.remove(i);
                notifyDataSetChanged();
            }
        });
        viewHolder.suaKhoanChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final View dialog = View.inflate(context,R.layout.dialog_sua,null);
                builder.setView(dialog);

                final EditText edSuaTen = dialog.findViewById(R.id.edTenSuaThu);
                final EditText edSuaTien = dialog.findViewById(R.id.edSuaSoTienThu);
                final EditText edSuaNgay = dialog.findViewById(R.id.edSuaNgayThu);
                final ImageView imgLich = dialog.findViewById(R.id.imgLichSua);

                edSuaTen.setText(khoanChiList.get(i).getTenKhoanChi());
                edSuaTien.setText(khoanChiList.get(i).getSoTienKhoanChi()+"");
//                edSuaTien.setText(formatVnCurrence(String.valueOf(khoanChiList.get(i).getSoTienKhoanChi()))+"");
                edSuaNgay.setText(sdf.format(khoanChiList.get(i).getNgayChi()));
                imgLich.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePickerDialog = new DatePickerDialog(dialog.getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                Calendar calendar1 = new GregorianCalendar(year,month,dayOfMonth);
                                edSuaNgay.setText(sdf.format(calendar1.getTime()));
                            }
                        },year,month,day);
                        datePickerDialog.show();
                    }
                });
                builder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        builder.setCancelable(true);
                        khoanChiDao = new KhoanChiDao(context);
                        try {
                            int result = khoanChiDao.updateKhoanChi(edSuaTen.getText().toString(),
                                    Double.parseDouble(edSuaTien.getText().toString()),
                                    sdf.parse(edSuaNgay.getText().toString()));
                            if (result>0){
                                Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();

                            }
                            khoanChiList.clear();
                            khoanChiList = (ArrayList<KhoanChi>)khoanChiDao.getAllKhoanChi();
                            notifyDataSetChanged();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        builder.setCancelable(true);
                    }
                });
                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return khoanChiList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgKhoanChi;
        private TextView tvTenKhoanChi;
        private TextView tvSoTienChi;
        private ImageView xoaKhoanChi;
        private ImageView suaKhoanChi;
        private TextView tvNgayChi;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgKhoanChi = itemView.findViewById(R.id.imgKhoanChi);
            tvTenKhoanChi = itemView.findViewById(R.id.tvTenKhoanChi);
            tvSoTienChi = itemView.findViewById(R.id.tvSoTienChi);
            xoaKhoanChi = itemView.findViewById(R.id.xoaKhoanChi);
            suaKhoanChi = itemView.findViewById(R.id.suaKhoanChi);
            tvNgayChi = itemView.findViewById(R.id.tvNgayChi);
        }
    }
    public void changeDataset(List<KhoanChi> khoanChiList) {
        this.khoanChiList = khoanChiList;
        notifyDataSetChanged();
    }
    public static String formatVnCurrence(String price) {

        NumberFormat format =
                new DecimalFormat("#,###,###,###");// #,##0.00 ¤ (¤:// Currency symbol)
        format.setCurrency(Currency.getInstance(Locale.US));//Or default locale

        price = (!TextUtils.isEmpty(price)) ? price : "0";
        price = price.trim();
        price = format.format(Double.parseDouble(price));
        price = price.replaceAll(",", "\\,");

        if (price.endsWith(",00")) {
            int centsIndex = price.lastIndexOf(",00");
            if (centsIndex != -1) {
                price = price.substring(0, centsIndex);
            }
        }
        price = String.format("%s ", price);
        return price;
    }

}
