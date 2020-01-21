package battle;
import monster.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

public class Battle {
	public AtomicReference<Monster> m = new AtomicReference<>(null);
	public Lock battleLock = new ReentrantLock();
	public Condition battleCondition = battleLock.newCondition();
	
	public static void main(String[] args) throws InterruptedException {
		Battle battle = new Battle();
		List<Monster> monsters = new ArrayList<>(Utils.generateMonsters(3));
		System.out.println(monsters);
		List<Thread> threads = new ArrayList<>();
		for (Monster m: monsters) {
			threads.add(new Thread(new MonsterRunner(battle, m)));
		}
		
		for (Thread t : threads) {
			t.start();
		}
		
		Thread.sleep(10000);
		System.out.println("Time's up, battle over");
		for (Thread t : threads) {
			t.interrupt();
			t.join();
		}
	
		for(Monster m : monsters) {
			System.out.println(m.getName() + " has " + m.getHitPoints() + " left.");
		}
		
	}

}
