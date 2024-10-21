package com.tranvodinhhoang.shared_reference;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SeekBar volumeSeekBar;
    private Switch switchMusic;
    private Switch switchSfx;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        volumeSeekBar = findViewById(R.id.volume_seekbar);
        switchMusic = findViewById(R.id.switch_music);
        switchSfx = findViewById(R.id.switch_sfx);
        sharedPreferences = getSharedPreferences("Game sound setting", MODE_PRIVATE);

        boolean isMusicOn = sharedPreferences.getBoolean("music_on", true);
        boolean isSfxOn = sharedPreferences.getBoolean("sfx_on", true);
        int savedVolume = sharedPreferences.getInt("volume", 50);

        switchMusic.setChecked(isMusicOn);
        switchSfx.setChecked(isSfxOn);
        volumeSeekBar.setProgress(savedVolume);

        switchMusic.setOnCheckedChangeListener((compoundButton, bool) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("music_on", bool);
            editor.apply();
            if (bool) {
                Toast.makeText(MainActivity.this, "Music ON", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Music OFF", Toast.LENGTH_SHORT).show();
            }
        });

        switchSfx.setOnCheckedChangeListener((compoundButton, bool) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("sfx_on", bool);
            editor.apply();
            if (bool) {
                Toast.makeText(MainActivity.this, "Sfx ON", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Sfx OFF", Toast.LENGTH_SHORT).show();
            }
        });
        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("volume", progress);
                editor.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(MainActivity.this, "Volume set to " + seekBar.getProgress() + "%", Toast.LENGTH_SHORT).show();
            }
        });
    }
}