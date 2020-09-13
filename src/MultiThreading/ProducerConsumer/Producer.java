public class Producer implements Runnable{
    private Queue<Product> queue;
    private int maxCapacity;

    public Producer(Queue queue, int maxCapacity) {
        this.queue = queue;
        this.maxCapacity = maxCapacity;
    }

    @Override
    public void run() {
        synchronized (queue) {
            while (queue.size() == maxCapacity) { //一定要用 while，而不是 if，下文解释
                try {
                    System.out.println("生产者" + Thread.currentThread().getName() + "等待中... Queue 已达到最大容量，无法生产");
                    wait();
                    System.out.println("生产者" + Thread.currentThread().getName() + "退出等待");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (queue.size() == 0) { //队列里的产品从无到有，需要通知在等待的消费者
                queue.notifyAll();
            }
            Random random = new Random();
            Integer i = random.nextInt();
            queue.offer(new Product("产品"  + i.toString()));
            System.out.println("生产者" + Thread.currentThread().getName() + "生产了产品：" + i.toString());
        }
    }
}
