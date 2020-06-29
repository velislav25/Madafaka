package com.example.friendfind.fragments.show;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.friendfind.R;
import com.example.friendfind.domain.Follows;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.LocationCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShowFragment extends Fragment implements OnMapReadyCallback {

    SupportMapFragment mapFragment;
    GoogleMap map;
    public static ShowFragment newInstance() {
        return new ShowFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_fragment, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if(mapFragment==null){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("xxx", "MapReady");
        map = googleMap;


        showFollowers();
    }

    public void showFollowers(){
        Follows.getInstance();

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("users");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("follower");

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable String s) {
                if(map!= null){
                    final String userid = dataSnapshot.getRef().getKey();
                    if(userid != null){
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(userid);
                        GeoFire geoFire = new GeoFire(ref);
                        geoFire.getLocation("location", new LocationCallback() {
                            @Override
                            public void onLocationResult(String key, GeoLocation location) {
                                Log.wtf("wtf","onCancelled");
                                if(location==null)
                                    return;
                                final LatLng latLng = new LatLng(location.latitude, location.longitude);
                                DatabaseReference user = FirebaseDatabase.getInstance().getReference().child("users").child(userid).child("email");
                                user.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        if(dataSnapshot.getValue() !=null ){
                                            String em = dataSnapshot.getValue().toString();
                                            map.addMarker(new MarkerOptions().position(latLng).title(em));
                                        }else {
                                            map.addMarker(new MarkerOptions().position(latLng).title("No email"));

                                        }
                                        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        map.addMarker(new MarkerOptions().position(latLng).title("No email"));
                                        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                    }
                                });

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.wtf("wtf","onCancelled");
                            }
                        });
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}