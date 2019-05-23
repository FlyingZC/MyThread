# demoList
## com.zc.z01demo包
T01CounterUnSafe
线程不安全:多线程计数线程不安全

T01Join:join,sleep,yield

T01JoinTenThreads:生成1-1000的二维数组,启动十个线程,分别得到每一行的最大值,join

T02CreateThreadCallable : 实现Callback创建线程,并通过Future接收执行结果

T02CreateThreadDemo : 创建线程的几种方式

T02InnerThread:使用内部类创建线程

T02CreateThreadRunnable : runnable

T02Daemon : 守护线程

T03WindowUnSafe:售票窗口,有线程安全问题

T03WindowSafe1 : synchronized同步代码块

T03WindowSafe2 : synchronized同步方法

T07Singleton:单例模式,懒汉式

T07SingletonProblem:启动十条线程创建测试懒汉式,发现有线程安全问题

T08DeadLock:模拟死锁产生的原因

T09CountTenHun:子线程执行10次.主线程执行100次....直接使用匿名内部类方式实现

T09CountTenHunClass:
`子线程执行10次.主线程执行100次....Business业务逻辑类中使用synchronized方法实现`

T09TongXin:实现两个线程交替输出1-100 wait,notify.
	class TongXin implements Runnable:业务直接实现Runnable接口方式

T10Customer:生产者消费者

T10CustomerImportant:
`实现多个生产者和消费者并发的情形,每次会消费或生产不同数量的商品`

T10CustomerRestaurant : 

T10ProducterCustomer:生产者消费者

T11WhichRun:当new Thread(new Runnable(){}){}里两个run方法均实现,执行哪个

T12Synchronized:同一个同步监视器对象加锁

T12Synchronized2:同步监视器对象不是同一个对象,不加锁

T12Synchronized3:当一个线程访问 某对象的synchronized方法时,另一个线程仍可以同时访问该对象的非同步代码
**注意其中Thread和Runnable和Business解耦的写法**

T12Synchronized4:当一个线程访问同步监视器对象的synchronized方法时,另一个线程 不可 访问其他的同步方法

T12SynchronizedMonitor:
`一个类中使用类名,对象名,静态方法,普通方法,线程的同步结果`

T12SynchronizedRe : synchronized锁支持可重入

### 中断
T18Interrupt:interrupt执行流程
T18Interrupt2

### ThreadLocal
T21ThreadLocalVariableHolder

T21ThreadLocal:线程范围内共享变量,ThreadLocal

# com.zc.concurrent包

# com.zc.re包

# com.zc.exercise包

