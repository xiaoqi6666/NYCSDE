public class Consumer implements Runnable{
    private Queue<Product> queue;
    private int maxCapacity;

    public Consumer(Queue queue, int maxCapacity) {
        this.queue = queue;
        this.maxCapacity = maxCapacity;
    }

    @Override
    public void run() {
        synchronized (queue) {
            while (queue.isEmpty()) {
                try {
                    System.out.println("消费者" + Thread.currentThread().getName() + "等待中... Queue 已缺货，无法消费");
                    wait();
                    System.out.println("消费者" + Thread.currentThread().getName() + "退出等待");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (queue.size() == maxCapacity) {
                queue.notifyAll();
            }

            Product product = queue.poll();
            System.out.println("消费者" + Thread.currentThread().getName() + "消费了：" + product.getName());
        }
    }
}
