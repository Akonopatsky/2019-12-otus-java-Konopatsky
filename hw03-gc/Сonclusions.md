Таблицы и графики представлены в файле [GсСomparу.pdf](GсСomparу.pdf)

Garbage-First (G1) Collector выглядит наиболее предпочтительным вариантом для использования. 
При использовании Garbage-First (G1) Collector:
1)	достигнута лучшая производительность на момент отсечки (примерно 100 сек), так и за время работы программы;
2)	наименьшие максимальные паузы и наименьшее общее время пауз, при большем их количестве;
3)	второе время до остановки работы программы с OutOfMemoryError.
На втором месте Parallel Collector , который проигрывает по производительности и паузам, но имеет следующие преимущества:
1)	приложение немного позже переходит к завершающему этапу;
2)	быстрее завершает работу приложения, 177,5 сек против 191,3;
Таким образом при использовании Parallel Collector минимизируется время когда сборщик мусора занимает все ресурсы и программа не выполняет полезной работы.
