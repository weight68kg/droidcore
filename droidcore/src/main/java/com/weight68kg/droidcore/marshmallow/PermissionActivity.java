package com.weight68kg.droidcore.marshmallow;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

import com.weight68kg.droidcore.R;


public class PermissionActivity extends AppCompatActivity {
    public static final String PERMISSION_DESCRIPTION = "permission_description";
    public static final String PERMISSION_PERMISSION = "permission_permission";
    private static PermissionCallback mCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_permission);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();
        initData();
        addListener();
    }


    public static void start(Activity context, @NonNull String permissionDes, PermissionCallback callback, @NonNull String... permissions) {
        mCallback = callback;
        Intent intent = new Intent(context, PermissionActivity.class);
        intent.putExtra(PERMISSION_DESCRIPTION, permissionDes);
        intent.putExtra(PERMISSION_PERMISSION, permissions);
        context.startActivity(intent);
    }

    public interface PermissionCallback {
        void hasPermission();

        void noPermission();
    }

    protected void initView() {
        String stringExtra = getIntent().getStringExtra(PERMISSION_DESCRIPTION);
        String[] stringArrayExtra = getIntent().getStringArrayExtra(PERMISSION_PERMISSION);
        performCodeWithPermission(stringExtra, mCallback, stringArrayExtra);
    }

    protected void initData() {

    }

    protected void addListener() {

    }

    public PermissionCallback permissionRunnable;

    // Android M 危险权限申请
    private int permissionRequestCode = 0x2388;
    private String message;


    /**
     * Android M运行时权限请求封装
     *
     * @param permissionDes 权限描述
     * @param runnable      请求权限回调
     * @param permissions   请求的权限（数组类型），直接从Manifest中读取相应的值，比如Manifest.permission.WRITE_CONTACTS
     * @author by liulxiang
     */
    public void performCodeWithPermission(@NonNull Object permissionDes, PermissionCallback runnable, @NonNull String... permissions) {
        if (permissionDes instanceof String) {
            message = (String) permissionDes;

        }
        if (permissionDes instanceof Integer) {
            if ((Integer) permissionDes > 0) {
                message = getResources().getText((Integer) permissionDes).toString();
            }
        }
        if (permissions == null || permissions.length == 0) return;
        this.permissionRunnable = runnable;
        if ((Build.VERSION.SDK_INT < Build.VERSION_CODES.M) || checkPermissionGranted(permissions)) {
            if (permissionRunnable != null) {
                permissionRunnable.hasPermission();
                permissionRunnable = null;
                finish();
            }
        } else {
            //permission has not been granted.
            requestPermission(message, permissionRequestCode, permissions);
        }

    }

    /**
     * @param permissions
     * @return
     */
    private boolean checkPermissionGranted(String[] permissions) {
        boolean flag = true;
        for (String p : permissions) {
            if (ActivityCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    boolean b = false;

    /**
     * @param permissionDes
     * @param requestCode
     * @param permissions
     */
    private void requestPermission(String permissionDes, final int requestCode, final String[] permissions) {
        if (shouldShowRequestPermissionRationale(permissions)) {
            //6.27 注销
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage(permissionDes)
                    .setPositiveButton("授权", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(PermissionActivity.this, permissions, requestCode);
                        }
                    });
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    dialog.dismiss();
                    finish();
                }
            });
            builder.show();

        } else {
            // Contact permissions have not been granted yet. Request them directly.
            ActivityCompat.requestPermissions(PermissionActivity.this, permissions, requestCode);
        }
    }

    /**
     * @param permissions
     * @return
     */
    private boolean shouldShowRequestPermissionRationale(String[] permissions) {
        boolean flag = false;
        for (String p : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, p)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == permissionRequestCode) {
            if (verifyPermissions(grantResults)) {
                if (permissionRunnable != null) {
                    permissionRunnable.hasPermission();
                    permissionRunnable = null;
                    finish();
                }
            } else {
                if (verifyPermissionsDenied(grantResults)) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(this)
                            .setTitle("提示")
                            .setMessage("是否手动开启权限")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    intent.setData(Uri.parse("package:" + getPackageName()));
                                    startActivity(intent);
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            finish();
                        }
                    });
                    builder.show();


                } else {
                    Toast.makeText(this, "暂无相关操作", Toast.LENGTH_SHORT).show();
                    if (permissionRunnable != null) {
                        permissionRunnable.noPermission();
                        permissionRunnable = null;
                        finish();
                    }
                }

            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    public boolean verifyPermissions(int[] grantResults) {
        if (grantResults.length < 1) {
            return false;
        }
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public boolean verifyPermissionsDenied(int[] deniedResults) {
        if (deniedResults.length < 1) {
            return false;
        }
        for (int result : deniedResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
