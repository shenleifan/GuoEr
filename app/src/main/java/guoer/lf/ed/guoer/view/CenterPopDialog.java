package guoer.lf.ed.guoer.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import guoer.lf.ed.guoer.R;
import guoer.lf.ed.guoer.utils.LogUtils;

import static android.view.Gravity.CENTER;
import static android.view.View.GONE;

public class CenterPopDialog extends Dialog {
    private static final String TAG = "CenterPopDialog";

    public CenterPopDialog(@NonNull Context context) {
        super(context);
    }

    public CenterPopDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private DialogInterface.OnClickListener positiveButtonClickListener;
        private DialogInterface.OnClickListener negativieButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public void setPositiveButton(String positiveButtonText, DialogInterface.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
        }

        public void setNegativeButton(String negativeButtonText, DialogInterface.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativieButtonClickListener = listener;
        }

        public void setContentView(View contentView) {
            this.contentView = contentView;
        }

        public CenterPopDialog create() {
            final CenterPopDialog dialog = new CenterPopDialog(context, R.style.dialog_pop);
            Window window = dialog.getWindow();
            View layout = LayoutInflater.from(context).inflate(R.layout.dialog_pop_layout, null);
            if (window != null) {
                LogUtils.d(TAG, "create()");
                window.setGravity(CENTER);
                window.setWindowAnimations(R.style.bottom_menu_animation);
                WindowManager wm = ((Activity) context).getWindowManager();
                Display display = wm.getDefaultDisplay();
                LogUtils.d(TAG, "Display " + display);
                Point point = new Point();
                display.getSize(point);
                WindowManager.LayoutParams layoutParams = window.getAttributes();
                layoutParams.width = point.x * 4 / 5;
                LogUtils.d(TAG, "point.width " + point.x);
                LogUtils.d(TAG, "layoutParams.width " + layoutParams.width);

                dialog.addContentView(layout, layoutParams);
//                dialog.addContentView(layout, new WindowManager.LayoutParams(
//                        WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT

                if (!TextUtils.isEmpty(positiveButtonText)) {
                    Button positiveButton = (Button) layout.findViewById(R.id.dialog_sure);
                    positiveButton.setText(positiveButtonText);
                    if (positiveButtonClickListener != null) {
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                            }
                        });
                    }
                } else {
                    ((Button) layout.findViewById(R.id.dialog_sure)).setVisibility(GONE);
                }

                if (!TextUtils.isEmpty(negativeButtonText)) {
                    Button negativeButton = (Button) layout.findViewById(R.id.dialog_cancel);
                    negativeButton.setText(negativeButtonText);
                    if (negativieButtonClickListener != null) {
                        negativeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                negativieButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                            }
                        });
                    }
                } else {
                    ((Button) layout.findViewById(R.id.dialog_cancel)).setVisibility(View.GONE);
                }

                if (!TextUtils.isEmpty(message)) {
                    ((TextView) layout.findViewById(R.id.dialog_message)).setText(message);
                } else if (contentView != null) {
                    LinearLayout linearLayout = ((LinearLayout) layout.findViewById(R.id.dialog_content_view));
                    linearLayout.removeAllViews();
                    linearLayout.addView(contentView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                }
            }
            dialog.setContentView(layout);
            return dialog;
        }
    }
}
