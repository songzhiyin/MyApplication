package com.szy.myapplication.Utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import com.bumptech.glide.load.engine.Resource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SkinManager {
    //外置卡皮肤APP的resouce
    private Resources skinrResource;
    private String skinPackage = "";
    private Context mConext;
    private static final SkinManager instance = new SkinManager();

    private SkinManager() {
    }

    public static SkinManager getInstance() {
        return instance;
    }

    public void init(Context context) {
        mConext = context.getApplicationContext();

    }

    public int getColor(int resId) {
        if (skinrResource == null) {
            return resId;
        }
        int a=mConext.getResources().getColor(resId);
        //颜色的值——white
        String resName = mConext.getResources().getResourceEntryName(resId);
        //获取在皮肤包中对应颜色值的id
        int skinId = skinrResource.getIdentifier(resName, "color", skinPackage);
        if (skinId == 0) {
            return resId;
        }
        int color=skinrResource.getColor(skinId);
        Log.i("slss", "getColor: "+a+"  "+color);
        return color;
    }

    public void loadSkinResouce(String path) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.setAccessible(true);
            addAssetPath.invoke(assetManager, path);
            skinrResource = new Resources(assetManager, mConext.getResources().getDisplayMetrics(), mConext.getResources().getConfiguration());
            PackageManager packageManager = mConext.getPackageManager();
            skinPackage = packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES).packageName;

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
