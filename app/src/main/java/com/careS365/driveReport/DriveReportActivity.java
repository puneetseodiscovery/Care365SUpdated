package com.careS365.driveReport;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.careS365.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DriveReportActivity extends AppCompatActivity {

    @BindView(R.id.rv_drive_report)
    RecyclerView rvDriveReport;
    ArrayList<BeanDriveReport> driveReportsArrayList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive_report);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        driveReportsArrayList=new ArrayList<>();

        driveReportsArrayList.add(new BeanDriveReport("Earlier Today","717,Chandi path,sector 31A, Chandigarh, India","8:08 PM - 9:12 PM (1hr 31min)","2 Mile drive","9:16 PM - 9:24 PM (7min)"));
        driveReportsArrayList.add(new BeanDriveReport("Yesterday","717,Chandi path,sector 31A, Chandigarh, India","8:08 PM - 9:12 PM (1hr 31min)","2 Mile drive","9:16 PM - 9:24 PM (7min)"));
        driveReportsArrayList.add(new BeanDriveReport("Ereyesterday","717,Chandi path,sector 31A, Chandigarh, India","8:08 PM - 9:12 PM (1hr 31min)","2 Mile drive","9:16 PM - 9:24 PM (7min)"));
        driveReportsArrayList.add(new BeanDriveReport("Tuesday","717,Chandi path,sector 31A, Chandigarh, India","8:08 PM - 9:12 PM (1hr 31min)","2 Mile drive","9:16 PM - 9:24 PM (7min)"));
        driveReportsArrayList.add(new BeanDriveReport("Monday","717,Chandi path,sector 31A, Chandigarh, India","8:08 PM - 9:12 PM (1hr 31min)","2 Mile drive","9:16 PM - 9:24 PM (7min)"));
        driveReportsArrayList.add(new BeanDriveReport("Sunday","717,Chandi path,sector 31A, Chandigarh, India","8:08 PM - 9:12 PM (1hr 31min)","2 Mile drive","9:16 PM - 9:24 PM (7min)"));

        rvDriveReport.setLayoutManager(new LinearLayoutManager(this));
        DriveReportAdapter adapter = new DriveReportAdapter(this, driveReportsArrayList);
        rvDriveReport.setAdapter(adapter);

    }

    public void onBackClicked(View view) {
        super.onBackPressed();
    }
}
