package demo3;

/**
 * 
 * 目标：在 main 函数启动一个新线程，运行一个方法，拿到这个方法的返回值后，退出主线程
 * @author 1
 *
 */

public class TheadTest extends Thread {
	
	// 1.创建线程 创建现成的几种方法 直接创建Thread 重写 runnable  线程池单例 线程池固定1个 变动1 无限制1 
	// 2.运行方法 拿到返回值 获取返回值得方法 Future Callable  
	// 3.退出主线程 退出线程的方法 1.正常执行完成  2.标识符退出  3.stop不安全，废弃  4.interrupt() 中断线程
	// 4.数据共享 1.volatile 内存数据共享   2.ThreadLocal 线程范围内数据 
	public static void main(String[] args) {

		long start = System.currentTimeMillis();
		// 线程穿件
		
		
		//1.直接创建线程，重写run,run中执行sum()方法，根据返回值输出字符串，正常执行完线程
		//new TheadTest().start();
		
		//2.线程重写runnable，runnable中执行方法，正常执行完成
//		Runnable task = new Runnable() {
//			@Override
//			public void run() {
//				try {
//					long start = System.currentTimeMillis();
//					int result = sum();
//					System.out.println("异步计算结果为：" + result);
//					System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				Thread t = Thread.currentThread();
//				System.out.println("当前线程:" + t.getName());
//			}
//		};
//		Thread thread = new Thread(task);
		
//		//3自定义线程 
//		int oo = 0 ;
//		String name = "";
//		MyThread thread1 = new MyThread(name);
//		thread1.setOo(oo);
//		thread1.start();
//		System.out.println("异步计算结果为：" + oo);
		
		
		// 线程池1.单独1个的；2固定1个的；
		//线程池关闭 shutdown  shutdown()： shutdownNow()：
		//4 线程池  4种： 单例的；固定的创建1个 ；变动的也创建1个 ；无限制的也创建1个线程 执行后Future获取结果，shutdown关闭线程池
//		ExecutorService executor = Executors.newCachedThreadPool();
//		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);
//		ExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);
//		ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
//		
//        Future<Integer> result = executor.submit(new Callable<Integer>() {
//            public Integer call() throws Exception {
//                return sum();
//            }
//        });
//        executor.shutdown();
//        try {
//            System.out.println("result:" + result.get());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
		
		
//	  5FutureTask 重写Callable 获取返回结果 
//	  FutureTask<Integer> task = new FutureTask<Integer>(new Callable<Integer>() {
//            @Override
//            public Integer call() throws Exception {
//                return sum();
//            }
//        });
//        new Thread(task).start();

		//6 ThreadLocal 线程内标量赋值获取参数
//		static ThreadLocal<Integer> localVar = new ThreadLocal<>(); 
//		Thread t1  = new Thread(new Runnable() {
//            @Override
//         public void run() {
//            	localVar.set(sum());
//                 System.out.println("异步计算结果为："+localVar.get());
//             }
//         });

		//7 interrupt 退出
//		try {
//			TheadTest t = new TheadTest();
//	        t.start();
//	        Thread.sleep(200);
//	        t.interrupt();
//	    } catch (InterruptedException e) {
//	        e.printStackTrace();
//	    }
		
		
		//8 标识符判断退出状态
//		public volatile boolean exit = false; 
//	    @Override
//	    public void run() {
//	        ServerSocket serverSocket = new ServerSocket(8080);
//	        while(!exit){
//	            serverSocket.accept(); //阻塞等待客户端消息
//	            ...
//	        }
//	    }
//	    
//	    public static void main(String[] args) {
//	        TheadTest t = new TheadTest();
//	        t.start();
//	        ...
//	        t.exit = true; 
//	    }

	}

	private static int sum() {
		return fibo(36);
	}

	private static int fibo(int a) {
		if (a < 2)
			return 1;
		return fibo(a - 1) + fibo(a - 2);
	}
	
	@Override
	public void run() {
		long start = System.currentTimeMillis();
		int result = sum();
		System.out.println("异步计算结果为：" + result);
		System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }
	
	static class MyThread extends Thread {
	    
	    private String name;
	    private int oo;
	    
	    public void setOo(int oo) {
	        this.oo = oo;
	    }
	    
	    public MyThread(String name) {
	        this.name = name;
	    }
	    
	    @Override
	    public void run() {
	    	 oo = sum();
	    }
	}  
	

//	参照代码	
//	public class Homework03 {
//	    
//	    public static void main(String[] args) {
//	        
//	        long start=System.currentTimeMillis();
//
//	        // 在这里创建一个线程或线程池，
//	        // 异步执行 下面方法
//	    
//	        int result = sum(); //这是得到的返回值
//	        
//	        // 确保  拿到result 并输出
//	        System.out.println("异步计算结果为："+result);
//	         
//	        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
//	        
//	        // 然后退出main线程
//	    }
//	    
//	    private static int sum() {
//	        return fibo(36);
//	    }
//	    
//	    private static int fibo(int a) {
//	        if ( a < 2) 
//	            return 1;
//	        return fibo(a-1) + fibo(a-2);
//	    }

}
