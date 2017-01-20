# DANCING LINKS

## LA COUVERTURE EXACTE DE MATRICE
<p align="justify">
Le problème de la couverture exacte de matrice (EMC) est un problème d’optimisation combinatoire NP-complet. Étant donnée une matrice contenant uniquement des 0 et des 1, il s’agit de déterminer un sous-ensemble de ses lignes contenant un 1 et un seul par colonne. Pour exemplifier, les lignes 0, 4 est une solution possible du problème EMC suivant.
</p>

<p align="center">
| 1 0 1 1 | <br>
| 0 1 1 0 | <br>
| 1 1 0 1 | <br>
| 1 0 0 1 | <br>
| 0 1 0 0 | <br>
</p>

<p align="justify">
Une variante de ce problème est d’avoir des colonnes primaires, qui doivent nécessairement être couvertes, et des colonnes secondaires, qui peuvent ne pas être couvertes.
</p>

<p align="justify">
De nombreux problèmes peuvent être réduits au problème d’EMC. Dans ce projet nous avons montré deux applications : le problème de pavage et la solution d’un jeu Sudoku quel- conque.
</p>

## LES LIENS DANSANTS

<p align="justify">
Les liens dansants sont une structure de données utilisée dans l’algorithme X, développé par Donald Knuth, qui permet de résoudre le problème de la couverture exacte de matrice.
</p>
<p align="justify">
Dans l’approche de notre projet pour ce problème, nous avons créé une classe "DLXTable" qui représente cette structure et qui est munie d’une méthode Solve() récursive qui implémente l’algorithme X. Pour ce faire, on a dû aussi implémenter d’autres méthodes auxiliaires, comme les méthodes pour lire l’entrée, pour couvrir/découvrir une colonne, sauvegarder et imprimer les sorties de l’algorithme.
</p>
<p align="justify">
Dans le code que nous vous avons fourni, on imprime la quantité totale de solutions ainsi que la première solution. Si besoin, notre code peut être très facilement changé pour imprimer toutes les solutions.
</p>
<p align="justify">
En ce qui concerne les structures auxiliaires, nous avons utilisé deux classes "Cell" et "Co- lumns" (qui hérite de Cell) pour représenter les cellules avec des 1’s et les cellules correspondant aux colonnes dans la structure des liens dansants.
</p>
<p align="justify">
En ce qui concerne l’algorithme, nous avons utilisé l’optimisation suggérée par l’article de Donald Knuth, selon laquelle il fallait toujours couvrir la colonne avec le moins d’éléments restants avant de faire l’appel récursif. Cela permet d’avoir moins de branches dans l’arbre de récursion.
</p>

## SCREENSHOTS

Application au problème du pavage

![Alt text](screenshots/gui.png?raw=true "Gui")
