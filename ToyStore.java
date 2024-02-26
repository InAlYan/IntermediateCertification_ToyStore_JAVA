import java.util.*;

public class ToyStore {

    // Приоритетная очередь для хранения игрушек в PriorityArrayList-ах, объединяющие игрушки по шансу выпадения
    private Queue<PriorityArrayList> pq = new PriorityQueue<>();

    // Хранит существующие частоты выпадения нарастающим итогом для внутренних нужд например частоты выпадения
    // 1, 2, 3, 4, 6, 7, 8, 9 будут храниться так: |1|3|6|10|16|23|31|40|
    private List<Double> camulativeFrequencyOfDropoutList = new ArrayList<>();

    public ToyStore() {}
    public ToyStore(Queue<PriorityArrayList> pq) {
        this.pq = pq;
    }

    public Queue<PriorityArrayList> getPq() {
        return pq;
    }

    /**
     * Возвращает лист существующих частот выпадения упорядоченных по возрастанию
     * @return resList - ArrayList<Double> лист существующих частот выпадения упорядоченных по возрастанию
     */
    private List<Double> getFrequencyOfDropoutList() {
        List<Double> resList = new ArrayList<>();
        for (PriorityArrayList pal: pq) {
            if (! resList.contains(pal.getFrequencyOfDropout())) {
                resList.add(pal.getFrequencyOfDropout());
            }
        }
        Collections.sort(resList);
        return resList;
    }

    /**
     * Создает из листа ArrayList<Double> вида in = 1,2,3,4,6,7,8,9 лист вида out = |1|3|6|10|16|23|31|40| => out[i] = in[i] + out[i-1]
     * @param inList - ArrayList<Double>
     * @return resList - ArrayList<Double> создает из листа ArrayList<Double> вида 1,2,3,4,6,7,8,9 лист вида: |1|3|6|10|16|23|31|40|
     */
    private List<Double> fillCamulativeFrequencyOfDropoutList(List<Double> inList) {
        List<Double> resList = new ArrayList<>();
        for (int i = 0; i < inList.size(); i++) {
            if (i == 0) {
                resList.add(i, inList.get(i));
            } else {
                resList.add(i, inList.get(i) + resList.get(i - 1));
            }
        }
        return resList;
    }

    /**
     * Возвращает настоящую частоту выпадения игрушки из листа (например 1,2,3,4,6,7,8,9) на основании выпавшего случайного числа
     * rndDropNumber (например 19) которое сравнивается с листом |1|3|6|10|16|23|31|40| выясняется что оно попадает в диапазон от
     * 16 до 23 и вычисляется настоящая частота выпадения игрушки как 23 - 16 = 7
     * @param rndDropNumber - double число, соотнесенное с тем, игрушка с какой частотой выпадения должна быть взята
     * @return double - настоящая частота выпадения игрушки на основании случайного входного числа rndDropNumber
     */
    private double translateDropNumberToFrequencyOfDrop(double rndDropNumber) {
        if (rndDropNumber > camulativeFrequencyOfDropoutList.getLast()) {
            rndDropNumber = camulativeFrequencyOfDropoutList.getLast();
        }
        if (rndDropNumber < camulativeFrequencyOfDropoutList.getFirst()) {
            rndDropNumber = camulativeFrequencyOfDropoutList.getFirst();
        }
        int sizeOfFrequencyOfDropoutList = camulativeFrequencyOfDropoutList.size();
        if (sizeOfFrequencyOfDropoutList > 2) {
            for (int i = 0; i < sizeOfFrequencyOfDropoutList; i++) {
                if (i == 0) {
                    if (camulativeFrequencyOfDropoutList.get(i) >= rndDropNumber) {
                        return camulativeFrequencyOfDropoutList.get(i);
                    }
                } else {
                    if (camulativeFrequencyOfDropoutList.get(i) >= rndDropNumber) {
                        return camulativeFrequencyOfDropoutList.get(i) - camulativeFrequencyOfDropoutList.get(i - 1);
                    }
                }
            }
            return camulativeFrequencyOfDropoutList.get(sizeOfFrequencyOfDropoutList - 1) -
                    camulativeFrequencyOfDropoutList.get(sizeOfFrequencyOfDropoutList - 2);
        } else {
            return camulativeFrequencyOfDropoutList.getFirst();
        }
    }

    /**
     * Возвращает ArrayList<Toy> соотнесенный с нашим случайно выпавшим числом rndDropNumber характеризующим "вес" игрушки, null - если такой элемент не найден
     * @param rndDropNumber - double число, соотнесенное с тем, лист с набором каких игрушек с какой частотой выпадения должна быть взята
     * @return PriorityArrayList - ArrayList<Toy> соотнесенный с нашим случайно выпавшим числом rndDropNumber характеризующим "вес" игрушки, null - если такой элемент не найден
     */
    private PriorityArrayList findNecessaryPriorityArrayList(double rndDropNumber) {
        double foundingFrequencyOfDrop = translateDropNumberToFrequencyOfDrop(rndDropNumber);
        for (PriorityArrayList pal: pq) {
            if (pal.getFrequencyOfDropout() == foundingFrequencyOfDrop) {
                return pal;
            }
        }
        return null;
    }

    /**
     * Получить игрушку в соответствии с частотой ее выпадения
     * @return toy - Toy игрушка
     */
    public Toy getNextToyByFrequencyOfDropout() throws EmptyToyStoreException {

        if (pq.isEmpty()) {
            throw new EmptyToyStoreException("Магазин игрушек пуст");
        }

        Random rnd = new Random();
        double rndDropNumber = rnd.nextDouble(0, camulativeFrequencyOfDropoutList.getLast());

        System.out.println("Нормированный к шансам выпадения игрушек текущий шанс: " + rndDropNumber); // ДЛЯ ТЕСТОВ

        // НАХОДИМ PriorityArrayList, ОБЪЕДИНЯЮЩИЙ ПО ЧАСТОТЕ ВЫПАДЕНИЯ dropNumber НЕСКОЛЬКО ИГРУШЕК
        PriorityArrayList npal = findNecessaryPriorityArrayList(rndDropNumber);
        Toy toyToReturn;
        if (npal != null) {
            // ВЕРНУТЬ ОДНУ СЛУЧАЙНУЮ ИГРУШКУ И УДАЛИТЬ ЕЕ ИЗ СПИСКА PriorityArrayList
            toyToReturn = npal.getToys().remove(rnd.nextInt(0, npal.getToys().size()));
            // ЕСЛИ СПИСОК PriorityArrayList СТАЛ ПУСТЫМ УДАЛИТЬ
            if (npal.getToys().isEmpty()) {
                pq.remove(npal);
            }
            return toyToReturn;
        }
        // ЗДЕСЬ ВОЗВРАЩАЕМ НАЙДЕННУЮ ПЕРВУЮ ИГРУШКУ С САМЫМ ВЫСОКИМ ШАНСОМ, ЕСЛИ ЧТО ТО ПОШЛО НЕ ТАК  И УДАЛЯЕМ ЕЕ ИЗ СПИСКА
        PriorityArrayList hfdpal = pq.peek(); // Лист игрушек с самым высоким шансом выпадения
        toyToReturn = hfdpal.getToys().remove(rnd.nextInt(0, hfdpal.getToys().size()));
        // ЕСЛИ СПИСОК PriorityArrayList СТАЛ ПУСТЫМ УДАЛИТЬ
        if (hfdpal.getToys().isEmpty()) {
            pq.remove(hfdpal);
        }
        return toyToReturn;
    }

    /**
     * Добавить в магазин новую игрушку
     * @param toy - Toy добавляемая в магазин игрушка
     * @return PriorityArrayList - лист объединяющий игрушки с одной частотой выпадения в который попала текущая игрушка
     * @throws IncorrectMatchFrequencyOfDropoutException
     */
    public PriorityArrayList appendToy(Toy toy) throws IncorrectMatchFrequencyOfDropoutException {
        for (PriorityArrayList pal: pq) {
            if (pal.getFrequencyOfDropout() == toy.getFrequencyOfDropout()) {
                // ДОБАВЛЯЕМ ИГРУШКУ В СУЩЕСТВУЮЩИЙ ARRAYLIST С ТАКИМ ШАНСОМ
                try {
                    pal.appendToy(toy);
                } catch (IncorrectMatchFrequencyOfDropoutException e) {
                    throw new IncorrectMatchFrequencyOfDropoutException(
                            String.format("Попытка записать в лист с игрушками с одинаковой частотой выпадения %f игрушку %s с другой частотой выпадения %f",
                                    toy.getFrequencyOfDropout(), toy.getName(), pal.getFrequencyOfDropout()), pal.getFrequencyOfDropout());
                }
                return pal;
            }
        }
        // СОЗДАЕМ ARRAYLIST С ТАКИМ ШАНСОМ
        PriorityArrayList newPal = new PriorityArrayList(toy.getFrequencyOfDropout());
        // ДОБАВЛЯЕМ ИГРУШКУ В СОЗДАННЫЙ ARRAYLIST С ТАКИМ ШАНСОМ
        try {
            newPal.appendToy(toy);
        } catch (IncorrectMatchFrequencyOfDropoutException e) {
            System.out.println(e.getMessage());
        }
        pq.add(newPal);
        // ПЕРЕЗАПОЛНЯЕМ List<Double> camulativeFrequencyOfDropoutlist
        this.camulativeFrequencyOfDropoutList = fillCamulativeFrequencyOfDropoutList(getFrequencyOfDropoutList());
        return newPal;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Магазин игрушек:\n");
        for (PriorityArrayList pal: pq) {
            sb.append(pal.toString());
        }
        return sb.toString();
    }
}
