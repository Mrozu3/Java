Implementacja kolejki priorytetowej bez java.util.PriorytyQueue, z dwoma operacjami:
Wstawianie nowego elementu do kolejki void add() - złożoność O(1)
Zwracanie i usuwanie z kolejki element o najwyższym priorytecie. Jeśli w kolejce znajduje się kilka obiektów o najwyższym priorytecie, zwracany jest ten z nich, który został tam wstawiony najwcześniej Integer get() - zlozonosc O(n);