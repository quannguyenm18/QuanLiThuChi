package com.hoang.lvhco.moneylover.adapter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.hoang.lvhco.moneylover.R;
import com.hoang.lvhco.moneylover.dao.KhoanChiDao;
import com.hoang.lvhco.moneylover.dao.KhoanThuDao;
import com.hoang.lvhco.moneylover.model.KhoanThu;

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

public class AdapterKhoanThu extends RecyclerView.Adapter<AdapterKhoanThu.ViewHolder> {
    static private DatePickerDialog.OnDateSetListener onDateSetListener1;
    private Context context;
    private List<KhoanThu> khoanThuArrayList;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private KhoanThuDao khoanThuDao;

    public AdapterKhoanThu(Context context, List<KhoanThu> khoanThuArrayList) {
        this.context = context;
        this.khoanThuArrayList = khoanThuArrayList;
        khoanThuDao = new KhoanThuDao(context);
    }

    @NonNull
    @Override
    public AdapterKhoanThu.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_khoan_thu, viewGroup, false);

        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull final AdapterKhoanThu.ViewHolder viewHolder, final int i) {
        final KhoanThu khoanThu = khoanThuArrayList.get(i);
        viewHolder.tvTenKhoanThu.setText(khoanThuArrayList.get(i).getTenKhoanThu());
//        viewHolder.tvSoTienThu.setText(khoanThuArrayList.get(i).getSoTienKhoanThu() + " VNĐ");
        viewHolder.tvSoTienThu.setText(formatVnCurrence(String.valueOf(khoanThuArrayList.get(i).getSoTienKhoanThu()))+"VNĐ");
        viewHolder.tvNgayThu.setText(sdf.format(khoanThuArrayList.get(i).getNgayThu()));
        viewHolder.imgKhoanThu.setImageResource(R.drawable.doanhthu);

        viewHolder.xoaKhoanThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                khoanThuDao = new KhoanThuDao(context);
                khoanThuDao.deleteKhoanThuByID(khoanThuArrayList.get(i).getId());
                khoanThuArrayList.remove(i);
                notifyDataSetChanged();
            }
        });

        viewHolder.suaKhoaThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final View dialog = View.inflate(context, R.layout.dialog_sua, null);
                builder.setView(dialog);



                final EditText edSuaTen = dialog.findViewById(R.id.edTenSuaThu);
                final EditText edSuaSoTien = dialog.findViewById(R.id.edSuaSoTienThu);
                final EditText edSuaNgay = dialog.findViewById(R.id.edSuaNgayThu);
                final ImageView imgSuaThu = dialog.findViewById(R.id.imgLichSua);

                edSuaTen.setText(khoanThuArrayList.get(i).getTenKhoanThu());
                edSuaSoTien.setText(khoanThuArrayList.get(i).getSoTienKhoanThu() + "");
//                edSuaSoTien.setText(formatVnCurrence(String.valueOf(khoanThuArrayList.get(i).getSoTienKhoanThu()))+"VNĐ");
                edSuaNgay.setText(sdf.format(khoanThuArrayList.get(i).getNgayThu()));
                imgSuaThu.setOnClickListener(new View.OnClickListener() {
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

                        khoanThuDao = new KhoanThuDao(context);
                        try {
                            int result1 = khoanThuDao.updateKhoanThu(edSuaTen.getText().toString(),
                                    Double.parseDouble(edSuaSoTien.getText().toString()),
                                    sdf.parse(edSuaNgay.getText().toString()));
                            if (result1>0){
                                Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                            }
                            khoanThuArrayList.clear();
                            khoanThuArrayList = (ArrayList<KhoanThu>)khoanThuDao.getAllKhoanThu();
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
        return khoanThuArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenKhoanThu, tvNgayThu, tvSoTienThu;
        ImageView xoaKhoanThu, suaKhoaThu;
        private ImageView imgKhoanThu;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNgayThu = itemView.findViewById(R.id.tvNgayThu);
            tvTenKhoanThu = itemView.findViewById(R.id.tvTenKhoanThu);
            tvSoTienThu = itemView.findViewById(R.id.tvSoTienThu);
            xoaKhoanThu = itemView.findViewById(R.id.xoaKhoanThu);
            suaKhoaThu = itemView.findViewById(R.id.suaKhoanThu);
            imgKhoanThu = itemView.findViewById(R.id.imgKhoanThu);


        }
    }

    public void changeDataset(List<KhoanThu> khoanThuList) {
        this.khoanThuArrayList = khoanThuList;
        notifyDataSetChanged();
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
