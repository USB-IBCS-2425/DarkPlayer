class Enclosure {
	String name;
	String animals;
	int numPandas;
	int totalWeight;
	boolean open;

	public int getNumAnimals() {
		return numPandas;
	}

	public void addAnimal(Panda animal) {
		animals += (animal.getName() + " (age " + animal.getAge() + ") ");
		totalWeight += animal.getWeight();
		numPandas++;
	}

	public String getAllAnimals() {
		return animals;
	}

	public boolean isOpen() {
		return open;
	}

	public int getTotalWeight() {
		return totalWeight;
	}

	public Enclosure(String n) {
		name = n;
		animals = "";
		numPandas = 0;
		totalWeight = 0;
		open = true;
	}

	public static void main(String[] args) {
		Enclosure PandaHouse = new Enclosure("PandaHouse");

		Panda Jeff = new Panda("Jeff", 5, 260);
		Panda Dan = new Panda("Dan", 12, 240);
		Panda Baby = new Panda("Baby", 1, 1);

		PandaHouse.addAnimal(Jeff);
		PandaHouse.addAnimal(Dan);
		PandaHouse.addAnimal(Baby);

		System.out.println(PandaHouse.getAllAnimals());
		System.out.println(PandaHouse.getNumAnimals());
	}

}