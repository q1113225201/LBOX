package com.sjl.lbox.app.QRCode.MyQRCode.decode;

import android.graphics.Bitmap;

import com.google.zxing.Result;
import com.google.zxing.ResultPointCallback;

public interface DecodeListener extends ResultPointCallback {
	void onDecodeSuccess(Result result, LuminanceSource source, Bitmap bitmap);

	void onDecodeFailed(LuminanceSource source);
}
