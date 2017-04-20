package com.sjl.lbox.app.mobile.image;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow.OnDismissListener;

import com.sjl.lbox.R;
import com.sjl.lbox.util.BitmapUtil;
import com.sjl.lbox.util.FileUtil;
import com.sjl.lbox.app.ui.CustomView.dialog.BasePopBottom;

import java.io.File;

/**
 * 图片选择类
 *
 * @author SJL
 * @date 2016/8/8 23:52
 */
public class PictureUtil {
    // 拍照
    public static int REQUEST_CODE_CAMERA = 0x101;
    // 相册
    public static int REQUEST_CODE_PHOTO = 0x102;
    // 裁剪
    public static int REQUEST_CODE_CROP = 0x103;

    private static String path = Environment.getExternalStorageDirectory() + "/cache/";

    private static String cameraPath = path + "camera.png";

    private static String filename = System.currentTimeMillis() + ".png";

    public interface BitmapLoadCallBack {
        void bitmapLoadSuccess(Bitmap bitmap, Uri uri) throws Exception;

        void bitmapLoadFailure(String error) throws Exception;
    }

    private static Context mContext;
    private static Activity mActivity;
    private static Fragment mFragment;

    public static void getInstance(Activity activity) {
        mContext = activity;
        mActivity = activity;
        mFragment = null;
    }

    public static void getInstance(Fragment fragment) {
        mContext = fragment.getActivity();
        mActivity = null;
        mFragment = fragment;
    }

    /**
     * 选择图片方式
     *
     * @param code REQUEST_CODE_CAMERA 拍照 REQUEST_CODE_PHOTO 相册
     */
    private static void chooseImage(int code) {
        if (code == REQUEST_CODE_CAMERA) {
            File file = new File(cameraPath);
            if (!new File(path).exists()) {
                new File(path).mkdir();
            }
            Uri imageUri;
            if (isSdk24()) {
                imageUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".fileprovider", file);
            } else {
                imageUri = Uri.fromFile(new File(cameraPath));
            }
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CODE_CAMERA);
        } else if (code == REQUEST_CODE_PHOTO) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");//相片类型
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
     * @param bitmapLoadCallBack
     * @throws Exception
     */
    public static void onActivityResult(int requestCode, int resultCode, Intent data,
                                        BitmapLoadCallBack bitmapLoadCallBack) throws Exception {
        if (resultCode != Activity.RESULT_OK) {
            bitmapLoadCallBack.bitmapLoadFailure("取消图片选择");
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
            Bitmap bitmap = null;
            if (data != null) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    bitmap = extras.getParcelable("data");
                }
            } else {
                bitmap = BitmapFactory.decodeFile(path + filename);
            }
            new File(cameraPath).delete();
            BitmapUtil.save(bitmap, path + filename);
            bitmapLoadCallBack.bitmapLoadSuccess(bitmap, Uri.fromFile(new File(path + filename)));
        } else {
            bitmapLoadCallBack.bitmapLoadFailure("取消图片选择");
        }
    }

    public static int outputX = 350;
    public static int outputY = 350;

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    private static void startPhotoZoom(Uri uri) {
        filename = System.currentTimeMillis() + ".png";
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
        intent.putExtra("return-data", true);
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
    public static Uri getImageContentUri(Uri uri) {
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

    private static String[] items = {"拍照", "从相册中选取"};
    private static BasePopBottom pop;
    private static Boolean flag = true;

    public interface OnPopDismiss {
        /**
         * flag=true，执行了系统dismiss
         * flag=false，执行了BasePopBottom的dismiss
         * 根据需求使用，可不用
         *
         * @param flag
         */
        void dismiss(Boolean flag);
    }

    /**
     * 弹出选择窗
     *
     * @return
     */
    public static void showPop(final OnPopDismiss onPopDismiss) {
        pop = new BasePopBottom(mContext, true);
        pop.show();
        flag = true;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.item_single_text, R.id.single_text, items);
        pop.getLv().setAdapter(adapter);
        pop.getLv().setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//				Toast.makeText(getActivity(), items[position], Toast.LENGTH_SHORT).show();
                if (position == 0) {
                    PictureUtil.chooseImage(PictureUtil.REQUEST_CODE_CAMERA);
                    flag = false;
                } else if (position == 1) {
                    PictureUtil.chooseImage(PictureUtil.REQUEST_CODE_PHOTO);
                    flag = false;
                }
                pop.dismiss();
            }
        });
        pop.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                pop.dismiss();
                if (onPopDismiss != null) {
                    onPopDismiss.dismiss(flag);
                }
            }
        });
        clearCache();
    }

    public static Boolean clearCache() {
        return FileUtil.deleteFile(path);
    }

    private static boolean isSdk24() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }
}
