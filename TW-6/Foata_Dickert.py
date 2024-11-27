from collections import deque

no_lines = int(input())
appeared = [-1] * 26
vert_to_char = [-1] * 26
res = ""

D = [[0 for _ in range(no_lines)] for _ in range(no_lines)]
for i in range(no_lines):
    D[i][i] = 1

prod = [[[], []] for _ in range(26)]
count = 0
for i in range(no_lines):
    res_i = str(input())
    #res += res_i
    first_char = ord(res_i[0]) - ord('a')
    if appeared[first_char] == -1:
        appeared[first_char] = count
        vert_to_char[count] = first_char
        prod[count][0].append(i)
        count += 1
    else:
        prod[appeared[first_char]][0].append(i)

    ind = 1
    while ind < len(res_i):
        while ind < len(res_i) and (res_i[ind] > 'z' or res_i[ind] < 'a'):
            ind += 1
        if ind < len(res_i):
            other_char = ord(res_i[ind]) - ord('a')
            if appeared[other_char] == -1:
                appeared[other_char] = count
                prod[count][1].append(i)
                count += 1
            else:
                prod[appeared[other_char]][1].append(i)
        ind += 1

for i in range(count):
    for l in prod[i][0]:
        for r in prod[i][1]:
            D[l][r] = 1
            D[r][l] = 1
print(D)


def BFS_with_deletion(start_vertex, Graph):
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


#print(res)

print("Podaj slowo:")
vertexes = []
ver_list = str(input())
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

for i in range(m-1):
    BFS_with_deletion(i, G)

for i in range(m):
    for x, visit in G[i]:
        if visit:
            inner_edges[x] += 1;
            print(f"{i+1} -> {x+1}")

print("Inner edges:")
print(inner_edges)

for k in range(m):
    Fi = []
    found = 0
    #print(inner_edges)
    for i in range(m):
        if not in_class[i] and inner_edges[i] == 0:
            found = 1
            in_class[i] = k+1
            Foata[k].append(i)

    for i in range(m):
        if in_class[i] == k+1:
            for x, visit in G[i]:
                if visit:
                    inner_edges[x] -= 1
    if not found:
        break

print("Foata classes:")
for i in range(m):
    if len(Foata[i]):
        print(f"{Foata[i]}", end=' ')
    else:
        break

#print(vertexes)
