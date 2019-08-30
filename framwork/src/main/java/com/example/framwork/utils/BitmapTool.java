package com.example.framwork.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.DisplayMetrics;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * <p>Bitmap操作类</p>
 * <p/>
 * <strong>
 * <p>使用案例参考</p>
 * <li>1、读取图片文件，预先将过于大的图片缩放至可接受范围{@link BitmapTool#decodeBitmap(int, String)}</li>
 * <li>2、旋转或缩放至指定大小{@link BitmapTool#rotateScaleBitmap(int, int, Bitmap)}</li>
 * <li>3、Bitmap储存至本地，100K以内{@link BitmapTool#saveBitmap(Bitmap, String)}</li>
 * <li>4、可以上传了</li>
 * </strong>
 *
 * @author zhaobo
 */
public class BitmapTool {

    /**
     * 图片转成string
     *
     * @param bitmap
     * @return
     */
    public static String convertIconToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(CompressFormat.PNG, 100, baos);
        byte[] appicon = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(appicon, Base64.DEFAULT);

    }

    /**
     * string转成bitmap
     *
     * @param st
     */
    public static Bitmap convertStringToIcon(String st) {
        // OutputStream out;
        Bitmap bitmap = null;
        try {
            // out = new FileOutputStream("/sdcard/aa.jpg");
            byte[] bitmapArray;
            bitmapArray = Base64.decode(st, Base64.DEFAULT);
            bitmap =
                    BitmapFactory.decodeByteArray(bitmapArray, 0,
                            bitmapArray.length);
            // bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * bitmap转换为字节数组
     *
     * @param b
     * @return
     */
    public static byte[] bitmapToByte(Bitmap b) {
        if (b == null) {
            return null;
        }
        ByteArrayOutputStream o = new ByteArrayOutputStream();
        b.compress(CompressFormat.PNG, 100, o);
        return o.toByteArray();
    }

    /**
     * 字节数组转换为bitmap
     *
     * @param b
     * @return
     */
    public static Bitmap byteToBitmap(byte[] b) {
        return (b == null || b.length == 0) ? null : BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    /**
     * bitmap转换为drawable
     *
     * @param b
     * @return
     */
    public static Drawable bitmapToDrawable(Bitmap b) {
        return b == null ? null : new BitmapDrawable(b);
    }

    /**
     * drawable转换为bitmap
     *
     * @param d
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable d) {
        return d == null ? null : ((BitmapDrawable) d).getBitmap();
    }

    /**
     * 将图片变成圆角（方法一）
     *
     * @param bitmap Bitmap
     * @param pixels 圆角的弧度
     * @return 圆角图片
     */
    public static Bitmap drawRoundBitmap(Bitmap bitmap, float pixels) {
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(outBitmap);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectf = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        // paint.setColor()的参数，除不能为Color.TRANSPARENT外，可以任意写
        paint.setColor(Color.RED);
        canvas.drawRoundRect(rectf, pixels, pixels, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return null;
    }

    /**
     * 将图片变成圆角（方法二）
     *
     * @param bitmap Bitmap
     * @param pixels 圆角的弧度
     * @return 圆角图片
     */
    public static Bitmap drawRoundCorner(Bitmap bitmap, float pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        RectF outerRect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // paint.setColor()的参数，除不能为Color.TRANSPARENT外，可以任意写
        paint.setColor(Color.WHITE);
        canvas.drawRoundRect(outerRect, pixels, pixels, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        Drawable drawable = new BitmapDrawable(bitmap);
        drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
        canvas.saveLayer(outerRect, paint, Canvas.ALL_SAVE_FLAG);
        drawable.draw(canvas);
        canvas.restore();

        return output;
    }

    /**
     * 防止内存溢出
     *
     * @param sdpath
     * @return
     */
    @SuppressWarnings("finally")
    public static Bitmap showBitmapSdPathOOM(String sdpath) {
        Bitmap map = null;
        try {
            File f = new File(sdpath);
            if (f != null && f.exists()) {
                map = BitmapFactory.decodeStream(new FileInputStream(f));
            }
        } catch (Exception ex) {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inSampleSize = 3;
            opts.inJustDecodeBounds = false;
            map = BitmapFactory.decodeFile(sdpath, opts);

        } finally {
            if (map == null) {
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inSampleSize = 3;
                opts.inJustDecodeBounds = false;
                map = BitmapFactory.decodeFile(sdpath, opts);
            }
            return map;
        }
    }


    public static Bitmap showBitmapSdPath(String sdpath) {
        Bitmap map = null;
        try {
            map = BitmapFactory.decodeFile(sdpath);
            // 转换成圆角图片

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return map;
        }

    }

    /**
     * 压缩图片至 800*480
     *
     * @param srcPath
     * @return
     */
    public static Bitmap decodeBitmap(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;// 这里设置高度为800f
        float ww = 480f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放⽐比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        return BitmapFactory.decodeFile(srcPath, newOpts);
    }

    /**
     * 将图片文件压缩到屏幕大小
     *
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap decodeBitmap(Context context, int resId) {
        // R.drawable.break_ground
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inPreferredConfig = Config.RGB_565;
        newOpts.inPurgeable = true;
        newOpts.inInputShareable = true;
        newOpts.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resId, newOpts);
        newOpts.inJustDecodeBounds = false;
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        final int screenHeight = metrics.heightPixels;// 这里设置为屏幕高度
        final int screenWidth = metrics.widthPixels;// 这里设置为屏幕宽度
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        final int height = newOpts.outHeight;
        final int width = newOpts.outWidth;
        int inSampleSize = 1;
        if (width == 0 || height == 0)
            inSampleSize = 1;
        if (height > screenHeight || width > screenWidth) {
            final int heightRatio = Math.round((float) height / (float) screenHeight);
            final int widthRatio = Math.round((float) width / (float) screenWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        newOpts.inSampleSize = inSampleSize;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        return BitmapFactory.decodeResource(context.getResources(), resId, newOpts);
    }

    /**
     * 将图片文件压缩到指定大小
     *
     * @param size 像素大小 入 700(px)
     * @param path 图片地址
     * @return
     */
    public static Bitmap decodeBitmap(int size, String path) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inPreferredConfig = Config.ARGB_8888;
        newOpts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, newOpts);

        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        final int height = newOpts.outHeight;
        final int width = newOpts.outWidth;
        int inSampleSize = 1;
        if (width == 0 || height == 0)
            inSampleSize = 1;
        if (height > size || width > size) {
            final int heightRatio = Math.round((float) height / (float) size);
            final int widthRatio = Math.round((float) width / (float) size);
            inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
        }
        newOpts.inSampleSize = inSampleSize;// 设置缩放比例

        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        newOpts.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, newOpts);
    }

    public static Bitmap decodeBitmap(int size, byte[] bytes) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inPreferredConfig = Config.ARGB_8888;
        newOpts.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, newOpts);

        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        final int height = newOpts.outHeight;
        final int width = newOpts.outWidth;
        int inSampleSize = 1;
        if (width == 0 || height == 0)
            inSampleSize = 1;
        if (height > size || width > size) {
            final int heightRatio = Math.round((float) height / (float) size);
            final int widthRatio = Math.round((float) width / (float) size);
            inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
        }
        newOpts.inSampleSize = inSampleSize;// 设置缩放比例

        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        newOpts.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, newOpts);
    }

    /**
     * 旋转缩放
     *
     * @param angle
     * @param size   目标尺寸
     * @param bitmap
     * @return
     */
    public static Bitmap rotateScaleBitmap(int angle, int size, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        if (angle != 0)
            matrix.postRotate(angle);

        int ww = bitmap.getWidth();
        int hh = bitmap.getHeight();
        if (ww > size || hh > size) {
            if (ww > hh) {
                float scale = size * 1f / ww * 1f;
                matrix.postScale(scale, scale);
            } else {
                float scale = size * 1f / hh * 1f;
                matrix.postScale(scale, scale);
            }
        }
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, ww, hh, matrix, true);

        return resizedBitmap;
    }

    /**
     * 读取图片的旋转的角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    private int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转图片
     *
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotateBitmap(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     * 将bitmap存入文件，这里会限制文件大小为100K
     *
     * @param bitmap
     * @param sd_path
     */
    public static String saveBitmap(Bitmap bitmap, String sd_path) {
        try {
            FileOutputStream fos = new FileOutputStream(new File(sd_path));
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            // 图片质量默认值为100，表示不压缩
            int quality = 100;
            // PNG是无损的，将会忽略质量设置。因此，这里设置为JPEG
            bitmap.compress(CompressFormat.PNG, quality, outStream);
            if (outStream.toByteArray().length / 1024 <= 100) {
                outStream.writeTo(fos);
                fos.flush();
                fos.close();
                outStream.close();
                return ".png";
            }
            while (outStream.toByteArray().length / 1024 > 100) {
                outStream.reset();
                bitmap.compress(CompressFormat.JPEG, quality, outStream);
                quality -= 10;
                // 压缩quality%，把压缩后的数据存放到baos中
                if (quality < 0) {
                    quality = 0;
                    break;
                }
            }
            outStream.writeTo(fos);
            fos.flush();
            fos.close();
            outStream.close();
            return ".jpg";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ".jpg";
    }

    //读取图片旋转高度
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    //将bitmmap转化为字节
    public static byte[] compressBitmap(int maxNumOfPixels, String imgpath) {
        double maxSize = 100.00;
        Bitmap bitmap = loadBitmap(maxNumOfPixels, imgpath);
        if (null != bitmap) {
            byte[] bBitmap = convertBitmap(bitmap);
            if (bitmap != null) {
                bitmap.recycle();
                bitmap = null;
            }
            double mid = bBitmap.length / 1024;
            if (mid > maxSize) {
                double i = mid / maxSize;
                bBitmap = compressBitmap((int) (maxNumOfPixels / Math.abs(i)),
                        imgpath);
            }
            return bBitmap;
        } else {
            return null;
        }
    }

    public static byte[] convertBitmap(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 100, baos);
        int options = 100;
//		LogUtil.e("===baos.toByteArray().length===" + baos.toByteArray().length);
//		LogUtil.e("===baos.size===" + baos.size());
        while (baos.size() / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            options -= 10;// 每次都减少10
            bitmap.compress(CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中

        }
        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bitmap != null) {
                bitmap.recycle();
                bitmap = null;
            }
        }
        return baos.toByteArray();

    }

    public static Bitmap loadBitmap(int maxNumOfPixels, String imgpath) {
        Bitmap bitmap = null;
        try {
            FileInputStream f = new FileInputStream(new File(imgpath));

            // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(imgpath, options);
            // 调用上面定义的方法计算inSampleSize值
            if (0 == maxNumOfPixels) {
                maxNumOfPixels = 128 * 128;
            }
            options.inSampleSize = computeSampleSize(options, -1,
                    maxNumOfPixels);
            // 使用获取到的inSampleSize值再次解析图片
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeStream(f, null, options);

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }
        return bitmap;
    }

    public static int computeSampleSize(BitmapFactory.Options options,

                                        int minSideLength, int maxNumOfPixels) {

        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;

        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,

                                                int minSideLength, int maxNumOfPixels) {

        double w = options.outWidth;

        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 :

                (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));

        int upperBound = (minSideLength == -1) ? 128 :

                (int) Math.min(Math.floor(w / minSideLength),

                        Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {

            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    /**
     * 保存文件
     */
    public static void saveFile(Context context, Bitmap bm, String path, String fileName) throws IOException {
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(CompressFormat.JPEG, 100, bos);
        bos.flush();
        bos.close();
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    path, fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(myCaptureFile)));
    }

    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }


}
