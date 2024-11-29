Do uruchomienia niezbędne jest zainstalowanie biblioteki Graphvitz, można to zrobić za pomocą pip-a.   
Aby przetestować działanie programu należy uruchomić plik main.ipynb. Następnie dane można wprowadzić na 2 sposoby.
# I sposób
Dane należy wpisać do pliku input.txt dane w podanym na przykładzie formacie:

6  
x := x + 1   
y := y + 2 * z   
x := 3 * x + z  
w := w + v  
z := y - z  
v := x + v  
acdcfbbe

W 1 linii podaje się ilość operacji, następnie podaje się operacje, każda w nowej linii, z zastrzeżeniem,   
aby nie pomijać znaków mnożenia przy zmiennych, czyli przykładowo: zamiast podawać 3x, zastąpić to 3*x.  
Wszystkie zmienne powinny być oznaczone małymi/dużymi literami alfabetu.   
Dobrze, gdyby każda z operacji miała format:  
zmienna := operacje   
Na sam koniec podaje się słowo w, oznaczające przykładowe wykonanie sekwencji akcji. Przyjmuje się, że operacje są oznaczane literami alfabetu, małymi lub dużymi (jeśli jest ich więcej niż 26) i są numerowane od małego 'a'. Duże litery następują zaraz po skończeniu się małych w numeracji.
# 2 sposób
Dane należy wpisać do pliku input_version2.txt w formacie:   
1 linia powinna zawierać ilość operacji - n   
2 linia ilość zmiennych - m   
Dalej następuje n linii takich, że na początku podajemy ilość zmiennych występujących po prawej stronie operacji, w kolejnej linii podajemy zmienną występującą po lewej stronie,   
a w następnej podajemy listę zmiennych występujących po prawej stronie, oddzielonych spacjami. Zmienne podajemy jako zwykłe liczby.   
Na sam koniec podajemy przykładowe słowo, w postaci liczb oddzielonych spacjami.   
Przykład tego formatu odpowiadający temu powyżej:   
6   
5   
1   
0   
0   
2   
1   
1 2   
2   
0   
0 2   
2   
3   
3 4   
2   
2   
1 2   
2   
4   
0 4   
0 2 3 2 5 1 1 4   
