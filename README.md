# concurrent_programming_example
These classes allow a large group of Monsters to battle together, "battle royale" style – that is, each Monster will repeatedly find another Monster to battle until it is knocked out.
Each Monster will battle in its own, separate thread, and the process proceeds as follows:
[When a Monster m1 is ready to battle, it checks the waiting area. If another monster m2 is already waiting, then m1 will battle m2. If no other Monster is waiting, m1 will go to the waiting area and wait for another monster to be ready. When two Monsters m1 and m2 battle, here is what happens: [First, one of the monsters is randomly chosen to be the attacker and the other will
be the defender.The attacker randomly chooses one of its attacks, and attacks the defender using the Monster.attack() method.]]
