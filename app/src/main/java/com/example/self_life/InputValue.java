package com.example.self_life;

import android.content.Context;
import android.widget.Toast;

public class InputValue {
    //생년월일 입력값 유효성 검사
    public static String parseBODNumberOrShowError(Context context, String input) {

        if (isValidPhoneNumberFormat(input)) {
            return input;
        } else {
            showToast(context, "생년월일을 잘못입력하셨습니다");
            return "";
        }
    }


    private static boolean isValidBODNumberFormat(String input) {
        if (input.length() != 8) {
            return false;
        }

        for (int i = 0; i < 8; i++) {
            if (!Character.isDigit(input.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    //휴대폰 번호값 유효성 검사
    public static String parsePhoneNumberOrShowError(Context context, String input) {
        if (isValidPhoneNumberFormat(input)) {
            return input;
        } else {
            showToast(context, "휴대폰 번호를 잘못입력하셨습니다");
            return "";
        }
    }

    private static boolean isValidPhoneNumberFormat(String input) {
        if (input.length() != 11) {
            return false;
        }

        for (int i = 0; i < 11; i++) {
            if (!Character.isDigit(input.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    //이름값 유효성 검사 + "이름이 숫자같은 유효하진 않은 글자 입력 여부 조사 코드 추가 예정
    public static String parseNameOrShowError(Context context, String input) {

        if (isValidNameFormat(input)) {
            return input;
        } else {
            showToast(context, "생년월일을 잘못입력하셨습니다");
            return "";
        }
    }

    private static boolean isValidNameFormat(String input) {
        if (input.length() >= 3) {
            return false;
        }

        for (int i = 0; i < 3; i++) {
            if (!Character.isDigit(input.charAt(i))) {
                return false;
            }
        }

        return true;
    }
    //비밀번호 입력값 유효성 검사
    public static String parsePasswordOrShowError(Context context, String input) {
        if (isValidPasswordFormat(input)) {
            return input;
        } else {
            showToast(context, "비밀번호를 잘못입력하셨습니다");
            return "";
        }
    }

    private static boolean isValidPasswordFormat(String input) {
        String regex = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*()+=]).{8,}$";
        return input.matches(regex);
    }

    //이메일 입력값 유효성 검사
    public static String parseEmailOrShowError(Context context, String input) {
        if (isValidEmailFormat(input)) {
            return input;
        } else {
            showToast(context, "이메일을 잘못입력하셨습니다");
            return "";
        }
    }

    private static boolean isValidEmailFormat(String input) {
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
        return input.matches(regex);
    }

    private static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
