import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import com.example.mthiaw.movieapp.MainActivity;

public class DeviceSize {
    //This will be used to determine the number of images we want to see in a row
    private int deviceWith;
    Point pointSize;

    public DeviceSize(int deviceWith, Point pointSize) {
        this.deviceWith = deviceWith;
        this.pointSize = pointSize;
    }

    public int getDeviceWith() {


        WindowManager wm =(WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        if(MainActivity.TABLET)
        {
            deviceWith = size.x/6;
        }
        else deviceWith=size.x/3;



        return deviceWith;
    }


    public Point getPointSize() {
        return pointSize;
    }

}
