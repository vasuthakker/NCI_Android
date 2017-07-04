package com.example.epuser.pickcontacts.fragments;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.epuser.pickcontacts.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by epuser on 29-Jun-17.
 */

public class ContactUsFragment extends Fragment implements OnMapReadyCallback,View.OnClickListener{
    private GoogleMap mMap;
    private Marker marker;
    private ImageView satelite,normalmap;
    private TextView phoneCall,mailclick;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_contact_us, container, false);




        return   view;

    }

    @Override
    public void onStart() {
        super.onStart();
        init();

    }

    private void init() {
        satelite =(ImageView)getActivity().findViewById(R.id.satelite);
        normalmap=(ImageView) getActivity().findViewById(R.id.normalMap);
        phoneCall =(TextView)getActivity().findViewById(R.id.phoneCall);
        mailclick=(TextView)getActivity().findViewById(R.id.mailclick) ;
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        normalmap.setOnClickListener(this);
        satelite.setOnClickListener(this);
        mailclick.setOnClickListener(this);
        phoneCall.setOnClickListener(this);


//
//        PhoneCallListener phoneListener = new PhoneCallListener();
//        TelephonyManager telephonyManager = (TelephonyManager) getActivity()
//                .getSystemService(Context.TELEPHONY_SERVICE);
//        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    @Override
    public void onClick(View v) {
        if (v==normalmap){
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            satelite.setVisibility(View.VISIBLE);
            normalmap.setVisibility(View.GONE);
        }
        else if (v==satelite){
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            normalmap.setVisibility(View.VISIBLE);
            satelite.setVisibility(View.GONE);
        }
        else if(v==phoneCall){
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+phoneCall.getText()));
//            if (ActivityCompat.checkSelfPermission(getActivity(),
//                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                return;
//            }
            startActivity(callIntent);

        }
        else if(v==mailclick){
            final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);


            emailIntent.setType("message/rfc822");
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"to@email.com"});
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Text");

            startActivity(Intent.createChooser(emailIntent, "Send mail..."));

        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng nci = new LatLng(21.028045,79.032894);
        marker= googleMap.addMarker(new MarkerOptions().position(nci)
                .title("National Cancer Institute"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(nci));
        mMap=googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
       // mMap.setOnMapClickListener(this);

    }


    private class PhoneCallListener extends PhoneStateListener {

        private boolean isPhoneCalling = false;

        String LOG_TAG = "LOGGING 123";

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if (TelephonyManager.CALL_STATE_RINGING == state) {

                Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
            }

            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {

                Log.i(LOG_TAG, "OFFHOOK");

                isPhoneCalling = true;
            }

            if (TelephonyManager.CALL_STATE_IDLE == state) {

                Log.i(LOG_TAG, "IDLE");

                if (isPhoneCalling) {

                    Log.i(LOG_TAG, "restart app");


                    Intent i = getActivity().getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage(
                                    getActivity().getBaseContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                    isPhoneCalling = false;
                }

            }
        }
    }
}
