hmo-project
===========

This is a completed project for the university course "Heuristic optimization methods":

* English: http://www.fer.unizg.hr/en/course/hom
* Croatian: http://www.fer.unizg.hr/predmet/hmo

Problem
=======

The problem was a routing problem for 100 consumers and 5 warehouses where each consumer had a set amount of units he wanted.
The goal was to find the lowest price with every consumer being satisfied and visited by only one vehicle.
The Price was calculated using fixed amounts for each warehouse we opened and each vehicle we used along with the distances a vehicle covers.
More details in file https://github.com/Josko/hmo-project/blob/master/HMO-projekt_instanca_problema.txt

Solution
========

This solution used a genethic algorithm to create Hamiltonian paths with lowest costs and then iterate on that.
At the end of the GA we get the individual wit hthe best fitness and do a local search to improve the solution.
Afterwards try all variations of connecting these paths to warehouses and get the one with the lowest score.

Results
=======

Our best result calculated with this program used 25 vehicles as follows:

<warehouse index>: <list of consumers visited>
1:  17 62 77 32
1:  6 96 85 16
1:  0 43 38 75
1:  11 21 83 39
1:  9 1 50 84
0:  33 29 8 91
1:  27 45 35 7 56
4:  34 61 67 37
4:  74 48 72 92
1:  13 26 94 30
4:  5 82 10 70
4:  25 15 44 73
0:  2 55 65 89
4:  19 93 60 63
1:  46 20 40 90
1:  52 98 51 97
4:  23 42 69
1:  31 14 81 64
4:  57 53 4 76
4:  18 99 79 49
4:  24 86 8
4:  22 58 71 3 41
0:  80 68 54 88
4:  12 95 59 66
0:  36 28 47 78

This had a total score of 282638. While the current record is about 274k.

