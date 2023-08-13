package com.messas.blueprintsdk;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class RaghHostActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView quantityProductPage, quantityProductPage_speed;
    SeekBar seekBar;

    TextView progressbarsechk;
    TextView connectedornot;
    String geeet;

    /////bitmap data
    Uri imageuri;
    int flag = 0;
    BluetoothSocket m5ocket;
    BluetoothManager mBluetoothManager;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothDevice device;
    ImageView imageposit;

    Button printimageA;

    TextView printtimer;
    Spinner papertype;
    String valueSpinner;
    String selectcategory;
    String wight;
    String height;
    //connected or not
    FirebaseFirestore firebaseFirestore;
    int flag1 = 0;


    TextView macaddress, bluename111;
    RelativeLayout printcommand;
    public static Bitmap bitmapdataMe;
    public static String myheight;
    public static String mywidth;
    public static String myprintercategory;
    String deviceId;
    String bluetoothName;
    UserSession session;
    private HashMap<String, String> user;
    private String name, email, photo, mobile, username;

    UserSession2 scason2;
    private HashMap<String, String> user2;
    private String name2, email2, photo2, mobile2, username2;
    private static final int REQUEST_ENABLE_BT = 1;

    private static final int REQUEST_LOCATION_PERMISSION = 2;

    // Create a BroadcastReceiver for ACTION_FOUND
// Create a BroadcastReceiver for ACTION_FOUND

    //for bluetooth device

    BluetoothAdapter bluetoothAdapter;
    private BluetoothDeviceAdapter deviceAdapter;
    private ListView listView;
    private BroadcastReceiver discoveryReceiver;
    int REQUEST_BLUETOOTH_SCAN_PERMISSION = 4;
    BottomSheetDialog bottomSheetDialog11 ;
    TextView macdetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ragh_host);
        macdetails=findViewById(R.id.macdetails);
        init();
        bottomSheetDialog11 = new BottomSheetDialog(RaghHostActivity.this);
        bottomSheetDialog11.setContentView(R.layout.secondd);

        connectedornot = findViewById(R.id.connectedornot);
        imageposit = findViewById(R.id.imageposit);
        session = new UserSession(RaghHostActivity.this);
        scason2 = new UserSession2(RaghHostActivity.this);
        //default value

        getValues();
        if (mobile==null|| TextUtils.isEmpty(mobile))
        {
            String sessionname = "THT";
            String sessionmobile = "01:01:01:01:01";
            String sessionphoto = "1";
            String sessionemail = "14";
            ZoneId z = ZoneId.of("Asia/Dhaka");
            LocalDate today = LocalDate.now(z);
            String sessionusername = "" + today;
            session.createLoginSession(sessionname, sessionemail, sessionmobile, sessionphoto, sessionusername);
        }
        else{
            //  Toast.makeText(this, ""+mobile, Toast.LENGTH_SHORT).show();
        }


        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();



        deviceId = "abc@gmail.com";
        macaddress = findViewById(R.id.macaddress);
        bluename111 = findViewById(R.id.bluename111);
        bluename111.setText(""+mobile);
        printcommand = findViewById(R.id.printcommand);

        //  intttt();



        try {
            Intent intent = getIntent();
            myheight = intent.getStringExtra("myheight");
            mywidth = intent.getStringExtra("mywidth");
            myprintercategory = "esc";
            byte[] bitmapData = getIntent().getByteArrayExtra("bitmapData");
            if (bitmapData != null) {
            //    bitmapdataMe = BitmapFactory.decodeByteArray(bitmapData, 0, bitmapData.length);
                //imageposit.setImageBitmap(bitmapdataMe);
//            // Use the bitmap as needed

            }
            //  Toast.makeText(this, ""+myheight+""+mywidth+""+bitmapdataMe, Toast.LENGTH_SHORT).show();
            selectcategory = intent.getStringExtra("category");
            wight = intent.getStringExtra("wigth");
            height = intent.getStringExtra("height");
            if (TextUtils.isEmpty(selectcategory)) {
                selectcategory = "esc";
            } else {
                selectcategory = selectcategory;

            }
            //width
            if (TextUtils.isEmpty(wight)) {
                wight = "384";
            } else {
                wight = wight;

            }
            //height
            if (TextUtils.isEmpty(height)) {
                height = "384";
            } else {
                height = height;

            }

        } catch (Exception e) {

        }


        papertype = findViewById(R.id.papertype);
        papertype.setOnItemSelectedListener(this);
        quantityProductPage_speed = findViewById(R.id.quantityProductPage_speed);

        String[] textSizes = getResources().getStringArray(R.array.papersize);
        ArrayAdapter adapter = new ArrayAdapter(this,
                R.layout.selectitem, textSizes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        papertype.setAdapter(adapter);

        printtimer = findViewById(R.id.printtimer);
        quantityProductPage = findViewById(R.id.quantityProductPage);
        progressbarsechk = findViewById(R.id.progressbarsechk);

        seekBar = findViewById(R.id.seekBar);

        ImageView closedialouge = findViewById(R.id.closedialouge);
        closedialouge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progressbarsechk = findViewById(R.id.progressbarsechk);
        progressbarsechk.setText("14");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                progressbarsechk.setText("" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //   Toast.makeText(getApplicationContext(),"seekbar touch stopped!", Toast.LENGTH_SHORT).show();
            }
        });
        //for all
        getValues();
        if ((TextUtils.isEmpty(email)|| email.toString()==null) && (TextUtils.isEmpty(photo)|| photo.toString()==null))
        {
            seekBar.setProgress(14);
        }
        else {
            seekBar.setProgress(Integer.parseInt(email));
            progressbarsechk.setText(""+email);
            quantityProductPage_speed.setText(photo);
        }



//for devices



        ImageView closedialouge1 = (ImageView) bottomSheetDialog11.findViewById(R.id.closedialouge__1);
        deviceAdapter = new BluetoothDeviceAdapter(this, new ArrayList<>());
        listView = (ListView) bottomSheetDialog11.findViewById(R.id.list_view);
        listView.setAdapter(deviceAdapter);


        discoveryReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    if (ActivityCompat.checkSelfPermission(RaghHostActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        if (device.getName() != null) {
                            deviceAdapter.add(device);
                            deviceAdapter.notifyDataSetChanged();
                            //Toast.makeText(context, ""+device, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (device.getName() != null) {
                            deviceAdapter.add(device);
                            deviceAdapter.notifyDataSetChanged();
                            //Toast.makeText(context, ""+device, Toast.LENGTH_SHORT).show();
                        }
                        // Toast.makeText(RaghHostActivity.this, "" + device.getName() + "\n" + device.getAddress(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        closedialouge1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(RaghHostActivity.this, "Dialouge Close", Toast.LENGTH_SHORT).show();

                bottomSheetDialog11.dismiss();
            }
        });


        ImageView searchagain = (ImageView) bottomSheetDialog11.findViewById(R.id.searchagain);
        searchagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog mDialouge = new Dialog(RaghHostActivity.this);
                mDialouge.requestWindowFeature(Window.FEATURE_NO_TITLE);
                mDialouge.setContentView(R.layout.initilizeallinformation);
                mDialouge.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //  if(listView.le)

                        mDialouge.dismiss();
                        int itemCount = deviceAdapter.getCount();
                        Toasty.success(getApplicationContext(), "Done", Toasty.LENGTH_SHORT, true).show();


                    }
                }, 5000);


                mDialouge.create();
                ;
                mDialouge.show();
            }
        });

        // Initialize Bluetooth adapter and device adapter

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
            return;
        }

        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }




        ///////
        RelativeLayout relagoo = findViewById(R.id.relagoo);
        relagoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // startActivity(new Intent(getApplicationContext(),MyDevicesActivity.class));
                deviceAdapter = new BluetoothDeviceAdapter(RaghHostActivity.this, new ArrayList<>());
                deviceAdapter = new BluetoothDeviceAdapter(RaghHostActivity.this, new ArrayList<>());
                listView = (ListView) bottomSheetDialog11.findViewById(R.id.list_view);
                listView.setAdapter(deviceAdapter);
                startDiscovery();


                bottomSheetDialog11.show();
            }
        });
//for devices

        mBluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        ////print  Section
        printcommand = findViewById(R.id.printcommand);
        //print Section
        printcommand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getValues();
                if ((TextUtils.isEmpty(name) || name.toString() == null) && (TextUtils.isEmpty(mobile) || mobile.toString() == null)) {
                    Toast.makeText(RaghHostActivity.this, "No printer Connected.", Toast.LENGTH_SHORT).show();
                } else {
                    if ((name.toString().equals("THT")|| name.toString().contains("THT")) && ( mobile.toString().equals("01:01:01:01:01"))) {
                        Toast.makeText(RaghHostActivity.this, "No printer Connected.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String BlueMac = mobile;


                        mBluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
                        mBluetoothAdapter = mBluetoothManager.getAdapter();
                        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(BlueMac);
                        ///Toasty.info(getApplicationContext(),"Please active bluetooth"+mBluetoothAdapter.isEnabled(),Toasty.LENGTH_SHORT,true).show();
                        if (!mBluetoothAdapter.isEnabled()) {
                            Toasty.info(getApplicationContext(), "Please active bluetooth", Toasty.LENGTH_SHORT, true).show();
                            android.app.AlertDialog.Builder mybuilder = new android.app.AlertDialog.Builder(RaghHostActivity.this);
                            mybuilder.setTitle("Confirmation")
                                    .setMessage("Do you want to active bluetooth");
                            mybuilder.setPositiveButton("Not Now", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).setNegativeButton("Right Now", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    if (ActivityCompat.checkSelfPermission(RaghHostActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                                        mBluetoothAdapter.enable();
                                        Toasty.info(getApplicationContext(), "Bluetooth is active now.", Toasty.LENGTH_SHORT, true).show();
                                    } else {
                                        mBluetoothAdapter.enable();
                                        Toasty.info(getApplicationContext(), "Bluetooth is active now.", Toasty.LENGTH_SHORT, true).show();
                                    }

                                }
                            }).create().show();

                            return;
                        } else {
                            if (selectcategory.toString().toLowerCase().toString().equals("cpcl")) {
                                Toasty.info(getApplicationContext(), "Bluetooth Device : " + bluename111.getText().toString() + "\n" +
                                        "Mac Address CC: " + BlueMac, Toasty.LENGTH_SHORT, true).show();
                                String sessionname = quantityProductPage_speed.getText().toString();
                                String sessionmobile = progressbarsechk.getText().toString();
                                String sessionphoto = "photo";
                                String sessionemail = "email";
                                ZoneId z = ZoneId.of("Asia/Dhaka");
                                LocalDate today = LocalDate.now(z);
                                String sessionusername = "" + today;
                                scason2.createLoginSession(sessionname, sessionemail, sessionmobile, sessionphoto, sessionusername);
                                printImage1(BlueMac);
                            } else if (selectcategory.toString().toLowerCase().toString().equals("esc")) {
                                int height = Integer.parseInt(myheight) * 8;
                                int width = Integer.parseInt(mywidth) * 8;
                                getValues();

                                String sessionname = name;
                                String sessionmobile = mobile;
                                String sessionphoto = "" + quantityProductPage_speed.getText().toString();
                                String sessionemail = "" + progressbarsechk.getText().toString();
                                ZoneId z = ZoneId.of("Asia/Dhaka");
                                LocalDate today = LocalDate.now(z);
                                String sessionusername = "" + today;
                                session.createLoginSession(sessionname, sessionemail, sessionmobile, sessionphoto, sessionusername);

                                Toasty.info(getApplicationContext(), "Bluetooth Device : " + name + "\n" +
                                        "Mac Address : " + BlueMac, Toasty.LENGTH_SHORT, true).show();
                                printImage2(BlueMac, width, height);
                            }

                        }
                    }

                }

            }
        });
    }
    private void init()
    {
        session = new UserSession(RaghHostActivity.this);
        scason2 = new UserSession2(RaghHostActivity.this);
        connectedornot = findViewById(R.id.connectedornot);
        getValues();
        BluetoothAdapter bluetoothAdapterq=BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapterq == null) {
            // Device doesn't support Bluetooth
            return;
        }

        if (!bluetoothAdapterq.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        if (bluetoothAdapterq.isEnabled()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
                }
            }
        }

        if (bluetoothAdapterq.isEnabled()) {
            if (ContextCompat.checkSelfPermission(RaghHostActivity.this, Manifest.permission.BLUETOOTH_CONNECT)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted, request it
                ActivityCompat.requestPermissions(RaghHostActivity.this,
                        new String[]{Manifest.permission.BLUETOOTH_CONNECT},
                        REQUEST_BLUETOOTH_CONNECT_PERMISSION);
            } else {
                // Permission already granted, proceed with the operation
                // Start your Bluetooth operation or intent here
                if (!bluetoothAdapterq.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
                        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
                } else {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT)
                            != PackageManager.PERMISSION_GRANTED ||
                            ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN)
                                    != PackageManager.PERMISSION_GRANTED) {

                        // Permissions are not granted, request them
                        ActivityCompat.requestPermissions(this,
                                new String[]{
                                        Manifest.permission.BLUETOOTH_CONNECT,
                                        Manifest.permission.BLUETOOTH_SCAN
                                },
                                REQUEST_BLUETOOTH_PERMISSIONS);
                    } else {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN)
                                != PackageManager.PERMISSION_GRANTED) {

                            // Permission is not granted, request it
                            ActivityCompat.requestPermissions(this,
                                    new String[]{Manifest.permission.BLUETOOTH_SCAN},
                                    REQUEST_BLUETOOTH_SCAN_PERMISSION);
                        } else {
                            session = new UserSession(RaghHostActivity.this);
                            scason2 = new UserSession2(RaghHostActivity.this);
                            getValues();
                            connectedornot = findViewById(R.id.connectedornot);
                            //Toast.makeText(this, "Tiki"+mobile, Toast.LENGTH_SHORT).show();
                            connectedornot = findViewById(R.id.connectedornot);
                            // Toast.makeText(this, ""+mobile, Toast.LENGTH_SHORT).show();
                            intttt(mobile.toString());

                            // connectedornot.setText("Not Connected");
                            // connectedornot.setTextColor(Color.parseColor("#FF0000"));
                            //  Drawable icon = getResources().getDrawable(R.drawable.ic_not_connected);
                            //connectedornot.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
                            if (count==0)
                            {
                                //allpaireddevice();
                                //  getValues();
                                //Toast.makeText(this, ""+mobile, Toast.LENGTH_SHORT).show();
                                count++;
                            }
                            else {}

                            // startDiscovery();
                            //allpaireddevice();
                            // Permission already granted, proceed with the Bluetooth scanning operation
                        }

                        // Permissions already granted, proceed with the Bluetooth scanning operation
                    }
                    //
                }
            }
        } else {

        }

    }
    int count =0;
    private void alldevice() {
        //Toast.makeText(this, "TTT"+mobile, Toast.LENGTH_SHORT).show();

        deviceAdapter = new BluetoothDeviceAdapter(this, new ArrayList<>());
        listView.setAdapter(deviceAdapter);
        discoveryReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                    if (ActivityCompat.checkSelfPermission(RaghHostActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        if (device.getName() != null && device.getAddress()!=null) {
                            boolean deviceExists = false;
                            for (int i = 0; i < deviceAdapter.getCount(); i++) {
                                BluetoothDevice existingDevice = deviceAdapter.getItem(i);
                                if (existingDevice != null && existingDevice.getAddress() != null) {
                                    if (existingDevice.getAddress().equals(device.getAddress())) {
                                        deviceExists = true;
                                        break;
                                    }
                                }
                            }

                            if (!deviceExists) {
                                //  Toast.makeText(RaghHostActivity.this, ""+mobile, Toast.LENGTH_SHORT).show();
                                deviceAdapter.add(device);
                                deviceAdapter.notifyDataSetChanged();
//                                Toast.makeText(context, "" + device, Toast.LENGTH_SHORT).show();
                            }

//                            deviceAdapter.add(device);
//                            deviceAdapter.notifyDataSetChanged();
//                           Toast.makeText(context, ""+device, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (device.getName() != null) {
                            boolean deviceExists = false;
                            for (int i = 0; i < deviceAdapter.getCount(); i++) {
                                BluetoothDevice existingDevice = deviceAdapter.getItem(i);
                                if (existingDevice != null && existingDevice.getAddress() != null) {
                                    if (existingDevice.getAddress().equals(device.getAddress())) {
                                        deviceExists = true;
                                        break;
                                    }
                                }
                            }

                            if (!deviceExists) {
                                // Toast.makeText(RaghHostActivity.this, ""+mobile, Toast.LENGTH_SHORT).show();
                                deviceAdapter.add(device);
                                deviceAdapter.notifyDataSetChanged();
//                                Toast.makeText(context, "" + device, Toast.LENGTH_SHORT).show();
                            }

                        }
                        // Toast.makeText(RaghHostActivity.this, "" + device.getName() + "\n" + device.getAddress(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
    }

    private void startDiscovery() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            if (bluetoothAdapter.isDiscovering()) {
                bluetoothAdapter.cancelDiscovery();
            }
            bluetoothAdapter.startDiscovery();

            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(discoveryReceiver, filter);
            alldevice();
            allpaireddevice();
        } else {
            if (bluetoothAdapter.isDiscovering()) {
                bluetoothAdapter.cancelDiscovery();
            }
            bluetoothAdapter.startDiscovery();

            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(discoveryReceiver, filter);
            alldevice();

            //allpaireddevice();
        }

    }

    int REQUEST_BLUETOOTH_CONNECT_PERMISSION = 2;
    int REQUEST_BLUETOOTH_PERMISSIONS = 3;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Toast.makeText(this, "Location", Toast.LENGTH_SHORT).show();
                // startDiscovery();

                if (ContextCompat.checkSelfPermission(RaghHostActivity.this, Manifest.permission.BLUETOOTH_CONNECT)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted, request it
                    ActivityCompat.requestPermissions(RaghHostActivity.this,
                            new String[]{Manifest.permission.BLUETOOTH_CONNECT},
                            REQUEST_BLUETOOTH_CONNECT_PERMISSION);
                    // Toast.makeText(this, "Locatiodddn", Toast.LENGTH_SHORT).show();


                } else {
                    // Permission already granted, proceed with the operation
                    // Start your Bluetooth operation or intent here
                    if (!bluetoothAdapter.isEnabled()) {
                        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
                            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                                    != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
                    } else {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT)
                                != PackageManager.PERMISSION_GRANTED ||
                                ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN)
                                        != PackageManager.PERMISSION_GRANTED) {

                            // Permissions are not granted, request them
                            ActivityCompat.requestPermissions(this,
                                    new String[]{
                                            Manifest.permission.BLUETOOTH_CONNECT,
                                            Manifest.permission.BLUETOOTH_SCAN
                                    },
                                    REQUEST_BLUETOOTH_PERMISSIONS);
                        } else {
                            startDiscovery();
                            //Toast.makeText(this, ""+mobile, Toast.LENGTH_SHORT).show();
                            // Toast.makeText(this, "Start", Toast.LENGTH_SHORT).show();
                            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN)
                                    != PackageManager.PERMISSION_GRANTED) {

                                // Permission is not granted, request it
                                ActivityCompat.requestPermissions(this,
                                        new String[]{Manifest.permission.BLUETOOTH_SCAN},
                                        REQUEST_BLUETOOTH_SCAN_PERMISSION);
                            } else {
                                startDiscovery();
                                //  Toast.makeText(this, ""+mobile, Toast.LENGTH_SHORT).show();
                                //  /(this, "hhhh", Toast.LENGTH_SHORT).show();
                                //alldevice();

                                // Permission already granted, proceed with the Bluetooth scanning operation
                            }

                            // Permissions already granted, proceed with the Bluetooth scanning operation
                        }
                        //
                    }
                }
            }
        } else if (requestCode == REQUEST_BLUETOOTH_CONNECT_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with the Bluetooth operation or intent
                alldevice();
            } else {
                // Permission denied, handle accordingly (e.g., show an explanation or disable functionality)
            }
        } else if (requestCode == REQUEST_BLUETOOTH_PERMISSIONS) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                alldevice();

                // Permissions granted, proceed with the Bluetooth scanning operation
            } else {
                // Permissions denied, handle accordingly (e.g., show an explanation or disable functionality)
            }
        } else if (requestCode == REQUEST_BLUETOOTH_SCAN_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                alldevice();
                // Permission granted, proceed with the Bluetooth scanning operation
            } else {
                // Permission denied, handle accordingly (e.g., show an explanation or disable functionality)
            }
        }
    }

    private void allpaireddevice() {
        //  getValues();
        // mobile="111";
        //Toast.makeText(this, "get", Toast.LENGTH_SHORT).show();
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            // Bluetooth is not supported on this device
            return;
        }
        if (!bluetoothAdapter.isEnabled()) {
            // You can prompt the user to enable Bluetooth here
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
            //  Toast.makeText(this, ""+bluename111.getText().toString(), Toast.LENGTH_SHORT).show();
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceAddress = device.getAddress();
                //  Toast.makeText(this, ""+deviceName, Toast.LENGTH_SHORT).show();
                if(TextUtils.isEmpty(mobile)|| mobile.toString()==null)
                {
//                    connectedornot.setText("Not Connected");
//                    connectedornot.setTextColor(Color.parseColor("#FF0000"));
//                    Drawable icon = getResources().getDrawable(R.drawable.ic_not_connected);
//                    connectedornot.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
                }else
                {
                    if (deviceAddress.equals(mobile)|| deviceAddress.contains(mobile))
                    {
                        try {
                            //  Toast.makeText(this, "12312313213213", Toast.LENGTH_SHORT).show();
                        /*
                        connectedornot.setText("Connected");
                        connectedornot.setTextColor(Color.parseColor("#006400"));
                        Drawable icon = getResources().getDrawable(R.drawable.ic_connected);
                        connectedornot.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
                         */
                            break;
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                    else {


//                    connectedornot.setText("Not Connected");
//                    connectedornot.setTextColor(Color.parseColor("#FF0000"));
//                    Drawable icon = getResources().getDrawable(R.drawable.ic_not_connected);
//                    connectedornot.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);


                    }
                }

                // You can use deviceName and deviceAddress as needed
                // For example, you can display these in a ListView or log them
            }
        }
        else
        {
            //Toast.makeText(this, ""+bluename111.getText().toString(), Toast.LENGTH_SHORT).show();

            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceAddress = device.getAddress();
                // Toast.makeText(this, ""+deviceName, Toast.LENGTH_SHORT).show();
                if(TextUtils.isEmpty(mobile)|| mobile.toString()==null)
                {
//                    connectedornot.setText("Not Connected");
//                    connectedornot.setTextColor(Color.parseColor("#FF0000"));
//                    Drawable icon = getResources().getDrawable(R.drawable.ic_not_connected);
//                    connectedornot.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
                }else
                {
                    if (deviceAddress.equals(mobile)|| deviceAddress.contains(mobile))
                    {
                        try {
                            //    Toast.makeText(this, "12312313213213", Toast.LENGTH_SHORT).show();
                        /*
                        connectedornot.setText("Connected");
                        connectedornot.setTextColor(Color.parseColor("#006400"));
                        Drawable icon = getResources().getDrawable(R.drawable.ic_connected);
                        connectedornot.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
                         */
                            break;
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                    else {


//                        connectedornot.setText("Not Connected");
//                        connectedornot.setTextColor(Color.parseColor("#FF0000"));
//                        Drawable icon = getResources().getDrawable(R.drawable.ic_not_connected);
//                        connectedornot.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);


                    }
                }

                // You can use deviceName and deviceAddress as needed
                // For example, you can display these in a ListView or log them
            }


        }

    }


   /*
    BroadcastReceiver discoveryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (ActivityCompat.checkSelfPermission(RaghHostActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                   // Toast.makeText(RaghHostActivity.this, "" + device.getName() + "\n" + device.getAddress(), Toast.LENGTH_SHORT).show();
                    if ( device.getName() != null) {

                       // BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                        deviceAdapter.add(device);
                        deviceAdapter.notifyDataSetChanged();
                        listView.setAdapter(deviceAdapter);

                    }

                }
                else {
                    Toast.makeText(RaghHostActivity.this, "" + device.getName() + "\n" + device.getAddress(), Toast.LENGTH_SHORT).show();
                }

                // Do something with the discovered device
            }
        }
    };
    */




    public void decrement(View view) {
        int value = Integer.parseInt(quantityProductPage.getText().toString());
        if (value==1) {
            Toasty.info(getApplicationContext(),"It is the lowest value.Print Copy value is not decrement now.", Toast.LENGTH_SHORT,true).show();
            //  Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
        else{
            value=value-1;
            quantityProductPage.setText(""+value);
        }
    }

    public void increment(View view) {
        int value = Integer.parseInt(quantityProductPage.getText().toString());
        if (value==99) {
            Toasty.warning(getApplicationContext(),"It is the highest value. Print Copy value is not increment now.", Toast.LENGTH_SHORT,true).show();
            //Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
        else{
            value=value+1;
            quantityProductPage.setText(""+value);
        }
    }


    //print section
    Uri bitmapUri;
    Bitmap mainimageBitmap;

    int PICK=12;
    boolean request=false;
    CountDownTimer countDownTimer,countDownTimer1;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void decrement_speed(View view) {
        int value = Integer.parseInt(quantityProductPage_speed.getText().toString());
        if (value==1) {
            Toasty.info(getApplicationContext(),"It is the lowest value.Print speed value is not decrement now.", Toast.LENGTH_SHORT,true).show();
            //Toast.makeText(this, "It is the lowest value.Print speed value is not decrement now.", Toast.LENGTH_SHORT).show();
        }
        else{
            value=value-1;
            quantityProductPage_speed.setText(""+value);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
//        if(MyDevicesActivity.DevicesBitmap!=null)
//            MyDevicesActivity.DevicesBitmap=bitmapdataMe;
    }

    public void increment_speed(View view) {
        int value = Integer.parseInt(quantityProductPage_speed.getText().toString());
        if (value==6) {
            Toasty.warning(getApplicationContext(),"It is the highest value. Print Speed value is not increment now.", Toast.LENGTH_SHORT,true).show();
            //   Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
        else{
            value=value+1;
            quantityProductPage_speed.setText(""+value);
        }
    }

    public void dialougeccc(View view) {
        finishAffinity();
    }
    ////bitmap and printing


    private  byte[]  BitmapToRGBbyteA(Bitmap bitmapOrg) {
        ArrayList<Byte> Gray_ArrayList;
        Gray_ArrayList =new ArrayList<Byte>();
        int height = 1080;
        if(bitmapOrg.getHeight()>height)
        {
            height=1080;
        }
        else
        {
            height=bitmapOrg.getHeight();
        }
        int width =384;
        int R = 0, B = 0, G = 0;
        //int pixles;
        int []pixels = new int[width * height];
        int x = 0, y = 0;
        Byte[] Gray_Send;
        //bitSet = new BitSet();
        try {

            bitmapOrg.getPixels(pixels, 0, width, 0, 0, width, height);
            int alpha = 0xFF << 24;
            //int []i_G=new int[7];
            int []i_G=new int[13];
            int Send_Gray=0x00;
            int StartInt=0;
            char  StartWords=' ';

            int k=0;
            int Send_i=0;
            int mathFlag=0;
            for(int i = 0; i < height; i++)
            {

                k=0;
                Send_i=0;
                for (int j = 0; j <width; j++)
                {
                    int grey = pixels[width * i + j];
                    int red = ((grey & 0x00FF0000) >> 16);
                    int green = ((grey & 0x0000FF00) >> 8);
                    int blue = (grey & 0x000000FF);
                    grey = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);


//==================================
                    if(grey>128)
                    {
                        //bufImage[j]=0x00;
                        mathFlag=0;

                    }
                    else
                    {
                        //bufImage[j]=0x01;
                        mathFlag=1;
                    }
                    k++;
                    if(k==1)
                    {
                        Send_i=0;
                        Send_i=Send_i+128*mathFlag;//mathFlag|0x80
                    }
                    else if(k==2)
                    {
                        Send_i=Send_i+64*mathFlag;//mathFlag|0x40
                    }
                    else if(k==3)
                    {
                        Send_i=Send_i+32*mathFlag;//mathFlag|0x20
                    }
                    else if(k==4)
                    {
                        Send_i=Send_i+16*mathFlag;//mathFlag|0x10
                    }
                    else if(k==5)
                    {
                        Send_i=Send_i+8*mathFlag;//mathFlag|0x08
                    }
                    else if(k==6)
                    {
                        Send_i=Send_i+4*mathFlag;//mathFlag|0x04
                    }
                    else if(k==7)
                    {
                        Send_i=Send_i+2*mathFlag;//mathFlag|0x02
                    }
                    else if(k==8)
                    {
                        Send_i=Send_i+1*mathFlag;//mathFlag|0x01
                        Gray_ArrayList.add((byte)Send_i);

                        Send_i=0;
                        k=0;
                    }

                }
                int aBc=0;

            }

            byte[] sss=new byte[Gray_ArrayList.size()];
            Gray_Send=new Byte[Gray_ArrayList.size()];
            Gray_ArrayList.toArray(Gray_Send);
            for(int xx=0;xx<Gray_Send.length;xx++){
                sss[xx]=Gray_Send[xx];
            }
            return  sss;
        } catch (Exception e) {

        }
        return null;
    }
    int bitmapHeight = 1080;
    OutputStream os = null;

    @Override
    public void onBackPressed() {
        finish();
    }

    private void printImage1(String bl) {
        final Bitmap bitmap = bitmapdataMe;
        String speed = quantityProductPage_speed.getText().toString();
        String density = progressbarsechk.getText().toString();

        //  final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dolphin);
        float scax= 384f / bitmap.getWidth();
        float scaly= 384f / bitmap.getHeight();
        Log.e("dolon",""+bitmap);
        Log.e("zzz",""+bitmap.getWidth());
        Log.e("zzz",""+bitmap.getHeight());
        Matrix matrix=new Matrix();
        matrix.postScale(scax,scaly);
        Bitmap resize= bitmap;//Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        Log.e("Ariful9",""+scax);
        //final Bitmap bitmap = bitmapdataMe;

        final byte[] bitmapGetByte = BitmapToRGBbyteA(resize);//convertBitmapToRGBBytes (resize);
        Log.e("Ariful4",""+bitmapGetByte);
        String BlueMac = bl;
        Log.e("Ariful66",""+geeet);
        mBluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(BlueMac);
        ///

        ////
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    /// Toast.makeText(AssenTaskDounwActivity.this, "Done", Toast.LENGTH_SHORT).show();
                    if (ActivityCompat.checkSelfPermission(RaghHostActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        m5ocket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                        m5ocket.connect();



                        for (int i=1;i<=Integer.parseInt(quantityProductPage.getText().toString());i++){
                            os = m5ocket.getOutputStream();

                            if(resize.getHeight()>bitmapHeight)
                            {
                                bitmapHeight=1080;
                            }
                            else
                            {
                                bitmapHeight=resize.getHeight();
                            }
                            Log.e("Ariful1",""+resize.getWidth());
                            Log.e("Ariful2",""+resize.getHeight());
                            Log.e("Ariful3",""+bitmap);
                            Random random=new Random();
                            int sendingnumber=random.nextInt(10);
                            int mimisecond=sendingnumber*1000;
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    // write your code here
                                    countDownTimer =new CountDownTimer(2000,1000) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {

                                            double seconddd=millisUntilFinished/1000;
                                            printtimer.setText("Sending Data : "+seconddd+" S");



                                        }

                                        @Override
                                        public void onFinish() {
                                            try {
                                                String t_line1 = "! 0 200 200 "+bitmapHeight+" 1 \r\n";//bitmap.getHeight()
                                                String t_line2 = "pw "+384+"\r\n";
                                                String t_line3 = "DENSITY "+density+"\r\n";
                                                String t_line4 = "SPEED "+speed+"\r\n";
                                                String t_line5 = "CG "+384/8+" "+bitmapHeight+" 0 0 ";
                                                String t_line6 ="PR 0\r\n";
                                                String t_line7= "FORM\r\n";
                                                String t_line8 = "PRINT\r\n";
                                                String t_line9 = "\r\n";
                                                os.write(t_line1.getBytes());
                                                os.write(t_line2.getBytes());
                                                os.write(t_line3.getBytes());
                                                os .write(t_line4.getBytes());
                                                os .write(t_line5.getBytes());

                                                os.write(bitmapGetByte);
                                                os .write(t_line9.getBytes());
                                                os .write(t_line6.getBytes());
                                                os.write(t_line7.getBytes());
                                                os.write(t_line8.getBytes());
                                                Log.e("Ariful5","PrintCommand");
                                            }catch (Exception e) {
                                                e.printStackTrace();
                                                Log.e("Ariful6",""+e.getMessage());
                                            }
                                            countDownTimer1=new CountDownTimer(1000,1000) {
                                                @Override
                                                public void onTick(long millisUntilFinished) {
                                                    long second=  (millisUntilFinished/1000);
                                                    int mysecond=Integer.parseInt(String.valueOf(second));



                                                }

                                                @Override
                                                public void onFinish() {

                                                    printtimer.setText("Print Out");
                                                    try {

                                                        os.flush();
                                                        //os.flush();
                                                        m5ocket.close();
                                                        if (print_flag==0)
                                                        {
                                                            Store_Speed();
                                                            print_flag++;
                                                        }
                                                        else{
                                                            Store_Speed();
                                                        }
                                                        Log.e("Ariful7","Go to print");

                                                    }catch (Exception e) {
                                                        e.printStackTrace();
                                                        Log.e("Ariful8",""+e.getMessage());
                                                    }

                                                    Toasty.success(getApplicationContext(),"Data Sending Complete",Toasty.LENGTH_SHORT,true).show();
                                                    return;
                                                }
                                            }.start();
                                            countDownTimer1.start();


                                        }
                                    };
                                    countDownTimer.start();
                                }
                            });
                        }

                    }
                    else {

                    }




                } catch (IOException e) {
                    // Toast.makeText(CPCLFresh.this, "Try Again. Bluetooth Connection Problem.", Toast.LENGTH_SHORT).show();
                    // printtimer.setText("Try Again. Bluetooth Connection Problem.");
                    Log.e("Error : ",""+e.getMessage());
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, e.getMessage(), Snackbar.LENGTH_SHORT).show();

                }
            }
        });
        thread.start();//1
    }

    //for esc

    boolean isConnected = false;
    private void printImage2(String bl,int mainimagewidth, int mainimageheight) {
        //Toast.makeText(this, "get", Toast.LENGTH_SHORT).show();

        final Bitmap bitmap = bitmapdataMe;
        float bitw = (float) mainimagewidth;
        float bith = (float)mainimageheight;

        // final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dolphin);
        float scax=bitw /bitmap.getWidth();
        float scaly=bith / bitmap.getHeight();
        Log.e("dolon",""+bitmap);
        Log.e("zzz",""+bitmap.getWidth());
        Log.e("zzz",""+bitmap.getHeight());
        Matrix matrix=new Matrix();
        matrix.postScale(scax,scaly);
        Bitmap resize=bitmap; //Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        Log.e("Ariful9",""+scax);
        //final Bitmap bitmap = bitmapdataMe;

        final byte[] bitmapGetByte = BitmapToRGBbyteAA(resize);//convertBitmapToRGBBytes (resize);
        Log.e("Ariful4",""+bitmapGetByte);
        String BlueMac = bl;
        Log.e("Ariful66",""+bl);
        mBluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(BlueMac);
        int n = Integer.parseInt(quantityProductPage.getText().toString());


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    /// Toast.makeText(AssenTaskDounwActivity.this, "Done", Toast.LENGTH_SHORT).show();
                    if (ActivityCompat.checkSelfPermission(RaghHostActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        if (isConnected==false)
                        {
                            m5ocket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                            m5ocket.connect();
                            isConnected= true;
                        }
                        else{
                            m5ocket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                            m5ocket.connect();
                            isConnected= true;
                        }
                        for(int i = 1; i<=n;i++)//angenl111
                        {
                            os = m5ocket.getOutputStream();

                            if(resize.getHeight()>bitmapHeight)
                            {
                                bitmapHeight=1080;
                            }
                            else
                            {
                                bitmapHeight=resize.getHeight();
                            }
                            bitmapWidth=resize.getWidth();
                            Log.e("Ariful1",""+resize.getWidth());
                            Log.e("Ariful2",""+resize.getHeight());
                            Log.e("Ariful3",""+bitmap);
                            Random random=new Random();
                            int sendingnumber=random.nextInt(10);
                            int mimisecond=sendingnumber*1000;


                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    // write your code here
                                    countDownTimer =new CountDownTimer(2000,1000) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {

                                            double seconddd=millisUntilFinished/1000;
                                            printtimer.setText("Sending Data : "+seconddd+" S");



                                        }

                                        @Override
                                        public void onFinish() {
                                            try {



                                                int StartInt=0;
                                                int PrintSpeedNow=Integer.parseInt(quantityProductPage_speed.getText().toString());
                                                int PrintDensityNow=Integer.parseInt(progressbarsechk.getText().toString());
                                                int PrintPaperTypeNow=0;
                                                StartInt=0x1d;
                                                //Gray_Arraylist.add((byte)StartInt);
                                                os.write((byte)StartInt);
                                                StartInt=0x0e;
                                                os.write((byte)StartInt);
                                                StartInt=0x1c;
                                                os.write((byte)StartInt);
                                                StartInt=0x60;
                                                os.write((byte)StartInt);

                                                StartInt=0x4D;
                                                os.write((byte)StartInt);
                                                StartInt=0x53;
                                                os.write((byte)StartInt);
                                                if(PrintSpeedNow==6)
                                                {
                                                    StartInt=0x01;//PrintSpeedNow
                                                }
                                                else if(PrintSpeedNow==5)
                                                {
                                                    StartInt=0x0b;
                                                }
                                                else if(PrintSpeedNow==4)
                                                {
                                                    StartInt=0x15;
                                                }
                                                else if(PrintSpeedNow==3)
                                                {
                                                    StartInt=0x1f;
                                                }
                                                else if(PrintSpeedNow==2)
                                                {
                                                    StartInt=0x29;
                                                }
                                                else
                                                {
                                                    StartInt=0x33;
                                                }
                                                os.write((byte)StartInt);
                                                StartInt=0x1c;
                                                os.write((byte)StartInt);
                                                StartInt=0x60;
                                                os.write((byte)StartInt);
                                                StartInt=0x7E;
                                                os.write((byte)StartInt);
                                                StartInt=0x7E;
                                                os.write((byte)StartInt);
                                                StartInt=PrintDensityNow+0;//PrintDensityNow
                                                os.write((byte)StartInt);
                                                StartInt=0x1c;
                                                os.write((byte)StartInt);
                                                StartInt=0x60;
                                                os.write((byte)StartInt);
                                                StartInt=0x50;
                                                os.write((byte)StartInt);
                                                StartInt=0x50;
                                                os.write((byte)StartInt);
                                                StartInt=PrintPaperTypeNow+0;
                                                os.write((byte)StartInt);
                                                StartInt=0x1d;
                                                os.write((byte)StartInt);
                                                StartInt=0x76;
                                                os.write((byte)StartInt);
                                                StartInt=0x30;
                                                os.write((byte)StartInt);
                                                StartInt=0x00;
                                                os.write((byte)StartInt);
                                                int widthH=bitmapWidth/8/256;
                                                int widthL=bitmapWidth/8%256;
                                                int heightH=bitmapHeight/256;
                                                int heightL=bitmapHeight%256;
                                                StartInt=widthL+0;//PrintDensityNow
                                                os.write((byte)StartInt);
                                                StartInt=widthH+0;//PrintDensityNow
                                                os.write((byte)StartInt);
                                                StartInt=heightL+0;//PrintDensityNow
                                                os.write((byte)StartInt);
                                                StartInt=heightH+0;//PrintDensityNow
                                                os.write((byte)StartInt);
                                                os.write(bitmapGetByte);
                                                StartInt=0x1c;
                                                os.write((byte)StartInt);
                                                StartInt=0x5e;
                                                os.write((byte)StartInt);
                                                Log.e("Ariful5","PrintCommand");
                                            }catch (Exception e) {
                                                e.printStackTrace();
                                                Log.e("Ariful6",""+e.getMessage());
                                            }
                                            countDownTimer1=new CountDownTimer(1000,1000) {
                                                @Override
                                                public void onTick(long millisUntilFinished) {
                                                    long second=  (millisUntilFinished/1000);
                                                    int mysecond=Integer.parseInt(String.valueOf(second));



                                                }

                                                @Override
                                                public void onFinish() {

                                                    printtimer.setText("Print Out");
                                                    try {

                                                        os.flush();
                                                        //os.flush();
                                                        m5ocket.close();
                                                        if (print_flag==0)
                                                        {
                                                            // Store_Speed();
                                                            print_flag++;
                                                        }
                                                        else{
                                                            print_flag++;
                                                        }
                                                        Log.e("Ariful7","Go to print");

                                                    }catch (Exception e) {
                                                        e.printStackTrace();
                                                        Log.e("Ariful8",""+e.getMessage());
                                                    }

                                                    Toasty.success(getApplicationContext(),"Data Sending Complete",Toasty.LENGTH_SHORT,true).show();
                                                    return;
                                                }
                                            }.start();
                                            countDownTimer1.start();


                                        }
                                    };
                                    countDownTimer.start();
                                }
                            });
                            // thread

                        }




                    }
                    else {
                        if (isConnected==false)
                        {
                            m5ocket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                            m5ocket.connect();
                            isConnected= true;
                        }
                        else{
                            m5ocket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                            m5ocket.connect();
                            isConnected= true;
                        }
                        for(int i = 1; i<=n;i++)//angenl111
                        {
                            os = m5ocket.getOutputStream();

                            if(resize.getHeight()>bitmapHeight)
                            {
                                bitmapHeight=1080;
                            }
                            else
                            {
                                bitmapHeight=resize.getHeight();
                            }
                            bitmapWidth=resize.getWidth();
                            Log.e("Ariful1",""+resize.getWidth());
                            Log.e("Ariful2",""+resize.getHeight());
                            Log.e("Ariful3",""+bitmap);
                            Random random=new Random();
                            int sendingnumber=random.nextInt(10);
                            int mimisecond=sendingnumber*1000;


                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    // write your code here
                                    countDownTimer =new CountDownTimer(2000,1000) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {

                                            double seconddd=millisUntilFinished/1000;
                                            printtimer.setText("Sending Data : "+seconddd+" S");



                                        }

                                        @Override
                                        public void onFinish() {
                                            try {



                                                int StartInt=0;
                                                int PrintSpeedNow=Integer.parseInt(quantityProductPage_speed.getText().toString());
                                                int PrintDensityNow=Integer.parseInt(progressbarsechk.getText().toString());
                                                int PrintPaperTypeNow=0;
                                                StartInt=0x1d;
                                                //Gray_Arraylist.add((byte)StartInt);
                                                os.write((byte)StartInt);
                                                StartInt=0x0e;
                                                os.write((byte)StartInt);
                                                StartInt=0x1c;
                                                os.write((byte)StartInt);
                                                StartInt=0x60;
                                                os.write((byte)StartInt);

                                                StartInt=0x4D;
                                                os.write((byte)StartInt);
                                                StartInt=0x53;
                                                os.write((byte)StartInt);
                                                if(PrintSpeedNow==6)
                                                {
                                                    StartInt=0x01;//PrintSpeedNow
                                                }
                                                else if(PrintSpeedNow==5)
                                                {
                                                    StartInt=0x0b;
                                                }
                                                else if(PrintSpeedNow==4)
                                                {
                                                    StartInt=0x15;
                                                }
                                                else if(PrintSpeedNow==3)
                                                {
                                                    StartInt=0x1f;
                                                }
                                                else if(PrintSpeedNow==2)
                                                {
                                                    StartInt=0x29;
                                                }
                                                else
                                                {
                                                    StartInt=0x33;
                                                }
                                                os.write((byte)StartInt);
                                                StartInt=0x1c;
                                                os.write((byte)StartInt);
                                                StartInt=0x60;
                                                os.write((byte)StartInt);
                                                StartInt=0x7E;
                                                os.write((byte)StartInt);
                                                StartInt=0x7E;
                                                os.write((byte)StartInt);
                                                StartInt=PrintDensityNow+0;//PrintDensityNow
                                                os.write((byte)StartInt);
                                                StartInt=0x1c;
                                                os.write((byte)StartInt);
                                                StartInt=0x60;
                                                os.write((byte)StartInt);
                                                StartInt=0x50;
                                                os.write((byte)StartInt);
                                                StartInt=0x50;
                                                os.write((byte)StartInt);
                                                StartInt=PrintPaperTypeNow+0;
                                                os.write((byte)StartInt);
                                                StartInt=0x1d;
                                                os.write((byte)StartInt);
                                                StartInt=0x76;
                                                os.write((byte)StartInt);
                                                StartInt=0x30;
                                                os.write((byte)StartInt);
                                                StartInt=0x00;
                                                os.write((byte)StartInt);
                                                int widthH=bitmapWidth/8/256;
                                                int widthL=bitmapWidth/8%256;
                                                int heightH=bitmapHeight/256;
                                                int heightL=bitmapHeight%256;
                                                StartInt=widthL+0;//PrintDensityNow
                                                os.write((byte)StartInt);
                                                StartInt=widthH+0;//PrintDensityNow
                                                os.write((byte)StartInt);
                                                StartInt=heightL+0;//PrintDensityNow
                                                os.write((byte)StartInt);
                                                StartInt=heightH+0;//PrintDensityNow
                                                os.write((byte)StartInt);
                                                os.write(bitmapGetByte);
                                                StartInt=0x1c;
                                                os.write((byte)StartInt);
                                                StartInt=0x5e;
                                                os.write((byte)StartInt);
                                                Log.e("Ariful5","PrintCommand");
                                            }catch (Exception e) {
                                                e.printStackTrace();
                                                Log.e("Ariful6",""+e.getMessage());
                                            }
                                            countDownTimer1=new CountDownTimer(1000,1000) {
                                                @Override
                                                public void onTick(long millisUntilFinished) {
                                                    long second=  (millisUntilFinished/1000);
                                                    int mysecond=Integer.parseInt(String.valueOf(second));



                                                }

                                                @Override
                                                public void onFinish() {

                                                    printtimer.setText("Print Out");
                                                    try {

                                                        os.flush();
                                                        //os.flush();
                                                        m5ocket.close();
                                                        if (print_flag==0)
                                                        {
                                                            // Store_Speed();
                                                            print_flag++;
                                                        }
                                                        else{
                                                            print_flag++;
                                                        }
                                                        Log.e("Ariful7","Go to print");

                                                    }catch (Exception e) {
                                                        e.printStackTrace();
                                                        Log.e("Ariful8",""+e.getMessage());
                                                    }

                                                    Toasty.success(getApplicationContext(),"Data Sending Complete",Toasty.LENGTH_SHORT,true).show();
                                                    return;
                                                }
                                            }.start();
                                            countDownTimer1.start();


                                        }
                                    };
                                    countDownTimer.start();
                                }
                            });
                            // thread

                        }



                        //View parentLayout = findViewById(android.R.id.content);
                        //Snackbar.make(parentLayout," e.getMessage()", Snackbar.LENGTH_SHORT).show();
                    }




                } catch (IOException e) {
                    // Toast.makeText(CPCLFresh.this, "Try Again. Bluetooth Connection Problem.", Toast.LENGTH_SHORT).show();
                    // printtimer.setText("Try Again. Bluetooth Connection Problem.");
                    Log.e("Error : ",""+e.getMessage());
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, e.getMessage(), Snackbar.LENGTH_SHORT).show();

                }
            }
        });
        thread.start();

    }
    private  byte[]  BitmapToRGBbyteAA(Bitmap bitmapOrg) {
        ArrayList<Byte> Gray_ArrayList;
        Gray_ArrayList =new ArrayList<Byte>();
        int height =1080;
        if(bitmapOrg.getHeight()>height)
        {
            height=1080;
        }
        else
        {
            height=bitmapOrg.getHeight();
        }
        int width =bitmapOrg.getWidth();
        int R = 0, B = 0, G = 0;
        //int pixles;
        int []pixels = new int[width * height];
        int x = 0, y = 0;
        Byte[] Gray_Send;
        //bitSet = new BitSet();
        try {

            bitmapOrg.getPixels(pixels, 0, width, 0, 0, width, height);
            int alpha = 0xFF << 24;
            //int []i_G=new int[7];
            int []i_G=new int[13];
            int Send_Gray=0x00;
            int StartInt=0;
            char  StartWords=' ';

            int k=0;
            int Send_i=0;
            int mathFlag=0;
            for(int i = 0; i < height; i++)
            {

                k=0;
                Send_i=0;
                for (int j = 0; j <width; j++)
                {
                    int grey = pixels[width * i + j];
                    int red = ((grey & 0x00FF0000) >> 16);
                    int green = ((grey & 0x0000FF00) >> 8);
                    int blue = (grey & 0x000000FF);
                    grey = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);


//==================================
                    if(grey>128)
                    {
                        //bufImage[j]=0x00;
                        mathFlag=0;

                    }
                    else
                    {
                        //bufImage[j]=0x01;
                        mathFlag=1;
                    }
                    k++;
                    if(k==1)
                    {
                        Send_i=0;
                        Send_i=Send_i+128*mathFlag;//mathFlag|0x80
                    }
                    else if(k==2)
                    {
                        Send_i=Send_i+64*mathFlag;//mathFlag|0x40
                    }
                    else if(k==3)
                    {
                        Send_i=Send_i+32*mathFlag;//mathFlag|0x20
                    }
                    else if(k==4)
                    {
                        Send_i=Send_i+16*mathFlag;//mathFlag|0x10
                    }
                    else if(k==5)
                    {
                        Send_i=Send_i+8*mathFlag;//mathFlag|0x08
                    }
                    else if(k==6)
                    {
                        Send_i=Send_i+4*mathFlag;//mathFlag|0x04
                    }
                    else if(k==7)
                    {
                        Send_i=Send_i+2*mathFlag;//mathFlag|0x02
                    }
                    else if(k==8)
                    {
                        Send_i=Send_i+1*mathFlag;//mathFlag|0x01
                        Gray_ArrayList.add((byte)Send_i);

                        Send_i=0;
                        k=0;
                    }

                }
                int aBc=0;

            }


            byte[] sss=new byte[Gray_ArrayList.size()];
            Gray_Send=new Byte[Gray_ArrayList.size()];
            Gray_ArrayList.toArray(Gray_Send);
            for(int xx=0;xx<Gray_Send.length;xx++){
                sss[xx]=Gray_Send[xx];
            }
            return  sss;
        } catch (Exception e) {

        }
        return null;
    }
    int print_flag = 0;

    int bitmapWidth=384;
    public  void Store_Speed()
    {
        String density = progressbarsechk.getText().toString();
        String speed = quantityProductPage_speed.getText().toString();
        String email = "abc@gmail.com";
        DensityModel densityModel=new DensityModel(speed,density,email);

        firebaseFirestore.collection("DensityAndSpeed")
                .document("abc@gmail.com")
                .set(densityModel)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

    private void getValues() {
        //validating session


        try {
            //get User details if logged in
            session.isLoggedIn();
            user = session.getUserDetails();

            name = user.get(UserSession.KEY_NAME);
            email = user.get(UserSession.KEY_EMAIL);
            mobile = user.get(UserSession.KEY_MOBiLE);
            photo = user.get(UserSession.KEY_PHOTO);
            username=user.get(UserSession.Username);
            //Toast.makeText(this, ""+photo+""+email, Toast.LENGTH_SHORT).show();




        }catch (Exception e) {
            // Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        //Toast.makeText(this, ""+username, Toast.LENGTH_SHORT).show();
    }
    //get device
    private class BluetoothDeviceAdapter extends ArrayAdapter<BluetoothDevice> {
        private LayoutInflater inflater;
        private Animation topAnimation, bottomAnimation, startAnimation, endAnimation;
        private SharedPreferences onBoardingPreference;
        FirebaseFirestore firebaseFirestore;
        private List<String> addedMacAddresses;
        String bluetoothName;
        UserSession userSession;
        private HashMap<String, String> user;
        private String name, email, photo, mobile,username;
        public BluetoothDeviceAdapter(Context context, List<BluetoothDevice> devices) {
            super(context, 0, devices);
            inflater = LayoutInflater.from(context);
            addedMacAddresses = new ArrayList<>();
            userSession = new UserSession(context);
            getValues();;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.show2, parent, false);


            }
            BluetoothDevice device = getItem(position);
            String deviceAddress = device.getAddress();

            // Check if the device's MAC address is already added to the list
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (bluetoothAdapter != null) {
                if (ActivityCompat.checkSelfPermission(RaghHostActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    bluetoothName = bluetoothAdapter.getName();
                } else {
                    bluetoothName = bluetoothAdapter.getName();
                }


                // Use the Bluetooth name as needed
            } else {
                // Bluetooth is not supported on this device
                bluetoothName = "unknown";
            }


            TextView deviceNameTextView = view.findViewById(R.id.listedd);


            deviceNameTextView.setText(device.getName() + "\n" + device.getAddress());

            RelativeLayout carditem = view.findViewById(R.id.carditem);

            firebaseFirestore = FirebaseFirestore.getInstance();


            carditem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ActivityCompat.checkSelfPermission(RaghHostActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        //Toast.makeText(v.getContext(), device.getName() + "\n" + device.getAddress(), Toast.LENGTH_SHORT).show();
                        String BlueMac = "FB:7F:9B:F2:20:B7";
                        String bluename = device.getName();
                        String bluemac= device.getAddress();
                        Connected_Model connected_model=new Connected_Model(bluename,bluemac,""+bluetoothName);
                        /*
                        if (TextUtils.isEmpty(photo)&& TextUtils.isEmpty(email))
                        {
                            String sessionname = device.getName();
                            String sessionmobile =device.getAddress();
                            String sessionphoto ="photo";
                            String sessionemail = "email";
                            ZoneId z = ZoneId.of( "Asia/Dhaka" );
                            LocalDate today = LocalDate.now( z );
                            String sessionusername = ""+today;

                            userSession.createLoginSession(sessionname,sessionemail,sessionmobile,sessionphoto,sessionusername);
                        }
                        else {
                            String sessionname = device.getName();
                            String sessionmobile =device.getAddress();
                            String sessionphoto =""+photo;
                            String sessionemail = ""+email;
                            ZoneId z = ZoneId.of( "Asia/Dhaka" );
                            LocalDate today = LocalDate.now( z );
                            String sessionusername = ""+today;

                            userSession.createLoginSession(sessionname,sessionemail,sessionmobile,sessionphoto,sessionusername);
                        }
                         */
                        String sessionname = device.getName();
                        String sessionmobile =device.getAddress();
                        String sessionphoto =""+"6";
                        String sessionemail = ""+"12";
                        ZoneId z = ZoneId.of( "Asia/Dhaka" );
                        LocalDate today = LocalDate.now( z );
                        String sessionusername = ""+today;
                        allpaireddevice();
                        connectedornot.setText("Connected");
                        macdetails.setText(device.getName()+" "+device.getAddress());
                        connectedornot.setTextColor(Color.parseColor("#006400"));
                        Drawable icon = getResources().getDrawable(R.drawable.ic_connected);
                        connectedornot.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
                        bottomSheetDialog11.dismiss();

                        userSession.createLoginSession(sessionname,sessionemail,sessionmobile,sessionphoto,sessionusername);
                        // Toast.makeText(RaghHostActivity.this, "Bluetooth Saved", Toast.LENGTH_SHORT).show();

                       /*
                        firebaseFirestore.collection("Connected_Device")
                                .document(""+bluetoothName)
                                .set(connected_model)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful())
                                        {
                                            v.getContext().startActivity(new Intent(v.getContext(),SecondActivity.class));
                                        }
                                    }
                                });
                        */
                    }
                    else {
                        // Toast.makeText(v.getContext(), device.getName() + "\n" + device.getAddress(), Toast.LENGTH_SHORT).show();
                        String BlueMac = "FB:7F:9B:F2:20:B7";
                        String bluename = device.getName();
                        String bluemac= device.getAddress();
                        Connected_Model connected_model=new Connected_Model(bluename,bluemac,""+bluetoothName);
                       /*
                        if (TextUtils.isEmpty(photo)&& TextUtils.isEmpty(email))
                        {
                            String sessionname = device.getName();
                            String sessionmobile =device.getAddress();
                            String sessionphoto ="photo";
                            String sessionemail = "email";
                            ZoneId z = ZoneId.of( "Asia/Dhaka" );
                            LocalDate today = LocalDate.now( z );
                            String sessionusername = ""+today;

                            userSession.createLoginSession(sessionname,sessionemail,sessionmobile,sessionphoto,sessionusername);
                        }
                        else {
                            String sessionname = device.getName();
                            String sessionmobile =device.getAddress();
                            String sessionphoto =""+photo;
                            String sessionemail = ""+email;
                            ZoneId z = ZoneId.of( "Asia/Dhaka" );
                            LocalDate today = LocalDate.now( z );
                            String sessionusername = ""+today;

                            userSession.createLoginSession(sessionname,sessionemail,sessionmobile,sessionphoto,sessionusername);
                        }
                        */
                        String sessionname = device.getName();
                        String sessionmobile =device.getAddress();
                        String sessionphoto =""+"6";
                        String sessionemail = ""+"12";
                        ZoneId z = ZoneId.of( "Asia/Dhaka" );
                        LocalDate today = LocalDate.now( z );
                        String sessionusername = ""+today;
                        allpaireddevice();
                        connectedornot.setText("Connected");
                        macdetails.setText(device.getName()+" "+device.getAddress());
                        connectedornot.setTextColor(Color.parseColor("#006400"));
                        Drawable icon = getResources().getDrawable(R.drawable.ic_connected);
                        connectedornot.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
                        bottomSheetDialog11.dismiss();

                        userSession.createLoginSession(sessionname,sessionemail,sessionmobile,sessionphoto,sessionusername);
                        //Toast.makeText(RaghHostActivity.this, "Bluetooth Saved", Toast.LENGTH_SHORT).show();
                      /*
                        firebaseFirestore.collection("Connected_Device")
                                .document(""+bluetoothName)
                                .set(connected_model)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful())
                                        {
                                            v.getContext().startActivity(new Intent(v.getContext(),SecondActivity.class));
                                        }
                                    }
                                });
                       */
                    }

                }
            });
            return view;
        }
        private void getValues() {
            //validating session


            try {
                //get User details if logged in
                userSession.isLoggedIn();
                user = userSession.getUserDetails();

                name = user.get(UserSession.KEY_NAME);
                if (TextUtils.isEmpty(name)|| name.toString()==null)
                {
                    name = "Unknown";
                }
                else {
                    name=name;
                }
                email = user.get(UserSession.KEY_EMAIL);
                mobile = user.get(UserSession.KEY_MOBiLE);
                photo = user.get(UserSession.KEY_PHOTO);
                username=user.get(UserSession.Username);
                if (TextUtils.isEmpty(mobile)|| mobile.toString()==null)
                {
                    mobile = "FB:7F:9B:F2:20:B7";
                }
                else {
                    mobile=mobile;
                }
                //Toast.makeText(this, ""+photo+""+email, Toast.LENGTH_SHORT).show();




            }catch (Exception e) {
                // Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            //Toast.makeText(this, ""+username, Toast.LENGTH_SHORT).show();
        }
    }

    //2
    private void getValues1() {
        //validating session


        try {
            //get User details if logged in
            scason2.isLoggedIn();
            user2 = scason2.getUserDetails();

            name2 = user2.get(UserSession.KEY_NAME);
            if (TextUtils.isEmpty(name2)|| name2.toString()==null)
            {
                name2 = "Unknown";
            }
            else {
                name2=name2;
            }
            email2 = user2.get(UserSession.KEY_EMAIL);
            mobile2 = user2.get(UserSession.KEY_MOBiLE);
            photo2 = user2.get(UserSession.KEY_PHOTO);
            username2=user2.get(UserSession.Username);
            if (TextUtils.isEmpty(mobile2)|| mobile2.toString()==null)
            {
                mobile2 = "FB:7F:9B:F2:20:B7";
            }
            else {
                mobile2=mobile2;
            }
            //Toast.makeText(this, ""+photo+""+email, Toast.LENGTH_SHORT).show();




        }catch (Exception e) {
            // Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        //Toast.makeText(this, ""+username, Toast.LENGTH_SHORT).show();
    }
    void intttt(String macadressss)
    {
        BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        connectedornot = findViewById(R.id.connectedornot);

        if (bluetoothAdapter.isEnabled())
        {
            if (bluetoothAdapter == null) {

                return;
            }
            if (!bluetoothAdapter.isEnabled()) {

                return;
            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "jjjjjj", Toast.LENGTH_SHORT).show();
            }
            else
            {
                connectedornot = findViewById(R.id.connectedornot);

                Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
                for (BluetoothDevice device : pairedDevices) {
                    String deviceName = device.getName();
                    String deviceAddress = device.getAddress();

                    if(TextUtils.isEmpty(macadressss)|| macadressss.toString()==null)
                    {
                        //Toast.makeText(this, "Not"+macadressss, Toast.LENGTH_SHORT).show();

                        connectedornot.setText("Not Connected");
                        connectedornot.setTextColor(Color.parseColor("#FF0000"));
                        Drawable icon = getResources().getDrawable(R.drawable.ic_not_connected);
                        connectedornot.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);

                        //Toast.makeText(this, "not", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        if (deviceAddress.equals(macadressss)|| deviceAddress.contains(macadressss))
                        {
                            try {



                                connectedornot.setText("Connected");
                                macdetails.setText(deviceName+" "+deviceAddress);
                                connectedornot.setTextColor(Color.parseColor("#006400"));
                                Drawable icon = getResources().getDrawable(R.drawable.ic_connected);
                                connectedornot.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);

                                // Toast.makeText(this, "got"+macadressss, Toast.LENGTH_SHORT).show();

                                break;
                            }catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                        else {
                            //Toast.makeText(this, "Not"+macadressss, Toast.LENGTH_SHORT).show();



                            connectedornot.setText("Not Connected");

                            connectedornot.setTextColor(Color.parseColor("#FF0000"));
                            Drawable icon = getResources().getDrawable(R.drawable.ic_not_connected);
                            connectedornot.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);



                        }
                    }

                }


            }
        }

        //Toast.makeText(this, "mac"+macadressss, Toast.LENGTH_SHORT).show();
    }
}
//for devices

