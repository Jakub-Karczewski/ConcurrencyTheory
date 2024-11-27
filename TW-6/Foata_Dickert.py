from collections import deque
from enum import Enum

no_lines = int(input())
appeared = [-1] * 26
vert_to_char = [-1] * 26

D = [[0 for _ in range(no_lines)] for _ in range(no_lines)]
for i in range(no_lines):
    D[i][i] = 1


class char(Enum):
    VAR = 1
    SIGN = 2


prod = [[[], []] for _ in range(26)]

global count
count = 0


def process_string(res, prod1, appeared1, vert_to_char1):
    global count

    first_char = ord(res[0]) - ord('a')
    if appeared1[first_char] == -1:
        appeared1[first_char] = count
        vert_to_char1[count] = first_char
        prod1[count][0].append(i)
        count += 1
    else:
        prod1[appeared1[first_char]][0].append(i)

    ind = 4
    state = char.VAR
    signs = ['+', '-', '*', '/', '^', '%']

    while ind < len(res):
        while ind < len(res) and (res[ind] > 'z' or res[ind] < 'a'):
            if res[ind] in signs:
                if state == char.VAR:
                    raise Exception("Bledne dane")
                state = char.VAR
            elif '0' <= res[ind] <= '9':
                if state == char.SIGN:
                    raise Exception("Bledne dane")
                while ind < len(res) and '0' <= res[ind] <= '9':
                    ind += 1
                state = char.SIGN
                continue
            ind += 1

        if ind < len(res):
            if state == char.SIGN:
                raise Exception("Bledne dane")
            other_char = ord(res[ind]) - ord('a')
            if appeared1[other_char] == -1:
                appeared1[other_char] = count
                prod1[count][1].append(i)
                count += 1
            else:
                prod1[appeared1[other_char]][1].append(i)
            state = char.SIGN
        ind += 1
    if state == char.VAR:
        raise Exception("Bledne dane")


for i in range(no_lines):
    res_i = str(input())
    process_string(res_i, prod, appeared, vert_to_char)

for i in range(count):
    for l in prod[i][0]:
        for r in prod[i][1]:
            D[l][r] = 1
            D[r][l] = 1


def bfs_with_deletion(start_vertex, Graph):
    Q = deque()
    ways = [0] * len(Graph)
    Q.append(start_vertex)
    while len(Q):
        v = Q.popleft()
        ways[v] += 1
        for w, active in Graph[v]:
            if active:
                Q.append(w)

    for j in range(len(Graph[start_vertex])):
        if ways[Graph[start_vertex][j][0]] > 1:
            print(f"Usuwam krawedz {start_vertex} -> {Graph[start_vertex][j][0]}")
            Graph[start_vertex][j][1] = False


vertexes = []
ver_list = str(input())

print("Dependencies:")
print(D)

m = len(ver_list)
inner_edges = [0] * m

Foata = [[] for _ in range(m)]
in_class = [0] * m

G = [[] for _ in range(m)]

for x in ver_list:
    vertexes.append(ord(x) - ord('a'))
for i in range(m - 1):
    for j in range(i + 1, m):
        if D[vertexes[i]][vertexes[j]]:
            G[i].append([j, True])

for i in range(m - 1):
    bfs_with_deletion(i, G)

for i in range(m):
    for x, visit in G[i]:
        if visit:
            inner_edges[x] += 1;
            print(f"{ver_list[i]} -> {ver_list[x]}")

for k in range(m):
    Fi = []
    found = 0
    for i in range(m):
        if not in_class[i] and inner_edges[i] == 0:
            found = 1
            in_class[i] = k + 1
            Foata[k].append(i)

    for i in range(m):
        if in_class[i] == k + 1:
            for x, visit in G[i]:
                if visit:
                    inner_edges[x] -= 1
    if not found:
        break

print("Foata classes:")
for i in range(m):
    if len(Foata[i]):
        Foata[i].sort(key=lambda y: ver_list[y])
        print("[", end=' ')
        for x in Foata[i]:
            print(f"{ver_list[x]}", end=' ')
        print("]", end=' ')
    else:
        break
