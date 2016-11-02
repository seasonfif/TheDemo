package com.demo.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import com.demo.R;
import java.io.File;

/**
 * 创建时间：2016年10月24日17:17 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */
public class ShareAcivity extends BaseActivity {

  @Override public void setView() {
    setContentView(R.layout.activity_share);
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Uri data = getIntent().getData();

    findViewById(R.id.share_only_text).setOnClickListener(this);
    findViewById(R.id.share_text_image).setOnClickListener(this);
  }

  @Override public void onClick(View v) {
    super.onClick(v);
    String strSubject = "我的主题";
    String strContent = "我的分享内容";
    String strDlgTitle = "对话框标题-文字分享";
    switch (v.getId()){
      case R.id.share_only_text:
        shareText(strDlgTitle, strSubject, strContent);
        break;
      case R.id.share_text_image:
        strDlgTitle = "对话框标题-图片分享";
        String imgPath = Environment.getExternalStorageDirectory().getPath()
            + File.separator + "1.jpg";
        Uri imageUri = Uri.fromFile(new File(imgPath));
        shareImg(strDlgTitle, strSubject, strContent, imageUri);
        break;
    }
  }

  /**
   * 分享文字内容
   *
   * @param dlgTitle
   *            分享对话框标题
   * @param subject
   *            主题
   * @param content
   *            分享内容（文字）
   */
  private void shareText(String dlgTitle, String subject, String content) {
    if (content == null || "".equals(content)) {
      return;
    }
    Intent intent = new Intent(Intent.ACTION_SEND);
    intent.setType("text/plain");
    if (subject != null && !"".equals(subject)) {
      intent.putExtra(Intent.EXTRA_SUBJECT, subject);
    }

    intent.putExtra(Intent.EXTRA_TEXT, content);

    // 设置弹出框标题
    if (dlgTitle != null && !"".equals(dlgTitle)) { // 自定义标题
      startActivity(Intent.createChooser(intent, dlgTitle));
    } else { // 系统默认标题
      startActivity(intent);
    }
  }

  /**
   * 分享图片和文字内容
   *
   * @param dlgTitle
   *            分享对话框标题
   * @param subject
   *            主题
   * @param content
   *            分享内容（文字）
   * @param uri
   *            图片资源URI
   */
  private void shareImg(String dlgTitle, String subject, String content,
      Uri uri) {
    if (uri == null) {
      return;
    }
    Intent intent = new Intent(Intent.ACTION_SEND);
    intent.setType("image/*");
    intent.putExtra(Intent.EXTRA_STREAM, uri);
    if (subject != null && !"".equals(subject)) {
      intent.putExtra(Intent.EXTRA_SUBJECT, subject);
    }
    if (content != null && !"".equals(content)) {
      intent.putExtra(Intent.EXTRA_TEXT, content);
    }

    // 设置弹出框标题
    if (dlgTitle != null && !"".equals(dlgTitle)) { // 自定义标题
      startActivity(Intent.createChooser(intent, dlgTitle));
    } else { // 系统默认标题
      startActivity(intent);
    }
  }
}
