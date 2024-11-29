Do uruchomienia niezbędne jest zainstalowanie biblioteki Graphvitz, można to zrobić za pomocą pip-a.   
Aby przetestować działanie programu należy uruchomić plik main.ipynb i  wpisać do pliku input.txt dane w podanym na przykładzie formacie:

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
