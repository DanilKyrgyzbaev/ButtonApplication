package com.nasaatchi.kg.buttonapplication.util;

import android.content.Context;
import android.graphics.Color;
import com.nasaatchi.kg.buttonapplication.R.string;

public enum PasswordCheck {
    WEAK(string.password_strength_weak, -65536), MEDIUM(string.password_strength_medium, Color.argb(255, 220, 185, 0)), STRONG(string.password_strength_strong, -16711936), VERY_STRONG(string.password_strength_very_strong, -16776961);


    static int REQUIRED_LENGTH = 8;
    static int MAXIMUM_LENGTH = 15;
    static boolean REQUIRE_SPECIAL_CHARACTERS = true;
    static boolean REQUIRE_DIGITS = true;
    static boolean REQUIRE_LOWER_CASE = true;
    static boolean REQUIRE_UPPER_CASE = false;
    int resId;
    int color;

    PasswordCheck(int resId, int color) {
        this.resId = resId;
        this.color = color;
    }

    public int getColor() {
        return this.color;
    }

    public CharSequence getText(Context context){
        return context.getText(this.resId);
    }


    public static PasswordCheck calculatePassword(String password) {
        boolean sawUpper = false;
        boolean sawLower = false;
        boolean sawDigit = false;
        boolean sawSpecial = false;


        for (int i = 0; i < password.length(); ++i) {
            char c = password.charAt(i);
            if (!sawSpecial && !Character.isLetterOrDigit(c)) {
                sawSpecial = true;
            } else if (!sawDigit && Character.isDigit(c)) {
                sawDigit = true;
            } else if (!sawUpper || !sawLower) {
                if (Character.isUpperCase(c)) {
                    sawUpper = true;
                } else {
                    sawLower = true;
                }

                if (sawUpper && sawLower) {
                }
            }
        }

        byte currentScore;
        if (password.length() > REQUIRED_LENGTH) {
            if (REQUIRE_SPECIAL_CHARACTERS && !sawSpecial || REQUIRE_UPPER_CASE && !sawUpper || REQUIRE_LOWER_CASE && !sawLower || REQUIRE_DIGITS && !sawDigit) {
                currentScore = 1;
            } else {
                currentScore = 2;
                if (password.length() > MAXIMUM_LENGTH) {
                    currentScore = 3;
                }
            }
        } else {
            currentScore = 0;
        }

        switch(currentScore) {
            case 0:
                return WEAK;
            case 1:
                return MEDIUM;
            case 2:
                return STRONG;
            case 3:
                return VERY_STRONG;
            default:
                return VERY_STRONG;
        }
    }

}
