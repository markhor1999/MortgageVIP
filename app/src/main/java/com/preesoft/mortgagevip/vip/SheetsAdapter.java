package com.preesoft.mortgagevip.vip;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.preesoft.mortgagevip.R;

import java.util.ArrayList;

public class SheetsAdapter extends RecyclerView.Adapter<SheetsAdapter.SheetsViewHolder> {

    private ArrayList<SheetModelClass> sheetModelClassArrayList = new ArrayList<>();
    public DownloadFile downloadFile;

    public SheetsAdapter(ArrayList<SheetModelClass> sheetModelClassArrayList, DownloadFile downloadFile) {
        this.sheetModelClassArrayList = sheetModelClassArrayList;
        this.downloadFile = downloadFile;
    }

    @NonNull
    @Override
    public SheetsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sheets_item, parent, false);
        return new SheetsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SheetsViewHolder holder, final int position) {
        holder.nameTextView.setText(sheetModelClassArrayList.get(position).getName());
    }


    @Override
    public int getItemCount() {
        return sheetModelClassArrayList.size();
    }

    public interface DownloadFile {
        void download(SheetModelClass sheetModelClass);
    }

    public class SheetsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameTextView;
        ImageView downloadImageView;

        public SheetsViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.file_name_tv);
            downloadImageView = itemView.findViewById(R.id.download_iv);

            downloadImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            downloadFile.download(sheetModelClassArrayList.get(getAdapterPosition()));
        }
    }
}
