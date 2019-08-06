package com.ovo.rxandroiddemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.ovo.network.utils.AppContext;
import com.ovo.network.utils.DefaultObserver;
import com.ovo.network.utils.ProgressUtils;
import com.ovo.rxandroiddemo.been.KLineInfo;
import com.ovo.rxandroiddemo.utils.RetrofitHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.functions.Func1;

public class MainActivity extends Activity {
    private final String TAG = this.getClass().getSimpleName();

    /****
     * 上拉刷新
     */
    private SmartRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppContext.context = this;

//        test();
//        testJust();
//        testMap();
//        test1();
        testMap();
        testFlatMap();
    }

    /**
     * 简单使用
     */
    private void test(){
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String a = "123";
                emitter.onNext(a);
                Thread.sleep(6*1000);
                emitter.onNext(a);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("-------", "onSubscribe");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("-------", s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("-------", "onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.e("-------", "onComplete");
                    }
                });

    }

    /**
     * 一秒执行一次
     */
    private void test1(){
        Observable.interval(1, TimeUnit.SECONDS, Schedulers.newThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.e(TAG, "一秒执行一次onNext：" + String.valueOf(aLong));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "一秒执行一次onComplete");
                    }
                });
    }

    /***
     * rxjava框架使用
     */
    private void test2(){
        RetrofitHelper.getApiServer().getKLine1()
//                .compose(this.<String>bindToLifecycle())
                .compose(ProgressUtils.applyProgressBar(MainActivity.this, "加载"))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<KLineInfo>() {
                    @Override
                    public void onSuccess(KLineInfo response) {
                        Log.e("====", response.toString() + "");
                    }
                });
    }

    /**
     * 上传图片
     */
    private void upload(){
        File file = new File("");
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Part body = MultipartBody.Part
                .createFormData("image", file.getName(),requestBody);
        RetrofitHelper.getApiServer().upload(body)
                .compose(ProgressUtils.applyProgressBar(MainActivity.this, "上传"))
                .observeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<String>() {
                    @Override
                    public void onSuccess(String response) {
                        Log.e("-------", "上传成功");
                    }
                });
    }

    /***
     * just的使用
     */
    private void testJust(){
        Observable.just("123").subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e("-------", s);
                    }
                });
    }

    /**操作符 map
     * map和flatMap都是来完成Observable构造的数据到Observer接收数据的一个转换
     * map函数只有一个参数，参数一般是Func1，
     * Func1的<I,O>I,O模版分别为输入和输出值的类型，
     * 实现Func1的call方法对I类型进行处理后返回O类型数据
     * */
    public void testMap(){
        class Course {
            private String name;
            public Course(String name) {
                this.name = name;
            }
            public String getName() {
                return name;
            }
            @Override
            public String toString() {
                return super.toString();
            }
        }

        class Student {
            private String name;
            private List<Course> courseList = new ArrayList<>();
            public String getName() {
                return name;
            }
            public List<Course> getCourseList() {
                return courseList;
            }
            public void setCourseList(List<Course> courseList) {
                this.courseList = courseList;
            }
            public void setName(String name) {
                this.name = name;
            }
        }

        List<Course> list1 = new ArrayList<>();
        list1.add(new Course("1"));
        list1.add(new Course("2"));
        list1.add(new Course("3"));

        Student student1 = new Student();
        student1.setName("student1");
        student1.setCourseList(list1);

        List<Course> list2 = new ArrayList<>();

        list2.add(new Course("4"));
        list2.add(new Course("5"));
        list2.add(new Course("6"));

        Student student2 = new Student();
        student2.setName("student2");
        student2.setCourseList(list2);
        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);

        rx.Observable.from(students).map(new Func1<Student, List<Course>>() {
            @Override
            public List<Course> call(Student student) {
                return student.getCourseList();
            }
        })
        .subscribe(new Subscriber<List<Course>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Course> courses) {
                for (int i = 0; i < courses.size(); i++) {
                    Log.e(TAG, "----1------" + courses.get(i).getName());
                }
            }
        });
    }

    /**操作符 flatMap
     * map和flatMap都是来完成Observable构造的数据到Observer接收数据的一个转换
     * flatMap实际上是将一个Observable对象分拆成两个，
     * 然后再依次发送出去，从而达到可以去掉for循环，优化结构的目的
     * flatMap函数也只有一个参数，也是Func1,Func1的<I,O>I,O模版分别为输入和输出值的类型，
     * 实现Func1的call方法对I类型进行处理后返回O类型数据，不过这里O为Observable类型
     * */
    public void testFlatMap(){
        class Course {
            private String name;
            public Course(String name) {
                this.name = name;
            }
            public String getName() {
                return name;
            }
            @Override
            public String toString() {
                return super.toString();
            }
        }

        class Student {
            private String name;
            private List<Course> courseList = new ArrayList<>();
            public String getName() {
                return name;
            }
            public List<Course> getCourseList() {
                return courseList;
            }
            public void setCourseList(List<Course> courseList) {
                this.courseList = courseList;
            }
            public void setName(String name) {
                this.name = name;
            }
        }

        List<Course> list1 = new ArrayList<>();
        list1.add(new Course("1"));
        list1.add(new Course("2"));
        list1.add(new Course("3"));

        Student student1 = new Student();
        student1.setName("student1");
        student1.setCourseList(list1);

        List<Course> list2 = new ArrayList<>();

        list2.add(new Course("4"));
        list2.add(new Course("5"));
        list2.add(new Course("6"));

        Student student2 = new Student();
        student2.setName("student2");
        student2.setCourseList(list2);
        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);

        rx.Observable.from(students)
                .flatMap(new Func1<Student, rx.Observable<Course>>() {
                    @Override
                    public rx.Observable<Course> call(Student student) {
                        return rx.Observable.from(student.getCourseList());
                    }
                })
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Course>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "-----onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "-------onError");
                    }

                    @Override
                    public void onNext(Course courses) {
                        Log.e(TAG, "-----onNext"+courses.getName());
                    }
                });
    }


    /**
     * 上拉加载下拉刷新
     */
    private void reflesh(){
        refreshLayout = findViewById(R.id.refreshLayout);

        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Log.e("RefreshLoadMore", "--------------onRefresh");
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefresh();
                        refreshLayout.resetNoMoreData();//恢复上拉状态
                    }
                },2000);
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Log.e("RefreshLoadMore", "--------------onLoadMore");
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (false){//没有更多数据
                            //设置之后，将不会再触发加载事件
                            refreshLayout.finishLoadMoreWithNoMoreData();
                        }else {
                            refreshLayout.finishLoadMore();
                        }
                    }
                },2000);
            }
        });
    }
}
