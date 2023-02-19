package com.konglianyuyin.mx.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


/**
 * @author hyx
 * @description
 * @date 2018/6/18.
 */
public class ActivityUtils {
    public static void addOrShowFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                                    @NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (fragmentManager.getFragments() != null) {
            for (Fragment f : fragmentManager.getFragments()) {
                transaction.hide(f);
            }
            if (fragmentManager.getFragments().contains(fragment)) {
                transaction.show(fragment);
                transaction.commitAllowingStateLoss();
                return;
            }
        }

        transaction.add(frameId, fragment);
//        transaction.commit();
        transaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
    }

}
