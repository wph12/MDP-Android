package com.example.mdp_group31.main;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mdp_group31.MainActivity;
import com.example.mdp_group31.R;

public class MapTabFragment extends Fragment {

    private static final String TAG = "MapFragment";

    SharedPreferences mapPref;
    private static SharedPreferences.Editor editor;

    Button resetMapBtn, saveMapObstacle, loadMapObstacle;
    ImageButton directionChangeImageBtn;
    ToggleButton setStartPointToggleBtn, obstacleImageBtn;
    GridMap gridMap;

    Switch dragSwitch;
    Switch changeObstacleSwitch;

    static String imageID="";
    static String imageBearing="North";
    static boolean dragStatus;
    static boolean changeObstacleStatus;

    private MainActivity mainActivity;

    public MapTabFragment(MainActivity main) {
        this.mainActivity = main;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_map_config, container, false);

        gridMap = this.mainActivity.getGridMap();
        final DirectionFragment directionFragment = new DirectionFragment();
        final SaveMapFragment saveMapFragment = new SaveMapFragment();
        final LoadMapFragment loadMapFragment = new LoadMapFragment();

        resetMapBtn = root.findViewById(R.id.resetBtn);
        setStartPointToggleBtn = root.findViewById(R.id.startpointToggleBtn);
        directionChangeImageBtn = root.findViewById(R.id.changeDirectionBtn);
        obstacleImageBtn = root.findViewById(R.id.addObstacleBtn);
        saveMapObstacle = root.findViewById(R.id.saveBtn);
        loadMapObstacle = root.findViewById(R.id.loadBtn);
        dragSwitch = root.findViewById(R.id.dragSwitch);
        changeObstacleSwitch = root.findViewById(R.id.changeObstacleSwitch);


        resetMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLog("Clicked resetMapBtn");
                showToast("Reseting map...");
                gridMap.resetMap(true);
            }
        });

        // switch for dragging
        dragSwitch.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton toggleButton, boolean isChecked) {
                showToast("Dragging is " + (isChecked ? "on" : "off"));
                dragStatus = isChecked;
                if (dragStatus) {
                    gridMap.setSetObstacleStatus(false);
                    if (setStartPointToggleBtn.isChecked()) {
                        setStartPointToggleBtn.toggle();
                    }
                    if (obstacleImageBtn.isChecked()) {
                        obstacleImageBtn.toggle();
                    }
                    changeObstacleSwitch.setChecked(false);
                }
            }
        });

        // switch for changing obstacle
        changeObstacleSwitch.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton toggleButton, boolean isChecked) {
                showToast("Changing Obstacle is " + (isChecked ? "on" : "off"));
                changeObstacleStatus = isChecked;
                if (changeObstacleStatus) {
                    gridMap.setSetObstacleStatus(false);
                    if (setStartPointToggleBtn.isChecked()) {
                        setStartPointToggleBtn.toggle();
                    }
                    if (obstacleImageBtn.isChecked()) {
                        obstacleImageBtn.toggle();
                    }
                    dragSwitch.setChecked(false);
                }
            }
        });

        setStartPointToggleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLog("Clicked setStartPointToggleBtn");
                if (setStartPointToggleBtn.getText().equals("SET START POINT")) {
                    gridMap.setCanDrawRobot(false);
                    gridMap.setStartCoordStatus(false);
                    gridMap.toggleCheckedBtn("setStartPointToggleBtn");
                }
                else if (setStartPointToggleBtn.getText().equals("CANCEL")) {
                    gridMap.setStartCoordStatus(true);
                    gridMap.setCanDrawRobot(true);
                    gridMap.toggleCheckedBtn("setStartPointToggleBtn");
                }
                changeObstacleSwitch.setChecked(false);
                dragSwitch.setChecked(false);
            }
        });

        saveMapObstacle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMapFragment.show(getActivity().getFragmentManager(),
                        "SaveMapFragment");
            }
        });

        loadMapObstacle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gridMap.resetMap(true);
                loadMapFragment.show(getActivity().getFragmentManager(),
                        "LoadMapFragment");
            }
        });

        directionChangeImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLog("Clicked directionChangeImageBtn");
                directionFragment.show(getActivity().getFragmentManager(),
                        "Direction Fragment");
                showLog("Exiting directionChangeImageBtn");
            }
        });

        obstacleImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLog("Clicked obstacleImageBtn");

                if (!gridMap.getSetObstacleStatus()) {
                    showToast("Please plot obstacles");
                    gridMap.setSetObstacleStatus(true);
                    gridMap.toggleCheckedBtn("obstacleImageBtn");
                }
                else {
                    int numObstacles = gridMap.getObstacleCoord().size();
                    showToast(numObstacles + " obstacles plotted");
                    gridMap.setSetObstacleStatus(false);
                }

                changeObstacleSwitch.setChecked(false);
                dragSwitch.setChecked(false);
                showLog("obstacle status = " + gridMap.getSetObstacleStatus());
                showLog("Exiting obstacleImageBtn");
            }
        });
        return root;
    }

    private void showLog(String message) {
        Log.d(TAG, message);
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}