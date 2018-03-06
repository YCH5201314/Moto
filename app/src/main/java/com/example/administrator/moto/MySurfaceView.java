package com.example.administrator.moto;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
/**
 * Created by Administrator on 2018\2\2 0002.
 */

public class MySurfaceView extends SurfaceView implements
        SurfaceHolder.Callback,Runnable{
    private SurfaceHolder sfh;
    private Paint paint;
    private boolean flag=true;
    private int X=300;
    private Thread th;
    private boolean isBattle=false;
    //手机屏幕的大小
    private float ScreenX,ScreenY;
    //定义两个圆形的中心点坐标与半径
    private float SmallCenterX=120,SmallCenterY=500,SmallCenterR=50;
    private float BigCenterX=120,BigCenterY=500,BigCenterR=100;
    public static final int LEFT=1,UP=2,RIGHT=3,DOWN=4,STOP=5;
    private int direction = STOP;// 用户按键的方向
    private int lastdirection=UP;//记录用户的上一个方向
    private Resources res=this.getResources();
    private int px=5,py=10;
    //实例化图片
         //背景图片
    Bitmap grassland= BitmapFactory.decodeResource(res,R.drawable.grassland);
        //人物图片
    Bitmap p1_zb1=BitmapFactory.decodeResource(res,R.drawable.p1_zb1);
    Bitmap p1_yb1=BitmapFactory.decodeResource(res,R.drawable.p1_yb1);
    Bitmap p1_zm1=BitmapFactory.decodeResource(res,R.drawable.p1_zm1);
    Bitmap p1_sm1=BitmapFactory.decodeResource(res,R.drawable.p1_sm1);
        //钥匙图片
    Bitmap key1=BitmapFactory.decodeResource(res,R.drawable.key_1);
    Bitmap key2=BitmapFactory.decodeResource(res,R.drawable.key_2);
    Bitmap key3=BitmapFactory.decodeResource(res,R.drawable.key_3);
         //装备图片
    Bitmap weapon1=BitmapFactory.decodeResource(res,R.drawable.weapon_1);
    Bitmap shield1=BitmapFactory.decodeResource(res,R.drawable.shield_1);
         //墙壁图片
    Bitmap wall1=BitmapFactory.decodeResource(res,R.drawable.wall1);
    Bitmap wall3=BitmapFactory.decodeResource(res,R.drawable.wall3);
    Bitmap wall4=BitmapFactory.decodeResource(res,R.drawable.wall4);
         //门图片
    Bitmap yellowdoor=BitmapFactory.decodeResource(res,R.drawable.yellowdoor);
    Bitmap bluedoor=BitmapFactory.decodeResource(res,R.drawable.bluedoor);
    Bitmap reddoor=BitmapFactory.decodeResource(res,R.drawable.reddoor);
         //楼梯
    Bitmap shanglou=BitmapFactory.decodeResource(res,R.drawable.shanglou);
    Bitmap xialou=BitmapFactory.decodeResource(res,R.drawable.xialou);
         //药水图片
    Bitmap potion1=BitmapFactory.decodeResource(res,R.drawable.potion_1);
    Bitmap potion2=BitmapFactory.decodeResource(res,R.drawable.potion_2);
    Bitmap potion3=BitmapFactory.decodeResource(res,R.drawable.potion_3);
         //宝物图片
    Bitmap feixingqi=BitmapFactory.decodeResource(res,R.drawable.feixingqi);
         //怪物图片
    Bitmap undead1=BitmapFactory.decodeResource(res,R.drawable.undead1);//30
    Bitmap undead2=BitmapFactory.decodeResource(res,R.drawable.undead2);//31
    Bitmap undead3=BitmapFactory.decodeResource(res,R.drawable.undead3);//32
    Bitmap undead4=BitmapFactory.decodeResource(res,R.drawable.undead4);//33
    Bitmap snake1=BitmapFactory.decodeResource(res,R.drawable.snake1);//34
    Bitmap snake2=BitmapFactory.decodeResource(res,R.drawable.snake2);//35
    Bitmap snake3=BitmapFactory.decodeResource(res,R.drawable.snake3);//36
    Bitmap snake4=BitmapFactory.decodeResource(res,R.drawable.snake4);//37

    Bitmap people1[]=new Bitmap[3];
    Bitmap people2[]=new Bitmap[3];
    Bitmap people3[]=new Bitmap[3];
    Bitmap people4[]=new Bitmap[3];

    //怪物数组
    int ogre[]={30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50};

    int current=0;
    //人物信息：勇士 攻击力 防御力 血量  红，蓝，黄钥匙
    private String hero="勇士",attack="攻击力:",defence="防御力:",blood="血量:",
            money="金币:",experience="经验值:",weapon="武器:",shield="盾牌:",
            storey="第     层",disposable="一次性物品:",forever="永久使用物品:",
            automaticl="自动使用物品:",monster="怪物";
    private int gongjili=18,fangyuli=18,HP=1200,jinbi=0,jingyan=0,louceng=1;
    private int redkey=0,bluekey=0,yellowkey=10;
    private String cheng="X";
    public MySurfaceView(Context context) {
        super(context);
        sfh=this.getHolder();
        sfh.addCallback(this);
        paint=new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(30);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        th=new Thread(this);
        th.start();
        ScreenX=this.getWidth();
        ScreenY=this.getHeight();
        InitGame();
    }

    private void InitGame() {
        for(int i=0;i<people1.length;i++){
            people1[i]=BitmapFactory.decodeResource(res,R.drawable.p1_sm1+i);
        }

        for(int i=0;i<people2.length;i++){
            people2[i]=BitmapFactory.decodeResource(res,R.drawable.p1_zm1+i);
        }

        for(int i=0;i<people3.length;i++){
            people3[i]=BitmapFactory.decodeResource(res,R.drawable.p1_zb1+i);
        }

        for(int i=0;i<people4.length;i++){
            people4[i]=BitmapFactory.decodeResource(res,R.drawable.p1_yb1+i);
        }


    }

    private void myDraw() {
        Canvas canvas=sfh.lockCanvas();
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(grassland,0,0,paint);
        //画人物信息
        canvas.drawText(storey,80,50,paint);
        canvas.drawText(String.valueOf(louceng),120,50,paint);
        canvas.drawText(hero,100,80,paint);
        canvas.drawText(attack,20,110,paint);
        canvas.drawText(String.valueOf(gongjili),120,110,paint);
        canvas.drawText(defence,20,140,paint);
        canvas.drawText(String.valueOf(fangyuli),120,140,paint);
        canvas.drawText(blood,20,170,paint);
        canvas.drawText(String.valueOf(HP),90,170,paint);
        canvas.drawText(money,20,200,paint);
        canvas.drawText(String.valueOf(jinbi),120,200,paint);
        canvas.drawText(experience,20,230,paint);
        canvas.drawText(String.valueOf(jingyan),120,230,paint);
        //钥匙
        canvas.drawBitmap(key1,20,240,paint);
        canvas.drawText(cheng,90,280,paint);
        canvas.drawText(String.valueOf(yellowkey),120,280,paint);
        canvas.drawBitmap(key2,20,290,paint);
        canvas.drawText(cheng,90,330,paint);
        canvas.drawText(String.valueOf(bluekey),120,330,paint);
        canvas.drawBitmap(key3,20,340,paint);
        canvas.drawText(cheng,90,380,paint);
        canvas.drawText(String.valueOf(redkey),120,380,paint);

        //装备
        canvas.drawText(weapon,ScreenX-220,50,paint);
        canvas.drawBitmap(weapon1,ScreenX-150,20,paint);
        canvas.drawText(shield,ScreenX-220,100,paint);
        canvas.drawBitmap(shield1,ScreenX-150,70,paint);
        canvas.drawText(disposable,ScreenX-220,150,paint);

        canvas.drawText(forever,ScreenX-220,250,paint);
        if(map[0][10][1]==0){
            canvas.drawBitmap(feixingqi,ScreenX-220,260,paint);
        }
        canvas.drawText(automaticl,ScreenX-220,350,paint);

        //画边框
        canvas.drawBitmap(wall3,0,0,paint);
        canvas.drawBitmap(wall3,0,ScreenY-20,paint);
        canvas.drawBitmap(wall4,0,20,paint);
        canvas.drawBitmap(wall4,ScreenX-20,20,paint);
        canvas.drawBitmap(wall4,240,20,paint);
        canvas.drawBitmap(wall4,ScreenX-250,20,paint);

        //画地图
        /*
        初始化地图  0代表空地 1代表墙壁 2代表铜钥匙 3代表银钥匙 4代表金钥匙
        5代表黄门 6代表蓝门 7代表红门 8代表上去的楼梯 9代表下去的楼梯 10代表初级药水
        11代表中级药水 12代表高级药水 13-20代表宝物 30-40代表怪物
         */

        for(int i=0;i<map[louceng-1].length;i++)
        {
            for (int j=0;j<map[louceng-1][i].length;j++){
                switch (map[louceng-1][i][j]){
                    case 1:
                        canvas.drawBitmap(wall1,260+j*70,25+i*52,paint);
                        break;
                    case 2:
                        canvas.drawBitmap(key1,260+j*70,25+i*52,paint);
                        break;
                    case 3:
                        canvas.drawBitmap(key2,260+j*70,25+i*52,paint);
                        break;
                    case 4:
                        canvas.drawBitmap(key3,260+j*70,25+i*52,paint);
                        break;
                    case 5:
                        canvas.drawBitmap(yellowdoor,260+j*70,25+i*52,paint);
                        break;
                    case 6:
                        canvas.drawBitmap(bluedoor,260+j*70,25+i*52,paint);
                        break;
                    case 7:
                        canvas.drawBitmap(reddoor,260+j*70,25+i*52,paint);
                        break;
                    case 8:
                        canvas.drawBitmap(shanglou,260+j*70,25+i*52,paint);
                        break;
                    case 9:
                        canvas.drawBitmap(xialou,260+j*70,25+i*52,paint);
                        break;
                    case 10:
                        canvas.drawBitmap(potion1,260+j*70,25+i*52,paint);
                        break;
                    case 11:
                        canvas.drawBitmap(potion2,260+j*70,25+i*52,paint);
                        break;
                    case 12:
                        canvas.drawBitmap(potion3,260+j*70,25+i*52,paint);
                        break;
                    case 13:
                        canvas.drawBitmap(feixingqi,260+j*70,25+i*52,paint);
                        break;
                    case 30:
                        canvas.drawBitmap(undead1,260+j*70,25+i*52,paint);
                        break;
                    case 31:
                        canvas.drawBitmap(undead2,260+j*70,25+i*52,paint);
                        break;
                    case 32:
                        canvas.drawBitmap(undead3,260+j*70,25+i*52,paint);
                        break;
                    case 33:
                        canvas.drawBitmap(undead4,260+j*70,25+i*52,paint);
                        break;
                    case 34:
                        canvas.drawBitmap(snake1,260+j*70,25+i*52,paint);
                        break;
                    case 35:
                        canvas.drawBitmap(snake2,260+j*70,25+i*52,paint);
                        break;
                    case 36:
                        canvas.drawBitmap(snake3,260+j*70,25+i*52,paint);
                        break;
                    case 37:
                        canvas.drawBitmap(snake4,260+j*70,25+i*52,paint);
                        break;
                        default:
                            break;
                }
            }
        }


        switch (lastdirection){
            case LEFT:
                if(direction==STOP)
                    canvas.drawBitmap(p1_zb1,260+px*70,25+py*52, paint);
                else
                    canvas.drawBitmap(people3[current],260+px*70,25+py*52, paint);
                break;
            case UP:
                if(direction==STOP)
                    canvas.drawBitmap(p1_sm1,260+px*70,25+py*52, paint);
                else
                    canvas.drawBitmap(people1[current], 260+px*70,25+py*52, paint);
                break;
            case RIGHT:
                if(direction==STOP)
                    canvas.drawBitmap(p1_yb1, 260+px*70,25+py*52, paint);
                else
                    canvas.drawBitmap(people4[current], 260+px*70,25+py*52, paint);
                break;
            case DOWN:
                if(direction==STOP)
                    canvas.drawBitmap(p1_zm1,260+px*70,25+py*52, paint);
                else
                    canvas.drawBitmap(people2[current],260+px*70,25+py*52, paint);
                break;
            default:
                break;
        }
        paint.setAlpha(0x77);
        canvas.drawCircle(BigCenterX,BigCenterY,BigCenterR,paint);
        //绘制小圆
        canvas.drawCircle(SmallCenterX,SmallCenterY,SmallCenterR,paint);

        if(isBattle){
            canvas.drawRect(400,200,850,400,paint);
            canvas.drawText(monster,500,230,paint);
            canvas.drawText(attack,450,260,paint);
            canvas.drawText(String.valueOf(gongjili),550,260,paint);
            canvas.drawText(defence,450,290,paint);
            canvas.drawText(String.valueOf(fangyuli),550,290,paint);
            canvas.drawText(blood,450,320,paint);
            canvas.drawText(String.valueOf(HP),520,320,paint);
            canvas.drawText("VS",610,230,paint);
            canvas.drawText(hero,700,230,paint);
            canvas.drawText(attack,650,260,paint);
            canvas.drawText(String.valueOf(gongjili),750,260,paint);
            canvas.drawText(defence,650,290,paint);
            canvas.drawText(String.valueOf(fangyuli),750,290,paint);
            canvas.drawText(blood,650,320,paint);
            canvas.drawText(String.valueOf(HP),720,320,paint);
            canvas.drawBitmap(undead1,500,330,paint);
            canvas.drawBitmap(p1_zm1,700,330,paint);
        }

        sfh.unlockCanvasAndPost(canvas);
    }


    //新封装一个圆周运动时，设置小圆中心点的坐标位置
    public void setSmallCircleXY(float centerX,float centerY,float R,
                                 double rad){
        //这里是根据角度弧度的转换，再通过三角函数定理得到的小圆坐标位置的
        //获取圆周运动的X坐标
        SmallCenterX=(float)(R*Math.cos(rad)+centerX);
        //获取圆周运动的Y坐标
        SmallCenterY=(float)(R*Math.sin(rad)+centerY);
    }
    //得到玩家触点相对于大圆角度的方法
    public double getRad(float px1,float py1,float px2,float py2 ){
        //得到两点X的距离
        float x=px2-px1;
        //得到两点Y的距离
        float y=py1-py2;
        //算出斜边长
        float XB=(float)Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
        //得到这个角度的余弦值
        float cosAngle=x/XB;
        //通过反余弦定理获取其角度的弧度
        float rad=(float)Math.acos(cosAngle);
        //当触屏的位置Y坐标<摇杆的Y坐标，取反值-0 到 -180
        if(py2<py1)
            rad=-rad;
        return rad;
    }

    public boolean onTouchEvent(MotionEvent event){
        //当用户手指抬起，应该恢复小圆到初始位置
        if(event.getAction()==MotionEvent.ACTION_UP){
            SmallCenterX=BigCenterX;
            SmallCenterY=BigCenterY;
            direction=5;
        }else if(event.getAction()==MotionEvent.ACTION_DOWN||event.getAction()==MotionEvent.ACTION_MOVE){
            int pointX=(int)event.getX();
            int pointY=(int)event.getY();
            //判断用户点击的位置是否在大圆内
            if(Math.sqrt(Math.pow(BigCenterX-pointX,2)+Math.pow(BigCenterY-pointY,2))<=BigCenterR){
                //让小圆跟随用户触点位置移动
                SmallCenterX=pointX;
                SmallCenterY=pointY;
            }else{
                setSmallCircleXY(BigCenterX,BigCenterY,BigCenterR,getRad(BigCenterX,
                        BigCenterY,pointX,pointY));
            }
            double rad=getRad(BigCenterX,
                    BigCenterY,(int)event.getX(),(int)event.getY());
            if(rad<0.785&&rad>-0.785){
                direction=3;
                lastdirection=direction;
            }else if(rad>0.785&&rad<2.355){
                direction=4;
                lastdirection=direction;
            }else if((rad>2.355&&rad<3.14)||(rad>-3.14&&rad<-2.355)){
                direction=1;
                lastdirection=direction;
            }else if(rad>-2.355&&rad<-0.785){
                direction=2;
                lastdirection=direction;
            }
        }
        return true;
    }

    //逻辑函数
    private void logic(){
        movePeople();

    }

    private void movePeople() {
        current++;
        if (current >= people3.length) {
            current = 0;
        }

        /*
        -1代表主角 0代表空地 1代表墙壁 2代表铜钥匙 3代表银钥匙 4代表金钥匙
        5代表黄门 6代表蓝门 7代表红门 8代表上去的楼梯 9代表下去的楼梯 10代表初级药水
        11代表中级药水 12代表高级药水 13代表飞行器 13-20代表宝物 30-40代表怪物
        */
            switch (direction) {
                case LEFT:
                    if(px-1<0){

                    }else {
                        switch (map[louceng - 1][py][px-1]) {
                            case 0:
                                px = px - 1;
                                break;
                            case 1:
                                px=px;
                                break;
                            case 2:
                                yellowkey++;
                                map[louceng - 1][py][px-1] = 0;
                                px = px - 1;
                                break;
                            case 3:
                                bluekey++;
                                map[louceng - 1][py][px-1] = 0;
                                px = px - 1;
                                break;
                            case 4:
                                redkey++;
                                map[louceng - 1][py][px-1] = 0;
                                px = px - 1;
                                break;
                            case 5:
                                if (yellowkey > 0) {
                                    yellowkey--;
                                    map[louceng - 1][py][px-1] = 0;
                                    px = px - 1;
                                } else {

                                }
                                break;
                            case 6:
                                if (bluekey > 0) {
                                    bluekey--;
                                    map[louceng - 1][py][px-1] = 0;
                                    px = px - 1;
                                } else {

                                }
                                break;
                            case 7:
                                if (redkey > 0) {
                                    redkey--;
                                    map[louceng - 1][py][px-1] = 0;
                                    px = px - 1;
                                } else {

                                }
                                break;
                            case 8:
                                louceng++;
                                px = px - 1;
                                break;
                            case 9:
                                louceng--;
                                px = px - 1;
                                break;
                            case 10:
                                map[louceng - 1][py][px-1] = 0;
                                HP += 100;
                                px = px - 1;
                                break;
                            case 11:
                                map[louceng - 1][py][px-1] = 0;
                                HP += 500;
                                px = px - 1;
                                break;
                            case 12:
                                map[louceng - 1][py][px-1] = 0;
                                HP += 1000;
                                px = px - 1;
                                break;
                            case 13:
                                map[louceng - 1][py][px-1] = 0;
                                px = px - 1;
                                break;
                            case 30:
                                Battle();
                                break;
                            default:
                                break;
                        }
                    }
                    break;

                case UP:
                    if(py-1<0){

                    }
                    else {
                        switch (map[louceng - 1][py-1][px]) {
                            case 0:
                                py=py-1;
                                break;
                            case 1:
                                break;
                            case 2:
                                yellowkey++;
                                map[louceng - 1][py-1][px] = 0;
                                py=py-1;
                                break;
                            case 3:
                                bluekey++;
                                map[louceng - 1][py-1][px] = 0;
                                py=py-1;
                                break;
                            case 4:
                                redkey++;
                                map[louceng - 1][py-1][px] = 0;
                                py=py-1;
                                break;
                            case 5:
                                if (yellowkey > 0) {
                                    yellowkey--;
                                    map[louceng - 1][py-1][px] = 0;
                                    py=py-1;
                                } else {

                                }
                                break;
                            case 6:
                                if (bluekey > 0) {
                                    bluekey--;
                                    map[louceng - 1][py-1][px] = 0;
                                    py=py-1;
                                } else {

                                }
                                break;
                            case 7:
                                if (redkey > 0) {
                                    redkey--;
                                    map[louceng - 1][py-1][px] = 0;
                                    py=py-1;
                                } else {

                                }
                                break;
                            case 8:
                                louceng++;
                                py=py-1;
                                break;
                            case 9:
                                louceng--;
                                py=py-1;
                                break;
                            case 10:
                                map[louceng - 1][py-1][px] = 0;
                                HP += 100;
                                py=py-1;
                                break;
                            case 11:
                                map[louceng - 1][py-1][px] = 0;
                                HP += 500;
                                py=py-1;
                                break;
                            case 12:
                                map[louceng - 1][py-1][px] = 0;
                                HP += 1000;
                                py=py-1;
                                break;
                            case 13:
                                map[louceng - 1][py-1][px] = 0;
                                py=py-1;
                                break;
                            default:
                                break;
                        }
                    }
                    break;
                case RIGHT:
                    if(px+1>10){

                    }else {
                        switch (map[louceng - 1][py][px+1]) {
                            case 0:
                                px=px+1;
                                break;
                            case 1:
                                break;
                            case 2:
                                yellowkey++;
                                map[louceng - 1][py][px+1] = 0;
                                px=px+1;
                                break;
                            case 3:
                                bluekey++;
                                map[louceng - 1][py][px+1] = 0;
                                px=px+1;
                                break;
                            case 4:
                                redkey++;
                                map[louceng - 1][py][px+1] = 0;
                                px=px+1;
                                break;
                            case 5:
                                if (yellowkey > 0) {
                                    yellowkey--;
                                    map[louceng - 1][py][px+1] = 0;
                                    px=px+1;
                                } else {

                                }
                                break;
                            case 6:
                                if (bluekey > 0) {
                                    bluekey--;
                                    map[louceng - 1][py][px+1] = 0;
                                    px=px+1;
                                } else {

                                }
                                break;
                            case 7:
                                if (redkey > 0) {
                                    redkey--;
                                    map[louceng - 1][py][px+1] = 0;
                                    px=px+1;
                                } else {

                                }
                                break;
                            case 8:
                                louceng++;
                                px=px+1;
                                break;
                            case 9:
                                louceng--;
                                px=px+1;
                                break;
                            case 10:
                                map[louceng - 1][py][px+1] = 0;
                                HP += 100;
                                px=px+1;
                                break;
                            case 11:
                                map[louceng - 1][py][px+1] = 0;
                                HP += 500;
                                px=px+1;
                                break;
                            case 12:
                                map[louceng - 1][py][px+1] = 0;
                                HP += 1000;
                                px=px+1;
                                break;
                            case 13:
                                map[louceng - 1][py][px+1] = 0;
                                px=px+1;
                                break;
                            default:
                                break;
                        }
                    }
                    break;
                case DOWN:
                    if(py+1>10){

                    }else {
                        switch (map[louceng - 1][py+1][px]) {
                            case 0:
                                py=py+1;
                                break;
                            case 1:
                                break;
                            case 2:
                                yellowkey++;
                                map[louceng - 1][py+1][px] = 0;
                                py=py+1;
                                break;
                            case 3:
                                bluekey++;
                                map[louceng - 1][py+1][px] = 0;
                                py=py+1;
                                break;
                            case 4:
                                redkey++;
                                map[louceng - 1][py+1][px] = 0;
                                py=py+1;
                                break;
                            case 5:
                                if (yellowkey > 0) {
                                    yellowkey--;
                                    map[louceng - 1][py+1][px] = 0;
                                    py=py+1;
                                } else {

                                }
                                break;
                            case 6:
                                if (bluekey > 0) {
                                    bluekey--;
                                    map[louceng - 1][py+1][px] = 0;
                                    py=py+1;
                                } else {

                                }
                                break;
                            case 7:
                                if (redkey > 0) {
                                    redkey--;
                                    map[louceng - 1][py+1][px] = 0;
                                    py=py+1;
                                } else {

                                }
                                break;
                            case 8:
                                louceng++;
                                py=py+1;
                                break;
                            case 9:
                                louceng--;
                                py=py+1;
                                break;
                            case 10:
                                HP += 100;
                                py=py+1;
                                map[louceng - 1][py+1][px] = 0;
                                break;
                            case 11:
                                HP += 500;
                                py=py+1;
                                map[louceng - 1][py+1][px] = 0;
                                break;
                            case 12:
                                HP += 1000;
                                py=py+1;
                                map[louceng - 1][py+1][px] = 0;
                                break;
                            case 13:
                                map[louceng - 1][py+1][px] = 0;
                                py=py+1;
                                break;
                            default:
                                break;
                        }
                    }
                    break;
                case STOP:
                    break;
                default:
                    break;
            }
        }

    private void Battle() {
        isBattle=true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        flag=false;
    }

    @Override
    public void run() {
        while (flag){
            long start=System.currentTimeMillis();
            myDraw();
            logic();
            long end=System.currentTimeMillis();
            try{
                if(end-start<X){
                    Thread.sleep(X-(end-start));
                }
            } catch ( InterruptedException e ) {
                e.printStackTrace();
            }

        }
    }


      /*
        初始化地图  -1代表主角 0代表空地 1代表墙壁 2代表铜钥匙 3代表银钥匙 4代表金钥匙
        5代表黄门 6代表蓝门 7代表红门 8代表上去的楼梯 9代表下去的楼梯 10代表初级药水
        11代表中级药水 12代表高级药水 13-20代表宝物 30-40代表怪物
         */

    int map[][][]={
            {
                    {8, 0,30, 31, 30, 0, 0, 0, 0, 0, 0},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                    {10, 0, 0, 5, 0, 1, 0, 2, 0, 1, 0},
                    {0, 33, 0, 1, 0, 1, 0, 10, 0, 1, 0},
                    {1, 5, 1, 1, 0, 1, 1, 1, 5, 1, 0},
                    {2, 0, 0, 1, 0, 5, 32, 34, 32, 1, 0},
                    {0, 35, 0, 1, 0, 1, 1, 1, 1, 1, 0},
                    {1, 5, 1, 1, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 1, 1, 5, 1, 1, 1, 5, 1},
                    {10, 0, 2, 1, 2, 0, 0, 1, 0, 32, 0},
                    {10, 13, 2, 1, 0, 0, 0, 1, 30, 11, 30},
            }
    };
}



