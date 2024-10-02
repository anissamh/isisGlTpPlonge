package lavage;

import java.util.LinkedList;
import java.util.List;

class PileDAssiettes {

	private final static int MAX = 10;
	private final List<Assiette> myList = new LinkedList<>();

	private boolean isEmpty() {
		return myList.isEmpty();
	}

	private boolean isFull() {
		return (myList.size() >= MAX);
	}

	synchronized	public void push(Assiette assiette) throws InterruptedException {
		while ((isFull())) {

			wait();
		}
		myList.add(assiette);
		System.out.printf("la pile contient %d assiettes%n", myList.size());
		//ici les laveurs pushen tque les assiettes sont lavées mais ne notifient personne c pour cela que l'essuyeur n arrive pas au debut à acceder à l'assiette
		notifyAll(); // Notifier que la pile est  pleine

	}

	//synchronized pour eviter que ça crache tout est sychro car on a +rurs thread du coup bcp de choses utilisent
	//pour sortr d un wait il faut notifier le thread
	synchronized public Assiette pop() throws InterruptedException {
		// Attendre que la pile ne soit pas vide
		while (isEmpty()) {
			wait();
		}
		//assert !isEmpty(); // On est sur que la pile n'est pas vide
		Assiette result = myList.remove(myList.size() - 1);
		System.out.printf("la pile contient %d assiettes%n", myList.size());
		notifyAll(); // Notifier que la pile n'est plus pleine
		return result;
	}
}
