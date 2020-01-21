package battle;

import monster.*;

public class MonsterRunner implements Runnable {
	private Battle battle;
	private Monster monster;

	public MonsterRunner(Battle battle, Monster monster) {
		this.battle = battle;
		this.monster = monster;
	}

	@Override
	public void run() {
		while (monster.getHitPoints() > 0) {
			try {
				battle.battleLock.lockInterruptibly();
				System.out.println(monster.getName() + ": Got the lock...");
			} catch (InterruptedException e1) {
				System.out.println(monster.getName() + ": interrupted in lock()");
				break;
			}

			if (battle.m.compareAndSet(null, monster)) {
				System.out.println(monster.getName() + ": waiting for another monster");
				try {
					battle.battleCondition.await();
				} catch (InterruptedException e) {
					battle.battleLock.unlock();
					System.out.println(monster.getName() + ": interrupted in await()");
					break;
				}
				System.out.println(monster.getName() + ": has been fought.");

			} else {
				Monster m2 = battle.m.getAndSet(null);
				System.out.println(monster.getName() + ": has found a waiting monster and is about to fight it.");

				if (Utils.RAND.nextBoolean()) {
					Utils.doAttack(monster, m2);
				} else {
					Utils.doAttack(m2, monster);
				}
				battle.battleCondition.signal();
			}
			battle.battleLock.unlock();
			System.out.println(monster.getName() + ": released the lock");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				break;
			}
		}
	}
}
