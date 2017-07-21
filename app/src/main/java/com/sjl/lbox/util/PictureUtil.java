package com.sjl.lbox.util;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * 只涉及拍照和相册选择图片
 * PictureUtil.chooseImage 选择
 * PictureUtil.onActivityResult 回调
 * @author SJL
 * @date 2017/7/10
 */
public class PictureUtil {
    private static final String TAG = "PictureUtil";
    // 拍照
    public static int REQUEST_CODE_CAMERA = 0x101;
    // 相册
    public static int REQUEST_CODE_PHOTO = 0x102;
    // 裁剪
    public static int REQUEST_CODE_CROP = 0x103;

    private static String path = Environment.getExternalStorageDirectory() + "/cache/";

    private static String cameraPath = path + "camera.png";

    private static String filename = System.currentTimeMillis() + ".png";

    public interface PictureLoadCallBack {
        void bitmapLoadSuccess(Bitmap bitmap, Uri uri);

        void bitmapLoadFailure(String error);
    }

    private static Context mContext;
    private static Activity mActivity;
    private static Fragment mFragment;
    private static PictureLoadCallBack mPictureLoadCallBack;
    public static void chooseImage(Activity activity,int code,PictureLoadCallBack pictureLoadCallBack){
        mContext = activity;
        mActivity = activity;
        mFragment = null;
        mPictureLoadCallBack = pictureLoadCallBack;
        chooseImage(code);
    }

    public static void chooseImage(Fragment fragment,int code,PictureLoadCallBack pictureLoadCallBack){
        mContext = fragment.getActivity();
        mActivity = null;
        mFragment = fragment;
        mPictureLoadCallBack = pictureLoadCallBack;
        chooseImage(code);
    }

    /**
     * 选择图片方式
     *
     * @param code REQUEST_CODE_CAMERA 拍照 REQUEST_CODE_PHOTO 相册
     */
    private static void chooseImage(int code) {
        if (code == REQUEST_CODE_CAMERA) {
            File file = new File(path);
            if (!file.exists()) {
                file.mkdir();
            }
            File imageFile = new File(cameraPath);
            Uri imageUri;
            if (isSdk24()) {
                imageUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".fileprovider", imageFile);
            } else {
                imageUri = Uri.fromFile(imageFile);
            }
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CODE_CAMERA);
        } else if (code == REQUEST_CODE_PHOTO) {
            Intent intent = new Intent(Intent.ACTION_PICK);
//            intent.setType("image/*");
            intent.setDataAndType(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    "image/*");//相片类型
            startActivityForResult(intent, REQUEST_CODE_PHOTO);
        }
    }

    private static void startActivityForResult(Intent intent, int requestCodePhoto) {
        if (mActivity == null) {
            mFragment.startActivityForResult(intent, requestCodePhoto);
        } else {
            mActivity.startActivityForResult(intent, requestCodePhoto);
        }
    }

    /**
     * 文件选择回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     * @throws Exception
     */
    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            LogUtil.e(TAG, "取消图片选择");
            if (mPictureLoadCallBack != null) {
                mPictureLoadCallBack.bitmapLoadFailure("");
            }
            return;
        }

        if (requestCode == REQUEST_CODE_PHOTO) {
            startPhotoZoom(data.getData());
        } else if (requestCode == REQUEST_CODE_CAMERA) {
            Uri uri = Uri.fromFile(new File(cameraPath));
            if (isSdk24()) {
                uri = getImageContentUri(uri);
            }
            startPhotoZoom(uri);
        } else if (requestCode == REQUEST_CODE_CROP) {
            /*Bitmap bitmap = null;
            if (data != null) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    bitmap = extras.getParcelable("data");
                } else {
                    bitmap = BitmapFactory.decodeFile(path + filename);
                }
            } else {
                bitmap = BitmapFactory.decodeFile(path + filename);
            }
            new File(cameraPath).delete();
            try {
                BitmapUtil.save(bitmap, path + filename);
            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.e(TAG, "save e:" + e.getMessage());
            }*/
            //已改为不回传数据
            Bitmap bitmap = BitmapFactory.decodeFile(path + filename);
            new File(cameraPath).delete();
            if (mPictureLoadCallBack != null) {
                mPictureLoadCallBack.bitmapLoadSuccess(bitmap, Uri.fromFile(new File(path + filename)));
            }
        } else {
            LogUtil.e(TAG, "取消图片选择");
            if (mPictureLoadCallBack != null) {
                mPictureLoadCallBack.bitmapLoadFailure("");
            }
        }
    }

    private static final int outputX = 350;
    private static final int outputY = 350;

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    private static void startPhotoZoom(Uri uri) {
        filename = System.currentTimeMillis() + ".png";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", outputX);
        intent.putExtra("aspectY", outputY);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path + filename)));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // intent.putExtra("outputFormat",
        // Bitmap.CompressFormat.PNG.toString());
        // 去黑边
        // intent.putExtra("scale", true);
        // intent.putExtra("scaleUpIfNeeded", true);
        // 关闭人脸识别
        // intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, REQUEST_CODE_CROP);
    }

    /**
     * 安卓7.0裁剪根据文件路径获取uri
     */
    private static Uri getImageContentUri(Uri uri) {
        File imageFile = new File(uri.getPath());
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = mContext.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            cursor.close();
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return mContext.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    /**
     * 删除缓存
     *
     * @return
     */
    public static Boolean clearCache() {
        return FileUtil.deleteFile(path);
    }

    private static boolean isSdk24() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }
}
