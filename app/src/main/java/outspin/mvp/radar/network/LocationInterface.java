package outspin.mvp.radar.network;

import android.location.Location;
import android.os.Bundle;

public interface LocationInterface {
    void onLocationChanged(Location var1); // Called when the location has changed
    void onStatusChanged(String var1, int var2, Bundle var3); // Called when the provider is disabled by the user
    void onProviderEnabled(String var1); // Called when the provider is enabled by the user
    void onProviderDisabled(String var1);
}
