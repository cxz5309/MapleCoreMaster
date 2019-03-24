package com.young.maplecoremaster;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Values values=new Values();

    LinearLayout Skill_Layout;
    LinearLayout Table_Layout;
    LinearLayout Level_Layout;
    LinearLayout Result_Layout;

    EditText EditX;
    EditText EditY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Skill_Layout = (LinearLayout) findViewById(R.id.skill_layout);
        Table_Layout = (LinearLayout) findViewById(R.id.table_layout);
        Level_Layout = (LinearLayout) findViewById(R.id.level_layout);
        Result_Layout = (LinearLayout) findViewById(R.id.result_layout);
        EditX = (EditText) findViewById(R.id.EditX);
        EditY = (EditText) findViewById(R.id.EditY);
        values.Init();

        settingOnClickListener settingOnClickListener = new settingOnClickListener();
        Button setting = (Button)findViewById(R.id.setting);
        setting.setOnClickListener(settingOnClickListener);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    class settingOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.setting:
                    values.setX(Integer.parseInt(String.valueOf(EditX.getText())));
                    values.setY(Integer.parseInt(String.valueOf(EditY.getText())));
                    values.setSkillImageOn(false);
                    Arrays.fill(values.getLevelSum(), 0);

                    if ((values.x > 0 && values.y > 0 && values.x < 13 && values.y < 13)&&( EditX.getText().length() != 0 && EditY.getText().length() != 0 )) {
                        RemoveAll(Skill_Layout, Table_Layout, Level_Layout, Result_Layout);
                        TableSetting(Skill_Layout, Table_Layout, Level_Layout, Result_Layout, values);
                    } else {
                        Toast.makeText(MainActivity.this, "범위(12x12) 이내로 입력해주세요", Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }
    }


    class skillOnClickListener implements Button.OnClickListener {
        final int[] skillImages=SkillImageSetting(values.getCharactor());
        final String[] skillTexts=SkillTextSetting(values.getCharactor());

        @Override
        public void onClick(final View view) {

            final ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.skill_list, skillTexts) {
                ViewHolder holder;

                class ViewHolder {
                    ImageView icon;
                    TextView title;
                }

                public View getView(int position, View convertView, ViewGroup parent) {
                    final LayoutInflater inflater = (LayoutInflater) getApplicationContext()
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    if (convertView == null) {
                        convertView = inflater.inflate(
                                R.layout.skill_list, null);

                        holder = new ViewHolder();
                        holder.icon = (ImageView) convertView
                                .findViewById(R.id.icon);
                        holder.title = (TextView) convertView
                                .findViewById(R.id.title);
                        convertView.setTag(holder);
                    } else {
                        // view already defined, retrieve view holder
                        holder = (ViewHolder) convertView.getTag();
                    }

                    holder.title.setText(skillTexts[position]);

                    holder.icon.setImageResource(skillImages[position]);
                    return convertView;
                }
            };

            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("스킬");
            builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    view.setBackground(getResources().getDrawable(skillImages[which], null));
                }
            });
            builder.create().show();
        }
    }

        @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.auto) {
            String[] auto_items = new String[15];
            auto_items = AutoSetting(values.getCharactor());
            values.setSkillImageOn(true);
            Arrays.fill(values.getLevelSum(), 0);

        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id){
            case R.id.evan:
                values.setCharactor("에반");
            case R.id.mage_firebenom:
                values.setCharactor("불독");
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public String[] AutoSetting(String Charactor) {
        String[] tmpArray = new String[15];
        switch (Charactor) {
            case "에반":
                tmpArray = getResources().getStringArray(R.array.evanAuto);
                break;
        }
        return tmpArray;
    }


    public void RemoveAll(LinearLayout skillLayout, LinearLayout tableLayout, LinearLayout levelLayout, LinearLayout resultLayout) {
        if (((LinearLayout) skillLayout.getParent()).getChildCount() > 0) {
            skillLayout.removeAllViews();
        }
        if (((LinearLayout) tableLayout.getParent()).getChildCount() > 0) {
            tableLayout.removeAllViews();
        }
        if (((LinearLayout) levelLayout.getParent()).getChildCount() > 0) {
            levelLayout.removeAllViews();
        }
        if (((LinearLayout) resultLayout.getParent()).getChildCount() > 0) {
            resultLayout.removeAllViews();
        }
    }

    public void TableSetting(LinearLayout SkillLayout, LinearLayout TableLayout, LinearLayout LevelLayout, LinearLayout ResultLayout, Values values) {
        SkillButtonSetting(SkillLayout, values);
        ButtonSetting(TableLayout, values);
        LevelSetting(LevelLayout, values);
        ResultSetting(ResultLayout, values);
/*
        MakeSkillListener(SkillLayout, values.x, values.skillImageOn, values.charactor);
        MakeCoreListener(TableLayout, LevelLayout, ResultLayout, values);
        MakeLevelListener(LevelLayout, values.y);
        MakeResultListener(LevelLayout, TableLayout, ResultLayout, values);
        if(values.loadOn){
            Load(TableLayout, ResultLayout, values);
        }*/
    }


    public String[] SkillTextSetting(String Charactor){
        String[] tmpArray = new String[15];
        switch (Charactor){
            case "에반":
                tmpArray = getResources().getStringArray(R.array.evanSkill);
                break;
        }
        return tmpArray;
    }

    public int[] SkillImageSetting(String Caractor) {
        int[] tmpArray = new int[15];
        switch(Caractor){
            case "에반":
                tmpArray = new int[]{R.drawable.evan_circle_mana, R.drawable.evan_circle_wind, R.drawable.evan_circle_earth,
                        R.drawable.evan_circle_sunder, R.drawable.evan_breath, R.drawable.evan_dragon_master};
                break;
        }
        return tmpArray;
    }
    public void SkillButtonSetting(LinearLayout skillLayout, Values values) {
        skillOnClickListener skillOnClickListener = new skillOnClickListener();

        int[] skillImages=SkillImageSetting(values.getCharactor());
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());

        LinearLayout.LayoutParams TmpBtnParams;
        //스킬 버튼 생성
        for (int j = 0; j < values.getX(); j++) {
            Button btn = new Button(MainActivity.this);
            btn.setTag("skill" + j);

            TmpBtnParams = new LinearLayout.LayoutParams(0, height);
            TmpBtnParams.gravity = Gravity.CENTER; //버튼의 Gravity를 지정
            TmpBtnParams.weight = 1;
            btn.setText("skill"); //버튼에 들어갈 텍스트를 지정(String)
            btn.setTag("skill"+j);
            btn.setLayoutParams(TmpBtnParams); //앞서 설정한 레이아웃파라미터를 버튼에 적용
            if(!values.isSkillImageOn()){
                btn.setBackgroundColor(Color.BLUE);
            } else{
                btn.setBackground(getResources().getDrawable(skillImages[j],null));
            }
            btn.setOnClickListener(skillOnClickListener);
            skillLayout.addView(btn); //지정된 뷰에 셋팅완료된 mButton을 추가'
        }
    }

    public void ButtonSetting(LinearLayout table, Values values){
        LinearLayout TmpLinear;
        LinearLayout.LayoutParams TmpLinearParam;

        final int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());

        for (int i = 0; i < values.getY(); i++) {
            TmpLinear = new LinearLayout(MainActivity.this);//X축 1줄 레이어 생성
            TmpLinear.setTag("xline"+i);
            TmpLinearParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);//그 레이어 파라미터

            TmpLinear.setLayoutParams(TmpLinearParam);// 레이어 파라미터 적용
            TmpLinear.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams TmpBtnParams;

            //코어 테이블 생성
            for (int j = 0; j < values.getX(); j++) {
                Button btn = new Button(MainActivity.this);
                //CoreButton coreButton= new CoreButton(j,i,"core");
                btn.setTag("core"+j+i);

                TmpBtnParams = new LinearLayout.LayoutParams(0, height);//레이아웃 파라미터
                TmpBtnParams.gravity = Gravity.CENTER; //버튼의 Gravity를 지정
                TmpBtnParams.weight = 1;
                btn.setText(""); //버튼에 들어갈 텍스트를 지정(String)

                btn.setLayoutParams(TmpBtnParams); //앞서 설정한 레이아웃파라미터를 버튼에 적용
                TmpLinear.addView(btn); //지정된 뷰에 셋팅완료된 mButton을 추가'
            }
            table.addView(TmpLinear);
        }
    }


    public void LevelSetting(final LinearLayout LevelLayout, Values values) {
        LinearLayout.LayoutParams TmpBtnParams;
        LinearLayout.LayoutParams TmpEditParams;
        final int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
        final int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());

        for (int i = 0; i < values.getY() + 2; i++) {
            if (i == 0) {
                Button btn = new Button(MainActivity.this);
                TmpBtnParams = new LinearLayout.LayoutParams(width, height);
                TmpBtnParams.gravity = Gravity.CENTER; //버튼의 Gravity를 지정
                btn.setLayoutParams(TmpBtnParams); //앞서 설정한 레이아웃파라미터를 버튼에 적용
                btn.setTag("LevelAutoButton");
                LevelLayout.addView(btn);
            } else if (i == values.getY() + 1) {
                Button btn = new Button(MainActivity.this);
                TmpBtnParams = new LinearLayout.LayoutParams(width, height);
                TmpBtnParams.gravity = Gravity.CENTER;
                btn.setLayoutParams(TmpBtnParams);
                btn.setTag("removeResultButton");
                LevelLayout.addView(btn);
            } else {
                int MaxLength = 2;
                EditText edit = new EditText(MainActivity.this);
                edit.setText(String.valueOf(values.getLevel()));
                edit.setTag("level" + String.valueOf(i - 1));
                edit.setInputType(InputType.TYPE_CLASS_NUMBER);//inputType설정=숫자만
                edit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MaxLength)});//최대 길이 설정 =2
                TmpEditParams = new LinearLayout.LayoutParams(width, height);
                TmpEditParams.gravity = Gravity.CENTER; //버튼의 Gravity를 지정
                edit.setLayoutParams(TmpEditParams); //앞서 설정한 레이아웃파라미터를 버튼에 적용
                LevelLayout.addView(edit);
            }
        }
    }

    public void ResultSetting(LinearLayout ResultLayout, Values values) {

        final int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
        LinearLayout.LayoutParams TmpBtnParams;

        for (int j = 0; j < values.getX(); j++) {
            TextView txt = new TextView(MainActivity.this);
            txt.setTag("result" + j);

            TmpBtnParams = new LinearLayout.LayoutParams(0, height);
            TmpBtnParams.gravity = Gravity.CENTER; //버튼의 Gravity를 지정
            TmpBtnParams.weight = 1;
            txt.setText(String.valueOf(values.getLevelSum()[values.getX()])); //버튼에 들어갈 텍스트를 지정(String)
            txt.setGravity(Gravity.CENTER);
            txt.setBackgroundColor(Color.YELLOW);
            txt.setLayoutParams(TmpBtnParams); //앞서 설정한 레이아웃파라미터를 버튼에 적용
            ResultLayout.addView(txt); //지정된 뷰에 셋팅완료된 mButton을 추가'
        }
    }
}
