package com.mariovrocha.carkeeper;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.codemybrainsout.onboarder.AhoyOnboarderActivity;
import com.codemybrainsout.onboarder.AhoyOnboarderCard;

import java.util.ArrayList;
import java.util.List;

public class OnboardingActivity extends AhoyOnboarderActivity {
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            onFinishButtonPressed();
            finish();
        }

        AhoyOnboarderCard firstCard = new AhoyOnboarderCard(getString(R.string.slide_1_title), getString(R.string.slide_1_desc), R.mipmap.ic_launcher_round);
        AhoyOnboarderCard secondCard = new AhoyOnboarderCard(getString(R.string.slide_2_title), getString(R.string.slide_2_desc), R.mipmap.ic_launcher_round);

        firstCard.setBackgroundColor(R.color.white);
        firstCard.setTitleTextSize(20);
        secondCard.setBackgroundColor(R.color.white);
        secondCard.setTitleTextSize(20);

        List<AhoyOnboarderCard> pages = new ArrayList<>();

        pages.add(secondCard);
        pages.add(firstCard);

        for (AhoyOnboarderCard page : pages) {
            page.setTitleColor(R.color.black);
            page.setDescriptionColor(R.color.primary_text);
        }

        setFinishButtonTitle(R.string.intro_finish);

        List<Integer> colorList = new ArrayList<>();
        setGradientBackground();

        setOnboardPages(pages);
    }

    @Override
    public void onFinishButtonPressed() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(OnboardingActivity.this, SignInActivity.class));
        finish();
    }
}
