package org.gaochun.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * 测试主页
 */
public class CActivity extends Activity implements View.OnClickListener{

    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_cactivity);

        name = (TextView) findViewById(R.id.name);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                Intent intent = new Intent(this,CheckRecyclerViewActivity.class);
                intent.putExtra("recyclerview",name.getText());
                startActivityForResult(intent, 1);
                break;

            case R.id.btn_2:    //多选
                Intent intent2 = new Intent(this, CheckListViewActivity.class);
                intent2.putExtra("listview", name.getText());
                startActivityForResult(intent2, 2);
                break;

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    final String more = data.getExtras().getString("rv");
                    name.setText(more);
                }
                break;
            case 2:
                if (resultCode == Activity.RESULT_OK) {
                    final String more = data.getExtras().getString("lv");
                    name.setText(more);

                }
                break;

            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
