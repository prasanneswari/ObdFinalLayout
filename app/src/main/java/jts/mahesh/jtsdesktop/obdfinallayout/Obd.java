package jts.mahesh.jtsdesktop.obdfinallayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Obd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obd);
        TextView txt =(TextView)findViewById(R.id.dstid);
        String str ="On-board diagnostics (OBD) is an automotive term referring to a vehicle's " +
                "self-diagnostic and reporting capability. OBD systems give the vehicle owner or" +
                " repair technician access to the status of the various vehicle subsystems." +
                " The amount of diagnostic information available via OBD has varied widely since" +
                " its introduction in the early 1980s versions of on-board vehicle computers. Early versions of OBD would simply illuminate a malfunction indicator light or \"idiot light\" if a problem was detected but would not provide any information as to the nature of the problem. Modern OBD implementations use a standardized digital communications port to provide real-time data in addition to a standardized series of diagnostic trouble codes, " +
                "or DTCs, which allow one to rapidly identify and remedy malfunctions within the " +
                "vehicle.";
        txt.setText(str);
    }
}
